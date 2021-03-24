public class BugDummy implements BugInterface {
    private int data;
    public BugDummy(int data) {
        this.data = data;
    }
    public void blah() {
    }
    public String toString() {
        return "Bug number " + this.data;
    }
    public boolean isResolved() {
        return false;
    }
}