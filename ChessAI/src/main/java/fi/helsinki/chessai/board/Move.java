package fi.helsinki.chessai.board;

import fi.helsinki.chessai.board.Board.Builder;
import fi.helsinki.chessai.board.pieces.Pawn;
import fi.helsinki.chessai.board.pieces.Piece;
import fi.helsinki.chessai.board.pieces.Rook;
import fi.helsinki.chessai.utility.BoardUtility;

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
     * Returns the board that the move is happening on.
     * @return 
     */
    public Board getBoard() {
        return this.board;
    }
    
    /**
     * Sets the pieces on their old positions for a new board.
     * @param builder 
     */
    private static void setPiecesOnNewBuild(Builder builder, Move move) {
        for(final Piece piece : move.board.currentPlayer().getActivePieces()) {
            if(!move.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for(final Piece piece : move.board.currentPlayer().getOpponent().getActivePieces()) {
                if(piece.getPosition() != move.movedPiece.getPosition()) {
                    builder.setPiece(piece);
                }
        }
    }
    
    /**
     * Attempts to execute the move and build a new board for it.
     * @return 
     */
    public Board execute() {
        final Builder builder = new Builder();
        setPiecesOnNewBuild(builder, this);
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getSide());
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
    }
    
    /**
     * Class for the en passant special attack for the pawn.
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
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getSide());
            return builder.build();
        }
    }  
    
    /**
     * Class for the pawn jump special move.
     */
    public final static class PawnJump extends Move {
        public PawnJump(Board board, Piece movedPiece, int destinationCoordinate) {      
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            setPiecesOnNewBuild(builder, this);
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getSide());
            return builder.build();
        }
    }    
    
    public static class  PawnPromotion extends Move {
        final Move promotionMove;
        final Pawn promotedPawn;

        public PawnPromotion(final Move promotionMove) {
            super(promotionMove.getBoard(), promotionMove.getMovedPiece(), promotionMove.getDestination());
            this.promotionMove = promotionMove;
            this.promotedPawn = (Pawn) promotionMove.getMovedPiece();
        }
        
                @Override
        public Board execute() {
            final Board pawnMovedBoard = this.promotionMove.execute();
            final Builder builder = new Builder();
            setPiecesOnNewBuild(builder, this);
            builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
            builder.setMoveMaker(pawnMovedBoard.currentPlayer().getSide());
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
        
        @Override
        public boolean isCastling() {
            return true;
        }
        
        @Override
        public Board execute() {
            final Builder builder = new Builder();
            setPiecesOnNewBuild(builder, this);
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.rookDestination, this.rook.getPieceSide(), false));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getSide());
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
            
        @Override
            public String toString() {
                return "Move doesn't exist";
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
        return  "Piece " + this.movedPiece.toString() + " moved from " + BoardUtility.getPositionAtCoordinate(this.movedPiece.getPosition())
                + " to " + BoardUtility.getPositionAtCoordinate(this.destinationCoordinate);
    }
}
