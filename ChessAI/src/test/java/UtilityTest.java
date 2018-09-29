
import fi.helsinki.chessai.utility.BoardUtility;
import fi.helsinki.chessai.utility.MyList;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author janne
 */
public class UtilityTest {
    
    @Test
    public void TestListIteration() {
        MyList<Integer> sut = new MyList<>();
        for(int i = 1; i < 5; i++) {
        sut.add(i);    
        }

        Iterator<Integer> itr = sut.iterator();
        int count = 0;
        Integer element = 0;
        assertTrue(itr.hasNext());
        while(itr.hasNext()) {
            element = itr.next();
            count++;
            if(count == 1) {
                assertTrue(element == 1);
            } else if (count == 2) {
                assertTrue(element == 2);
            } else if (count == 3) {
                assertTrue(element == 3);
            } else if (count == 4) {
                assertTrue(element == 4);
            }
        }
        assertTrue(count !=5);
    }
    
    @Test
    public void TestListContains() {
        MyList<Integer> sut = new MyList<>();
        for(int i = 1; i < 5; i++) {
            sut.add(i);    
        }
        assertTrue(sut.contains(4));
        assertFalse(sut.contains(5));
    }
    
    @Test
    public void TestListAddAll() {
        MyList<Integer> sut = new MyList<>();
        MyList<Integer> sut2 = new MyList<>();
        for(int i = 1; i < 5; i++) {
            sut.add(i);    
        }
        
        for(int i = 15; i < 20; i++) {
            sut2.add(i);    
        }
        sut.addAll(sut2);
        assertTrue(sut.contains(16));
        assertTrue(sut.size() == 9);
    }
    
    @Test
    public void TestOutOfBounds() {
        assertTrue(!BoardUtility.isOutOfBounds(1, 2, 1));
        assertFalse(!BoardUtility.isOutOfBounds(15, 5, 0));
        assertTrue(!BoardUtility.isOutOfBounds(1, 9, 1));
    }
    
    @Test
    public void testAlgebreicNotation() {
        assertEquals(BoardUtility.getPositionAtCoordinate(0), "A8");
        assertEquals(BoardUtility.getPositionAtCoordinate(1), "B8");
        assertEquals(BoardUtility.getPositionAtCoordinate(8), "A7");
        assertEquals(BoardUtility.getPositionAtCoordinate(2), "C8");
        assertEquals(BoardUtility.getPositionAtCoordinate(3), "D8");
        assertEquals(BoardUtility.getPositionAtCoordinate(4), "E8");
        assertEquals(BoardUtility.getPositionAtCoordinate(5), "F8");
        assertEquals(BoardUtility.getPositionAtCoordinate(6), "G8");
        assertEquals(BoardUtility.getPositionAtCoordinate(7), "H8");
}
}
