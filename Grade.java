public class Grade implements Comparable<Grade> {
    private String name;
    private int capacity;

    public Grade(String name, int capacity) {
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
    public int compareTo(Grade other) {
        // Compare based on the capacity attribute
        return Integer.compare(this.capacity, other.capacity);
    }

    @Override
    public String toString() {
        return "Grade [name=" + name + ", capacity=" + capacity + "]";
    }
}