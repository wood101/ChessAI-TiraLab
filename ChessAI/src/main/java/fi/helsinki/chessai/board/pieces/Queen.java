package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.player.Side;
import fi.helsinki.chessai.utility.PieceUtility;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.utility.MyList;

/**
 * The class for the queen piece
 * @author janne
 */
public class Queen extends Piece{
        private final static int[] MOVE_VECTORS = {-8, -1, 1, 8, -9, -7, 7, 9};
    
    /**
     * Constructor
     * @param position the position of the piece on the board
     * @param pieceSide The colour of the piece
     * @param firstMove
     */
    public Queen (final int position, final Side pieceSide, final boolean firstMove) {
        super(PieceType.QUEEN, position, pieceSide, firstMove);
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
    public Queen movePiece(final Move move) {
        return new Queen(move.getDestination(), move.getMovedPiece().getPieceSide(), false);
    }
    
    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }
}
