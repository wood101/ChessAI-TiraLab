/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.utility.MyList;
import fi.helsinki.chessai.utility.PieceUtility;

/**
 * The class for the king piece
 * @author janne
 */
public class King extends Piece{
    private final static int[] MOVES = {-9, -8, -7, -1, 1, 7, 8, 9};
    
    /**
     * Constructor
     * @param position the position of the piece on the board
     * @param pieceSide The colour of the piece
     * @param firstMove
     */
    public King (final int position, final Side pieceSide, final boolean firstMove) {
        super(PieceType.KING, position, pieceSide, firstMove);
    }
    
     /**
     * Legal moves for the king piece.
     * @param board
     * @return returns legal moves
     */
    @Override
    public MyList<Move> getLegalMoves(Board board) {
        final MyList<Move> legalMoves = PieceUtility.getLegalSingleMoves(board, this, MOVES, 1);
        if(board.getBlackPlayer() != null) {
            if(this.getPieceSide() == Side.BLACK) {
                legalMoves.addAll(board.getBlackPlayer().getCastleMoves());
            } else {
                legalMoves.addAll(board.getWhitePlayer().getCastleMoves());
            }
            
        }
        return legalMoves;
    }
    
    @Override
    public King movePiece(final Move move) {
        return new King(move.getDestination(), move.getMovedPiece().getPieceSide(), false);
    }
    
    @Override
    public String toString() {
        return PieceType.KING.toString();
    }
}
