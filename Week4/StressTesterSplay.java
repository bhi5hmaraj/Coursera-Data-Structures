import java.util.*;
import java.io.*;
import java.security.SecureRandom;
public class StressTesterSplay
{


    /************************ SOLUTION STARTS HERE ***********************/
    static long mod = (long) 1000000001; 
    static long add(long a, long b) {
	return ((a % mod) + (b % mod)) % mod;
    }

    static long sub(long a, long b) {
	long sub = ((a % mod) - (b % mod)) % mod;
	return sub < 0 ? mod + sub : sub;
    }

    static char ops[] = {'+','-','?','s'};
    private static Object getRandom(Set<Long> set)
    {
	return set.toArray()[new SecureRandom().nextInt(set.size())];
    }
    private static void generator(PrintWriter input, PrintWriter out){   //Writes a single instance for a test case

	SecureRandom rand = new SecureRandom();
	TreeSet<Long> set = new TreeSet<>();
	//int q = rand.nextInt(q_MAX) + 1;
	int q = q_MAX;
	input.println(q);
	long x = 0;
	int elem;
	while(q-->0)
	{
	    int i = rand.nextInt(4);
	    switch(i)
	    {
	    case 0:
		elem = rand.nextInt(e_MAX);
		set.add(add(elem, x));
		input.println(ops[i] + " " +elem);
		break;
	    case 1:
		long del;
		if(set.size() == 0)
		    del = rand.nextInt(e_MAX);
		else
		    del = (long)getRandom(set);
		set.remove(add(del, x));
		input.println(ops[i] + " " + del);
		break;
	    case 2:
		long e2;
		if(set.size() == 0)
		    e2 = rand.nextInt(e_MAX);
		else
		    e2 = (long)getRandom(set);
		out.println(set.contains(add(e2, x)) ? "Found" : "Not found");
		input.println(ops[i] + " " + e2);
		break;
	    case 3:
		long L = add(x, rand.nextInt(e_MAX));
		long R = add(x, rand.nextInt(e_MAX));
		while(L > R)
		{
		    L = add(x, rand.nextInt(e_MAX));
		    R = add(x, rand.nextInt(e_MAX));
		}
		L = sub(L, x);
		R = sub(R, x);
		input.println(ops[i] + " " + L + " " + R);
		L = add(L, x);
		R = add(R, x);
		if(L > R)
		    throw new IllegalArgumentException("Wrong arguments ");

		long sum = 0;
		for(Long ptr = set.ceiling(L); ptr != null && ptr <= R ; ptr = set.higher(ptr))
		    sum += ptr;

		out.println(x = sum);
		break;
	    }
	}

    }

    private static int JUDGE(String correctFile, String toBeChecked)throws IOException
    {
	FastScanner key = new FastScanner(new FileInputStream(correctFile));
	FastScanner unchecked = new FastScanner(new FileInputStream(toBeChecked));
	String read = null;
	int line = 0;
	while((read = key.nextLine()) != null)
	{
	    String candidate = unchecked.nextLine();
	    line++;
	    if(!read.equals(candidate))
		return line;
	}
	return -1;
    }
    /************************ SOLUTION ENDS HERE ************************/



    static final int q_MAX = 5000;
    static final int e_MAX = (int)(1e9);
    static int runs = 10;
    /************************ TEMPLATE STARTS HERE *********************/

    public static void main(String []args) throws IOException {

	String correctFile = "correct_output.txt";
	String inputFile = "input.txt";
	
	while(runs-->0)	
	{	   

	    PrintWriter input = new PrintWriter(inputFile);
	    PrintWriter out = new PrintWriter(correctFile);
	    generator(input, out);
	    input.close();
	    out.close();
	    SetRangeSum.main(null);
	    RangeSum.main(null);
	    int verdict1 = JUDGE(correctFile, SetRangeSum.outputFile);
	    int verdict2 = JUDGE(correctFile, RangeSum.outputFile);
	    if(verdict1 > 0)	
		System.err.println("Wrong answer (Splay) at line :" + verdict1);
	    if(verdict2 > 0)
		System.err.println("Wrong answer (AVL) at line : " + verdict2);

	    if(verdict1 > 0 || verdict2 > 0)
		return;

	    System.out.println("PASS");
	}
    }    

    static class FastScanner{
	BufferedReader reader;
	StringTokenizer st;
	FastScanner(InputStream stream){reader=new BufferedReader(new InputStreamReader(stream));st=null;}	
	String next()
	{while(st == null || !st.hasMoreTokens()){try{String line = reader.readLine();if(line == null){return null;}		    
	st = new StringTokenizer(line);}catch (Exception e){throw new RuntimeException();}}return st.nextToken();}
	String nextLine()  {String s=null;try{s=reader.readLine();}catch(IOException e){e.printStackTrace();}return s;}	    	  	
	int    nextInt()   {return Integer.parseInt(next());}
	long   nextLong()  {return Long.parseLong(next());}		
	double nextDouble(){return Double.parseDouble(next());}
	char   nextChar()  {return next().charAt(0);}
	int[]  nextIntArray(int n)         {int[] arr= new int[n];   int i=0;while(i<n){arr[i++]=nextInt();}  return arr;}
	long[] nextLongArray(int n)        {long[]arr= new long[n];  int i=0;while(i<n){arr[i++]=nextLong();} return arr;}	
	int[]  nextIntArrayOneBased(int n) {int[] arr= new int[n+1]; int i=1;while(i<=n){arr[i++]=nextInt();} return arr;}	    	
	long[] nextLongArrayOneBased(int n){long[]arr= new long[n+1];int i=1;while(i<=n){arr[i++]=nextLong();}return arr;}	    	
	void   close(){try{reader.close();}catch(IOException e){e.printStackTrace();}}				
    }

    /************************ TEMPLATE ENDS HERE ************************/
}