import java.util.*;
import java.io.*;
public class Heapify
{

    
    /************************ SOLUTION STARTS HERE ***********************/

    static class Pair
    {
	int a,b;
	Pair(int a,int b)
	{
	    this.a = a;
	    this.b = b;
	}
    }
    
    private static int heap[];
    private static ArrayList<Pair> arl;
    
    private static void sink(int index)
    {
	int left =  (index << 1) + 1;
	int right = (index << 1) + 2;
	int other = index;
	if (left < heap.length && heap[left] < heap[other])
	    other = left;
	if (right < heap.length && heap[right] < heap[other])
	    other = right;
	
	if(other != index)
	{
	    swap(heap, index, other);
	    arl.add(new Pair(index, other));
	    sink(other);
	}
    }
    private static void swap(int arr[] , int i ,int  j)
    {
	int temp = arr[i];
	arr[i] = arr[j];
	arr[j] = temp;
    }
    private static void solve(FastScanner s1, PrintWriter out){

	arl = new ArrayList<>();
	int N = s1.nextInt();
	heap = s1.nextIntArray(N);
	for (int i = (N - 2) >> 1; i >= 0; i--)
	    sink(i);

	out.println(arl.size());
	for (Pair p : arl)
	    out.println(p.a + " " + p.b);
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