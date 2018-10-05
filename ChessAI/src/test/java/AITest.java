
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.board.MoveTransition;
import fi.helsinki.chessai.player.ai.MiniMax;
import fi.helsinki.chessai.player.ai.MoveStrategy;
import fi.helsinki.chessai.utility.BoardUtility;
import static org.junit.Assert.assertEquals;
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
public class AITest {
    
    @Test
    public void testFoolsMate() {
        Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateAtPosition("F2"), BoardUtility.getCoordinateAtPosition("F3")));
        assertTrue(t1.getMoveStatus().isDone());
        board = (Board) t1.getBoard();
        final MoveTransition t2 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateAtPosition("E7"), BoardUtility.getCoordinateAtPosition("E5")));
        assertTrue(t2.getMoveStatus().isDone());
        board = (Board) t2.getBoard();
        final MoveTransition t3 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateAtPosition("G2"), BoardUtility.getCoordinateAtPosition("G4")));
        assertTrue(t3.getMoveStatus().isDone());
        board = (Board) t3.getBoard();
        
        final MoveStrategy strategy = new MiniMax(4);
        final Move aiMove = strategy.execute(t3.getTransitionBoard());
        final Move expectedMove = Move.MoveFactory.createMove(board, BoardUtility.getCoordinateAtPosition("D8"), BoardUtility.getCoordinateAtPosition("H4"));
        assertEquals(aiMove, expectedMove);
    }
    
}
