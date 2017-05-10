import java.util.*;
import java.io.*;
class Networkpacket
{


    /************************ SOLUTION STARTS HERE ***********************/

    private static void solve(FastScanner s1, PrintWriter out){

	int buf_size = s1.nextInt();
	int n = s1.nextInt();
	if(n==0)
	    return;
	int A[] = new int[n];
	int P[] = new int[n];
	for(int i=0;i<n;i++)
	{
	    A[i] = s1.nextInt();
	    P[i] = s1.nextInt();
	}
	Queue<Integer> queue = new ArrayDeque<>();
	long time_in[] = new long[n];
	queue.add(0);
	int ptr = 1;
	long t = A[0];
	while(!queue.isEmpty())
	{
	    //out.println(queue);
	    int curr = queue.remove();
	    t = (A[curr] > t)?A[curr]:t;
	    long end_time = t + P[curr];
	    time_in[curr] = t;

	    while(ptr < n)
	    {
		if(A[ptr] >= end_time)
		{
		    queue.add(ptr);
		    ptr++;
		    break;
		}		    
		
		if(queue.size()+1 == buf_size)
		    time_in[ptr] = -1;
		else
		    queue.add(ptr);

		ptr++;
	    }

	    t += P[curr];
	}
	for(long i:time_in)
	    out.println(i);
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