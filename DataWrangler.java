// --== CS400 File Header Information ==--
// Name: Joash Shankar
// Email: jshankar@wisc.edu
// Team: Red
// Group: IE
// TA: Siddharth Mohan
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.zip.DataFormatException;

/**
 * Imports a CSV file to make a RBT of bugs and exports a RBT to make a CSV file
 * 
 * @author Joash Shankar
 */
public class DataWrangler implements DataWranglerInterface {

  /**
   * Reads a CSV file and makes a RBT of the bugs listed
   * 
   * @param input to the CSV
   * @return RBT representing the contents of the file
   * @throws FileNotFoundException if CSV is not located at passed path
   * @throws DataFormatException if an anomaly is detected in the CSV file
   * @throws IOException in case there's a predefined exception 
   */
  @Override
  public RedBlackTree<BugInterface> importBugSet(Reader input) throws FileNotFoundException, DataFormatException, IOException {
    
    BufferedReader br = new BufferedReader(input);
    RedBlackTree<BugInterface> bugRBT = new RedBlackTree<>();
    
    int totalFields = 0; // number of columns in file
    boolean headerLine = false; 
    String newLine = ""; // variable to store each new line read from the BufferedReader
    int[] indices = new int[5]; // the column numbers at which each of the values corresponding to the field of a Bug object are stored at
    
    while ((newLine = br.readLine()) != null) {
      
      if (!headerLine) {
        
        String[] bugValues = newLine.split(","); // assigns each Bug object data field with its respective column number
        totalFields = bugValues.length; 
            
        // goes through first line and assigns headers with the respective column indices
        for (int i = 0; i < bugValues.length; i++) {
          if (bugValues[i].equals("name")) {
            indices[0] = i;
          }
          else if (bugValues[i].equals("timeCreated")) {
            indices[1] = i;
          }
          else if (bugValues[i].equals("description")) {
            indices[2] = i;
          }
          else if (bugValues[i].equals("priorityLevel")) {
            indices[3] = i;
          }
          else if (bugValues[i].equals("isResolved")) {
            indices[4] = i;
          }
        }
        
        headerLine = true;
     }
     
     else {
       // separates line in terms of commas while ignoring the commas inside quotation marks
       // got Regex expression online - https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
       String[] finalBugValues = newLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            
       // checks if the number of data fields are the same
       if (finalBugValues.length != totalFields) {
         throw new DataFormatException("The given .csv file has an invalid format: The total number of columns does not equal the final number of bug values!"); 
       }
       
       // create a new bug based on the data extracted from columns
       Bug newBug = new Bug(finalBugValues[indices[0]], Long.valueOf(finalBugValues[indices[1]]), finalBugValues[indices[2]], Double.valueOf(finalBugValues[indices[3]]), Boolean.valueOf(finalBugValues[indices[4]]));
    
       // checks if priority level is valid   
       if(newBug.getPriorityLevel() < 0.0 || newBug.getPriorityLevel() > 10.0) {
         throw new DataFormatException("The given .csv file has an invalid format: Priority Level must be between 0.0 and 10.0!");
       }
    
       // if there's a data format error, then add Bug object to the RBT obtained from file reader   
       bugRBT.insert(newBug);
     }
   }
  
   return bugRBT;
  }


  /**
   * Exports a RBT to a CSV file for sharing with other applications
   * 
   * @param tree to export to CSV
   * @param filePath location to save contents to. (Must be .csv)
   * @throws IOException in case there's a predefined exception
   * @throws IllegalArgumentException if the filePath does not end with .csv
   */
  @Override
  public void export(RedBlackTree<BugInterface> tree, String filePath) throws IOException, IllegalArgumentException { 
    FileWriter writer = new FileWriter(filePath);
    
    // Write the CSV header to the file
    writer.write("name,timeCreated,description,priorityLevel,isResolved\n"); 
    
    Iterator<BugInterface> treeIterator = tree.iterator();
    
    // true case: while tree has a node (in-order traversal)
    while (treeIterator.hasNext()) { 
      // get all the bug's data fields for the CSV 
      BugInterface currentBug = treeIterator.next();
      String currentName = currentBug.getName();
      long currentTimeCreated = currentBug.getTimeCreated();
      String currentDescription = currentBug.getDescription();
      double currentPriority = currentBug.getPriorityLevel();
      boolean currentIsResolved = currentBug.isResolved();
      
      // format the string
      String bugLineCSV = currentName + "," + currentTimeCreated + "," + currentDescription + "," + currentPriority + "," + currentIsResolved + "\n";
      
      // write formatted string to the file 
      writer.write(bugLineCSV);
    }
    
    // false case: close the writer -> you're done
    writer.close();
    
  }
  
}
