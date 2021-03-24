import java.util.List;

public interface BackendInterface {
    /**
     * Adds bug to Rbt
     * @param title of bug
     * @param message of bug
     * @param priority of bug
     */
    public void addBug(String title, String message, double priority);

    /**
     * toggles bug
     * @param b bug to be toggled
     */
    public void toggleBug(BugInterface b);

    /**
     * returns list of up to 3 unresolved bugs
     * @param startingIndex starting index to add unresolved bugs
     * @return list of up to 3 unresolved bugs
     */
    public List<BugInterface> getThreeUnresolvedBugs(int startingIndex);

    /**
     * returns list of up to 3 resolved bugs
     * @param startingIndex starting index to add resolved bugs
     * @return list of up to 3 resolved bugs
     */
    public List<BugInterface> getThreeResolvedBugs(int startingIndex);

    /**
     * exports rbt into same or different csv
     * @param filePath to csv file
     */
    public void export(String filePath);
}
