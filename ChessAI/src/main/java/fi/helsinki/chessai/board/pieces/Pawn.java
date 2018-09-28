/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.utility.BoardUtility;
import fi.helsinki.chessai.utility.MyList;
import java.util.ArrayList;

/**
 * The class for the pawn piece
 * @author janne
 */
public class Pawn extends Piece{
    private final static int[] PossibleMoves = {7, 8, 9, 16};
    
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
        for(final int offset : PossibleMoves) {
            pieceDestination = this.position + (this.getPieceSide().getDirection() * offset);
            if(BoardUtility.isValidTile(pieceDestination) && !BoardUtility.isOutOfBounds(this.position, pieceDestination, 1)) {
                
                if(offset == 8 && !board.getTile(pieceDestination).occupied()){
                    legalMoves.add(new Move.RegularMove(board, this, pieceDestination));
                    
                } else if(offset == 16 && firstMove) {
                    final int behindPieceDestination = this.position + (this.getPieceSide().getDirection() * 8);
                    if(!board.getTile(behindPieceDestination).occupied() && !board.getTile(pieceDestination).occupied()) {
                        legalMoves.add(new Move.PawnJump(board, this, pieceDestination));
                    }
                    
                } else if (offset == 7 && board.getTile(pieceDestination).occupied() || offset == 9 && board.getTile(pieceDestination).occupied()) {
                    final Piece pieceAtDestination = board.getTile(pieceDestination).getPiece();
                    if(this.pieceSide != pieceAtDestination.getPieceSide()) {
                        legalMoves.add(new Move.AttackMove(board, this, pieceDestination, this));
                    }
                    
                } else if (board.getEnPassantPawn() != null) {
                    final Piece enPassantPawn = board.getEnPassantPawn();
                    if(enPassantPawn.getPosition() + 1 == this.position || enPassantPawn.getPosition() - 1 == this.position) {
                        if(this.pieceSide != enPassantPawn.getPieceSide()) {
                            legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, pieceDestination, enPassantPawn));
                        }
                    }
                }
            }
        }
        return legalMoves;
    }
    
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestination(), move.getMovedPiece().getPieceSide(), move.getMovedPiece().isFirstMove());
    }
    
        @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}

