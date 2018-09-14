/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Board;
import java.util.Collection;

/**
 * Class for the chess pieces.
 * @author janne
 */
public abstract class Piece {
    protected final int position;
    protected final Side pieceSide;
    
    Piece(final int position, final Side pieceSide) {
        this.pieceSide = pieceSide;
        this.position = position;
    }
    
    /**
     * Get the allegiance of the piece.
     * @return 
     */
    public Side getPieceSide() {
        return this.pieceSide;
    }
    
    /**
     * Get the position of the piece on the board.
     * @return 
     */
    public int getPosition() {
        return this.position;
    }
    
    /**
     * Get a list of the possible moves the piece can make.
     * @param board
     * @return 
     */
    public abstract Collection<Move>getLegalMoves(final Board board);
    
    /**
     * Enum of the different types of pieces
     */
    public enum PieceType {
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");
                
        private String pieceName;
        
        /**
         * Name of the piece.
         * @param pieceName 
         */
        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }
        
        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
