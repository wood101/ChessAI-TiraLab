/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

import com.google.common.collect.ImmutableList;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.BoardUtil;
import fi.helsinki.chessai.board.Tile;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The class for the king piece
 * @author janne
 */
public class King extends Piece{
    private final static int[] PossibleMoves = {-9, -8, -7, -1, 1, 7, 8, 9};
    
    /**
     * Constructor
     * @param position the position of the piece on the board
     * @param pieceSide The colour of the piece
     */
    public King(final int position, final Side pieceSide) {
        super(position, pieceSide);
    }
    
     /**
     * Legal moves for the king piece.
     * @param board
     * @return returns legal moves
     */
    @Override
    public Collection<Move> getLegalMoves(Board board) {
        int pieceDestination;
        final Collection<Move> legalMoves = new ArrayList<>();
        
        for(final int offset : PossibleMoves) {
            pieceDestination = this.position + offset;
            if(PieceUtil.isOutOfBounds(this.position, pieceDestination, 1)) {
                continue;
            }           
            if(BoardUtil.isValidTile(pieceDestination)) {
                
                final Tile destinationTile = board.getTile(pieceDestination);
                
                if(!destinationTile.occupied()) {
                    legalMoves.add(new Move.RegularMove(board, this, pieceDestination));
                } else {
                    final Piece pieceAtDestination = destinationTile.getPiece();
                    if(this.pieceSide != pieceAtDestination.getPieceSide()) {
                        legalMoves.add(new Move.AttackMove(board, this, pieceDestination, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
    
        @Override
    public String toString() {
        return PieceType.KING.toString();
    }
}
