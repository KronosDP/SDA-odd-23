import java.util.NoSuchElementException;

class DoublyLinkedList<E extends Comparable<E>> {

    private static class Node<E> {

        Node<E> next;
        Node<E> prev;
        E value;

        private Node(E value, Node<E> next, Node<E> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
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
    public DoublyLinkedList() {
        // creation of the dummy node
        head = new Node<E>(null, null, null);
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
            throw new NullPointerException("Cannot add null element to the list");
        }
        Node<E> newNode = new Node<E>(value, null, tail);
        if (tail == head) {
            head.next = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    // utility function for traversing the list
    public String toString() {
        Node<E> p = head.next;
        String s = "[ ";
        while (p != null) {
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
        if (pos >= size || pos < 0) {
            throw new IndexOutOfBoundsException("position cannot be greater than size or negative");
        }
        Node<E> current = head.next;
        for (int i = 0; i < pos; i++) {
            current = current.next;
        }
        E saved = current.value;
        if (current.prev == head) {
            head.next = current.next;
        } else {
            current.prev.next = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }
        current = null;
        size--;
        return saved;
    }

    // O(N) time complexity
    public E get(int pos) {
        if (pos >= size) {
            throw new IndexOutOfBoundsException("position cannot be greater than size");
        } else if (pos < 0) {
            throw new IndexOutOfBoundsException("position cannot be negative");
        }
        Node<E> p = head.next;
        for (int i = 0; i < pos; i++) {
            p = p.next;
        }
        return p.value;
    }

    // O(N) time complexity
    public E set(int pos, E value) {
        if (pos >= size) {
            throw new IndexOutOfBoundsException("position cannot be greater than size");
        } else if (pos < 0) {
            throw new IndexOutOfBoundsException("position cannot be negative");
        }
        Node<E> p = head.next;
        for (int i = 0; i < pos; i++) {
            p = p.next;
        }
        E saved = p.value;
        p.value = value;
        return saved;
    }

    // O(1) time complexity
    public E removeFirst() {
        if (size == 0) throw new NoSuchElementException();
        Node<E> temp = head;
        if (head.next != null) {
            head.next.prev = null;
        } else {
            tail = null;
        }
        head = head.next;
        size--;
        return temp.value;
    }

    // O(1) time complexity
    public E removeLast() {
        if (size == 0) throw new NoSuchElementException();
        Node<E> temp = tail;
        if (tail.prev != null) {
            tail.prev.next = null;
        } else {
            head = null;
        }
        tail = tail.prev;
        size--;
        return temp.value;
    }

    // O(1) time complexity
    public void addFirst(E value) {
        Node<E> newNode = new Node<E>(value, head, null);
        if (head != null) {
            head.prev = newNode;
        }
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        size++;
    }

    // O(1) time complexity
    public void addLast(E value) {
        Node<E> newNode = new Node<E>(value, null, tail);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
    }

    // O(N) time complexity
    public void add(int pos, E value) {
        if (pos > size) {
            throw new IndexOutOfBoundsException("position cannot be greater than size");
        } else if (pos < 0) {
            throw new IndexOutOfBoundsException("position cannot be negative");
        }
        Node<E> before = head;
        for (int i = 0; i < pos; i++) {
            before = before.next;
        }
        Node<E> newNode = new Node<E>(value, before.next, before);
        if (before.next != null) {
            before.next.prev = newNode;
        }
        before.next = newNode;
        if (before == tail) {
            tail = newNode;
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
    public DoublyLinkedList<E> sortAscending() {
        if (isEmpty() || head.next == head.next.next) {
            // If the list is empty or has only one actual element (excluding dummy), it is already sorted.
            return this;
        }

        Node<E> start = head.next; // start from the first actual node, not the dummy
        Node<E> end = null;
        boolean swapped;

        do {
            swapped = false;
            Node<E> current = start;

            while (current.next != end) {
                Node<E> nextNode = current.next;

                // Compare using compareTo method of the Kelas class
                if (current.value.compareTo(nextNode.value) > 0) {
                    // Swap nodes
                    E temp = current.value;
                    current.value = nextNode.value;
                    nextNode.value = temp;
                    swapped = true;
                }

                current = current.next;
            }

            // Move end to the last swapped node
            end = current;

            // Now we need to go backwards
            Node<E> currentBack = end;

            while (currentBack.prev != start.prev) { // include the start node
                Node<E> prevNode = currentBack.prev;

                // Compare using compareTo method of the Kelas class
                if (currentBack.value.compareTo(prevNode.value) < 0) {
                    // Swap nodes
                    E temp = currentBack.value;
                    currentBack.value = prevNode.value;
                    prevNode.value = temp;
                    swapped = true;
                }

                currentBack = currentBack.prev;
            }

            // Move start to the first swapped node
            start = currentBack;

        } while (swapped);

        return this;
    }

    public DoublyLinkedList<E> sortDescending(){
        if (isEmpty() || head.next == head.next.next) {
            // If the list is empty or has only one actual element (excluding dummy), it is already sorted.
            return this;
        }

        Node<E> start = head.next; // start from the first actual node, not the dummy
        Node<E> end = null;
        boolean swapped;

        do {
            swapped = false;
            Node<E> current = start;

            while (current.next != end) {
                Node<E> nextNode = current.next;

                // Compare using compareTo method of the Kelas class
                if (current.value.compareTo(nextNode.value) < 0) {
                    // Swap nodes
                    E temp = current.value;
                    current.value = nextNode.value;
                    nextNode.value = temp;
                    swapped = true;
                }

                current = current.next;
            }

            // Move end to the last swapped node
            end = current;

            // Now we need to go backwards
            Node<E> currentBack = end;

            while (currentBack.prev != start.prev) { // include the start node
                Node<E> prevNode = currentBack.prev;

                // Compare using compareTo method of the Kelas class
                if (currentBack.value.compareTo(prevNode.value) > 0) {
                    // Swap nodes
                    E temp = currentBack.value;
                    currentBack.value = prevNode.value;
                    prevNode.value = temp;
                    swapped = true;
                }

                currentBack = currentBack.prev;
            }

            // Move start to the first swapped node
            start = currentBack;

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

    public DoublyLinkedList<E> getReverse() {
        DoublyLinkedList<E> reversed = new DoublyLinkedList<E>();
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
        // removeLast: O(1)
        // addFirst: O(1)
        // addLast: O(1)
        // add: O(N)
        // sort: O(N^2) because we use bubble sort

        // can only get and add from the 0th index and the last index
        // add means you can add new from the last index

        // testing for integer
        testInteger(true);

        // test integer edge case
        testIntegerEdgeCase(true);

        // test for object
        testObject(true);

    }

    public static void testInteger(boolean run) {
        if (!run) {
            return;
        }

        DoublyLinkedList<Integer> cl = new DoublyLinkedList<Integer>();

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

        DoublyLinkedList<Integer> cl = new DoublyLinkedList<Integer>();

        cl.append(1);
        cl.append(2);
        cl.append(3);

        // [ 1 , 2 , 3 ]
        System.out.println(cl.toString());

        cl.removeLast();
        cl.removeLast();

        // [ 1 ]
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

        Kelas g1 = new Kelas("A", 100);
        Kelas g2 = new Kelas("B", 90);
        Kelas g3 = new Kelas("C", 200);

        DoublyLinkedList<Kelas> cl = new DoublyLinkedList<Kelas>();

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
