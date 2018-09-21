package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Board;

/**
 * Class for the different movement types in chess.
 * @author janne
 */
public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;
    
    /**
     * Constructor
     * @param board
     * @param movedPiece
     * @param destinationCoordinate 
     */
    private Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }
    
    /**
     * Normal movement of chess pieces.
     */
    public static final class RegularMove extends Move {
        public RegularMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }
    
    /**
     * Movement that results in another piece getting removed from the game.
     */
    public static final class AttackMove extends Move {
        final Piece attackedPiece;
        public AttackMove(Board board, Piece movedPiece, int destinationCoordinate, final Piece attackedPiece) {      
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }
    
    public String printDestination() {
        return "" + this.destinationCoordinate;
    }
}
