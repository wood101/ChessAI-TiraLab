
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.board.MoveTransition;
import fi.helsinki.chessai.board.pieces.King;
import fi.helsinki.chessai.board.pieces.Pawn;
import fi.helsinki.chessai.board.pieces.Piece;
import fi.helsinki.chessai.board.pieces.Piece.PieceType;
import fi.helsinki.chessai.board.pieces.Rook;
import fi.helsinki.chessai.utility.BoardUtility;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class MoveTest {
    Board.Builder boardBuilder;
    Board board;
    
    @Before
    public void setUp() {
        boardBuilder = new Board.Builder();
    }
    
    @Test
    public void testCastling() {
        boardBuilder.setMoveMaker(Side.WHITE);
        boardBuilder.setPiece(new Rook(BoardUtility.getCoordinateFromNotation("H1"), Side.WHITE, true));
        boardBuilder.setPiece(new King(BoardUtility.getCoordinateFromNotation("E1"), Side.WHITE, true, false));
        board = boardBuilder.build();
        MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("E1"), BoardUtility.getCoordinateFromNotation("G1")));
        assertTrue(t1.getMoveStatus().isDone());
        
        boardBuilder.setMoveMaker(Side.WHITE);
        boardBuilder.setPiece(new Rook(BoardUtility.getCoordinateFromNotation("H1"), Side.WHITE, true));
        boardBuilder.setPiece(new King(BoardUtility.getCoordinateFromNotation("E1"), Side.WHITE, true, false));
        boardBuilder.setPiece(new Rook(BoardUtility.getCoordinateFromNotation("F4"), Side.BLACK, true));
        board = boardBuilder.build();
        MoveTransition t2 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("E1"), BoardUtility.getCoordinateFromNotation("G1")));
        assertFalse(t2.getMoveStatus().isDone());
    }
    
    @Test
    public void testPromotion() {
        boardBuilder.setMoveMaker(Side.WHITE);
        boardBuilder.setPiece(new Pawn(BoardUtility.getCoordinateFromNotation("A7"), Side.WHITE, true));
        board = boardBuilder.build();
        MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("A7"), BoardUtility.getCoordinateFromNotation("A8")));
        assertTrue(t1.getMoveStatus().isDone());
        board = t1.getTransitionBoard();
        assertTrue(board.getTile(BoardUtility.getCoordinateFromNotation("A8")).getPiece().getPieceType() == PieceType.QUEEN);
    }    
    
        @Test
    public void testPawnJump() {
        boardBuilder.setMoveMaker(Side.WHITE);
        boardBuilder.setPiece(new Pawn(BoardUtility.getCoordinateFromNotation("A6"), Side.WHITE, true));
        board = boardBuilder.build();
        MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("A6"), BoardUtility.getCoordinateFromNotation("A8")));
        assertTrue(t1.getMoveStatus().isDone());
    } 
    
    @Test
    public void testEnPassantMove() {
        boardBuilder.setMoveMaker(Side.WHITE);
        boardBuilder.setPiece(new Pawn(BoardUtility.getCoordinateFromNotation("A5"), Side.WHITE, true));
        Pawn enPassant = new Pawn(BoardUtility.getCoordinateFromNotation("B5"), Side.BLACK, false);
        boardBuilder.setPiece(enPassant);
        boardBuilder.setEnPassantPawn(enPassant);
        board = boardBuilder.build();
        MoveTransition t1 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("A5"), BoardUtility.getCoordinateFromNotation("B6")));
        assertTrue(t1.getMoveStatus().isDone());
        board = t1.getTransitionBoard();
        assertFalse(board.getTile(BoardUtility.getCoordinateFromNotation("B5")).occupied());
        
        boardBuilder.setMoveMaker(Side.WHITE);
        boardBuilder.setPiece(new Pawn(BoardUtility.getCoordinateFromNotation("A5"), Side.WHITE, true));
        boardBuilder.setPiece(enPassant);
        boardBuilder.setEnPassantPawn(null);
        board = boardBuilder.build();
        MoveTransition t2 = board.currentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtility.getCoordinateFromNotation("A5"), BoardUtility.getCoordinateFromNotation("B6")));
        assertFalse(t2.getMoveStatus().isDone());
    } 
}
