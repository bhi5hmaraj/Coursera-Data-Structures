import java.util.*;
import java.io.*;
import java.security.SecureRandom;
public class StressTesterRope
{


    /************************ SOLUTION STARTS HERE ***********************/
    
    
    
    private static void generator(PrintWriter input){   //Writes a single instance for a test case

	SecureRandom rand = new SecureRandom();
	int len = l_MAX;
	char arr[] = new char[len];
	for(int i=0;i<len;i++)
	    arr[i] = (char)('a' + rand.nextInt(26));
	
	input.println(new String(arr));
	int query = q_MAX;
	input.println(query);
	while(query-->0)
	{
	    int L = rand.nextInt(len);
	    int R = (L == len-1)? L : L + rand.nextInt(len-L-1);
	    int K = rand.nextInt(len-(R-L));
	    input.println(L + " " + R + " " + K);
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



    static final int q_MAX = 100000;
    static final int l_MAX = 10000;
    static int runs = 10;
    /************************ TEMPLATE STARTS HERE *********************/

    public static void main(String []args) throws IOException {

	
	String inputFile = "input.txt";
	
	while(runs-->0)	
	{	   
	    PrintWriter input = new PrintWriter(inputFile);
	    generator(input);
	    input.close();
	    long start = System.nanoTime();
	    RopeNaive.main(null);
	    long stop = System.nanoTime();
	    System.err.print("Time(Naive) : "+((stop-start)/1e9) + " s ");	    
	    start = System.nanoTime();
	    RopeFast.main(null);
	    stop = System.nanoTime();
	    System.err.println(" Time(Fast) : "+((stop-start)/1e9) + " s ");
	    int verdict = JUDGE(RopeNaive.outputFile, RopeFast.outputFile);
	    if(verdict > 0)	
	    {
		System.err.println("Wrong answer (Splay) at line :" + verdict);
		return;
	    }
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