public class CircleLinkedList<E extends Comparable<E>> {

    private static class Node<E> {

        Node<E> next;
        E value;

        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    // For better O.O design this should be private allows for better black box
    // design
    private int size;
    // this will point to dummy node;
    private Node<E> head = null;
    private Node<E> tail = null; // keeping a tail pointer to keep track of the end of list

    // constructor for class.. here we will make a dummy node for circly linked list
    // implementation
    // with reduced error catching as our list will never be empty;
    public CircleLinkedList() {
        // creation of the dummy node
        head = new Node<E>(null, head);
        tail = head;
        size = 0;
    }

    // getter for the size... needed because size is private.
    public int getSize() {
        return size;
    }

    // for the sake of simplistiy this class will only contain the append function
    // or addLast other
    // add functions can be implemented however this is the basses of them all
    // really.
    public void append(E value) {
        if (value == null) {
            // we do not want to add null elements to the list.
            throw new NullPointerException("Cannot add null element to the list");
        }
        // head.next points to the last element;
        if (tail == null) {
            tail = new Node<E>(value, head);
            head.next = tail;
        } else {
            tail.next = new Node<E>(value, head);
            tail = tail.next;
        }
        size++;
    }

    // utility function for traversing the list
    public String toString() {
        Node<E> p = head.next;
        String s = "[ ";
        while (p != head) {
            s += p.value;
            if (p != tail) {
                s += " , ";
            }
            p = p.next;
        }
        return s + " ]";
    }

    // O(N) time complexity
    public E remove(int pos) {
        if (pos > size || pos < 0) {
            // catching errors
            throw new IndexOutOfBoundsException("position cannot be greater than size or negative");
        }
        // we need to keep track of the element before the element we want to remove we
        // can see why
        // bellow.
        Node<E> before = head;
        for (int i = 1; i <= pos; i++) {
            before = before.next;
        }
        Node<E> destroy = before.next;
        E saved = destroy.value;
        // assigning the next reference to the element following the element we want to
        // remove...
        // the last element will be assigned to the head.
        before.next = before.next.next;
        // scrubbing
        if (destroy == tail) {
            tail = before;
        }
        destroy = null;
        size--;
        return saved;
    }

    // O(N) time complexity
    public E get(int pos) {
        if (pos >= size) {
            // catching errors
            throw new IndexOutOfBoundsException("position cannot be greater than size");
        } else if (pos < 0) {
            throw new IndexOutOfBoundsException("position cannot be negative");
        }
        Node<E> p = head.next;
        for (int i = 1; i <= pos; i++) {
            p = p.next;
        }
        return p.value;
    }

    // O(N) time complexity
    public E set(int pos, E value) {
        if (pos >= size) {
            // catching errors
            throw new IndexOutOfBoundsException("position cannot be greater than size");
        } else if (pos < 0) {
            throw new IndexOutOfBoundsException("position cannot be negative");
        }
        Node<E> p = head.next;
        for (int i = 1; i <= pos; i++) {
            p = p.next;
        }
        E saved = p.value;
        p.value = value;
        return saved;
    }

    // O(1) time complexity
    public E removeFirst() {
        return remove(0);
    }

    // O(N) time complexity
    public E removeLast() {
        return remove(size - 1);
    }

    // O(1) time complexity
    public void addFirst(E value) {
        add(0, value);
    }

    // O(N) time complexity
    public void addLast(E value) {
        add(size, value);
    }

    // O(N) time complexity
    public void add(int pos, E value) {
        if (pos > size) {
            // catching errors
            throw new IndexOutOfBoundsException("position cannot be greater than size");
        } else if (pos < 0) {
            throw new IndexOutOfBoundsException("position cannot be negative");
        }
        Node<E> before = head;
        for (int i = 1; i <= pos; i++) {
            before = before.next;
        }
        before.next = new Node<E>(value, before.next);
        if (before == tail) {
            tail = before.next;
        }
        size++;
    }

    /*
     * method for sort list in ascending order
     * sort by a specific attribute of class
     * becareful, tbis is bubble sort so it is O(N^2)
     * 
     * @return sorted list
     */
    public CircleLinkedList<E> sortAscending() {
        if (isEmpty() || head.next == head.next.next) {
            // If the list is empty or has only one element, it is already sorted.
            return this;
        }

        boolean swapped;
        Node<E> current;
        Node<E> tail = head; // tail starts from the dummy node

        do {
            swapped = false;
            current = head.next; // start from the node next to the dummy node

            while (current.next != tail) {
                Node<E> nextNode = current.next;

                // Compare using compareTo method of the Grade class
                if (current.value.compareTo(nextNode.value) > 0) {
                    // Swap nodes
                    E temp = current.value;
                    current.value = nextNode.value;
                    nextNode.value = temp;
                    swapped = true;
                }

                current = current.next;
            }

            // Move tail to the last swapped node
            tail = current;

        } while (swapped);

        return this;
    }

    public CircleLinkedList<E> sortDescending() {
        if (isEmpty() || head.next == head.next.next) {
            // If the list is empty or has only one element, it is already sorted.
            return this;
        }

        boolean swapped;
        Node<E> current;
        Node<E> tail = head; // tail starts from the dummy node

        do {
            swapped = false;
            current = head.next; // start from the node next to the dummy node

            while (current.next != tail) {
                Node<E> nextNode = current.next;

                // Compare using compareTo method of the Grade class
                if (current.value.compareTo(nextNode.value) < 0) {
                    // Swap nodes
                    E temp = current.value;
                    current.value = nextNode.value;
                    nextNode.value = temp;
                    swapped = true;
                }

                current = current.next;
            }

            // Move tail to the last swapped node
            tail = current;

        } while (swapped);

        return this;
    }

    public void reverse() {
        if (isEmpty() || head.next == head.next.next) {
            // If the list is empty or has only one element, it is already sorted.
            return;
        }

        Node<E> current = head.next;
        Node<E> prev = null;
        Node<E> next = null;

        while (current != head) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        head.next = prev;
    }

    public CircleLinkedList<E> getReverse() {
        CircleLinkedList<E> reversed = new CircleLinkedList<E>();
        Node<E> current = head.next;
        while (current != head) {
            reversed.addFirst(current.value);
            current = current.next;
        }
        return reversed;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        // Basically one way linked list
        // Write all time complexity here

        // remove: O(N)
        // get: O(N)
        // set: O(N)
        // removeFirst: O(1)
        // removeLast: O(N)
        // addFirst: O(1)
        // addLast: O(N)
        // add: O(N)
        // sort: O(N^2) because we use bubble sort

        // can only get and add from the 0th index and the last index
        // add means you can add new from the last index

        // testing for integer
        testInteger(false);

        // test integer edge case
        testIntegerEdgeCase(true);

        // test for object
        testObject(false);

    }

    public static void testInteger(boolean run) {
        if (!run) {
            return;
        }

        CircleLinkedList<Integer> cl = new CircleLinkedList<Integer>();

        cl.append(1);
        cl.append(2);
        cl.append(3);

        // [ 1 , 2 , 3 ]
        System.out.println(cl.toString());

        // 2
        System.out.println(cl.remove(1));

        // [ 1 , 3 ]
        System.out.println(cl.toString());

        cl.addFirst(0);
        cl.addLast(4);

        // [ 0 , 1 , 3 , 4 ]
        System.out.println(cl.toString());

        cl.add(2, 2);

        // [ 0 , 1 , 2 , 3 , 4 ]
        System.out.println(cl.toString());

        cl.removeFirst();
        cl.removeLast();

        // [ 1 , 2 , 3 ]
        System.out.println(cl.toString());

        cl.set(0, 10);
        cl.set(1, 20);
        cl.set(2, 30);

        // [ 10 , 20 , 30 ]
        System.out.println(cl.toString());

        cl.removeFirst();
        cl.removeLast();

        // [ 20 ]
        System.out.println(cl.toString());

        cl.addFirst(0);
        cl.addLast(4);

        // [ 0 , 20 , 4 ]
        System.out.println(cl.toString());

        // 0
        System.out.println(cl.get(0));
        // 20
        System.out.println(cl.get(1));
        // 4
        System.out.println(cl.get(2));
    }

    public static void testIntegerEdgeCase(boolean run) {
        if (!run) {
            return;
        }

        CircleLinkedList<Integer> cl = new CircleLinkedList<Integer>();

        cl.append(1);
        cl.append(2);
        cl.append(3);

        // [ 1 , 2 , 3 ]
        System.out.println(cl.toString());

        cl.removeLast();
        cl.removeLast();

        System.out.println(cl.toString());

        System.out.println(cl.get(0));

        // this bellow makes it error
        // System.out.println(cl.get(1));

        cl.add(1, 2);

        System.out.println(cl.toString());
    }

    public static void testObject(boolean run) {
        if (!run) {
            return;
        }

        Grade g1 = new Grade("A", 100);
        Grade g2 = new Grade("B", 90);
        Grade g3 = new Grade("C", 200);

        CircleLinkedList<Grade> cl = new CircleLinkedList<Grade>();

        cl.append(g1);
        cl.append(g2);
        cl.append(g3);

        System.out.println(cl.toString());

        cl.sortAscending();

        System.out.println(cl.toString());

        cl.sortDescending();

        System.out.println(cl.toString());

    }

}
