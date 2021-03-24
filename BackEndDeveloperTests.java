import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.*;

public class BackEndDeveloperTests {

    @Test
    /**
     *Test to get three resolved bugs from a non-empty RBT with only unresolved bugs
     */
    public void testGetThreeResolvedBugsFromOnlyUnresolved(){
        String [] test = {};
        BackendInterface backend = new Backend(test);

        backend.addBug("Test Bug", "This bug is being added to the RBT", 10.0);
        assertTrue(backend.getThreeResolvedBugs(0).size() == 0);
    }

        /**
         *Test to add a bug to the RBT
         */

    @Test
    public void testAddBug(){
        String [] test = {};
        BackendInterface backend = new Backend(test);

        backend.addBug("Test Bug", "This bug is being added to the RBT", 10.0);
        assertTrue(backend.getThreeUnresolvedBugs(0).size() == 1);
    }


    /**
     *Test to resolve a bug in the RBT
     */

    @Test
    public void testResolveBug(){
        String [] test = {};
        BackendInterface backend = new Backend(test);;
        backend.addBug("Test Bug", "This bug is being added to the RBT", 10.0);
        backend.toggleBug(backend.getThreeUnresolvedBugs(0).get(0));
        assertTrue(backend.getThreeUnresolvedBugs(0).size() == 0);
    }

    /**
     *Test to get three unresolved bugs sorted by priority from a non-empty RBT
     */

    @Test
    public void testGetThreeUnresolvedBugs(){
        String [] test = {};
        BackendInterface backend = new Backend(test);
        backend.addBug("Test Bug", "This bug is being added to the RBT", 9.0);
        backend.addBug("Test Bug2", "This bug is being added to the RBT",10.0);
        assertTrue(backend.getThreeUnresolvedBugs(0).size() == 2);
    }

    /**
     *Test to get three resolved bugs sorted by priority from a non-empty RBT
     */

    @Test
    public void testGetThreeResolvedBugs(){

        String [] test = {};
        BackendInterface backend = new Backend(test);
        backend.addBug("Test Bug", "This bug is being added to the RBT", 0.0);
        backend.addBug("Test Bug2", "This bug is being added to the RBT", 10.0);
        backend.toggleBug(backend.getThreeUnresolvedBugs(0).get(0));
        backend.toggleBug(backend.getThreeUnresolvedBugs(0).get(0));
        assertTrue(backend.getThreeResolvedBugs(0).size() == 2);

    }


}