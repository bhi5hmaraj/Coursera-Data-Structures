import java.util.*;
import java.io.*;
public class Hashingwithchains
{

    
    /************************ SOLUTION STARTS HERE ***********************/

    static class MyMap
    {
	private ArrayDeque<String>[] arr;
	private int m;
	private int p;  //The prime used in computing the polynomial hash
	private int x;  //The random seed used in computing hash
	@SuppressWarnings("unchecked")
	MyMap(int m)
	{
	    this.m = m;
	    arr = (ArrayDeque<String>[])new ArrayDeque[m];
	    for(int i=0;i<m;i++)arr[i] = new ArrayDeque<>();
	    p = 1000000007;
	    x = 263;	    
	}
	int hash(String str)
	{
	    long hash = 0;
	    for (int i = str.length() - 1; i >= 0; i--)
		hash = ((hash * x) % p + (long) (str.charAt(i))) % p;

	    hash %= m;
	    return (int) hash;
	}
	void add(String str)
	{
	    int index = hash(str);	    
	    if(!arr[index].contains(str))
		arr[index].addFirst(str);
	}
	void del(String str)
	{
	    arr[hash(str)].remove(str);
	}
	boolean find(String str)
	{
	    return arr[hash(str)].contains(str);
	}
	Iterable<String> check(int i)
	{
	    return arr[i];
	}
    }

    
    private static void solve(FastScanner s1, PrintWriter out){

	int m = s1.nextInt();
	int query = s1.nextInt();
	MyMap map = new MyMap(m);
	while(query-->0)
	{
	    String choice = s1.next();
	    switch(choice)
	    {
	    case "add":
		map.add(s1.next());
		break;
	    case "del":
		map.del(s1.next());
		break;
	    case "find":
		out.println(map.find(s1.next())?"yes":"no");
		break;
	    case "check":
		for(String s:map.check(s1.nextInt()))
		    out.print(s + " ");
		out.println();
		break;
	    }
	}

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