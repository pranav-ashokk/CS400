// --== CS400 File Header Information ==--
// Name: Harrison White
// Email: hwhite9@wisc.edu
// Team: IE red
// Role: Frontend
// TA: Sid
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Assertions;

/**
 * This class contains 5 JUnit tests designed to test the frontend implementation of Project 2, which
 * is a software bug tracker.
 *
 * NOTE: It is important to run the tests ONE AT A TIME, because the output depends on the first
 * time a user enters Bug Expansion Mode (and since it's static you can't just create a new object each
 * time).
 * @author Harrison White
 */
public class FrontEndDeveloperTests {
    private final PrintStream standardOut = System.out;
    private final InputStream standardIn = System.in;
    private final String welcomeMessage = "Welcome to the Bug Tracker! Here's a list of the possible commands:\n"
            + "\tType 'a' to add a bug\n"
            + "\tType 'c' to see the top three unresolved bugs and enter bug expansion mode\n"
            + "\tType 'r' to see the top three resolved bugs and enter bug expansion mode\n"
            + "\tType 's' to save the current bug lists to a .csv file\n"
            + "\tType 'x' to exit\n\nEnter command (base mode): ";
    private final String goodbyeMessage = "\nEnter command (base mode): " +
            "Thanks for using the Bug Tracker!\n";
    private final String bugExpansionMessage = "\nYou are now in bug expansion mode.\n"
            + "\tType 'n' to see the next three bugs, or 'p' to get the previous 3\n"
            + "\tType a bug's number to expand it and see all info\n"
            + "\t\tFrom there, press 't' to toggle it as resolved/unresolved or 'x' to return to bug expansion mode\n"
            + "\tType 'x' to return to the base mode\n\nEnter command (bug expansion mode): ";
    InputStream inputStreamSimulator;
    ByteArrayOutputStream outputStreamCaptor;

    /**
     * This method gets called before every test to change the input stream to toInput and capture
     * the output stream.
     * @param toInput string to feed as input to the frontend
     */
    public void setUp(String toInput) {
        // set the input stream to our input
        inputStreamSimulator = new ByteArrayInputStream(toInput.getBytes());
        System.setIn(inputStreamSimulator);
        outputStreamCaptor = new ByteArrayOutputStream();
        // set the output to the stream captor to read the output of the front end
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * This method gets called after each test concludes running the frontend, to reset the input and
     * output to normal and ensure results can be printed regularly.
     * The frontend is intended to be run with a dummy backend class that will return true (indicating
     * a successful operation) and a fixed result for many methods.
     */
    public void tearDown() {
        System.setOut(standardOut);
        System.setIn(standardIn);
    }

    /**
     * Tests the frontend's ability to add a new bug by simulating a user entering a bug with the title
     * "Title", description "Description", and an urgency rating of 10.0.
     */
    @Test
    public void testAddBug() {
        setUp("a\nTitle\nDescription\n10.0\nx\n");
        Frontend.main(new String[]{});
        tearDown();
        // expectedOut is meant to be whatever returns from the dummy class upon a successful addition
        String expectedOut = welcomeMessage + "Enter bug's title: " + "Enter bug's description: " +
                "Enter urgency (0.0 [least urgent] to 10.0 [most urgent]): " +
                "Bug added!\n" + "\nEnter command (base mode): " + "Thanks for using the Bug Tracker!\n";
        Assertions.assertEquals(expectedOut, outputStreamCaptor.toString());
    }

    /**
     * Tests the frontend's ability to print the list of current unresolved bugs by simulating a user
     * entering "c" and then exiting the program.
     */
    @Test
    public void testPrintCurrentBugs() {
        setUp("c\nx\nx\n");
        Frontend.main(new String[]{});
        tearDown();

        // expectedOut is whatever returns from the dummy class
        String expectedOut = welcomeMessage + "\t1. Bug number 1\n\t2. Bug number 2\n\t3. Bug number 3\n"
                + bugExpansionMessage + goodbyeMessage;
        Assertions.assertEquals(expectedOut, outputStreamCaptor.toString());
    }

    /**
     * Tests the frontend's ability to print out a list of resolved bugs by simulating a user entering
     * "r" and then exiting the program.
     */
    @Test
    public void testPrintResolvedBugs() {
        setUp("r\nx\nx\n");
        Frontend.main(new String[]{});
        tearDown();

        // expectedOut is whatever returns from the dummy class
        String expectedOut = welcomeMessage + "\t1. Bug number 4\n\t2. Bug number 5\n\t3. Bug number 6\n"
                + bugExpansionMessage + goodbyeMessage;
        Assertions.assertEquals(expectedOut, outputStreamCaptor.toString());
    }

    /**
     * Tests the frontend's ability to toggle a bug from resolved to unresolved by simulating a user
     * entering "c", then selecting the first bug by entering "1" and toggling it as resolved by
     * entering "t", then entering "r" to get a list of resolved bugs, selecting the first one by
     * entering "1" toggling it with "t", and exiting the program.
     */
    @Test
    public void testToggleBug() {
        setUp("c\n1\nt\nx\nx\nr\n1\nt\nx\nx\nx\n");
        Frontend.main(new String[]{});
        tearDown();

        // expectedOut is whatever returns from the dummy class
        String expectedOut = welcomeMessage + "\t1. Bug number 1\n\t2. Bug number 2\n\t3. Bug number 3\n"
                + bugExpansionMessage + "Bug number 1\n" + "\nEnter command (t to toggle or x to return to bug expansion mode): " +
                "Bug set to unresolved\n" + "\nEnter command (t to toggle or x to return to bug expansion mode): " +
                "\nEnter command (bug expansion mode): " + "\nEnter command (base mode): " +
                "\t1. Bug number 4\n\t2. Bug number 5\n\t3. Bug number 6\n" + "\nEnter command (bug expansion mode): " +
                "Bug number 4\n" + "\nEnter command (t to toggle or x to return to bug expansion mode): " +
                "Bug set to unresolved\n" + "\nEnter command (t to toggle or x to return to bug expansion mode): " +
                "\nEnter command (bug expansion mode): " + goodbyeMessage;
        Assertions.assertEquals(expectedOut, outputStreamCaptor.toString());
    }

    /**
     * Tests the frontend's ability to pass the export command on to the backend by simulating a user
     * entering "s" and then exiting the program.
     */
    @Test
    public void testExport() {
        setUp("s\n/Filepath/to/csv.csv\nx\n");
        Frontend.main(new String[]{});
        tearDown();

        // expectedOut is whatever returns from the dummy class
        String expectedOut = welcomeMessage + "Enter filepath to save the CSV to: " + "CSV file saved!\n" + goodbyeMessage;
        Assertions.assertEquals(expectedOut, outputStreamCaptor.toString());
    }
}

