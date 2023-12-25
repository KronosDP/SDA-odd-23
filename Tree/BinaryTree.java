import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<E extends Comparable<E>> {
    private Node<E> root;

    private class Node<T> {
        T element;
        Node<T> left;
        Node<T> right;

    }

    public BinaryTree() {
        root = null;
    }

    // insert
    public void insert(E element) {
        Node<E> newNode = new Node<E>();
        newNode.element = element;
        newNode.left = null;
        newNode.right = null;

        if (root == null) {
            root = newNode;
        } else {
            Node<E> current = root;
            Node<E> parent;

            while (true) {
                parent = current;
                if (element.compareTo(current.element) < 0) {
                    current = current.left;
                    if (current == null) {
                        parent.left = newNode;
                        return;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parent.right = newNode;
                        return;
                    }
                }
            }
        }
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

    // delete
    public boolean delete(E element) {
        Node<E> current = root;
        Node<E> parent = root;
        boolean isLeftChild = true;

        while (current.element.compareTo(element) != 0) {
            parent = current;
            if (element.compareTo(current.element) < 0) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            if (current == null) {
                return false;
            }
        }

        // case 1: no children
        if (current.left == null && current.right == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }

        // case 2: one child
        else if (current.right == null) {
            if (current == root) {
                root = current.left;
            } else if (isLeftChild) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        } else if (current.left == null) {
            if (current == root) {
                root = current.right;
            } else if (isLeftChild) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        }

        // case 3: two children
        else {
            Node<E> successor = getSuccessor(current);

            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }

            successor.left = current.left;
        }
        return true;
    }

    // getSuccessor
    private Node<E> getSuccessor(Node<E> node) {
        Node<E> parent = node;
        Node<E> successor = node;
        Node<E> current = node.right;

        while (current != null) {
            parent = successor;
            successor = current;
            current = current.left;
        }

        if (successor != node.right) {
            parent.left = successor.right;
            successor.right = node.right;
        }

        return successor;
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
            Node<E> node = queue.remove();
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

    public boolean isBinaryTree() {
        return isBinaryTree(root);
    }

    private boolean isBinaryTree(Node<E> node) {
        if (node == null) {
            return true;
        }
        if (node.left != null && node.left.element.compareTo(node.element) > 0) {
            return false;
        }
        if (node.right != null && node.right.element.compareTo(node.element) < 0) {
            return false;
        }
        return isBinaryTree(node.left) && isBinaryTree(node.right);
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node<E> node) {
        if (node == null) {
            return true;
        }
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }
        return isBalanced(node.left) && isBalanced(node.right);
    }

    private int getHeight(Node<E> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    public static void main(String[] args) {

        // insert: O(N)
        // find: O(N)
        // delete: O(N)

        testIntegers(false);
        testGrade(true);
    }

    public static void testIntegers(boolean run) {
        if (!run) {
            return;
        }
        BinaryTree<Integer> tree = new BinaryTree<Integer>();
        tree.insert(50);
        tree.insert(25);
        tree.insert(75);
        tree.insert(12);
        tree.insert(37);
        tree.insert(43);
        tree.insert(30);
        tree.insert(33);
        tree.insert(87);
        tree.insert(93);
        tree.insert(97);
        System.out.println(tree);

        // remove
        tree.delete(37);
        System.out.println(tree);

        // find
        System.out.println(tree.find(30));

        System.out.println("Is Binary Tree (sorted) " + tree.isBinaryTree());
        System.out.println("Is Tree Balanced " + tree.isBalanced());
    }

    public static void testGrade(boolean run){
        if (!run) {
            return;
        }
        BinaryTree<Grade> tree = new BinaryTree<Grade>();
        tree.insert(new Grade("A", 100));
        tree.insert(new Grade("B", 90));
        tree.insert(new Grade("C", 10));
        tree.insert(new Grade("D", 70));
        tree.insert(new Grade("E", 60));
        tree.insert(new Grade("F", 32));
        tree.insert(new Grade("G", 40));
        tree.insert(new Grade("H", 30));
        tree.insert(new Grade("I", 60));
        tree.insert(new Grade("J", 10));
        System.out.println(tree);

        // remove
        tree.delete(new Grade("G", 40));
        System.out.println(tree);

        // find
        System.out.println(tree.find(new Grade("H", 30)));

        System.out.println("Is Binary Tree (sorted) " + tree.isBinaryTree());
        System.out.println("Is Tree Balanced " + tree.isBalanced());
    }

}
