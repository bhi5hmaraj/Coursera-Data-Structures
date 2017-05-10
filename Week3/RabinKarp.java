import java.util.*;
import java.io.*;
public class RabinKarp
{


    /************************ SOLUTION STARTS HERE ***********************/

    private static long p = 1000000007;
    private static long x = 31;
    private static long hash(String str)
    {
	long hash = 0;
	for(int i=str.length()-1;i>=0;i--)
	    hash = add(mul(hash, x), str.charAt(i));
		
	return hash;
    }
    private static long modPow(long a,long b,long mod)   // squared exponentiation  
    {
	if(b == 0L || a == 1L)
	    return 1L;
	else if(b == 1L)
	    return a;
	else
	{
	    if((b & 1L) == 0L)  		//Checking whether b is even (fast)  
		return modPow((a * a) % mod,b >> 1,mod); 
	    else
		return (a * modPow((a * a) % mod,b >> 1,mod)) % mod ;
	}
    }    
    private static long add(long a,long b)
    {
	return ((a%p)+(b%p))%p;
    }
    private static long mul(long a,long b)
    {
	return ((a%p) * (b%p))%p;
    }
    private static long sub(long a ,long b)
    {
	long sub = ((a%p) - (b%p))%p;
	return (sub < 0 )?p + sub : sub;
    }
    private static void solve(FastScanner s1, PrintWriter out){

	String pat  = s1.nextLine();
	String text = s1.nextLine();
	long pHash = hash(pat);

	ArrayDeque<Integer> vector = new ArrayDeque<>();
	int p_len = pat.length();
	int t_len = text.length();
	long curr_hash = 0;
	long pow = modPow(x, p_len, p) %  p;
	for (int i = t_len - 1; i >= t_len - p_len; i--)
	    curr_hash = add(mul(curr_hash, x), text.charAt(i));

	if (curr_hash == pHash && text.substring(t_len - p_len).equals(pat))
	    vector.push(t_len - p_len);

	for(int i=t_len-p_len-1;i>=0;i--)
	{	   
	    curr_hash = add(sub(mul(curr_hash, x), mul(text.charAt(i+p_len), pow)), text.charAt(i)); //Don't skimp on the subtraction part !
	    
	    if (curr_hash == pHash && text.substring(i, i + p_len).equals(pat))
		vector.push(i);
	}

	for(int pos:vector)
	    out.print(pos + " ");
    }



    /************************ SOLUTION ENDS HERE ************************/





    /************************ TEMPLATE STARTS HERE *********************/

    public static void main(String []args) throws IOException {
	FastScanner in  = new FastScanner(System.in);
	PrintWriter out = 
		new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), false); 
	solve(in, out);
	in.close();
	out.close();
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