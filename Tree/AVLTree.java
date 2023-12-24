import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<E extends Comparable<E>> {
    private Node<E> root;

    private class Node<T> {
        T element;
        Node<T> left, right;
        int height;

        Node(T element) {
            this.element = element;
            this.left = this.right = null;
            this.height = 1;
        }
    }

    public AVLTree() {
        root = null;
    }

    // A utility function to get the height of the tree
    private int height(Node<E> N) {
        if (N == null)
            return 0;
        return N.height;
    }

    // A utility function to get maximum of two integers
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // A utility function to right rotate subtree rooted with y
    private Node<E> rightRotate(Node<E> y) {
        Node<E> x = y.left;
        Node<E> T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    private Node<E> leftRotate(Node<E> x) {
        Node<E> y = x.right;
        Node<E> T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    private int getBalance(Node<E> N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    // A utility function to get the node with minimum element value
    private Node<E> minValueNode(Node<E> node) {
        Node<E> current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null)
            current = current.left;

        return current;
    }

    // A utility function to get size of the tree
    private int size(Node<E> node) {
        if (node == null)
            return 0;
        else
            return (size(node.left) + 1 + size(node.right));
    }

    public int size() {
        return size(root);
    }

    // insert
    public void insert(E element) {
        root = insert(root, element);
    }

    private Node<E> insert(Node<E> node, E element) {
        // Perform the normal BST insertion
        if (node == null) {
            return (new Node<E>(element));
        }

        if (element.compareTo(node.element) < 0)
            node.left = insert(node.left, element);
        else if (element.compareTo(node.element) > 0)
            node.right = insert(node.right, element);
        else // Duplicate keys not allowed
            return node;

        // Update height of this ancestor node
        node.height = 1 + max(height(node.left), height(node.right));

        // Get the balance factor
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && element.compareTo(node.left.element) < 0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && element.compareTo(node.right.element) > 0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && element.compareTo(node.left.element) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && element.compareTo(node.right.element) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // return the (unchanged) node pointer
        return node;
    }

    // delete
    public boolean delete(E element) {
        int initialSize = size();
        root = delete(root, element);
        return size() != initialSize;
    }

    private Node<E> delete(Node<E> root, E element) {
        // Perform the normal BST deletion
        if (root == null) {
            return root;
        }

        if (element.compareTo(root.element) < 0) {
            root.left = delete(root.left, element);
        } else if (element.compareTo(root.element) > 0) {
            root.right = delete(root.right, element);
        } else {
            // node with only one child or no child
            if ((root.left == null) || (root.right == null)) {
                Node<E> temp = null;
                if (root.left != null) {
                    temp = root.left;
                } else if (root.right != null) {
                    temp = root.right;
                }

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else { // One child case
                    root = temp; // Copy the contents of the non-empty child
                }
            } else {
                // node with two children: get the inorder
                // successor (smallest in the right subtree)
                Node<E> temp = minValueNode(root.right);

                // copy the inorder successor's data to this node
                root.element = temp.element;

                // delete the inorder successor
                root.right = delete(root.right, temp.element);
            }
        }

        // if the tree had only one node then return
        if (root == null) {
            return root;
        }

        // update height of the current node
        root.height = max(height(root.left), height(root.right)) + 1;

        // get the balance factor
        int balance = getBalance(root);

        // if this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // find
    public Node<E> find(E element) {
        Node<E> current = root;
        while (current.element.compareTo(element) != 0) {
            if (element.compareTo(current.element) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
            if (current == null) {
                return null;
            }
        }
        return current;
    }

    // traverse
    public void traverse(int traverseType) {
        switch (traverseType) {
            case 1:
                System.out.print("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2:
                System.out.print("\nInorder traversal: ");
                inOrder(root);
                break;
            case 3:
                System.out.print("\nPostorder traversal: ");
                postOrder(root);
                break;
            case 4:
                System.out.print("\nLevelorder traversal: ");
                levelOrder(root);
                break;
        }
        System.out.println();
    }

    // preOrder
    private void preOrder(Node<E> localRoot) {
        if (localRoot != null) {
            System.out.print(localRoot.element + " ");
            preOrder(localRoot.left);
            preOrder(localRoot.right);
        }
    }

    // inOrder
    private void inOrder(Node<E> localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.left);
            System.out.print(localRoot.element + " ");
            inOrder(localRoot.right);
        }
    }

    // postOrder
    private void postOrder(Node<E> localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.left);
            postOrder(localRoot.right);
            System.out.print(localRoot.element + " ");
        }
    }

    // levelOrder
    private void levelOrder(Node<E> localRoot) {
        Queue<Node<E>> queue = new LinkedList<Node<E>>();
        queue.add(localRoot);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            System.out.print(node.element + " ");
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        printTree(root, "", true);
        return sb.toString();
    }

    private void printTree(Node<E> node, String indent, boolean last) {
        if (node != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }
            System.out.println(node.element);
            printTree(node.left, indent, false);
            printTree(node.right, indent, true);
        }
    }

    public boolean isAVLTree() {
        return isAVLTree(root);
    }

    private boolean isAVLTree(Node<E> node) {
        if (node == null) {
            return true;
        }

        // Check BST property
        if (node.left != null && node.left.element.compareTo(node.element) > 0) {
            return false;
        }
        if (node.right != null && node.right.element.compareTo(node.element) < 0) {
            return false;
        }

        // Check balance property
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }

        // Check recursively for all nodes
        return isAVLTree(node.left) && isAVLTree(node.right);
    }

    private int getHeight(Node<E> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    public static void main(String[] args) {

        // insert: O(log n)
        // delete: O(log n)
        // find: O(log n)
        // traverse: O(n)

        testIntegers(false);
        testKelas(true);

    }

    public static void testIntegers(boolean run) {
        if (!run) {
            return;
        }
        AVLTree<Integer> tree = new AVLTree<Integer>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(3);
        tree.insert(7);
        tree.insert(13);
        tree.insert(17);
        tree.insert(1);
        tree.insert(4);
        tree.insert(6);
        tree.insert(8);
        tree.insert(12);
        tree.insert(14);
        tree.insert(16);
        tree.insert(18);
        System.out.println(tree);
        System.out.println("Is AVL tree: " + tree.isAVLTree());

        // delete leaf node
        tree.delete(1);
        tree.delete(4);
        tree.delete(6);
        tree.delete(8);
        tree.delete(12);
        tree.delete(14);
        tree.delete(16);
        tree.delete(18);

        System.out.println(tree);
        System.out.println("Is AVL tree: " + tree.isAVLTree());

    }

    public static void testKelas(boolean run) {
        if (!run) {
            return;
        }

        AVLTree<Kelas> tree = new AVLTree<Kelas>();
        tree.insert(new Kelas("A", 100));
        tree.insert(new Kelas("B", 90));
        tree.insert(new Kelas("C", 10));
        tree.insert(new Kelas("D", 70));
        tree.insert(new Kelas("E", 60));
        tree.insert(new Kelas("F", 32));
        tree.insert(new Kelas("G", 40));
        tree.insert(new Kelas("H", 30));
        tree.insert(new Kelas("I", 60));
        tree.insert(new Kelas("J", 10));
        System.out.println(tree);

        // delete
        tree.delete(new Kelas("C", 10));
        tree.delete(new Kelas("D", 70));
        tree.delete(new Kelas("E", 60));
        tree.delete(new Kelas("F", 32));
        tree.delete(new Kelas("G", 40));
        System.out.println(tree);

        // find
        System.out.println(tree.find(new Kelas("H", 30)));

        System.out.println("Is AVL Tree (sorted & balance) " + tree.isAVLTree());

    }

}

class Kelas implements Comparable<Kelas> {
    private String name;
    private int capacity;

    public Kelas(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int compareTo(Kelas other) {
        // Compare based on the capacity attribute
        return Integer.compare(this.capacity, other.capacity);
    }

    @Override
    public String toString() {
        return "Kelas [name=" + name + ", capacity=" + capacity + "]";
    }
}
