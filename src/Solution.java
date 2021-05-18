import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'bfs' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER m
     *  3. 2D_INTEGER_ARRAY edges
     *  4. INTEGER s
     */

    public static List<Integer> bfs(int n, int m, List<List<Integer>> edges, int s) {
        // Write your code here
        //s = root node
        //n = number of nodes, m = number of edges
        //edges is a list of objects, each of which is a list with 2 nodes describing an edge between them.

        //Assemble the graph
        Map<Integer, Node> graph = new HashMap<Integer, Node>();
        for (int i = 1; i <= n; i++) {
            graph.put(i, new Node(i));
            //System.out.printf("Adding node to graph: %d.\n", i);
        }
        for (List<Integer> edge : edges) {
            Integer u = edge.get(0);
            Integer v = edge.get(1);

            //System.out.printf("Adding edge: %d -> %d.\n", u, v);
            graph.get(u).children.add(v);
            graph.get(v).children.add(u);
        }


        //create results arraylist and fill with -1s
        ArrayList<Integer> results = new ArrayList<>();
        System.out.printf("Prepping results, size: %d.\n", n);
        for (int i = 0; i <= n; i++) {
            //System.out.printf("Results dummy entry -1 at index: %d.\n", i);
            results.add(-1);
        }


        Deque<Integer> toVisit = new ArrayDeque<>();
        Deque<Integer> nextLevel = new ArrayDeque<>();
        HashSet<Integer> visited = new HashSet<>();

        Integer dist = 0;
        toVisit.addLast(s);
        while(!toVisit.isEmpty()) {
            Integer cursor = toVisit.pop();
            //System.out.printf("Visiting: %d.\n", cursor);
            visited.add(cursor);
            results.set(cursor, dist * 6);
           // System.out.printf("result added: [%d] %d.\n", cursor, dist * 6);

            for (Integer child : graph.get(cursor).children) {
                if(!visited.contains(child)) {
                    //System.out.printf("Adding to next level: %d.\n", child);
                    nextLevel.addLast(child);
                }
            }

            if(toVisit.isEmpty() && !nextLevel.isEmpty()) {
                toVisit = nextLevel;
                nextLevel = new ArrayDeque<>();
                dist++;
                //System.out.printf("Level complete, moving on to next level: %d.\n", dist);

            }

        }

        results.remove(s);
        results.remove(0);
        return results;
    }

    static class Node {
        public HashSet<Integer> children;
        public Integer nodeNum;

        public Node(int i) {
            children = new HashSet<>();
            nodeNum = i;
        }

    }


}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        for (int qItr = 0; qItr < q; qItr++) {
            String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            int n = Integer.parseInt(firstMultipleInput[0]);

            int m = Integer.parseInt(firstMultipleInput[1]);

            List<List<Integer>> edges = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                String[] edgesRowTempItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                List<Integer> edgesRowItems = new ArrayList<>();

                for (int j = 0; j < 2; j++) {
                    int edgesItem = Integer.parseInt(edgesRowTempItems[j]);
                    edgesRowItems.add(edgesItem);
                }

                edges.add(edgesRowItems);
            }

            int s = Integer.parseInt(bufferedReader.readLine().trim());

            List<Integer> result = Result.bfs(n, m, edges, s);

            for (int i = 0; i < result.size(); i++) {
                bufferedWriter.write(String.valueOf(result.get(i)));

                if (i != result.size() - 1) {
                    bufferedWriter.write(" ");
                }
            }

            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
