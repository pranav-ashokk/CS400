// --== CS400 File Header Information ==--
// Name: Harrison White
// Email: hwhite9@wisc.edu
// Team: IE red
// Role: Frontend
// TA: Sid
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.List;
import java.util.Scanner;

/**
 * This class implements the frontend for Project Two, which is a CLI bug tracker.
 * @author Harrison White
 */
public class Frontend {
  private static BackendInterface backend;
  private static boolean firstTimeInExpansionMode = true;

  /**
   * Main method. Displays welcome information and instantiates backend
   * @param args cmd-line args used to get an optional path to a csv data structure
   */
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    backend = new Backend(args);

    System.out.println("Welcome to the Bug Tracker! Here's a list of the possible commands:\n"
        + "\tType 'a' to add a bug\n"
        + "\tType 'c' to see the top three unresolved bugs and enter bug expansion mode\n"
        + "\tType 'r' to see the top three resolved bugs and enter bug expansion mode\n"
        + "\tType 's' to save the current bug lists to a .csv file\n"
        + "\tType 'x' to exit");

    baseMode(in);
  }

  /**
   * This method handles "base mode", which is the default mode for the application. It allows users
   * to add a new bug, view lists of unresolved/resolved bugs, save the current bug structure to a
   * csv, and exit the application.
   * @param in Scanner for user input
   */
  public static void baseMode(Scanner in) {
    while (true) {
      System.out.print("\nEnter command (base mode): ");
      String nextCmd = in.next();

      // add new bug
      if (nextCmd.equals("a")) {
        in.nextLine(); // needed to clear the \n from entering the previous command
        System.out.print("Enter bug's title: ");
        String title = in.nextLine();
        System.out.print("Enter bug's description: ");
        String description = in.nextLine();
        System.out.print("Enter urgency (0.0 [least urgent] to 10.0 [most urgent]): ");
        double urgency;
        if (in.hasNextDouble() && (urgency = in.nextDouble()) <= 10.0 && urgency >= 0.0);
        else {
          in.nextLine(); // to clear the input
          System.out.println("Error: invalid urgency. Bug addition process aborted.");
          continue;
        }
        backend.addBug(title, description, urgency);
        System.out.println("Bug added!");
      }

      // view list of unresolved bugs
      else if (nextCmd.equals("c")) {
        List<BugInterface> bugList = backend.getThreeUnresolvedBugs(0);
        printBugsAndGoToExpansionMode(in, bugList, 0, false);
      }

      // view list of resolved bugs
      else if (nextCmd.equals("r")) {
        List<BugInterface> bugList = backend.getThreeResolvedBugs(0);
        printBugsAndGoToExpansionMode(in, bugList, 0, true);
      }

      // export csv
      else if (nextCmd.equals("s")) {
        System.out.print("Enter filepath to save the CSV to: ");
        String filepath = in.next();
        backend.export(filepath);
        System.out.println("CSV file saved!");
      }

      // exit program
      else if (nextCmd.equals("x")) {
        System.out.println("Thanks for using the Bug Tracker!");
        break;
      }

      else {
        System.out.println("Invalid command. Please try again.");
      }
    }
  }

  /**
   * This method handles "bug expansion mode", which is launched when a user requests a list of
   * resolved/unresolved bugs. It allows users to request the next or previous 3 items in the list
   * (creating a scrolling effect), expand a bug, or return to the base mode.
   * @param in Scanner for user input
   * @param lastIndex the last startingIndex that was used to get a list of three bugs
   * @param isResolvedList boolean to check if we're looking at the resolved or unresolved bug list
   */
  public static void bugExpansionMode(Scanner in, List<BugInterface> bugList, int lastIndex, boolean isResolvedList) {
    // print the commands if the user hasn't been in bug expansion mode before
    if (firstTimeInExpansionMode) {
      System.out.println("\nYou are now in bug expansion mode.\n"
          + "\tType 'n' to see the next three bugs, or 'p' to get the previous 3\n"
          + "\tType a bug's number to expand it and see all info\n"
          + "\t\tFrom there, press 't' to toggle it as resolved/unresolved or 'x' to return to bug expansion mode\n"
          + "\tType 'x' to return to the base mode");
      firstTimeInExpansionMode = false;
    }

    while (true) {
      System.out.print("\nEnter command (bug expansion mode): ");

      // if user is trying to select a bug
      if (in.hasNextInt()) {
        int selectedBugNum = in.nextInt();
        // ensure bug selected is valid
        if (selectedBugNum < (lastIndex + bugList.size() + 1) && selectedBugNum > (lastIndex)) {
          BugInterface selectedBug = bugList.get(selectedBugNum - lastIndex - 1);
          bugExpandedMode(in, selectedBug);
        }
        else {
          System.out.println("Error: invalid bug selected! Please choose a bug from the most recent list of three.");
        }
        continue;
      }

      // if input wasn't an int, it was a command. So proceed as normal
      String nextCmd = in.next();

      // get next bugs
      if (nextCmd.equals("n")) {
        lastIndex += 3;
        bugList = isResolvedList ? backend.getThreeResolvedBugs(lastIndex) : backend.getThreeUnresolvedBugs(lastIndex);
        printBugsAndGoToExpansionMode(in, bugList, lastIndex, isResolvedList);
        break;
      }

      // get previous bugs
      else if (nextCmd.equals("p")) {
        lastIndex -= 3;
        bugList = isResolvedList ? backend.getThreeResolvedBugs(lastIndex) : backend.getThreeUnresolvedBugs(lastIndex);
        printBugsAndGoToExpansionMode(in, bugList, lastIndex, isResolvedList);
        break;
      }

      // exit bug expansion mode
      else if (nextCmd.equals("x")) {
        break;
      }

      else {
        System.out.println("Invalid command. Please try again.");
      }
    }
  }

  /**
   * This method handles when a user expands a bug. It prints the bug and then allows the user to
   * toggle the bug as resolved/unresolved and exit back to bug expansion mode.
   * @param in Scanner for user input
   * @param selectedBug Bug the user selected
   */
  public static void bugExpandedMode(Scanner in, BugInterface selectedBug) {
    System.out.println(selectedBug);

    // loop to allow user to toggle bug or return to bug expansion mode
    while (true) {
      System.out.print("\nEnter command (t to toggle or x to return to bug expansion mode): ");
      String cmd = in.next();
      if (cmd.equals("t")) {
        backend.toggleBug(selectedBug);
        System.out.println("Bug set to " + (selectedBug.isResolved() ? "resolved" : "unresolved"));
      }
      else if (cmd.equals("x")) {
        break;
      }
      else {
        System.out.println("Invalid command. Please try again.");
      }
    }
  }

  /**
   * This method handles printing the bugs returned by backend.getThreeLatestXBugs and, if appropriate,
   * will launch bug expansion mode.
   * @param in Scanner for user input
   * @param bugList list of bugs to be printed
   * @param lastIndex the value for startingIndex the last time the list was fetched
   * @param isResolvedList boolean to check if we're printing resolved or unresolved bugs
   */
  public static void printBugsAndGoToExpansionMode(Scanner in, List<BugInterface> bugList, int lastIndex, boolean isResolvedList) {
    // check for edge case of empty list
    if (bugList.isEmpty()) {
      System.out.println("No bugs to display!");
    }
    else {
      for (int i = 0; i < bugList.size(); i++) {
        System.out.println("\t" + (i + lastIndex + 1) + ". " + bugList.get(i).toString());
      }
      bugExpansionMode(in, bugList, lastIndex, isResolvedList);
    }
  }
}
