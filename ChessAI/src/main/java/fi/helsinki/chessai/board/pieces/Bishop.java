package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.player.Side;
import fi.helsinki.chessai.utility.PieceUtility;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.utility.MyList;

/**
 * The class for the bishop piece
 * @author janne
 */
public class Bishop extends Piece {
    private final static int[] MOVE_VECTORS = {-9, -7, 7, 9};
    
    /**
     * Constructor
     * @param position the position of the piece on the board
     * @param pieceSide The colour of the piece
     * @param firstMove
     */
    public Bishop (final int position, final Side pieceSide, final boolean firstMove) {
        super(PieceType.BISHOP, position, pieceSide, firstMove);
    }
    
    /**
     * Calls PieceUtil class for legal moves.
     * @param board
     * @return 
     */
    @Override
    public MyList<Move> getLegalMoves(Board board) {
        return PieceUtility.getLegalVectorMoves(board, this, MOVE_VECTORS);    
    }
    
    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }

    @Override
    public Piece movePiece(final Move move) {
        return new Bishop(move.getDestination(), move.getMovedPiece().getPieceSide(), false);
    }
}
