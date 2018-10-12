package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.utility.MyList;
import fi.helsinki.chessai.utility.PieceUtility;

/**
 * The class for the knight piece
 * @author janne
 */
public class Knight extends Piece {
    private final static int[] MOVES = {-17, -15, -10, -6, 6, 10, 15, 17};
    
    /**
     * Constructor
     * @param position the position of the piece on the board
     * @param pieceSide The colour of the piece
     * @param firstMove
     */
    public Knight (final int position, final Side pieceSide, final boolean firstMove) {
        super(PieceType.KNIGHT, position, pieceSide, firstMove);
    }
    
    /**
     * Legal moves for the knight piece.
     * @param board
     * @return returns legal moves
     */
    @Override
    public MyList<Move> getLegalMoves(Board board) {
        return PieceUtility.getLegalSingleMoves(board, this, MOVES, 2);
    }
    
    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getDestination(), move.getMovedPiece().getPieceSide(), false);
    }    
    
    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }
}
