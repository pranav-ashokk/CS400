// --== CS400 File Header Information ==--
// Name: Joash Shankar
// Email: jshankar@wisc.edu
// Team: Red
// Group: IE
// TA: Siddharth Mohan
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

/**
 * Contains the methods which are to be implemented in the Bug class
 *
 * @author Joash Shankar
 */
public interface BugInterface extends Comparable<BugInterface> {
    /*
    List of private fields within a Bug instance:
    boolean isResolved:        Flag for determining if bug has been fixed
    final String name:         Short name given to the bug
    final String description:        Description of the bug. Can be changed as the bug is being fixed
    final double priorityLevel:   Used to place larger weight on critical bugs.
                               Greater priorityLevel value => more critical bug
    final long timeCreated:    Stores the System.currentTimeMillis() when this bug
                               was created.

    final double ID:           Unique identifier which is calculated in the constructor.
                               Created with a formula using priorityLevel and timeCreated.
                               ID = (1 / timeCreated) * priorityLevel
                               Older Bugs will produce a larger ID than newer ones
    */

    /*
    Two example initialization for a Bug object:

    First constructor takes in isResolved. Primarily used for importing from CSV since the CSV will specify this value
        BugInterface bug = new Bug(String name, String description, long timeCreated, double priorityLevel, boolean isResolved);

    Second constructor does not take in isResolved. Primarily used for Frontend since bugs which are added can be assumed unresolved.
        BugInterface bug = new Bug(String name, String description, long timeCreated, double priorityLevel);
    */

    /**
     * @return true if this bug has been fixed, false if not
     */
    public boolean isResolved();

    /**
     * Toggles the Bug's isResolved field to close/reopen a bug
     */
    public void toggleStatus();

    /**
     * @return The name of this bug
     */
    public String getName();

    /**
     * @return A description of this bug
     */
    public String getDescription();

    /**
     * @return The priority level/urgency of this bug from 0.0 to 10.0
     */
    public double getPriorityLevel();

    /**
     * @return Time in milliseconds from epoch this Bug was created
     */
    public long getTimeCreated();

    /**
     * @return The unique identifier for this Bug, which is computed using the following formula:
     *         ID = (1 / timeCreated) * priorityLevel
     */
    public double getID();

    /**
     * Compares Bugs based on ID.
     *
     * @param otherBug Bug to compare to
     * @return 1 if this Bug should be fixed sooner than otherBug.
     *         -1 if this Bug should be fixed after otherBug. 0 if bugs have equivalent urgency
     */
    public int compareTo(BugInterface otherBug);

    /**
     * @return a String representation of this Bug.
     */
    public String toString();
}
