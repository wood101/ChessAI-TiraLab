
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.board.MoveTransition;
import fi.helsinki.chessai.player.ai.MiniMax;
import fi.helsinki.chessai.player.ai.MoveStrategy;
import fi.helsinki.chessai.utility.BoardUtility;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class AITest {
    Board board;
    
    @Before
    public void setUp() {
        board = Board.createStandardBoard();
    }
    
    @Test
    public void testFoolsMateWhereBlackWins() {
        MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("F2"), BoardUtility.getCoordinateFromNotation("F3")));
        assertTrue(t1.getMoveStatus().isDone());
        board = (Board) t1.getBoard();
        
        MoveTransition t2 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("E7"), BoardUtility.getCoordinateFromNotation("E5")));
        assertTrue(t2.getMoveStatus().isDone());
        board = (Board) t2.getBoard();
        
        MoveTransition t3 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("G2"), BoardUtility.getCoordinateFromNotation("G4")));
        assertTrue(t3.getMoveStatus().isDone());
        board = (Board) t3.getBoard();
        
        final MoveStrategy strategy = new MiniMax(2, false);
        final Move aiMove = strategy.execute(t3.getTransitionBoard());
        final Move expectedMove = Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("D8"), BoardUtility.getCoordinateFromNotation("H4"));
        assertEquals(aiMove, expectedMove);
    }
    
    @Test
    public void testFoolsMateWhereWhiteWins() {
        MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("E2"), BoardUtility.getCoordinateFromNotation("E3")));
        assertTrue(t1.getMoveStatus().isDone());
        board = (Board) t1.getBoard();
        
        MoveTransition t2 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("F7"), BoardUtility.getCoordinateFromNotation("F5")));
        assertTrue(t2.getMoveStatus().isDone());
        board = (Board) t2.getBoard();
        
        MoveTransition t3 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("A2"), BoardUtility.getCoordinateFromNotation("A3")));
        assertTrue(t1.getMoveStatus().isDone());
        board = (Board) t3.getBoard();    
        
        MoveTransition t4 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("G7"), BoardUtility.getCoordinateFromNotation("G5")));
        assertTrue(t4.getMoveStatus().isDone());
        board = (Board) t4.getBoard();
        
        final MoveStrategy strategy = new MiniMax(2, false);
        final Move aiMove = strategy.execute(t4.getTransitionBoard());
        final Move expectedMove = Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("D1"), BoardUtility.getCoordinateFromNotation("H5"));
        assertEquals(aiMove, expectedMove);
    }    
    
}
