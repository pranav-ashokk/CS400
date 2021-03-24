// --== CS400 File Header Information ==--
// Name: Joash Shankar
// Email: jshankar@wisc.edu
// Team: Red
// Group: IE
// TA: Siddharth Mohan
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

/**
 * This class contains a set of tests for the Bug and DataWrangler class implementations 
 * 
 * @author Joash Shankar
 */
public class DataWranglerTests {
  
  /**
   * Tests the functionality of importBugSet() to verify the successful import of bugs
   */
  @Test
  public void testImportBugSet() {
    long trackCurrentTime = System.currentTimeMillis();
    
    BugInterface bug1 = new Bug("First Bug", trackCurrentTime, "This tests the import", 5.0, false);  
    BugInterface bug2 = new Bug("Second Bug", trackCurrentTime, "This tests the import", 6.0, true);  
    BugInterface bug3 = new Bug("Third Bug", trackCurrentTime, "This tests the import", 7.0, false);  
    RedBlackTree<BugInterface> expected = new RedBlackTree<BugInterface>();
    
    expected.insert(bug1);
    expected.insert(bug2);
    expected.insert(bug3);
    
    Assertions.assertEquals(
        "[\nName: Second Bug\n" +
        "Created on: " + trackCurrentTime + "\n" + 
        "Description: This tests the import\n" + 
        "Priority: 6.0\n" +
        "Resolved: yes\n" +
        "ID: " + bug2.getID() + ", \n" +
        "Name: Third Bug\n" +
        "Created on: " + trackCurrentTime + "\n" + 
        "Description: This tests the import\n" + 
        "Priority: 7.0\n" +
        "Resolved: no\n" +
        "ID: " + bug3.getID() + ", \n" +
        "Name: First Bug\n" +
        "Created on: " + trackCurrentTime + "\n" + 
        "Description: This tests the import\n" + 
        "Priority: 5.0\n" +
        "Resolved: no\n" + 
        "ID: " + bug1.getID() + "]", expected.root.toString()); // makes sure RBT prints out expected level order (based on bug's ID) after feeding CSV in
                          
  }
  
  /**
   * Tests the functionality of export() to verify if the expected file contents in a CSV are produced
   * @return true if the test passed, false if its failed 
   */
  @Test
  public void testExport() {
    long trackCurrentTime = System.currentTimeMillis();
    
    BugInterface bug1 = new Bug("First Bug", trackCurrentTime, "This tests the export", 3.0, false);  
    BugInterface bug2 = new Bug("Second Bug", trackCurrentTime, "This tests the export", 5.0, false);
    BugInterface bug3 = new Bug("Third Bug", trackCurrentTime, "This tests the export", 7.0, false); 
    RedBlackTree<BugInterface> expected = new RedBlackTree<BugInterface>();
    DataWranglerInterface data = new DataWrangler();
    
    expected.insert(bug1);
    expected.insert(bug2);
    expected.insert(bug3);
    
    try {
      data.export(expected, "DataWranglerTests.csv");
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    try {
      Assertions.assertEquals(
          "name,timeCreated,description,priorityLevel,isResolved\n" + 
          "Third Bug," + trackCurrentTime + ",This tests the export,7.0,false\n" + 
          "Second Bug," + trackCurrentTime + ",This tests the export,5.0,false\n" + 
          "First Bug," + trackCurrentTime + ",This tests the export,3.0,false\n", Files.readString(Path.of("DataWranglerTests.csv")));
    } catch (IOException e) {
      e.printStackTrace();
    }    
  }
  
  /**
   * Test the functionality of compareTo() when comparing different bugs 
   * @return true if the test passed, false if its failed 
   */
  @Test
  public void testCompareTo() {
    BugInterface bug1 = new Bug("First Bug", 100, "This is for the first test of compareTo()", 2.0, false);  
    BugInterface bug2 = new Bug("Second Bug", 100, "This is for the first test of compareTo()", 7.0, false);  
    Assertions.assertEquals(1, bug1.compareTo(bug2), "bug1 should be smaller than bug2");
    
    BugInterface bug3 = new Bug("Third Bug", 100, "This is for the second test of compareTo()", 5.0, false);  
    BugInterface bug4 = new Bug("Fourth Bug", 100, "This is for the second test of compareTo()", 5.0, false);  
    Assertions.assertEquals(0, bug3.compareTo(bug4), "bug3 should be equal to bug4");
    
    BugInterface bug5 = new Bug("Fifth Bug", 100, "This is for the third test of compareTo()", 7.0, false);  
    BugInterface bug6 = new Bug("Sixth Bug", 100, "This is for the third test of compareTo()", 2.0, false);  
    Assertions.assertEquals(-1, bug5.compareTo(bug6), "bug5 should be larger than bug6");
  }
  
  /**
   * Tests the functionality of toString() to see if it produces the expected summary of the bug
   * @return true if the test passed, false if its failed 
   */
  @Test
  public void testToString() {
    long trackCurrentTime = System.currentTimeMillis();
    
    // if bug is resolved
    BugInterface bug1 = new Bug("First Bug", trackCurrentTime, "This is for testing toString() when the bug is resolved", 4.0, true);  
    String expected1 = "\nName: First Bug \nCreated on: " + Instant.ofEpochMilli(trackCurrentTime).toString() + "\nDescription: This is for testing toString() when the bug is resolved \nPriority: 4.0 \nResolved: yes \nID: " + bug1.getID(); 
    Assertions.assertEquals(
        "\nName: First Bug \n" + 
        "Created on: " + Instant.ofEpochMilli(trackCurrentTime).toString() + "\n" +
        "Description: This is for testing toString() when the bug is resolved \n" + 
        "Priority: 4.0 \n" + 
        "Resolved: yes \n" + 
        "ID: " + bug1.getID(), expected1);
    
    // if bug is not resolved
    BugInterface bug2 = new Bug("Second Bug", trackCurrentTime, "This is for testing toString() when the bug is unresolved", 4.0, false);  
    String expected2 = "\nName: Second Bug \nCreated on: " + Instant.ofEpochMilli(trackCurrentTime).toString() + "\nDescription: This is for testing toString() when the bug is unresolved \nPriority: 4.0 \nResolved: no \nID: " + bug2.getID(); 
    Assertions.assertEquals(
        "\nName: Second Bug \n" + 
        "Created on: " + Instant.ofEpochMilli(trackCurrentTime).toString() + "\n" +
        "Description: This is for testing toString() when the bug is unresolved \n" + 
        "Priority: 4.0 \n" + 
        "Resolved: no \n" + 
        "ID: " + bug2.getID(), expected2);
  }
  
  /**
   * Tests the functionality of toggleStatus() to see if it changes a bug's isResolved field 
   * @return true if the test passed, false if its failed 
   */
  @Test
  public void testToggleStatus() {
    BugInterface testToggle1 = new Bug("First Bug", 10, "This tests toggleStatus() if the bug is resolved", 6.0, true);  
    BugInterface testToggle2 = new Bug("Second Bug", 20, "This tests toggleStatus() if the bug is unresolved", 6.0, false);  
    
    testToggle1.toggleStatus(); // if bug object's isResolved parameter is originally true
    testToggle2.toggleStatus(); // if bug object's isResolved parameter is originally false

    Assertions.assertEquals(false, testToggle1.isResolved());
    Assertions.assertEquals(true, testToggle2.isResolved());
  }
}
