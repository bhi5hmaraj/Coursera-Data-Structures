import java.io.*;
import java.util.*;

class RopeNaive {
    class FastScanner {
	StringTokenizer tok = new StringTokenizer("");
	BufferedReader in;

	FastScanner(InputStream is) {
	    in = new BufferedReader(new InputStreamReader(is));
	}

	String next() throws IOException {
	    while (!tok.hasMoreElements())
		tok = new StringTokenizer(in.readLine());
	    return tok.nextToken();
	}
	int nextInt() throws IOException {
	    return Integer.parseInt(next());
	}
    }

    class Rope {
	String s;

	void process( int i, int j, int k ) {
	    // Replace this code with a faster implementation
	    String t = s.substring(0, i) + s.substring(j + 1);
	    s = t.substring(0, k) + s.substring(i, j + 1) + t.substring(k);
	}

	String result() {
	    return s;
	}
	Rope( String s ) {
	    this.s = s;
	}
    }
    static String outputFile = "correct_output.txt";
    public static void main( String[] args ) throws IOException {
	new RopeNaive().run();
    }
    /*
    public void run() throws IOException {
	FastScanner in  = new FastScanner(System.in);
	PrintWriter out = new PrintWriter(System.out);
	String str = in.next();
	int N = str.length();
	Rope rope = new Rope(str);
	for (int q = in.nextInt(); q > 0; q--) {
	    int i = in.nextInt();
	    int j = in.nextInt();
	    int k = in.nextInt();
	    if(!(i >= 0 && i <= j && j <= N-1) || !(k >= 0 && k<= (N - (j - i + 1))))
		throw new IllegalArgumentException("i "+i +" j "+j+" k "+k);
	    rope.process(i, j, k);
	}
	out.println(rope.result());
	out.close();
    }
    */
    public void run() throws IOException {
	FastScanner in  = new FastScanner(new FileInputStream("input.txt"));
	PrintWriter out = new PrintWriter(outputFile);
	String str = in.next();
	int N = str.length();
	Rope rope = new Rope(str);
	for (int q = in.nextInt(); q > 0; q--) {
	    int i = in.nextInt();
	    int j = in.nextInt();
	    int k = in.nextInt();
	    if(!(i >= 0 && i <= j && j <= N-1) || !(k >= 0 && k<= (N - (j - i + 1))))
		throw new IllegalArgumentException("i "+i +" j "+j+" k "+k);
	    rope.process(i, j, k);
	}
	out.println(rope.result());
	out.close();
    }
}
