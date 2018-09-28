package fi.helsinki.chessai.board;

import fi.helsinki.chessai.board.Board.Builder;
import fi.helsinki.chessai.board.pieces.Pawn;
import fi.helsinki.chessai.board.pieces.Piece;
import fi.helsinki.chessai.board.pieces.Rook;

/**
 * Class for the different movement types in chess.
 * @author janne
 */
public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;
    
    public static final Move NULL_MOVE = new NullMove();
    
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
     * Computes a hash code for a move.
     * @return 
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result *= prime * this.movedPiece.hashCode();
        result *= prime * this.destinationCoordinate;
        return result;
    }
    /**
    * Checks whether two moves are the same.
    * @param other
    * @return 
    */
    @Override
    public boolean equals(final Object other) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof Move)) {
            return false;
        }
        final Move otherMove = (Move) other;
        return getDestination()== otherMove.getDestination() && getMovedPiece().equals(otherMove.getMovedPiece());
    }
    
    /**
     * Get the location of the destination.
     * @return 
     */
    public int getDestination() {
        return this.destinationCoordinate;
    }
    
    
    /**
     * Get the piece that moves.
     * @return 
     */
    public Piece getMovedPiece() {
        return this.movedPiece;
    }
    
    /**
     * Get the position of the piece before movement.
     * @return 
     */
    public int getCurrentPosition() {
        return this.movedPiece.getPosition();
    }
    
    /**
     * Returns true if the move is a castling move. 
     * @return 
     */
    public boolean isCastling() {
        return false;
    }
    
    /**
     * Returns the piece that is attacking.
     * @return 
     */
    public Piece getAttackedPiece() {
        return null;
    }
    
    /**
     * Attempts to execute the move and build a new board for it.
     * @return 
     */
    public Board execute() {
        final Builder builder = new Builder();
        for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
            if(!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        if(this.movedPiece.isFirstMove()) {
            this.movedPiece.Moved();    
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getSide());
        builder.setMoveTransition(this);
        return builder.build();
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
    public static class AttackMove extends Move {
        final Piece attackedPiece;
        public AttackMove(Board board, Piece movedPiece, int destinationCoordinate, final Piece attackedPiece) {      
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
        
        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }
        
        @Override
        public boolean equals(final Object other) {
            if(this == other) {
                return true;
            }
            if(!(other instanceof AttackMove)) {
                return false;
            }
            final AttackMove otherMove = (AttackMove) other;
            return super.equals(otherMove) && getAttackedPiece().equals(otherMove.getAttackedPiece());
        }
        
        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }
    }
    
    /**
     * Class for the en passant special attack move for the pawn.
     */
    public static final class PawnEnPassantAttackMove extends AttackMove {
        public PawnEnPassantAttackMove(Board board, Piece movedPiece, int destinationCoordinate, final Piece attackedPiece) {      
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
        
        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                if(!this.board.getEnPassantPawn().equals(piece)) {
                    builder.setPiece(piece);   
                }
                
            }
            if(this.movedPiece.isFirstMove()) {
                this.movedPiece.Moved();    
            }
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getSide());
            builder.setMoveTransition(this);
            return builder.build();
        }
    }  
    
    /**
     * Class for the pawn jump special move.
     */
    public static final class PawnJump extends Move {
        public PawnJump(Board board, Piece movedPiece, int destinationCoordinate) {      
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            if(this.movedPiece.isFirstMove()) {
                this.movedPiece.Moved();    
            }
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getSide());
            builder.setMoveTransition(this);
            return builder.build();
        }
    }    

    /**
     * Class for the castling move of the rook and king.
     */
   public static class CastleMove extends Move {
       protected final Rook rook;
       protected final int rookStart;
       protected final int rookDestination;
       
        public CastleMove(Board board, Piece movedPiece, int destinationCoordinate, Rook rook, int rookStart, int rookDestination) {
            super(board, movedPiece, destinationCoordinate);
            this.rook = rook;
            this.rookStart = rookStart;
            this.rookDestination = rookDestination;
        }

        public Rook getCastleRook() {
            return this.rook;
        }
        
        @Override
        public boolean isCastling() {
            return true;
        }
        
        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece) && !this.rook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            this.movedPiece.Moved();    
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.rookDestination, this.rook.getPieceSide(), false));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getSide());
            builder.setMoveTransition(this);
            return builder.build();
        } 
    }
    
   /**
    * Class for an invalid move.
    */
    public static final class NullMove extends Move {
        public NullMove() {      
            super(null, null, -1);
        }        
        
        @Override
            public Board execute() {
                throw new RuntimeException("Cannot execute a move that doesn't exist");
            }
    }    
    
    /**
     * Builder for new moves.
     */
    public static class MoveFactory {
        private MoveFactory () {
            throw new RuntimeException("Not runnable");
        }
        
        /**
         * Creates a new move.
         * @param board
         * @param currentCoordinate
         * @param destinationCoordinate
         * @return
         */
        public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {
            for(Move move : board.getAllLegalMoves()) {
                if(move.getCurrentPosition() == currentCoordinate && move.getDestination() == destinationCoordinate) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
    
    public String toString() {
        return  this.movedPiece.toString() + " moved on " + this.destinationCoordinate;
    }
}
