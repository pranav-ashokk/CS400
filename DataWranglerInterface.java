// --== CS400 File Header Information ==--
// Name: Joash Shankar
// Email: jshankar@wisc.edu
// Team: Red
// Group: IE
// TA: Siddharth Mohan
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.zip.DataFormatException;

/**
 * Contains the methods which are to be implemented in the DataWrangler class
 * 
 * @author Joash Shankar
 */
public interface DataWranglerInterface {
  
    /**
     * Reads a CSV file and makes a RB-Tree of the bugs listed.
     * 
     * @param filePath path to the CSV
     * @return RB-Tree representing the contents of the file
     * @throws FileNotFoundException if CSV is not located at passed path
     * @throws DataFormatException if an anomaly is detected in the CSV file
     * @throws IOException If something funky happens
     */
    public RedBlackTree<BugInterface> importBugSet(Reader input) throws FileNotFoundException, DataFormatException, IOException;
    
    /**
     * Exports a Bug tree to a CSV file for sharing with other applications.
     * 
     * @param tree Tree to export to CSV
     * @param filePath location to save contents to. Must be .csv
     * @throws IOException If something funky happens
     * @throws IllegalArgumentException if the filePath does not end with .csv
     */
    public void export(RedBlackTree<BugInterface> tree, String filePath) throws IOException, IllegalArgumentException;
}