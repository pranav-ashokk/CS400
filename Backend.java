import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * The BackendClass uses the Data Wrangler's interface to create new Bug objects and import and export Red Black
 * Trees from a given CSV file. The backend code will also implement methods to add, resolve, and return a list of resolved
 * or unresolved bugs to and from Red Black Trees
 * @author Andrew Liao
 */
public class Backend implements BackendInterface{

    private RedBlackTree<BugInterface> rbt;
    private List<Bug> listOfAllBugs;
    private List<Bug> listOfUnresolvedBugs;
    private List<Bug> listOfResolvedBugs;

    /**
     *Constructor for String[] that reads data file passed through command line
     * Puts Bugs data from csv file into a Red Black Tree
     * @param args the command line argument array
     * @throws FileNotFoundException
     */
    public Backend (String args[]){
//        try{ new FileReader(args[0]);}
//        catch(FileNotFoundException e){
//            System.out.println("CSV file not found. Creating new bug structure.");
//        }
        DataWrangler dataInput = new DataWrangler();

        if(args.length == 0){
            rbt = new RedBlackTree<>();
        }
        else {
            try {
                FileReader r = new FileReader(args[0]);
                rbt = dataInput.importBugSet(r);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DataFormatException e) {
                e.printStackTrace();
            }
        }
        listOfAllBugs = new ArrayList<>();
        listOfUnresolvedBugs = new ArrayList<>();
        listOfResolvedBugs = new ArrayList<>();
    }

    /**
     * Constructor for Reader that reads data file passed through command line
     * Puts Bugs from csv file into a Red Black Tree
     * @param r
     */
    public Backend(Reader r){
        DataWrangler dataInput = new DataWrangler();
        try {
            rbt = dataInput.importBugSet(r);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        listOfAllBugs = new ArrayList<>();
        listOfUnresolvedBugs = new ArrayList<>();
        listOfResolvedBugs = new ArrayList<>();
    }

    /**
     * Private helper method to load listOfAllBugs with all bugs in RBT in level order
     * @param rbt rbt that holds all bugs
     */
    private void getAllBugs(RedBlackTree rbt){
        listOfAllBugs.clear();
        Iterator<Bug> treeNodeIterator = rbt.iterator();

        while (treeNodeIterator.hasNext()) {
            Bug data = treeNodeIterator.next();
            listOfAllBugs.add(data);
        }
    }

    /**
     * Private helper method to load listOfUnresolvedBugs with all unresolved bugs in RBT in level order
     */
    private void getAllUnresolved(){
        listOfUnresolvedBugs.clear();
        getAllBugs(rbt);
        for(Bug bug : listOfAllBugs){
            if(!bug.isResolved())
                listOfUnresolvedBugs.add(bug);
        }
    }

    /**
     * Private helper method to load listOfAllResolvedBugs with all resolved bugs in RBT in level order
     */
    private void getAllResolved(){
        listOfResolvedBugs.clear();
        getAllBugs(rbt);
        for(Bug bug : listOfAllBugs){
            if(bug.isResolved())
                listOfResolvedBugs.add(bug);
        }
    }

    /**
     * Creates a new Bug object and inserts it into the Red Black Tree
     * @param title Title of the bug
     * @param message Message of the Bug
     * @param priority Priority of bug calculated by formula in DataWrangler code
     */
    @Override
    public void addBug(String title, String message, double priority) {
        Bug bug = new Bug(title, message, priority);
        rbt.insert(bug);
        listOfUnresolvedBugs.add(bug);
    }

    /**
     * Finds a specific Bug by its reference, and classifies this bug as either resolved or unresolved.
     * If Bug b is not in the rbt, do nothing
     * @param b Bug that is being toggled
     */
    @Override
    public void toggleBug(BugInterface b) {
        if(rbt.contains(b)){
            b.toggleStatus();
            getAllResolved();
            getAllUnresolved();
        }
    }

    /**
     * Returns a list of unresolved bugs starting at a user inputted index
     * @param startingIndex starting index to add unresolved bugs
     * @return empty list if invalid input, otherwise return list of up to 3 unresolved bugs
     */
    @Override
    public List<BugInterface> getThreeUnresolvedBugs(int startingIndex) {
        getAllUnresolved();

        List<BugInterface> listOfThree = new ArrayList<>();
        if(startingIndex>listOfUnresolvedBugs.size()){
            return listOfThree;
        }

        try{
            for(int i = startingIndex; i<startingIndex+3; i++){
                listOfThree.add(listOfUnresolvedBugs.get(i));
            }
        }
        catch(IndexOutOfBoundsException e){
            return listOfThree;
        }
        return listOfThree;


    }

    /**
     * Returns a list of resolved bugs starting at a user inputted index
     * @param startingIndex starting index to add resolved bugs
     * @return empty list if invalid input, otherwise return list of up to 3 resolved bugs
     */
    @Override
    public List<BugInterface> getThreeResolvedBugs(int startingIndex) {
        getAllResolved();

        List<BugInterface> listOfThree = new ArrayList<>();
        if(startingIndex>listOfResolvedBugs.size()){
            return listOfThree;
        }

        try{
            for(int i = startingIndex; i<startingIndex+3; i++){
                listOfThree.add(listOfResolvedBugs.get(i));
            }
        }
        catch(IndexOutOfBoundsException e){
            return listOfThree;
        }
        return listOfThree;
    }

    /**
     * use the export() method from DataWrangler to save most recently changed RBT back into either the same or
     * different csv file.
     * @param filePath
     */
    @Override
    public void export(String filePath) {
        DataWrangler dataExport = new DataWrangler();
        try{
            dataExport.export(rbt, filePath);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
