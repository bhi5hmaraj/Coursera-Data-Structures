import java.util.*;
import java.io.*;
public class Binarytreetraversals implements Runnable
{


    /************************ SOLUTION STARTS HERE ***********************/

    private static int tree[][];    
    private static void inOrder(int root,PrintWriter out)
    {
	if(root != -1)
	{
	    inOrder(tree[root][1], out);
	    out.print(tree[root][0] + " ");
	    inOrder(tree[root][2], out);
	}
    }
    private static void preOrder(int root,PrintWriter out)
    {
	if(root != -1)
	{
	    out.print(tree[root][0] + " ");
	    preOrder(tree[root][1],out);
	    preOrder(tree[root][2],out);
	}
    }
    private static void postOrder(int root,PrintWriter out)
    {
	if(root != -1)
	{
	    postOrder(tree[root][1], out);
	    postOrder(tree[root][2], out);
	    out.print(tree[root][0]+ " ");
	}
    }
    
    private static void solve(FastScanner s1, PrintWriter out){

	int n = s1.nextInt();
	tree = new int[n][3];	
	for(int i=0;i<n;i++)
	    tree[i] = s1.nextIntArray(3);

	inOrder  (0, out);  out.println();
	preOrder (0, out);  out.println();
	postOrder(0, out);  out.println();
    }



    /************************ SOLUTION ENDS HERE ************************/





    /************************ TEMPLATE STARTS HERE *********************/

    public static void main(String []args) throws IOException {
	
	new Thread(null, new Binarytreetraversals(), "Increase Stack", 1 << 25).start();
	
    }    
    @Override
    public void run() {
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