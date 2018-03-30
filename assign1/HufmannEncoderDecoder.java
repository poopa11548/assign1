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

public class HufmannEncoderDecoder implements compressor {
	class NodeBits {
		boolean bit;
		NodeBits next = null;
		
		public NodeBits(boolean b) {
			bit = b;
		}
		
	}
	
	class ArrayListBit {
		NodeBits Head;
		NodeBits Tail;
		
		public ArrayListBit() {
			Head = Tail = null;
		}
		
		public boolean IsEmpty() {
			if (Head == null)
				return true;
			else
				return false;
		}
		
		public void addfirst(boolean b) {
			if (IsEmpty()) {
				Head = new NodeBits(b);
				Tail = Head;
			} else {
				NodeBits tmp = Head;
				Head = new NodeBits(b);
				Head.next = tmp;
			}
		}
		
		public void print() {
			NodeBits tmp = Head;
			while (tmp != null) {
				if (tmp.bit)
					System.out.print(1);
				else
					System.out.print(0);
				tmp = tmp.next;
			}
		}
	}
	
	class Node implements Comparable<Node> {
		char ch;
		int freq;
		boolean[] code;
		Node right = null;
		Node left = null;
		
		public Node(char _ch, int _freq) {
			ch = _ch;
			freq = _freq;
		}
		
		public void setcode(String s) {
			code = new boolean[s.length()];
			for (int i = 0; i < s.length(); i++)
				if (s.charAt(i) == '1')
					code[i] = true;
				else
					code[i] = false;
		}
		
		public Node(Node _left, Node _right) {
			left = _left;
			right = _right;
			freq = _left.freq + _right.freq;
			ch = '\0';
		}
		
		public void SetCode(String s) {
			code = new boolean[s.length()];
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == '1')
					code[i] = true;
				else
					code[i] = false;
			}
		}
		
		@Override
		public int compareTo(Node node) {
			return freq - node.freq;
		}
	}
	
	public HufmannEncoderDecoder() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void Compress(String[] input_names, String[] output_names)  {
		try {
			int[] freq = new int[256];
			File file = new File(input_names[0]);
			InputStream finput = new FileInputStream(file);
			byte[] Bytes = new byte[(int) file.length()];
			finput.read(Bytes, 0, Bytes.length);
			for (int i = 0; i < Bytes.length; i++) {
				freq[ByteToInt(Bytes[i])]++;
			}
			File file2 = new File(output_names[0]);
			OutputStream out = new FileOutputStream(file2);
			Node root = buildTree(freq);
			SetCode(root, "");
			Node[] nodes = new Node[256];
			fillNodes(root, nodes);
			String sg = RootToVec(root);
			char[] ch = new char[sg.length()];
			for (int i = 0; i < sg.length(); i++)
				ch[i] = sg.charAt(i);
			BitSet bits = new BitSet();
			byte[] bytes = ByteFromChar(ch);
			Vector<Boolean> vec = new Vector<Boolean>();
			int k = 0;
			for (int i = 0; i < Bytes.length; i++) {
				for (int j = 0; j < nodes[ByteToInt(Bytes[i])].code.length; j++) {
					//vec.add(nodes[ByteToInt(Bytes[i])].code[j]);
					if (nodes[ByteToInt(Bytes[i])].code[j])
						bits.set(k);
					k++;
				}
			}
			byte[] b = bits.toByteArray();
			BitSet sss = new BitSet();
			sss = BitSet.valueOf(b);
			int x = bits.length() - sss.length();
			byte _byte = (byte) x;//count zero lest
			out.write(_byte);
			out.write(bytes);
			out.write(b);
			out.close();
			finput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String RootToVec(Node root) {
		if (root.left == null && root.right == null) {
			String str = Integer.toBinaryString(root.ch);
			while (str.length() != 8)
				str = "0" + str;
			return "0" + str;
		}
		return "1" + RootToVec(root.left) + RootToVec(root.right);
	}
	
	public byte[] ByteFromVec(Vector<Boolean> v) {
		int len;
		if (v.size() % 8 == 0)
			len = v.size() / 8;
		else
			len = v.size() / 8 + 1;
		byte[] b;
		BitSet f = new BitSet();
		for (int i = 0; i < v.size(); i++) {
			if (v.get(i))
				f.set(i);
			else
				f.clear(i);
		}
		b = f.toByteArray();
		if (b.length != len) {
			byte[] d = new byte[len];
			for (int i = 0; i < b.length; i++)
				d[i] = b[i];
			d[len - 1] = (byte) 0;
			b = d;
		}
		return b;
	}
	
	public byte[] ByteFromChar(char[] v) {
		int len;
		if (v.length % 8 == 0)
			len = v.length / 8;
		else
			len = v.length / 8 + 1;
		byte[] b;
		BitSet f = new BitSet();
		for (int i = 0; i < v.length; i++) {
			if (v[i] == '1')
				f.set(i);
			else
				f.clear(i);
		}
		b = f.toByteArray();
		if (b.length != len) {
			byte[] d = new byte[len];
			for (int i = 0; i < b.length; i++)
				d[i] = b[i];
			d[len - 1] = (byte) 0;
			b = d;
		}
		return b;
	}
	
	public void fillNodes(Node root, Node[] nodes) {
		if (root.right == null && root.left == null) {
			nodes[(int) root.ch] = root;
			return;
		}
		fillNodes(root.left, nodes);
		fillNodes(root.right, nodes);
	}
	
	public void print(Node root) {
		if (root.right == null && root.left == null) {
			for (int i = 0; i < root.code.length; i++)
				System.out.print(root.code[i]);
			System.out.println(" is the char=" + root.ch + " freq=" + root.freq);
			return;
		}
		print(root.left);
		print(root.right);
	}
	
	public void SetCode(Node root, String s) {
		if (root.right == null && root.left == null) {
			root.setcode(s);
			return;
		}
		SetCode(root.right, s + '0');
		SetCode(root.left, s + '1');
	}
	
	public Node buildTree(int[] freq) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		for (int i = 0; i < freq.length; i++) {
			if (freq[i] != 0) {
				Node node = new Node((char) i, freq[i]);
				pq.add(node);
			}
		}
		while (pq.size() > 1) {
			Node right = pq.poll();
			Node left = pq.poll();
			pq.add(new Node(right, left));
		}
		
		return pq.poll();
		
	}
	
	public int ByteToInt(byte b) {
		int ret = 0;
		BitSet set = BitSet.valueOf(new byte[]{b});
		for (int i = 0; i < 8; i++) {
			if (set.get(i))
				ret = (int) (ret + Math.pow(2, i));
		}
		return ret;
	}
	
	@Override
	public void Decompress(String[] input_names, String[] output_names) {
		try {
			
			File file = new File(input_names[0]);
			InputStream finput = new FileInputStream(file);
			byte[] Bytes = new byte[(int) file.length()];
			finput.read(Bytes, 0, Bytes.length);
			int x = (int) Bytes[0];
			BitSet bits = new BitSet();
			bits = BitSet.valueOf(Bytes);
			Node root = new Node('\0', 0);
			int[] k = new int[2];
			k[0] = 8;
			BuildTree(root, bits, k);
			int size = bits.length();
			size = k[0] % 8;
			for (int i = 0; i < 8 - size; i++)
				k[0]++;
			Vector<Byte> b = LsToByte(bits, root, k, x);
			File file2 = new File(output_names[0]);
			OutputStream out = new FileOutputStream(file2);
			byte[] bb = new byte[b.size()];
			for (int i = 0; i < b.size(); i++)
				bb[i] = b.get(i);
			out.write(bb);
			out.close();
			finput.close();
        
      /*  LinkedList<Boolean> ls=new LinkedList<Boolean>();
        for(int i=8;i<bits.length();i++)
        	ls.add(bits.get(i));
       Node root=new Node('\0',0);
        int size=ls.size();
       BuildTree(root,ls);
       size=(size-ls.size())%8;
       for(int i=0;i<8-size;i++)
    	   ls.poll();
       for(int i=0;i<x;i++)
    	   ls.addLast(false);
      Vector<Byte> b=LsToByte(ls,root);
       File file2=new File(output_names[0]);
       OutputStream out=new FileOutputStream(file2);
       byte[] bb=new byte[b.size()];
       for(int i=0;i<b.size();i++)
    	   bb[i]=b.get(i);
       out.write(bb);
		out.close();
		finput.close();*/
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Vector<Byte> LsToByte(BitSet ls, Node root, int[] k, int x) {
		Node p = root;
		Vector<Byte> lb = new Vector<Byte>();
		while (k[0] - 1 < ls.length() + x) {
			if (p.left == null && p.right == null) {
				lb.add((byte) p.ch);
				p = root;
			} else if (ls.get(k[0])) {
				p = p.left;
				k[0]++;
			} else {
				p = p.right;
				k[0]++;
			}
		}
		return lb;
	}
	
	public Vector<Byte> LsToByte(LinkedList<Boolean> ls, Node root) {
		Node p = root;
		boolean flag = true;
		Vector<Byte> lb = new Vector<Byte>();
		while (flag) {
			if (p.left == null && p.right == null || ls.isEmpty()) {
				lb.add((byte) p.ch);
				p = root;
				if (ls.isEmpty())
					flag = false;
			} else if (ls.poll())
				p = p.left;
			else
				p = p.right;
		}
		
		return lb;
	}
	
	public void BuildTree(Node root, BitSet ls, int[] k) {
		if (!ls.get(k[0])) {
			k[0]++;
			int ret = 0, j = 7;
			for (int i = 0; i < 8; i++) {
				if (ls.get(k[0]))
					ret = (int) (ret + Math.pow(2, j));
				j--;
				k[0]++;
			}
			root.ch = (char) ret;
			return;
		}
		if (ls.get(k[0]))
			k[0]++;
		root.left = new Node('\0', 0);
		root.right = new Node('\0', 0);
		BuildTree(root.left, ls, k);
		BuildTree(root.right, ls, k);
	}
	
	public void BuildTree(Node root, LinkedList<Boolean> ls) {
		if (!ls.getFirst()) {
			ls.pollFirst();
			int ret = 0, j = 7;
			for (int i = 0; i < 8; i++) {
				if (ls.pollFirst())
					ret = (int) (ret + Math.pow(2, j));
				j--;
			}
			root.ch = (char) ret;
			return;
		}
		if (ls.getFirst())
			ls.pollFirst();
		root.left = new Node('\0', 0);
		root.right = new Node('\0', 0);
		BuildTree(root.left, ls);
		BuildTree(root.right, ls);
	}
	
	
	@Override
	public byte[] CompressWithArray(String[] input_names, String[] output_names) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public byte[] DecompressWithArray(String[] input_names, String[] output_names) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
