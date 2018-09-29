package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.utility.BoardUtility;
import fi.helsinki.chessai.utility.MyList;

/**
 * The class for the pawn piece
 * @author janne
 */
public class Pawn extends Piece{
    private final static int[] MOVES = {7, 8, 9, 16};
    
    /**
     * Constructor
     * @param position the position of the piece on the board
     * @param pieceSide The colour of the piece
     * @param firstMove
     */
    public Pawn (final int position, final Side pieceSide, final boolean firstMove) {
        super(PieceType.PAWN, position, pieceSide, firstMove);
    }
    
    /**
     * List of possible moves for the pawn
     * @param board
     * @return 
     */
    @Override
    public MyList<Move> getLegalMoves(Board board) {
        int pieceDestination;
        final MyList<Move> legalMoves = new MyList<>();
        for(final int offset : MOVES) {
            pieceDestination = this.position + (this.getPieceSide().getDirection() * offset);
            if (BoardUtility.isValidTile(pieceDestination) && !BoardUtility.isOutOfBounds(this.position, pieceDestination, 1)) {
                //Forward one tile move
                if (offset == 8 && !board.getTile(pieceDestination).occupied()){
                    if(this.pieceSide.isPromotionRow(pieceDestination)) {
                        legalMoves.add(new Move.PawnPromotion(new Move.RegularMove(board, this, pieceDestination)));
                    } else {
                        legalMoves.add(new Move.RegularMove(board, this, pieceDestination));
                    }
                //Forward two tiles move
                } else if (offset == 16 && firstMove) {
                    final int behindPieceDestination = this.position + (this.getPieceSide().getDirection() * 8);
                    if (!board.getTile(behindPieceDestination).occupied() && !board.getTile(pieceDestination).occupied()) {
                        legalMoves.add(new Move.PawnJump(board, this, pieceDestination));
                    }
                //Attack move   
                } else if (offset == 7 && board.getTile(pieceDestination).occupied() || offset == 9 && board.getTile(pieceDestination).occupied()) {
                    if(this.pieceSide.isPromotionRow(pieceDestination)) {
                        legalMoves.add(new Move.PawnPromotion(new Move.AttackMove(board, this, pieceDestination, this)));
                    } else {
                        final Piece pieceAtDestination = board.getTile(pieceDestination).getPiece();
                        if (this.pieceSide != pieceAtDestination.getPieceSide()) {
                            legalMoves.add(new Move.AttackMove(board, this, pieceDestination, this));
                        }
                    }
                //En passant
                } else if (board.getEnPassantPawn() != null && !board.getTile(pieceDestination).occupied()) {
                    if (offset == 7 && board.getEnPassantPawn().getPosition() + this.getPieceSide().getDirection()  == this.position || offset == 9 && board.getEnPassantPawn().getPosition() - this.getPieceSide().getDirection() == this.position) {
                        if (this.pieceSide != board.getEnPassantPawn().getPieceSide()) {
                            legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, pieceDestination, board.getEnPassantPawn()));
                        }
                    }
                }
            }
        }
        return legalMoves;
    }
    
    private MyList<Move> promotionMoves() {
        
        return null;
    }
    
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestination(), move.getMovedPiece().getPieceSide(), false);
    }
    
        @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }

    public Piece getPromotionPiece() {
        return new Queen(this.position, this.pieceSide, false);
    }
}

