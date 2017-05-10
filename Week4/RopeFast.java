import java.util.*;
import java.io.*;

public class RopeFast {

    /************************ SOLUTION STARTS HERE ***********************/

    static class Vertex {
        char   key;
        // Sum of all the keys in the subtree - remember to update
        // it after each operation that changes the tree.
        int    size;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(char key, int size, Vertex left, Vertex right, Vertex parent) {
            this.key = key;
            this.size = size;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        static void update(Vertex v) {
            if (v == null)
                return;
            v.size = (1 + (v.left != null ? v.left.size : 0) + (v.right != null ? v.right.size : 0));
            if (v.left != null) {
                v.left.parent = v;
            }
            if (v.right != null) {
                v.right.parent = v;
            }
        }

        @Override
        public String toString() {
            return "[key = " + key + "]" + " p = " + (parent != null ? parent.key : "null") + " l = "
                    + (left != null ? left.key : "null") + " r = " + (right != null ? right.key : "null");
        }
    }

    static class VertexPair {
        Vertex left;
        Vertex right;

        VertexPair() {
        }

        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }
    }

    static class SplayTree {
        Vertex root = null;

        public SplayTree() {
        }

        public SplayTree(Vertex root) {
            this.root = root;
        }

        void update(Vertex v) {
            if (v == null)
                return;
            v.size = (1 + size(v.left) + size(v.right));
            if (v.left != null) {
                v.left.parent = v;
            }
            if (v.right != null) {
                v.right.parent = v;
            }
        }

        int size(Vertex v) {
            return v == null ? 0 : v.size;
        }

        void smallRotation(Vertex v) {
            Vertex parent = v.parent;
            if (parent == null) {
                return;
            }
            Vertex grandparent = v.parent.parent;
            if (parent.left == v) {
                Vertex m = v.right;
                v.right = parent;
                parent.left = m;
            } else {
                Vertex m = v.left;
                v.left = parent;
                parent.right = m;
            }

            update(parent);
            update(v);
            v.parent = grandparent;
            if (grandparent != null) {
                if (grandparent.left == parent) {
                    grandparent.left = v;
                } else {
                    grandparent.right = v;
                }
            }
            update(grandparent);

        }

        void bigRotation(Vertex v) {
            if (v.parent.left == v && v.parent.parent.left == v.parent) {
                // Zig-zig
                smallRotation(v.parent);
                smallRotation(v);
            } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
                smallRotation(v.parent);
                smallRotation(v);
            } else {
                smallRotation(v);
                smallRotation(v);
            }
        }

        Vertex splay(Vertex v) {
            if (v == null)
                return null;
            while (v.parent != null) {
                if (v.parent.parent != null)
                    bigRotation(v);
                else
                    smallRotation(v);
            }
            return v;
        }

        private static void print(Vertex root) {
            if (root != null) {
                print(root.left);
                System.out.println(root);
                print(root.right);
            }
        }

        public String getString() {
            StringBuilder sb = new StringBuilder();
            getString(root, sb);
            return sb.toString();
        }

        private static void getString(Vertex root, StringBuilder sb) {
            if (root != null) {
                getString(root.left, sb);
                sb.append(root.key);
                getString(root.right, sb);
            }
        }

        VertexPair split(Vertex root, int pos) {
            VertexPair result = new VertexPair();
            Vertex piv = get(pos, root);
            result.left = splay(piv);
            if (result.left == null) {
                result.right = root;
                return result;
            }
            result.right = result.left.right;
            result.left.right = null;
            if (result.right != null)
                result.right.parent = null;
            update(result.left);
            update(result.right);
            return result;
        }

        void processQuery(int i, int j, int k) {
            VertexPair leftMiddle = split(root, i - 1);
            VertexPair middleRight = split(leftMiddle.right, j - i);
            Vertex substr = middleRight.left;
            Vertex afterCut = merge(leftMiddle.left, middleRight.right);
            VertexPair cut = split(afterCut, k - 1);
            root = merge(merge(cut.left, substr), cut.right);
        }

        Vertex get(int i, Vertex root) // zero based index
        {
            if (root != null) {
                if (i == size(root.left))
                    return root;
                else if (i < size(root.left))
                    return get(i, root.left);
                else
                    return get(i - size(root.left) - 1, root.right);
            } else
                return null;
        }

        Vertex merge(Vertex left, Vertex right) {
            if (left == null)
                return right;
            if (right == null)
                return left;
            right = splay(get(0, right));
            left = splay(get(left.size - 1, left));
            left.right = right;
            update(left);
            return left;
        }

        static String graphicPrint(Vertex root) {
            return toString(new StringBuilder(), true, new StringBuilder(), root).toString();
        }

        private static StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb, Vertex root) {

            if (root == null) {
                sb.append("Tree Empty\n");
                return sb;
            }

            if (root.right != null) {
                toString(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb, root.right);
            }
            sb.append(prefix).append(isTail ? "└── " : "┌── ").append(root.key).append("\n");
            if (root.left != null) {
                toString(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb, root.left);
            }
            return sb;
        }

        @Override
        public String toString() {
            return SplayTree.toString(new StringBuilder(), true, new StringBuilder(), root).toString();
        }
    }

    private static SplayTree constructTree(String arr) {
        return new SplayTree(insert(arr, 0, arr.length() - 1));
    }

    private static Vertex insert(String arr, int lo, int hi) {
        if (lo <= hi) {
            int mid = (lo + hi) / 2;
            Vertex newVertex = new Vertex(arr.charAt(mid), 1, null, null, null);
            newVertex.left = insert(arr, lo, mid - 1);
            newVertex.right = insert(arr, mid + 1, hi);
            Vertex.update(newVertex);
            return newVertex;
        } else
            return null;
    }

    private static void solve(FastScanner s1, PrintWriter out) {

        String str = s1.nextLine();
        SplayTree tree = constructTree(str);
        int query = s1.nextInt();
        while (query-- > 0)
            tree.processQuery(s1.nextInt(), s1.nextInt(), s1.nextInt());

        out.println(tree.getString());
    }

    /************************ SOLUTION ENDS HERE ************************/

    /************************ TEMPLATE STARTS HERE *********************/

    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), false);
        solve(in, out);
        in.close();
        out.close();
    }

    static String outputFile = "rope_fast.txt";

    /*
     * For connecting with the Stress Tester public static void main(String
     * []args) throws IOException { FastScanner in = new FastScanner(new
     * FileInputStream("input.txt")); PrintWriter out = new PrintWriter(new
     * FileWriter(outputFile), false); solve(in, out); in.close(); out.close();
     * }
     */
    static class FastScanner {
        BufferedReader  reader;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream));
            st = null;
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    String line = reader.readLine();
                    if (line == null) {
                        return null;
                    }
                    st = new StringTokenizer(line);
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
            return st.nextToken();
        }

        String nextLine() {
            String s = null;
            try {
                s = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        char nextChar() {
            return next().charAt(0);
        }

        int[] nextIntArray(int n) {
            int[] arr = new int[n];
            int i = 0;
            while (i < n) {
                arr[i++] = nextInt();
            }
            return arr;
        }

        long[] nextLongArray(int n) {
            long[] arr = new long[n];
            int i = 0;
            while (i < n) {
                arr[i++] = nextLong();
            }
            return arr;
        }

        int[] nextIntArrayOneBased(int n) {
            int[] arr = new int[n + 1];
            int i = 1;
            while (i <= n) {
                arr[i++] = nextInt();
            }
            return arr;
        }

        long[] nextLongArrayOneBased(int n) {
            long[] arr = new long[n + 1];
            int i = 1;
            while (i <= n) {
                arr[i++] = nextLong();
            }
            return arr;
        }

        void close() {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /************************ TEMPLATE ENDS HERE ************************/
}