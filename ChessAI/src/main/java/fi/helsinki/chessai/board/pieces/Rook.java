/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Board;
import java.util.Collection;

/**
 * The class for the rook piece
 * @author janne
 */
public class Rook extends Piece{
        private final static int[] PossibleMoveVectors = {-8, -1, 1, 8};
    
    /**
     * Constructor
     * @param position the position of the piece on the board
     * @param pieceSide The colour of the piece
     */
    public Rook (final int position, final Side pieceSide) {
        super(position, pieceSide);
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
    
        @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }
}

