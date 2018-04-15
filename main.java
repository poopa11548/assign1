
package assign1;

import java.io.IOException;
import java.util.Date;

import javax.swing.JApplet;

public class main {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String[] s = new String[2];
		s[0]="c:\\data\\input-text.txt";
		String[] x=new String[2];
		x[0]="c:\\data\\decoded.txt";
		HufmannEncoderDecoder x1=new HufmannEncoderDecoder();
		HufmannEnglishEnDe x2=new HufmannEnglishEnDe();
		long time= System.currentTimeMillis();
		x2.Compress(s, x);
	    //x1.Compress(s, x);
		System.out.println("compress="+(double)(System.currentTimeMillis()-time)/1000);
		long time2= System.currentTimeMillis();
		s[0]="c:\\data\\output-text.txt";
		//x1.Decompress(x, s);
		//byte[] b=x1.CompressWithArray(s,x);
		//s[0]="c:\\data\\output-text.txt";
	    x2.Decompress(x,s);
	    System.out.println("decompress="+(double)(System.currentTimeMillis()-time2)/1000);
	    System.out.println("total="+(double)(System.currentTimeMillis()-time)/1000);
		//b=x1.DecompressWithArray(x,s);
		
	}

}