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
import fi.helsinki.chessai.board.pieces.Move.*;
import java.util.ArrayList;
import java.util.Collection;

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
     */
    public Knight(final int position, final Side pieceSide) {
        super(position, pieceSide);
    }
    
    /**
     * Legal moves for the knight piece.
     * @param board
     * @return returns legal moves
     */
    @Override
    public Collection<Move> getLegalMoves(Board board) {
        int pieceDestination;
        final Collection<Move> legalMoves = new ArrayList<>();
        
        for(final int offset : PossibleMoves) {
            pieceDestination = this.position + offset;    
            if(BoardUtil.isValidTile(pieceDestination) && !PieceUtil.isOutOfBounds(this.position, pieceDestination, 2)) {
                
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
        return ImmutableList.copyOf(legalMoves);
    }
    
        @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }
}
