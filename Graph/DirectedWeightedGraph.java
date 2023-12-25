import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DirectedWeightedGraph<T> {
    private int vertices;
    private List<List<Edge<T>>> adjList;
    private boolean bothWays;
    private Map<T, Integer> vertexToIndexMap; // New map to store vertex-index mapping

    private class Edge<E> {
        E destination;
        long weight;

        public Edge(E source, E destination, long weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    public DirectedWeightedGraph(boolean bothWays) {
        this.bothWays = bothWays;
        adjList = new ArrayList<>();
        vertexToIndexMap = new HashMap<>(); // Initialize the map
    }

    public void addEdge(T source, T destination, long weight) {
        int sourceIndex = getIndex(source);
        Edge<T> edge = new Edge<>(source, destination, weight);
        if (bothWays) {
            int destinationIndex = getIndex(destination);
            Edge<T> edge2 = new Edge<>(destination, source, weight);
            adjList.get(destinationIndex).add(edge2);
        }
        adjList.get(sourceIndex).add(edge);
    }

    public int getIndex(T vertex) {
        return vertexToIndexMap.getOrDefault(vertex, -1); // Retrieve index from map
    }

    public void addVertex(T vertex) {
        adjList.add(new ArrayList<>());
        vertexToIndexMap.put(vertex, vertices++); // Add vertex-index mapping to map
    }

    public void printGraph() {
        for (int i = 0; i < vertices; i++) {
            List<Edge<T>> list = adjList.get(i);
            System.out.print("Vertex " + i + " is connected to: ");
            for (int j = 0; j < list.size(); j++) {
                System.out.print(list.get(j).destination + " (weight: " + list.get(j).weight + "), ");
            }
            System.out.println();
        }
    }

    public void prim() {
        int[] parent = new int[vertices];
        long[] key = new long[vertices];
        boolean[] mstSet = new boolean[vertices];

        for (int i = 0; i < vertices; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < vertices - 1; i++) {
            int u = minKey(key, mstSet);
            mstSet[u] = true;

            for (int j = 0; j < adjList.get(u).size(); j++) {
                int v = getIndex(adjList.get(u).get(j).destination);
                long weight = adjList.get(u).get(j).weight;
                if (!mstSet[v] && weight < key[v]) {
                    parent[v] = u;
                    key[v] = weight;
                }
            }
        }

    }

    public int minKey(long[] key, boolean[] mstSet) {
        long min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < vertices; i++) {
            if (!mstSet[i] && key[i] < min) {
                min = key[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    public void dijkstra() {
        int[] parent = new int[vertices];
        long[] distance = new long[vertices];
        boolean[] visited = new boolean[vertices];

        for (int i = 0; i < vertices; i++) {
            distance[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        distance[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < vertices - 1; i++) {
            int u = minDistance(distance, visited);
            visited[u] = true;

            for (int j = 0; j < adjList.get(u).size(); j++) {
                int v = getIndex(adjList.get(u).get(j).destination);
                long weight = adjList.get(u).get(j).weight;
                if (!visited[v] && distance[u] != Integer.MAX_VALUE && distance[u] + weight < distance[v]) {
                    parent[v] = u;
                    distance[v] = distance[u] + weight;
                }
            }
        }
    }

    public int minDistance(long[] distance, boolean[] visited) {
        long min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < vertices; i++) {
            if (!visited[i] && distance[i] < min) {
                min = distance[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    public void BFS() {
        boolean[] visited = new boolean[vertices];
        for (int i = 0; i < vertices; i++) {
            visited[i] = false;
        }

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                BFSUtil(i, visited);
            }
        }
    }

    public void BFSUtil(int vertex, boolean[] visited) {
        List<Integer> queue = new ArrayList<>();
        visited[vertex] = true;
        queue.add(vertex);

        while (!queue.isEmpty()) {
            int v = queue.remove(0);
            System.out.print(v + " ");

            for (int i = 0; i < adjList.get(v).size(); i++) {
                int u = getIndex(adjList.get(v).get(i).destination);
                if (!visited[u]) {
                    visited[u] = true;
                    queue.add(u);
                }
            }
        }
    }

    public void DFS() {
        boolean[] visited = new boolean[vertices];
        for (int i = 0; i < vertices; i++) {
            visited[i] = false;
        }

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                DFSUtil(i, visited);
            }
        }
    }

    public void DFSUtil(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");

        for (int i = 0; i < adjList.get(vertex).size(); i++) {
            int u = getIndex(adjList.get(vertex).get(i).destination);
            if (!visited[u]) {
                DFSUtil(u, visited);
            }
        }
    }

    public void isEulerian() {
        int odd = 0;
        for (int i = 0; i < vertices; i++) {
            if (adjList.get(i).size() % 2 != 0) {
                odd++;
            }
        }

        if (odd == 0) {
            System.out.println("Graph is Eulerian");
        } else if (odd == 2) {
            System.out.println("Graph is Semi-Eulerian");
        } else {
            System.out.println("Graph is not Eulerian");
        }
    }

    public void isHamiltonian() {
        boolean[] visited = new boolean[vertices];
        for (int i = 0; i < vertices; i++) {
            visited[i] = false;
        }

        if (isHamiltonianUtil(0, visited)) {
            System.out.println("Graph is Hamiltonian");
        } else {
            System.out.println("Graph is not Hamiltonian");
        }
    }

    public boolean isHamiltonianUtil(int vertex, boolean[] visited) {
        if (vertex == vertices - 1) {
            return true;
        }

        visited[vertex] = true;

        for (int i = 0; i < adjList.get(vertex).size(); i++) {
            int u = getIndex(adjList.get(vertex).get(i).destination);
            if (!visited[u]) {
                if (isHamiltonianUtil(u, visited)) {
                    return true;
                }
            }
        }

        visited[vertex] = false;
        return false;
    }

    public void isBipartite() {
        int[] color = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            color[i] = -1;
        }

        for (int i = 0; i < vertices; i++) {
            if (color[i] == -1) {
                if (!isBipartiteUtil(i, color)) {
                    System.out.println("Graph is not Bipartite");
                    return;
                }
            }
        }

        System.out.println("Graph is Bipartite");
    }

    public boolean isBipartiteUtil(int vertex, int[] color) {
        color[vertex] = 1;

        List<Integer> queue = new ArrayList<>();
        queue.add(vertex);

        while (!queue.isEmpty()) {
            int v = queue.remove(0);

            for (int i = 0; i < adjList.get(v).size(); i++) {
                int u = getIndex(adjList.get(v).get(i).destination);
                if (color[u] == -1) {
                    color[u] = 1 - color[v];
                    queue.add(u);
                } else if (color[u] == color[v]) {
                    return false;
                }
            }
        }

        return true;
    }

    public void isPlanar() {
        int V = vertices;
        int E = 0;
        for (int i = 0; i < V; i++) {
            E += adjList.get(i).size();
        }
        E /= 2;

        int F = 2 - V + E;

        if (F == 0) {
            System.out.println("Graph is Planar");
        } else {
            System.out.println("Graph is not Planar");
        }
    }

    public void isBiconnected() {
        int[] disc = new int[vertices];
        int[] low = new int[vertices];
        int[] parent = new int[vertices];
        boolean[] visited = new boolean[vertices];
        boolean[] ap = new boolean[vertices];

        for (int i = 0; i < vertices; i++) {
            parent[i] = -1;
            visited[i] = false;
            ap[i] = false;
        }

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                isBiconnectedUtil(i, visited, disc, low, parent, ap);
            }
        }

        for (int i = 0; i < vertices; i++) {
            if (ap[i]) {
                System.out.println(i + " ");
            }
        }
    }

    public void isBiconnectedUtil(int vertex, boolean[] visited, int[] disc, int[] low, int[] parent, boolean[] ap) {
        int children = 0;
        visited[vertex] = true;
        int time = 0;
        disc[vertex] = low[vertex] = ++time;

        for (int i = 0; i < adjList.get(vertex).size(); i++) {
            int u = getIndex(adjList.get(vertex).get(i).destination);
            if (!visited[u]) {
                children++;
                parent[u] = vertex;
                isBiconnectedUtil(u, visited, disc, low, parent, ap);

                low[vertex] = Math.min(low[vertex], low[u]);

                if (parent[vertex] == -1 && children > 1) {
                    ap[vertex] = true;
                }

                if (parent[vertex] != -1 && low[u] >= disc[vertex]) {
                    ap[vertex] = true;
                }
            } else if (u != parent[vertex]) {
                low[vertex] = Math.min(low[vertex], disc[u]);
            }
        }
    }

    public void isStronglyConnected() {
        boolean[] visited = new boolean[vertices];
        for (int i = 0; i < vertices; i++) {
            visited[i] = false;
        }

        DFSUtil(0, visited);

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                System.out.println("Graph is not Strongly Connected");
                return;
            }
        }

        DirectedWeightedGraph<T> g = getTranspose();
        for (int i = 0; i < vertices; i++) {
            visited[i] = false;
        }

        g.DFSUtil(0, visited);

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                System.out.println("Graph is not Strongly Connected");
                return;
            }
        }

        System.out.println("Graph is Strongly Connected");
    }

    public DirectedWeightedGraph<T> getTranspose() {
        DirectedWeightedGraph<T> g = new DirectedWeightedGraph<>(bothWays);

        for (int i = 0; i < vertices; i++) {
            g.addVertex(getVertex(i));
        }

        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < adjList.get(i).size(); j++) {
                int u = getIndex(adjList.get(i).get(j).destination);
                long weight = adjList.get(i).get(j).weight;
                g.addEdge(getVertex(u), getVertex(i), weight);
            }
        }

        return g;
    }

    public T getVertex(int index) {
        for (Map.Entry<T, Integer> entry : vertexToIndexMap.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void hamiltonianCycle() {
        int[] path = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            path[i] = -1;
        }

        path[0] = 0;

        if (!hamiltonianCycleUtil(path, 1)) {
            System.out.println("No Hamiltonian Cycle");
        } else {
            printPath(path);
        }
    }

    public boolean hamiltonianCycleUtil(int[] path, int pos) {
        if (pos == vertices) {
            if (adjList.get(path[pos - 1]).contains(getVertex(path[0]))) {
                return true;
            } else {
                return false;
            }
        }

        for (int v = 1; v < vertices; v++) {
            if (isSafe(v, path, pos)) {
                path[pos] = v;

                if (hamiltonianCycleUtil(path, pos + 1)) {
                    return true;
                }

                path[pos] = -1;
            }
        }

        return false;
    }

    public boolean isSafe(int v, int[] path, int pos) {
        if (!adjList.get(path[pos - 1]).contains(getVertex(v))) {
            return false;
        }

        for (int i = 0; i < pos; i++) {
            if (path[i] == v) {
                return false;
            }
        }

        return true;
    }

    public void printPath(int[] path) {
        for (int i = 0; i < vertices; i++) {
            System.out.print(path[i] + " ");
        }
        System.out.println(path[0]);
    }

    public void hamiltonianPath() {
        int[] path = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            path[i] = -1;
        }

        path[0] = 0;

        if (!hamiltonianPathUtil(path, 1)) {
            System.out.println("No Hamiltonian Path");
        } else {
            printPath(path);
        }
    }

    public boolean hamiltonianPathUtil(int[] path, int pos) {
        if (pos == vertices) {
            return true;
        }

        for (int v = 1; v < vertices; v++) {
            if (isSafe(v, path, pos)) {
                path[pos] = v;

                if (hamiltonianPathUtil(path, pos + 1)) {
                    return true;
                }

                path[pos] = -1;
            }
        }

        return false;
    }

    public void topologicalSort() {
        int[] indegree = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < adjList.get(i).size(); j++) {
                int u = getIndex(adjList.get(i).get(j).destination);
                indegree[u]++;
            }
        }

        List<Integer> queue = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        int count = 0;
        List<Integer> topOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.remove(0);
            topOrder.add(u);

            for (int i = 0; i < adjList.get(u).size(); i++) {
                int v = getIndex(adjList.get(u).get(i).destination);
                if (--indegree[v] == 0) {
                    queue.add(v);
                }
            }

            count++;
        }

        if (count != vertices) {
            System.out.println("Graph contains cycle");
        } else {
            for (int i = 0; i < topOrder.size(); i++) {
                System.out.print(topOrder.get(i) + " ");
            }
        }
    }

    public static void main(String[] args) {
        DirectedWeightedGraph<Integer> g = new DirectedWeightedGraph<>(true);

        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);

        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 1);
        g.addEdge(2, 1, 2);
        g.addEdge(2, 3, 5);
        g.addEdge(1, 3, 1);

        g.printGraph();

        g.prim();
        g.dijkstra();

        g.BFS();
        g.DFS();

        g.isEulerian();
        g.isHamiltonian();
        g.isBipartite();
        g.isPlanar();
        g.isBiconnected();
        g.isStronglyConnected();

        g.hamiltonianCycle();
        g.hamiltonianPath();

        g.topologicalSort();
    }

}
