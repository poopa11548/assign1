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
	static String[] SpecialWord={ " I "," a " , " about " ," after ", " all ",  " also " ,
		 " an ", " and " , " any ", " as " , " at " , " back ", " be ",
		" because ", " but ", " by ", " can ", " come ", " could ",
		 " day ", " do ", " even ", " first "," for ", " from ",
		 " get ", " give ", " go ", " good ", " have ",
		" he ", " her ", " him ", " his ", " how ", " if ",
		 " in ", " into ", " it ", " its "," just "," know ", " like ", " look ",
		 " make ", " me ", " most ", " my ", " new ", " no ", " not ", " now ", " of ",
		 " on ", " one "," only ", " or ", " other ", " our ", " out ", " over ", " people ",
		  " say " , " see ", " she ", " so ", " some ", " take "," than ", " that ",
		 " the ", " their ", " them "," then ", " there ", " these ", " they ", " think ",
		 " this " , " time ",  " to " , " two ", " up ", " us ", " use ", " want ", " way ",
		 " we ", " well "," what "," when ", " which ", " who ", " will ", " with ",
		 " work " , " would ", " year ", " you ", " your "};
	class Node implements Comparable<Node>{
		char ch;
		int freq;
		String Special;
		boolean[] code;
		Node right=null;
		Node left=null;
		public Node(char _ch, int _freq){
			ch=_ch;
			freq=_freq;
		}
		public Node(String _Special,int _freq){
			Special=_Special;
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
		byte[] b=CompressWithArray(input_names, output_names);
		/*int []  freq=new int [256];
		File file=new File(input_names[0]);
		InputStream input = new FileInputStream(file);
		File file2=new File(output_names[0]);
	    OutputStream out=new FileOutputStream(file2);
		byte[] Bytes = new byte[(int)file.length()];
        input.read(Bytes, 0, Bytes.length);
        for(int i=0;i<Bytes.length;i++){
        	freq[ByteToInt(Bytes[i])]++;
        }
        Node rootfreq=buildTree(freq,Bytes.length);
        String StringTree= RootToString(rootfreq);
        Node SpecialRoot=BuildSpecialTree();
        Node root=buildFinshTree(rootfreq,SpecialRoot);
        SetCode(root,"");
        Node[] nodes=new Node[256];
        Node[] SpecialNode=new Node[SpecialWord.length];
        fillNodes(root,nodes,SpecialNode);
        BitSet bits=new BitSet();
        byte[] ByteTree = ByteFromString(StringTree);
       int k=0,i=0;//index for BitSet
        while(i<Bytes.length){
        	if(Bytes[i]!=' '){
        		for(int j=0;j<nodes[ByteToInt(Bytes[i])].code.length;j++){
             		if(nodes[ByteToInt(Bytes[i])].code[j])
             			bits.set(k);
             	k++;
        	}
        		 i++;
        	}
        	else{
        	   int key=checkWord(Bytes,i,0,SpecialWord.length);
        		if(key!=SpecialWord.length){
        			for(int j=0;j<SpecialNode[key].code.length;j++){
        				if(SpecialNode[key].code[j])
        					bits.set(k);
        				k++;
        			}
        			i=i+SpecialWord[key].length();
        		}
        	
        		else{
        		 for(int j=0;j<nodes[ByteToInt(Bytes[i])].code.length;j++){
             		if(nodes[ByteToInt(Bytes[i])].code[j])
             			bits.set(k);
             	k++;
        	}
        		 i++;
        	 }
        	}
      }
        bits.set(k);
        byte[] ByteCode = bits.toByteArray();
       	out.write(ByteTree);
       	out.write(ByteCode);
       	out.close();
       	input.close();*/
	}
	private Node buildFinshTree(Node rootfreq, Node specialRoot) {
		Node temp=rootfreq;
		while(temp.left.left!=null)
			temp=temp.left;
	Node node=new Node(temp.left,specialRoot);
	temp.left=node;
	return rootfreq;
	}
	public int checkWord(byte[] bytes,int i,int left,int right){
		if(left>right)
			return SpecialWord.length;
		int mid=(left+right)/2;
		if(mid==-1||mid==SpecialWord.length)
			return SpecialWord.length;
		int key=checkWord(bytes,i, SpecialWord[mid]);
		if(key==2)
			return SpecialWord.length;
		else if(key==1)
			return checkWord(bytes,i, mid+1, right);
		else if(key==-1){
			return checkWord(bytes,i, left, mid-1);
		}
		else{
		return mid;
		}
	}
	public int checkWord(byte[] bytes,int i,String word){
		for(int j=0;j<word.length();j++){
			if(i+j==bytes.length)
				return 2;
			else if(ByteToInt(bytes[j+i])<word.charAt(j))
				return -1;
			else if(ByteToInt(bytes[j+i])>word.charAt(j))
				return 1;
			
		}
		return 0;
	}
	private String RootToString(Node root) {
		if(root.left==null&&root.right==null){
			String str=Integer.toBinaryString(root.ch) ;
			while(str.length()!=8)
				str="0"+str;
			return "0"+str ;
		}
		return "1"+RootToString(root.left)+RootToString(root.right);
	}
	private void fillNodes(Node root, Node[] nodes,Node[] Special) {
		if(root.right==null&&root.left==null){
			if(root.Special!=null){
				Special[root.freq]=root;
			}
			else 
			nodes[(int)root.ch]=root;
			
			return;
		}
		fillNodes(root.left, nodes,Special);
		fillNodes(root.right, nodes,Special);
	}
	
	public void print(Node root){
		if(root.right==null&&root.left==null){
			for(int i=0;i<root.code.length;i++)
				System.out.print(root.code[i]);
			if(root.ch=='\0')
				System.out.println(root.Special);
			else
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
	public Node BuildSpecialTree(){
		PriorityQueue<Node> pq=new PriorityQueue<Node>();
		for(int i=0;i<SpecialWord.length;i++){
			Node node=new Node(SpecialWord[i],i);
			pq.add(node);
		}
		while(pq.size()>1){
			Node right=pq.poll();
			Node left=pq.poll();
			pq.add(new Node(right,left));
		}
		
	return pq.poll();
	}
	public Node buildTree(int[] freq,int len) {
		PriorityQueue<Node> pq=new PriorityQueue<Node>();
		for(int i=0;i<256;i++){
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
	public void Decompress(String[] input_names, String[] output_names) throws IOException
	{
		byte[] b=DecompressWithArray(input_names, output_names);
		/*File file=new File(input_names[0]);
		InputStream finput = new FileInputStream(file);
		byte[] Bytes = new byte[(int)file.length()];
        finput.read(Bytes, 0, Bytes.length);
        //int ZeroLast=(int)Bytes[0];
        BitSet bits=new BitSet();
        bits=BitSet.valueOf(Bytes);
        Node rootfreq=new Node('\0', 0);
        int[] k=new int[2];//k[0] index
        k[0]=0;// first is The amount of zeros at the last.
        BuildTree(rootfreq, bits, k);
        Node SpecialRoot=BuildSpecialTree();
        Node root=buildFinshTree(rootfreq,SpecialRoot);
        int size=k[0]%8;
        for(int i=0;i<8-size;i++)//complete to byte
        	k[0]++;
        //print(root);
        Vector<Byte> b=TreeToByte(bits,root,k);
        File file2=new File(output_names[0]);
        OutputStream out=new FileOutputStream(file2);
        byte[] ByteToSend=new byte[b.size()];
        for(int i=0;i<b.size();i++)
        ByteToSend[i]=b.get(i);
        out.write(ByteToSend);
 		out.close();
 		finput.close();*/   
	}
	private Vector<Byte> TreeToByte(BitSet ls, Node root, int[] k) {
		Node p=root;
		Vector<Byte> lb=new Vector<Byte>();
		while(k[0]-1<ls.length()-1){
			if(p.left==null&&p.right==null){
				if(p.Special!=null){
				for(int i=0;i<p.Special.length();i++)
					lb.add((byte)p.Special.charAt(i));
				p=root;
				}
				
			else{
				lb.add((byte)p.ch);
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
	public byte[] CompressWithArray(String[] input_names, String[] output_names) throws IOException{
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
        Node rootfreq=buildTree(freq,Bytes.length);
        String StringTree= RootToString(rootfreq);
        Node SpecialRoot=BuildSpecialTree();
        Node root=buildFinshTree(rootfreq,SpecialRoot);
        SetCode(root,"");
        Node[] nodes=new Node[256];
        Node[] SpecialNode=new Node[SpecialWord.length];
        fillNodes(root,nodes,SpecialNode);
        BitSet bits=new BitSet();
        byte[] ByteTree = ByteFromString(StringTree);
       int k=0,i=0;//index for BitSet
        while(i<Bytes.length){
        	if(Bytes[i]!=' '){
        		for(int j=0;j<nodes[ByteToInt(Bytes[i])].code.length;j++){
             		if(nodes[ByteToInt(Bytes[i])].code[j])
             			bits.set(k);
             	k++;
        	}
        		 i++;
        	}
        	else{
        	   int key=checkWord(Bytes,i,0,SpecialWord.length);
        		if(key!=SpecialWord.length){
        			for(int j=0;j<SpecialNode[key].code.length;j++){
        				if(SpecialNode[key].code[j])
        					bits.set(k);
        				k++;
        			}
        			i=i+SpecialWord[key].length();
        		}
        	
        		else{
        		 for(int j=0;j<nodes[ByteToInt(Bytes[i])].code.length;j++){
             		if(nodes[ByteToInt(Bytes[i])].code[j])
             			bits.set(k);
             	k++;
        	}
        		 i++;
        	 }
        	}
      }
        bits.set(k);//one of last
        byte[] ByteCode = bits.toByteArray();
    	byte[] ByteToSend=new byte[ByteTree.length+ByteCode.length];
        	for(int j=0;j<ByteTree.length;j++)
        		ByteToSend[j]=ByteTree[j];
        	for(int j=0;j<ByteCode.length;j++)
        		ByteToSend[j+ByteTree.length]=ByteCode[j];
     	out.write(ByteToSend);
        	out.close();
        	input.close();
 		return ByteToSend;
		
	}
	public byte[] DecompressWithArray(String[] input_names, String[] output_names) throws IOException{
		File file=new File(input_names[0]);
		InputStream finput = new FileInputStream(file);
		byte[] Bytes = new byte[(int)file.length()];
        finput.read(Bytes, 0, Bytes.length);
        BitSet bits=new BitSet();
        bits=BitSet.valueOf(Bytes);
        Node rootfreq=new Node('\0', 0);
        int[] k=new int[2];//k[0] index
        k[0]=0;// first is The amount of zeros at the last.
        BuildTree(rootfreq, bits, k);
        Node SpecialRoot=BuildSpecialTree();
        Node root=buildFinshTree(rootfreq,SpecialRoot);
        int size=k[0]%8;
        for(int i=0;i<8-size;i++)//complete to byte
        	k[0]++;
        Vector<Byte> b=TreeToByte(bits,root,k);
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
