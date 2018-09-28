/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.utility.PieceUtil;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.utility.MyList;

/**
 * The class for the queen piece
 * @author janne
 */
public class Queen extends Piece{
        private final static int[] PossibleMoveVectors = {-8, -1, 1, 8, -9, -7, 7, 9};
    
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
        return PieceUtil.getLegalVectorMoves(board, this, PossibleMoveVectors);    
    }
    
    @Override
    public Queen movePiece(final Move move) {
        return new Queen(move.getDestination(), move.getMovedPiece().getPieceSide(), move.getMovedPiece().isFirstMove());
    }
    
    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }
}
