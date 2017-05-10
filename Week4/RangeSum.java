import java.util.*;
import java.io.*;
public class RangeSum
{


    /************************ SOLUTION STARTS HERE ***********************/

    static long mod = (long) 1000000001; 
    static long add(long a, long b) {
	return ((a % mod) + (b % mod)) % mod;
    }

    static class AVLTree
    {
	static class Node 
	{
	    long key;
	    long sum;
	    int height;
	    Node left,right,parent;
	    Node(long key)
	    {
		this.key = key;
		height = 1;
		sum = key;
		left = right = parent = null;
	    }
	    @Override
	    public String toString() {
		return "[key = "+key+" h = "+height+"]"+ " p = "    
			+          (parent != null ? parent.key :"null")  
			+" l = " + (left   != null ? left.key   :"null")  
			+" r = " + (right  != null ? right.key  :"null");
	    }
	}
	Node root;
	public AVLTree() {
	    root = null;	    
	}
	private int height(Node n)
	{
	    return n == null ? 0 : n.height;
	}
	public void add(long key)
	{
	    if(search(root, key) == null)
	    {
		root = add(key, root, null);
		Node N = search(root, key);
		root = rebalance(N);
	    }
	}
	public boolean find(long key)
	{
	    return search(root, key) != null;
	}
	private long sum(Node n)
	{
	    return n == null ? 0 : n.sum;
	}
	private void adjustHeight(Node N)
	{
	    N.height = 1 + Math.max(height(N.left),height(N.right));
	    if(N.left != null)
		N.left.parent = N;
	    if(N.right != null)
		N.right.parent = N;
	    adjustSum(N); //Small hack , so that I need not call it explicitly!!
	}
	private void adjustSum(Node N)
	{
	    N.sum = N.key + sum(N.left) + sum(N.right);	    
	}
	private Node rotateRight(Node N)
	{
	    Node oldPar = N.parent;
	    Node newN = N.left;
	    N.left = newN.right;
	    if(newN != null && newN.right != null && newN.right.parent != null)
		newN.right.parent = N;
	    adjustHeight(N);
	    newN.parent = oldPar;
	    newN.right = N;
	    adjustHeight(newN);
	    N.parent = newN;
	    if(oldPar != null)
	    {
		if(oldPar.left == N)
		    oldPar.left = newN;
		else
		    oldPar.right = newN;

		adjustHeight(oldPar);
	    }
	    return newN;
	}
	private Node rotateLeft(Node N)
	{
	    Node oldPar = N.parent;
	    Node newN = N.right;
	    N.right = newN.left;
	    if(newN != null  && newN.left != null && newN.left.parent != null)
		newN.left.parent = N;
	    adjustHeight(N);
	    newN.parent = oldPar;
	    newN.left = N;
	    N.parent = newN;
	    adjustHeight(newN);
	    if(oldPar != null)
	    {
		if(oldPar.left == N)
		    oldPar.left = newN;
		else
		    oldPar.right = newN;

		adjustHeight(oldPar);
	    }
	    return newN;
	}
	private Node search(Node root , long key)
	{
	    if(root != null)
		return ((root.key == key) ? root : (key < root.key ? search(root.left, key) : search(root.right, key)));
	    else
		return null;
	}
	public Node smallest(Node root)
	{
	    if(root.left == null)
		return root;
	    else
		return smallest(root.left);
	}
	public Node largest(Node root)
	{
	    if(root.right == null)
		return root;
	    else
		return largest(root.right);
	}
	public void del(long key)
	{
	    Node toBeRemoved = search(root, key);
	    if(toBeRemoved != null)
		delete(toBeRemoved);
	}


	public long sumInRange(long L,long R)
	{
	    return (sumLess(root,R) - sumLess(root,L)) + ( find(R) ? R : 0) ;
	}
	private long sumLess(Node root,long key)
	{
	    if(root != null)
	    {
		if(root.key == key)
		    return sum(root.left);
		else if(key < root.key)
		    return sumLess(root.left, key);
		else
		    return root.key + sum(root.left) + sumLess(root.right, key);
	    }
	    else
		return 0;
	}
	private void delete(Node N)
	{

	    if(N.left == null && N.right == null)
	    {
		if(N == root)
		    root = null;
		else
		{
		    Node par = N.parent;
		    if(par.left == N)
			par.left = null;
		    else
			par.right = null;

		    Node orig_par = par;
		    while(par != null)
		    {
			adjustHeight(par);
			par = par.parent;
		    }
		    root = rebalance(orig_par);
		}
	    }
	    else if(N.right == null && N.left != null)
	    {
		Node par = N.parent;
		if(par != null)
		{
		    if(par.left == N)
			par.left = N.left;
		    else
			par.right = N.left;

		    N.left.parent = par;
		    Node orig_par = par;
		    while(par != null)
		    {
			adjustHeight(par);
			par = par.parent;
		    }

		    root = rebalance(orig_par);

		}
		else
		{
		    root = root.left;
		    root.parent = null;
		    adjustHeight(root);
		}
	    }
	    else if(N.left == null && N.right != null)
	    {
		Node par = N.parent;
		if(par != null)
		{
		    if(par.left == N)
			par.left = N.right;
		    else		    
			par.right = N.right;

		    N.right.parent = par;

		    Node orig_par = par;
		    while(par != null)
		    {
			adjustHeight(par);
			par = par.parent;
		    }
		    root = rebalance(orig_par);
		}
		else
		{
		    root = root.right;
		    root.parent = null;
		    adjustHeight(root);
		}
	    }
	    else
	    {
		Node small = smallest(N.right);
		N.key = small.key; //Copy all properties of the node
		delete(small);
	    }
	}
	private Node rebalance(Node N)
	{
	    Node par = N.parent;
	    if (height(N.left) - height(N.right) >= 2)
	    {
		Node M = N.left;
		if(height(M.right) > height(M.left))
		    M = rotateLeft(M);
		N = rotateRight(N);
	    }
	    if(height(N.right) - height(N.left) >= 2)
	    {
		Node M = N.right;
		if(height(M.left) > height(M.right))
		    M = rotateRight(M);

		N = rotateLeft(N);
	    }

	    if(par != null)	    
		return rebalance(par);	    
	    else 
		return N;

	}
	private Node add(long key,Node root, Node parent)
	{
	    if(root == null)
	    {
		Node newNode = new Node(key);
		newNode.parent = parent;
		return newNode;
	    }
	    else
	    {
		if(key < root.key)
		    root.left = add(key, root.left , root);
		else if (key > root.key)
		    root.right = add(key, root.right, root);
		else 
		    return root;

		adjustHeight(root);
		return root;
	    }
	}
	private void print(Node root)
	{
	    if(root!=null)
	    {
		print(root.left);
		System.out.println(root);
		print(root.right);
	    }
	}
	private StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb, Node root) {

	    if(root ==null)
	    {
		sb.append("Tree Empty");
		return sb;
	    }
	    if(root.right!=null) {
		toString(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb,root.right);
	    }
	    sb.append(prefix).append(isTail ? "└── " : "┌── ").append(root.key).append("\n");
	    if(root.left!=null) {
		toString(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb,root.left);
	    }
	    return sb;
	}

	@Override
	public String toString() {
	    return this.toString(new StringBuilder(), true, new StringBuilder(),root).toString();
	}
    }

    private static void solve(FastScanner s1, PrintWriter out){

	AVLTree tree = new AVLTree();
	long x = 0;
	int t = s1.nextInt();
	while(t-->0)
	{
	    char choice = s1.nextChar();
	    long i;
	    switch(choice)
	    {
	    case '+':
		i = s1.nextLong();
		tree.add(add(i, x));
		break;
	    case '-':
		i = s1.nextLong();
		tree.del(add(i, x));
		break;
	    case '?':
		i = s1.nextLong();
		out.println(tree.find(add(i, x)) ? "Found" : "Not found");
		break;
	    case 's':		
		out.println( x = tree.sumInRange(add(s1.nextLong(),x), add(s1.nextLong(),x)) );
		break;

	    }
	    System.out.println(tree);
	    //tree.print(tree.root);
	}

    }
    private static void solve2(FastScanner s1, PrintWriter out){

	TreeSet<Long> set = new TreeSet<>();
	long x = 0;
	int t = s1.nextInt();
	while(t-->0)
	{
	    char choice = s1.nextChar();
	    long i;
	    switch(choice)
	    {
	    case '+':
		i = s1.nextLong();
		set.add(add(i, x));
		break;
	    case '-':
		i = s1.nextLong();
		set.remove(add(i, x));
		break;
	    case '?':
		i = s1.nextLong();
		out.println(set.contains(add(i, x)) ? "Found" : "Not found");
		break;
	    case 's':		

		long L = add(s1.nextLong(),x);
		long R = add(s1.nextLong(),x);

		long sum = 0;		
		for(long val : set.subSet(L, true, R, true))
		    sum += val;
		
		out.println(x = sum);
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


    static String outputFile = "output_AVL.txt";
    /*
    public static void main(String []args) throws IOException {
	FastScanner in  = new FastScanner(new FileInputStream("input.txt"));
	PrintWriter out = 
		new PrintWriter(outputFile); 
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