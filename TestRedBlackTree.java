// --== CS400 File Header Information ==--
// Name: Pranav Ashok
// Email: pashok@wisc.edu
// Team: IE
// TA: Sid Mohan
// Lecturer: Gary Dahl
// Notes to Grader: None

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Pranav Ashok
 *
 * This class tests the Red Black Tree implementation in RedBlackTree.java. Specifically, it tests the insert method
 * and checks whether or not the properties of the Red Black Tree are maintained.
 */
public class TestRedBlackTree {
    /**
     * Tests whether or not the insert function of RedBlackTree works as expected when trying to insert a node to
     * an empty tree, as well as two nodes after the root
     */
    @Test
    public void testRBTreeInsertRoot(){
        RedBlackTree<Integer> RBTree = new RedBlackTree<>();

        RBTree.insert(4);

        assertTrue(RBTree.root.data==4 && RBTree.size()==1 && RBTree.root.isBlack);

        RBTree.insert(2);
        RBTree.insert(6);

        assertTrue(RBTree.size()==3 && RBTree.root.leftChild.data==2 && RBTree.root.rightChild.data==6
                    && !RBTree.root.leftChild.isBlack && !RBTree.root.rightChild.isBlack);
    }

    /**
     * Tests whether or not the properties of the Red Black Tree are maintained when a node is inserted and causes a
     * case 1 violation
     */
    @Test
    public void testRBTreeInsertCaseOne(){
        RedBlackTree<Integer> RBTree = new RedBlackTree<>();

        RBTree.insert(50);
        RBTree.insert(25);
        RBTree.insert(75);
        RBTree.insert(10);
        RBTree.insert(5);



        assertTrue(RBTree.root.isBlack && RBTree.root.leftChild.isBlack && RBTree.root.rightChild.isBlack &&
                !RBTree.root.leftChild.leftChild.isBlack && !RBTree.root.leftChild.rightChild.isBlack);

        assertTrue(RBTree.root.toString().equals("[50, 10, 75, 5, 25]"));
    }

    /**
     * Tests whether or not the properties of the Red Black Tree are maintained when a node is inserted and causes a
     * case 2 violation
     */
    @Test
    public void testRBTreeInsertCaseTwo(){
        RedBlackTree<Integer> RBTree = new RedBlackTree<>();

        RBTree.insert(50);
        RBTree.insert(25);
        RBTree.insert(75);
        RBTree.insert(10);
        RBTree.insert(15);

        assertTrue(RBTree.root.isBlack && RBTree.root.leftChild.isBlack && RBTree.root.rightChild.isBlack &&
                !RBTree.root.leftChild.leftChild.isBlack && !RBTree.root.leftChild.rightChild.isBlack);

        assertTrue(RBTree.root.toString().equals("[50, 15, 75, 10, 25]"));
    }

    /**
     * Tests whether or not the properties of the Red Black Tree are maintained when a node is inserted and causes a
     * case 3 violation
     */
    @Test
    public void testRBTreeInsertCaseThree(){
        RedBlackTree<Integer> RBTree = new RedBlackTree<>();

        RBTree.insert(50);
        RBTree.insert(25);
        RBTree.insert(75);
        RBTree.insert(10);
        RBTree.insert(5);
        RBTree.insert(2);

        assertTrue(RBTree.root.isBlack && !RBTree.root.leftChild.isBlack && RBTree.root.rightChild.isBlack &&
                RBTree.root.leftChild.leftChild.isBlack && RBTree.root.leftChild.rightChild.isBlack &&
                !RBTree.root.leftChild.leftChild.leftChild.isBlack);

        assertTrue(RBTree.root.toString().equals("[50, 10, 75, 5, 25, 2]"));
    }
}
