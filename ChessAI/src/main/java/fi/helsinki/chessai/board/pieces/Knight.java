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
 *
 * @author janne
 */
public class Knight extends Piece {
    private final static int[] PossibleMoves = {-17, -15, -10, -6, 6, 10, 15, 17};
    
    Knight(final int position, final Side pieceSide) {
        super(position, pieceSide);
    }
    
    @Override
    public Collection<Move> legalMoves(Board board) {
        int pieceDestination;
        final Collection<Move> legalMoves = new ArrayList<>();
        
        for(final int currentPieceOffset : PossibleMoves) {
            pieceDestination = this.position + currentPieceOffset;
            if(!PieceUtil.isOutOfBounds(this.position, pieceDestination, 2)) {
                continue;
            }           
            if(BoardUtil.isValidTile(pieceDestination)) {
                
                final Tile destinationTile = board.getTile(pieceDestination);
                
                if(destinationTile.occupied()) {
                    legalMoves.add(new MajorMove(board, this, pieceDestination));
                } else {
                    final Piece pieceAtDestination = destinationTile.getPiece();
                    final Side pieceSide = pieceAtDestination.getPieceSide();
                    
                    if(this.pieceSide != pieceSide) {
                        legalMoves.add(new AttackMove(board, this, pieceDestination, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
