package assign1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.PriorityQueue;
import java.util.Vector;

import assign1.HufmannEncoderDecoder.Node;

/**
 * Assignment 1
 * Submitted by: 
 * Student 1. 	ID# XXXXXXXXX
 * Student 1. 	ID# XXXXXXXXX
 */
//Uncomment if you wish to use FileOutputStream and FileInputStream for file access.
//import java.io.FileOutputStream;
//import java.io.FileInputStream;

public class HufmannEnglishEnDe extends HufmannEncoderDecoder
{
	class Node implements Comparable<Node>{
		char[] ch=new char[2];
		int freq;
		boolean[] code;
		Node right=null;
		Node left=null;
		public Node(char _ch, int _freq){
			ch[0]=_ch;
			freq=_freq;
		}
		public Node(char[] _ch, int _freq){
			ch[0]=_ch[0];
			ch[1]=_ch[1];
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
	public HufmannEnglishEnDe()
	{
		// TODO Auto-generated constructor stub
	}
	public void Compress(String[] input_names, String[] output_names) throws IOException
	{
		int [][]  freq=new int [256][257];
		File file=new File(input_names[0]);
		InputStream input = new FileInputStream(file);
		File file2=new File(output_names[0]);
	    OutputStream out=new FileOutputStream(file2);
		byte[] Bytes = new byte[(int)file.length()];
        input.read(Bytes, 0, Bytes.length);
        for(int i=0;i<Bytes.length;i++){
        	freq[ByteToInt(Bytes[i])][256]++;
        	if(i!=Bytes.length-1)
        		freq[ByteToInt(Bytes[i])][ByteToInt(Bytes[i+1])]++;
        }
        Node root=buildTree(freq,Bytes.length);
        SetCode(root,"");
        Node[][] nodes=new Node[256][257];
        fillNodes(root,nodes);
        String StringTree= RootToString(root);
        BitSet bits=new BitSet();
        byte[] ByteTree = ByteFromString(StringTree);
        int k=0,i=0;//index for BitSet
        while(i<Bytes.length){
        	if(i>=Bytes.length-1){
        		for(int j=0;j<nodes[ByteToInt(Bytes[i])][256].code.length;j++){
             		if(nodes[ByteToInt(Bytes[i])][256].code[j])
             			bits.set(k);
             	k++;
        	}
        		 i++;
        	}
        	else if(nodes[ByteToInt(Bytes[i])][ByteToInt(Bytes[i+1])]!=null){
        		
           		 for(int j=0;j<nodes[ByteToInt(Bytes[i])][ByteToInt(Bytes[i+1])].code.length;j++){
                 		if(nodes[ByteToInt(Bytes[i])][ByteToInt(Bytes[i+1])].code[j])
                 			bits.set(k);
                 	k++;
           	 }
           		 i=i+2;
           	 }
        		else{
        		 for(int j=0;j<nodes[ByteToInt(Bytes[i])][256].code.length;j++){
             		if(nodes[ByteToInt(Bytes[i])][256].code[j])
             			bits.set(k);
             	k++;
        	}
        		 i++;
        	 }
        	
      }
        //print(root);
        byte[] ByteCode = bits.toByteArray();
       	int x=k-bits.length();
       	byte ByteZeroEnd=(byte)x;//count zero lest
       	out.write(ByteZeroEnd);
       	out.write(ByteTree);
       	out.write(ByteCode);
       	out.close();
       	input.close();
	}
	private String RootToString(Node root) {
		if(root.left==null&&root.right==null){
			String str=Integer.toBinaryString(root.ch[0]) ;
			while(str.length()!=8)
				str="0"+str;
			String str2=Integer.toBinaryString(root.ch[1]) ;
			while(str2.length()!=8)
				str2="0"+str2;
			return "0"+str+str2 ;
		}
		return "1"+RootToString(root.left)+RootToString(root.right);
	}
	private void fillNodes(Node root, Node[][] nodes) {
		if(root.right==null&&root.left==null){
			if(root.ch[1]==0)
			nodes[(int)root.ch[0]][256]=root;
			else
			nodes[(int)root.ch[0]][(int)root.ch[1]]=root;
			return;
		}
		fillNodes(root.left, nodes);
		fillNodes(root.right, nodes);
	}
	
	public void print(Node root){
		if(root.right==null&&root.left==null){
			for(int i=0;i<root.code.length;i++)
				System.out.print(root.code[i]);
			 System.out.println(" is the char="+root.ch[0]+root.ch[1]+" freq="+root.freq);
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
	private Node buildTree(int[][] freq,int len) {
		//int k=0;
		PriorityQueue<Node> pq=new PriorityQueue<Node>();
		for(int i=0;i<256;i++){
			if(freq[i][256]!=0){
				Node node=new Node((char)i,freq[i][256]);
				pq.add(node);
				for(int j=0;j<256;j++){
					if(freq[i][j]>len*0.003){
						//k++;
						char[] a={(char) i,(char) j};
						node=new Node(a,freq[i][j]);
						pq.add(node);
					}
				}
			}
			
		}
		//System.out.println(k);
		while(pq.size()>1){
			Node right=pq.poll();
			Node left=pq.poll();
			pq.add(new Node(right,left));
		}
		
	return pq.poll();
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
        //print(root);
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
	private Vector<Byte> TreeToByte(BitSet ls, Node root, int[] k,int x) {
		Node p=root;
		Vector<Byte> lb=new Vector<Byte>();
		while(k[0]-1<ls.length()+x){
			if(p.left==null&&p.right==null){
				if(p.ch[1]!=0){
				lb.add((byte)p.ch[0]);
				lb.add((byte)p.ch[1]);
				p=root;
				}
			else{
				lb.add((byte)p.ch[0]);
				p=root;
			}
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
	private void BuildTree(Node root, BitSet bits, int[] k) {
		if(!bits.get(k[0])){//zero
			k[0]++;
			int ret=0,j=7;
			for(int i=0;i<8;i++){//calculate the char ASCII
				if(bits.get(k[0]))
				ret=(int) (ret+Math.pow(2,j));
				j--;
				k[0]++;
			}
			root.ch[0]=(char)ret;
			ret=0;
			j=7;
			for(int i=0;i<8;i++){//calculate the char ASCII
				if(bits.get(k[0]))
				ret=(int) (ret+Math.pow(2,j));
				j--;
				k[0]++;
			}
			root.ch[1]=(char)ret;
			return;
		}
			if(bits.get(k[0]))
				k[0]++;
				root.left=new Node('\0',0);
				root.right=new Node('\0',0);
				BuildTree(root.left, bits,k);
				BuildTree(root.right, bits,k);
	}
}
