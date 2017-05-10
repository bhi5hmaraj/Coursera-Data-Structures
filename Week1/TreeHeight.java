import java.util.*;
import java.io.*;
class TreeHeight
{


    /************************ SOLUTION STARTS HERE ***********************/

    private static ArrayList<Integer>[] adj;
    private static boolean marked[];
    private static int V;
    private static int computeHeightBFS(int root)
    {
	Queue<Integer> queue = new ArrayDeque<>();
	queue.add(root);
	int distTo[] = new int[V];
	distTo[root] = 1;
	int height = 1;
	while(!queue.isEmpty())
	{
	    int u = queue.remove();
	    marked[u] = true;
	    for(int v:adj[u])
	    {
		if(!marked[v])
		{
		    queue.add(v);
		    distTo[v] = distTo[u]+1;
		    height = Math.max(height,distTo[v]);
		}
	    }
	}
	return height;
    }
    private static int computeHeightDFS(int root)
    {
	marked[root] = true;
	int height = 0;
	for(int v:adj[root])
	    if(!marked[v])
		height = Math.max(height , computeHeightDFS(v));

	return height + 1;
    }
    @SuppressWarnings("unchecked")
    private static void solve(FastScanner s1, PrintWriter out){

	V = s1.nextInt();
	marked = new boolean[V];
	adj = (ArrayList<Integer>[])new ArrayList[V];
	for(int i=0;i<V;i++)adj[i] = new ArrayList<>();

	int root = -1;
	for(int i=0;i<V;i++)
	{
	    int v = s1.nextInt();
	    if(v != -1)
	    {
		adj[i].add(v);
		adj[v].add(i);
	    }
	    else
		root = i;
	}
	//out.println(computeHeightBFS(root));       //Time 1.6s
	out.println(computeHeightDFS(root));     //Time 0.88s
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
    
    /*//Use this if you are using dfs
    public static void main(String []args) throws IOException {
	new Thread(null, new Runnable() {
	    public void run() {
		new TreeHeight().run();
	    }
	}, "IncreaseStackPlzz", 1L << 25).start();

    }    
    public void run()
    {
	FastScanner in  = new FastScanner(System.in);
	PrintWriter out = 
		new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), false); 
	solve(in, out);
	in.close();
	out.close();
    }
     */
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