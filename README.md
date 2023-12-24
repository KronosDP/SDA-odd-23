---
layout: default
title: The Space Complexity ReadMe
---

# Graph

- `addEdge(T source, T destination, long weight)`: O(E)
- `addVertex(T vertex)`: O(1)
- `printGraph()`: O(V + E)
- `prim()`: O(V^2) or O((V + E) \* log(V))
- `dijkstra()`: O((V + E) \* log(V))
- `BFS()`: O(V + E)
- `DFS()`: O(V + E)
- `isEulerian()`: O(V)
- `isHamiltonian()`: O(V!)
- `isBipartite()`: O(V + E)
- `isPlanar()`: O(V + E)
- `isBiconnected()`: O(V + E)
- `isStronglyConnected()`: O(V + E)
- `getTranspose()`: O(V + E)
- `hamiltonianCycle()`: O(V!)
- `hamiltonianPath()`: O(V!)
- `topologicalSort()`: O(V + E)

# Linked List

| Method                  | Circle Linked List | Doubly Linked List |
| ----------------------- | ------------------ | ------------------ |
| `getSize()`             | O(1)               | O(1)               |
| `append(E value)`       | O(1)               | O(1)               |
| `remove(int pos)`       | O(N)               | O(N)               |
| `get(int pos)`          | O(N)               | O(N)               |
| `set(int pos, E value)` | O(N)               | O(N)               |
| `removeFirst()`         | O(1)               | O(1)               |
| `removeLast()`          | N/A                | O(1)               |
| `addFirst(E value)`     | O(1)               | O(1)               |
| `addLast(E value)`      | O(N)               | O(1)               |
| `add(int pos, E value)` | O(N)               | O(N)               |
| `sortAscending()`       | O(N^2)             | O(N^2)             |
| `reverse()`             | O(N)               | O(N)               |
| `isEmpty()`             | O(1)               | O(1)               |

# Tree

| Method                       | AVL Tree | Binary Tree |
| ---------------------------- | -------- | ----------- |
| `height(Node<E> N)`          | O(1)     | N/A         |
| `insert(E element)`          | O(log N) | O(N)        |
| `delete(E element)`          | O(log N) | O(N)        |
| `find(E element)`            | O(log N) | O(N)        |
| `traverse(int traverseType)` | O(N)     | O(N)        |
| `isAVLTree()`                | O(N)     | N/A         |
| `isBinaryTree()`             | N/A      | O(N)        |
| `isBalanced()`               | N/A      | O(N)        |
