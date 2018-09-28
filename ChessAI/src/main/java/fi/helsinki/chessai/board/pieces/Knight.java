/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Tile;
import fi.helsinki.chessai.board.Move.*;
import fi.helsinki.chessai.utility.BoardUtility;
import fi.helsinki.chessai.utility.MyList;

/**
 * The class for the knight piece
 * @author janne
 */
public class Knight extends Piece {
    private final static int[] PossibleMoves = {-17, -15, -10, -6, 6, 10, 15, 17};
    
    /**
     * Constructor
     * @param position the position of the piece on the board
     * @param pieceSide The colour of the piece
     * @param firstMove
     */
    public Knight (final int position, final Side pieceSide, final boolean firstMove) {
        super(PieceType.KNIGHT, position, pieceSide, firstMove);
    }
    
    /**
     * Legal moves for the knight piece.
     * @param board
     * @return returns legal moves
     */
    @Override
    public MyList<Move> getLegalMoves(Board board) {
        int pieceDestination;
        final MyList<Move> legalMoves = new MyList<>();
        
        for(final int offset : PossibleMoves) {
            pieceDestination = this.position + offset;    
            if(BoardUtility.isValidTile(pieceDestination) && !BoardUtility.isOutOfBounds(this.position, pieceDestination, 2)) {
                final Tile destinationTile = board.getTile(pieceDestination);
                
                if(!destinationTile.occupied()) {
                    legalMoves.add(new RegularMove(board, this, pieceDestination));
                } else {
                    final Piece pieceAtDestination = destinationTile.getPiece();
                    if(this.pieceSide != pieceAtDestination.getPieceSide()) {
                        legalMoves.add(new AttackMove(board, this, pieceDestination, pieceAtDestination));
                    }
                }
            }
        }
        return legalMoves;
    }
    
    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getDestination(), move.getMovedPiece().getPieceSide(), move.getMovedPiece().isFirstMove());
    }    
    
    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }
}
