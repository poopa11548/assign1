
package assign1;

/**
 * Assignment 1
 * Submitted by: 
 * Student 1. 	ID# XXXXXXXXX
 * Student 1. 	ID# XXXXXXXXX
 */

// Uncomment if you wish to use FileOutputStream and FileInputStream for file access.
//import java.io.FileOutputStream;
//import java.io.FileInputStream;


import base.compressor;









import java.io.*;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;


public class HufmannEncoderDecoder implements compressor
{
class Node implements Comparable<Node>{
	char ch;
	int freq;
	boolean[] code;
	Node right=null;
	Node left=null;
	public Node(char _ch, int _freq){
		ch=_ch;
		freq=_freq;
	}
	public void setcode(String s){
		code=new boolean[s.length()];
		for(int i=0;i<s.length();i++)
			if(s.charAt(i)=='1')
			  code[i]=true;
			else
			  code[i]=false;
	}
	public Node(Node _left,Node _right){
		left=_left;
		right=_right;
		freq=_left.freq+_right.freq;
		ch='\0';
	}
	public void SetCode(String s){
		code=new boolean[s.length()];
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)=='1')
				code[i]=true;
			else
				code[i]=false;
		}
	}
	@Override
	public int compareTo(Node node) {
		return freq-node.freq;
	}
}
	public HufmannEncoderDecoder()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Compress(String[] input_names, String[] output_names) throws IOException
	{
		int []  freq=new int [256];
		File file=new File(input_names[0]);
		InputStream input = new FileInputStream(file);
		File file2=new File(output_names[0]);
	    OutputStream out=new FileOutputStream(file2);
		byte[] Bytes = new byte[(int)file.length()];
        input.read(Bytes, 0, Bytes.length);
        for(int i=0;i<Bytes.length;i++){
        	freq[ByteToInt(Bytes[i])]++;
        }
        Node root=buildTree(freq);
        SetCode(root,"");
        Node[] nodes=new Node[256];
        fillNodes(root,nodes);
        String StringTree= RootToString(root);
        BitSet bits=new BitSet();
        byte[] ByteTree = ByteFromString(StringTree);
    	int k=0;//index for BitSet
        for(int i=0;i<Bytes.length;i++){
        	for(int j=0;j<nodes[ByteToInt(Bytes[i])].code.length;j++){
        		if(nodes[ByteToInt(Bytes[i])].code[j])
        			bits.set(k);
        	k++;
        	}
        }
        byte[] ByteCode = bits.toByteArray();
       	int x=k-bits.length();
       	byte ByteZeroEnd=(byte)x;//count zero lest
       	out.write(ByteZeroEnd);
       	out.write(ByteTree);
       	out.write(ByteCode);
       	out.close();
       	input.close();
	}
	public String RootToString(Node root){
		if(root.left==null&&root.right==null){
			String str=Integer.toBinaryString(root.ch) ;
			while(str.length()!=8)
				str="0"+str;
			return "0"+str;
		}
		return "1"+RootToString(root.left)+RootToString(root.right);
	}
	public byte[] ByteFromString(String s){
		int len;
		if(s.length()%8==0)
			len=s.length()/8;
		else
			len=s.length()/8+1;
		BitSet f=new BitSet();
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)=='1')
				f.set(i);
		}	
		byte[] b=f.toByteArray();
			if(b.length!=len){
				byte[] d=new byte[len];
				for(int i=0;i<b.length;i++)
					d[i]=b[i];
				d[len-1]=(byte)0;
				b=d;
			}
		return b;
	}
	public void fillNodes(Node root,Node[] nodes){
		if(root.right==null&&root.left==null){
			nodes[(int)root.ch]=root;
			return;
		}
		fillNodes(root.left, nodes);
		fillNodes(root.right, nodes);
	}
	public void print(Node root){
		if(root.right==null&&root.left==null){
			for(int i=0;i<root.code.length;i++)
				System.out.print(root.code[i]);
			 System.out.println(" is the char="+root.ch+" freq="+root.freq);
			 return;
		}
			print(root.left);
			print(root.right);
	}
	public void SetCode(Node root,String s){
		if(root.right==null&&root.left==null){
			root.setcode(s);
			return;
		}
		SetCode(root.right,s +'0');
		SetCode(root.left,s+'1');
	}
	public Node buildTree(int [] freq){
		PriorityQueue<Node> pq=new PriorityQueue<Node>();
		for(int i=0;i<freq.length;i++){
			if(freq[i]!=0){
				Node node=new Node((char)i,freq[i]);
				pq.add(node);
			}
		}
			while(pq.size()>1){
				Node right=pq.poll();
				Node left=pq.poll();
				pq.add(new Node(right,left));
			}
			
		return pq.poll();
	}
	public int ByteToInt(byte b){
		int ret=0;
		BitSet set = BitSet.valueOf(new byte[] { b });
		for(int i=0;i<8;i++){
			if(set.get(i))
			ret=(int) (ret+Math.pow(2, i));
		}
		return ret;
	}
	public void Decompress(String[] input_names, String[] output_names) throws IOException
	{
		File file=new File(input_names[0]);
		InputStream finput = new FileInputStream(file);
		byte[] Bytes = new byte[(int)file.length()];
        finput.read(Bytes, 0, Bytes.length);
        int ZeroLast=(int)Bytes[0];
        BitSet bits=new BitSet();
        bits=BitSet.valueOf(Bytes);
        Node root=new Node('\0', 0);
        int[] k=new int[2];//k[0] index
        k[0]=8;//8 bits first is The amount of zeros at the last.
        BuildTree(root, bits, k);
        int size=k[0]%8;
        for(int i=0;i<8-size;i++)//complete to byte
        	k[0]++;
        Vector<Byte> b=TreeToByte(bits,root,k,ZeroLast);
        File file2=new File(output_names[0]);
        OutputStream out=new FileOutputStream(file2);
        byte[] ByteToSend=new byte[b.size()];
        for(int i=0;i<b.size();i++)
        ByteToSend[i]=b.get(i);
        out.write(ByteToSend);
 		out.close();
 		finput.close();
	}
	public  Vector<Byte> TreeToByte(BitSet ls,Node root,int[] k ,int x){
		Node p=root;
		Vector<Byte> lb=new Vector<Byte>();
		while(k[0]-1<ls.length()+x){
			if(p.left==null&&p.right==null){
				lb.add((byte)p.ch);
				p=root;
				}
			else if(ls.get(k[0])){
				p=p.left;
			k[0]++;
			}
			else{
				p=p.right;
				k[0]++;
			}
		}
		return lb;
	}
	public void BuildTree(Node root,BitSet bits,int[] k){
		if(!bits.get(k[0])){//zero
			k[0]++;
			int ret=0,j=7;
			for(int i=0;i<8;i++){//calculate the char ASCII
				if(bits.get(k[0]))
				ret=(int) (ret+Math.pow(2,j));
				j--;
				k[0]++;
			}
			root.ch=(char)ret;
			return;
		}
		if(bits.get(k[0]))
		k[0]++;
		root.left=new Node('\0',0);
		root.right=new Node('\0',0);
		BuildTree(root.left, bits,k);
		BuildTree(root.right, bits,k);
		}
	public byte[] CompressWithArray(String[] input_names, String[] output_names) throws IOException
	{
		int []  freq=new int [256];
		File file=new File(input_names[0]);
		InputStream input = new FileInputStream(file);
		File file2=new File(output_names[0]);
	    OutputStream out=new FileOutputStream(file2);
		byte[] Bytes = new byte[(int)file.length()];
        input.read(Bytes, 0, Bytes.length);
        for(int i=0;i<Bytes.length;i++){
        	freq[ByteToInt(Bytes[i])]++;
        }
        Node root=buildTree(freq);
        SetCode(root,"");
        Node[] nodes=new Node[256];
        fillNodes(root,nodes);
        String StringTree= RootToString(root);
        BitSet bits=new BitSet();
        byte[] ByteTree = ByteFromString(StringTree);
    	int k=0;//index for BitSet
        for(int i=0;i<Bytes.length;i++){
        	for(int j=0;j<nodes[ByteToInt(Bytes[i])].code.length;j++){
        		if(nodes[ByteToInt(Bytes[i])].code[j])
        			bits.set(k);
        	k++;
        	}
        }
        byte[] ByteCode = bits.toByteArray();
       	int x=k-bits.length();
       	byte ByteZeroEnd=(byte)x;//count zero lest
       	byte[] ByteToSend=new byte[ByteTree.length+ByteCode.length+1];
       	ByteToSend[0]=ByteZeroEnd;
       	for(int i=0;i<ByteTree.length;i++)
       		ByteToSend[i+1]=ByteTree[i];
       	for(int i=0;i<ByteCode.length;i++)
       		ByteToSend[i+ByteTree.length+1]=ByteCode[i];
    	out.write(ByteToSend);
       	out.close();
       	input.close();
		return ByteToSend;
	}

	@Override
	public byte[] DecompressWithArray(String[] input_names, String[] output_names) throws IOException
	{
		File file=new File(input_names[0]);
		InputStream finput = new FileInputStream(file);
		byte[] Bytes = new byte[(int)file.length()];
        finput.read(Bytes, 0, Bytes.length);
        int ZeroLast=(int)Bytes[0];
        BitSet bits=new BitSet();
        bits=BitSet.valueOf(Bytes);
        Node root=new Node('\0', 0);
        int[] k=new int[2];//k[0] index
        k[0]=8;//8 bits first is The amount of zeros at the last.
        BuildTree(root, bits, k);
        int size=k[0]%8;
        for(int i=0;i<8-size;i++)//complete to byte
        	k[0]++;
        Vector<Byte> b=TreeToByte(bits,root,k,ZeroLast);
        File file2=new File(output_names[0]);
        OutputStream out=new FileOutputStream(file2);
        byte[] ByteToSend=new byte[b.size()];
        for(int i=0;i<b.size();i++)
        ByteToSend[i]=b.get(i);
        out.write(ByteToSend);
 		out.close();
 		finput.close();
		return ByteToSend;
	}

}