import java.util.*;
import java.io.*;
class Checkbrackets
{

    
    /************************ SOLUTION STARTS HERE ***********************/

    static class Pair
    {
	char ch;
	int index;
	Pair(char ch,int index)
	{
	    this.ch = ch;
	    this.index = index;
	}
    }
    private static void solve(FastScanner s1, PrintWriter out){

	HashMap<Character,Character> map = new HashMap<>();
	map.put(']', '[');
	map.put('}', '{');
	map.put(')', '(');
	
	char arr[] = s1.nextLine().toCharArray();
	ArrayDeque<Pair> stk = new ArrayDeque<>();
	
	for(int i=0;i<arr.length;i++)
	{
	    Pair curr = new Pair(arr[i], i+1);
	    if(arr[i]=='['||arr[i]=='{'||arr[i]=='(')
	    {
		stk.push(curr);
	    }
	    else if(map.containsKey(arr[i]))
	    {
		if(stk.isEmpty())
		{
		    out.print(i+1);
		    return;
		}
		else
		{
		    char pop = stk.pop().ch;
		    if(pop != map.get(arr[i]))
		    {
			out.print(i+1);
			return;
		    }
		}
	    }
	}
	if(!stk.isEmpty())		  
	    out.print(stk.getLast().index);	
	else
	    out.print("Success");
	
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