// --== CS400 File Header Information ==--
// Name: Joash Shankar
// Email: jshankar@wisc.edu
// Team: Red
// Group: IE
// TA: Siddharth Mohan
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

/**
 * This class is used to create Bug objects that stores information such as the name, 
 * description, priority level, time created, ID, and if the bug is resolved or not
 * 
 * @author Joash Shankar
 */
public class Bug implements BugInterface{
  private boolean isResolved = false;       
  private final String name;        
  private final String description;         
  private final double priorityLevel;                 
  private final long timeCreated = System.currentTimeMillis();                           
  private final double ID; // Unique identifier which is calculated in the constructor with a formula using priorityLevel and timeCreated
                           // Older Bugs will have a larger ID than newer ones

  /**
   * First constructor that is primarily used for importing from the CSV file since the CSV file will specify if a bug is resolved or not
   * 
   * @param name of the bug
   * @param description of the bug
   * @param timeCreated stores the System.currentTimeMillis() when the bug was created
   * @param priorityLevel places a larger weight on critical bugs (greater priorityLevel value => more critical bug)
   * @param isResolved is a flag for determining if a bug has been fixed
   */
  public Bug(String name, long timeCreated, String description, double priorityLevel, boolean isResolved) {
    this.name = name;
    timeCreated = System.currentTimeMillis();
    this.description = description;
    this.priorityLevel = priorityLevel; 
    this.ID = (1.0 / (double)timeCreated) * priorityLevel;
    this.isResolved = isResolved;
  }
  
  /**
   * Second constructor does not take in isResolved, and is primarily used for the Frontend since added bugs can be assumed as unresolved
   * 
   * @param name of the bug
   * @param description of the bug
   * @param priorityLevel places a larger weight on critical bugs (greater priorityLevel value => more critical bug)
   */
  public Bug(String name, String description, double priorityLevel) {
    this.name = name;
    this.description = description;
    this.priorityLevel = priorityLevel;
    this.ID = (1.0 / (double)timeCreated) * priorityLevel;
  }
  
  /**
   * Checks if bug has been resolved or not
   * 
   * @return true if this bug has been fixed, false if not
   */
  @Override
  public boolean isResolved() {
    if (isResolved == true) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Toggles the Bug's isResolved field to close/reopen a bug
   */
  @Override
  public void toggleStatus() {
    isResolved = !isResolved;
  }

  /**
   * Getter method for the name of the bug
   * 
   * @return The name of this bug
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Getter method for the description of the bug
   * 
   * @return A description of this bug
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Getter method for the priority level of the bug
   * 
   * @return The priority level of the bug (from 0.0 to 10.0)
   */
  @Override
  public double getPriorityLevel() {
    return priorityLevel;
  }

  /**
   * Getter method for the time the bug was created
   * 
   * @return Time in milliseconds from epoch the bug was created
   */
  @Override
  public long getTimeCreated() {
    return timeCreated;
  }

  /**
   * Getter method for the ID of the bug
   * 
   * @return The unique identifier for the bug, which is computed as: ID = (1 / timeCreated) * priorityLevel
   */
  @Override
  public double getID() {
    return ID;
  }

  /**
   * Compares Bugs based on their IDs
   * 
   * @param otherBug Bug to compare to
   * @return 1 if this Bug should be fixed sooner than otherBug. 
   *         -1 if this Bug should be fixed after otherBug. 
   *         0 if bugs have equivalent urgency
  */
  @Override
  public int compareTo(BugInterface otherBug) {
    if (this.getPriorityLevel() == otherBug.getPriorityLevel()) {
      return 0;
    } 
    else if (this.getPriorityLevel() < otherBug.getPriorityLevel()) {
      return 1;
    } 
    else {
      return -1;
    }
  }
  
  /**
   * String representation after knowing information about the bug
   * 
   * @return a String representation of the bug
   */
  @Override
  public String toString() {    
    if (isResolved()) {
      return "\nName: " + getName() + "\nCreated on: " + getTimeCreated() + "\nDescription: " + getDescription() + "\nPriority: " + getPriorityLevel() + "\nResolved: yes" +  "\nID: " + getID();
    }
    else {
      return "\nName: " + getName() + "\nCreated on: " + getTimeCreated() + "\nDescription: " + getDescription() + "\nPriority: " + getPriorityLevel() + "\nResolved: no" +  "\nID: " + getID();
    }
  }
}
