package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.utility.MyList;

/**
 * Class for the chess pieces.
 * @author janne
 */
public abstract class Piece {
    
    protected final PieceType pieceType;
    protected final int position;
    protected final Side pieceSide;
    protected boolean firstMove;
    
    /**
     * Constructor for the piece class.
     * @param pieceType
     * @param position
     * @param pieceSide 
     */
    Piece(final PieceType pieceType, final int position, final Side pieceSide, final boolean firstMove) {
        this.pieceSide = pieceSide;
        this.position = position;
        this.pieceType = pieceType;
        this.firstMove = firstMove;
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
     * Returns the amounth of score the piece is worth.
     * @return 
     */
    public int getPieceValue() {
        return this.pieceType.pieceValue;
    }
    
    /**
     * Get a list of the possible moves the piece can make.
     * @param board
     * @return 
     */
    public abstract MyList<Move>getLegalMoves(final Board board);
    
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
        PAWN("P", 100),
        KNIGHT("N", 300),
        BISHOP("B", 300),
        ROOK("R", 500),
        QUEEN("Q", 900),
        KING("K", 20000);
                
        private final String pieceName;
        private final int pieceValue;
        
        /**
         * Name of the piece.
         * @param pieceName 
         */
        PieceType(final String pieceName, final int pieceValue) {
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }
        
        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
