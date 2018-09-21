/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import java.util.Collection;

/**
 * Class for the chess pieces.
 * @author janne
 */
public abstract class Piece {
    
    protected final PieceType pieceType;
    protected final int position;
    protected final Side pieceSide;
    protected final boolean firstMove;
    private final int cachedHashCode;
    
    /**
     * Constructor for the piece class.
     * @param pieceType
     * @param position
     * @param pieceSide 
     */
    Piece(final PieceType pieceType, final int position, final Side pieceSide) {
        this.pieceSide = pieceSide;
        this.position = position;
        this.pieceType = pieceType;
        this.firstMove = false;
        this.cachedHashCode = computeHashCode();
    }
    
    /**
     * Computes a hash code for a piece.
     * @return 
     */
    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceSide.hashCode();
        result = 31 * result + position;
        result = 31 * result + (firstMove ? 1 : 0);
        return result;
    }
    
    /**
     * Checks whether two pieces are the same.
     * @param other
     * @return 
     */
    @Override
    public boolean equals(final Object other) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return position == otherPiece.getPosition() && pieceType == otherPiece.getPieceType() && pieceSide == otherPiece.getPieceSide() && firstMove == otherPiece.isFirstMove();
    }
    
    /**
     * Returns the previously calculated hash code.
     * @return 
     */
    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }
    
    /**
     * Get the allegiance of the piece.
     * @return 
     */
    public Side getPieceSide() {
        return this.pieceSide;
    }
    
    /**
     * Get what the piece actually is.
     * @return 
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }
    
    /**
     * Get the position of the piece on the board.
     * @return 
     */
    public int getPosition() {
        return this.position;
    }
    
    /**
     * Returns true if the piece has not moved during the game.
     * @return 
     */
    public boolean isFirstMove() {
        return this.firstMove;
    }
    
    /**
     * Get a list of the possible moves the piece can make.
     * @param board
     * @return 
     */
    public abstract Collection<Move>getLegalMoves(final Board board);
    
    /**
     * Returns the piece with an updated position.
     * @param move
     * @return 
     */
    public abstract Piece movePiece(Move move);
    
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
