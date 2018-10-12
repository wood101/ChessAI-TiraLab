
import fi.helsinki.chessai.utility.MyList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MyListTest {
    
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
    
    @Test(expected = NoSuchElementException.class)
    public void TestListIterationError() {
        MyList<Integer> sut = new MyList<>();
        Iterator<Integer> itr = sut.iterator();    
        itr.next();
    }
    
    @Test
    public void TestListContains() {
        MyList<Integer> sut = new MyList<>();
        for(int i = 1; i < 5; i++) {
            sut.add(i);    
        }
        assertTrue(sut.contains(4));
        assertFalse(sut.contains(5));
        assertFalse(sut.contains(null));
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
        assertFalse(sut.addAll(new MyList<>()));
    }
    
    @Test
    public void TestListRemove() {
        MyList<Integer> sut = new MyList<>();
        for(int i = 1; i < 5; i++) {
            sut.add(i);    
        }
        assertTrue(sut.size() == 4);
        sut.remove(0);
        assertTrue(sut.size() == 3);
        assertFalse(sut.contains(0));
    } 
    
    @Test
    public void TestListToString() {
        MyList<Integer> sut = new MyList<>();
        for(int i = 1; i < 5; i++) {
            sut.add(i);    
        }
        System.out.println(sut.toString());
        assertTrue(sut.toString().equals("[1,2,3,4]"));
    }        
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsExceptionForAdd() {
        MyList emptyList = new MyList();
        emptyList.get(0);
    } 

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsExceptionForRemove() {
        MyList emptyList = new MyList();
        emptyList.remove(0);
    }    
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsExceptionForSet() {
        MyList<Integer> emptyList = new MyList();
        emptyList.set(0, 5);
    }        
}
