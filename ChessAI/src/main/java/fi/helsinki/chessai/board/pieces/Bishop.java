package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Board;
import java.util.Collection;

/**
 * The class for the bishop piece
 * @author janne
 */
public class Bishop extends Piece {
    private final static int[] PossibleMoveVectors = {-9, -7, 7, 9};
    
    /**
     * Constructor
     * @param position the piece's position on the board
     * @param pieceSide 
     */
    public Bishop (final int position, final Side pieceSide) {
        super(position, pieceSide);
    }
    
    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }
    
    /**
     * Calls PieceUtil class for legal moves.
     * @param board
     * @return 
     */
    @Override
    public Collection<Move> getLegalMoves(Board board) {
        return PieceUtil.getLegalVectorMoves(board, this, PossibleMoveVectors);    
    }
}
