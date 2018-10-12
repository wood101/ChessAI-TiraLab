
import fi.helsinki.chessai.utility.BoardUtility;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class UtilityTest {
    
    @Test
    public void TestOutOfBounds() {
        assertTrue(!BoardUtility.isOutOfBounds(1, 2, 1));
        assertFalse(!BoardUtility.isOutOfBounds(15, 5, 0));
        assertTrue(!BoardUtility.isOutOfBounds(1, 9, 1));
    }
    
    @Test
    public void testAlgebreicNotation() {
        assertEquals(BoardUtility.getNotationFromCoodinate(0), "A8");
        assertEquals(BoardUtility.getNotationFromCoodinate(1), "B8");
        assertEquals(BoardUtility.getNotationFromCoodinate(8), "A7");
        assertEquals(BoardUtility.getNotationFromCoodinate(2), "C8");
        assertEquals(BoardUtility.getNotationFromCoodinate(3), "D8");
        assertEquals(BoardUtility.getNotationFromCoodinate(4), "E8");
        assertEquals(BoardUtility.getNotationFromCoodinate(5), "F8");
        assertEquals(BoardUtility.getNotationFromCoodinate(6), "G8");
        assertEquals(BoardUtility.getNotationFromCoodinate(7), "H8");
    }
}
