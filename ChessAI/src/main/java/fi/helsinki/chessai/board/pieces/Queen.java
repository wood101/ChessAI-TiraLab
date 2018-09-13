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
import java.util.List;

/**
 *
 * @author janne
 */
public class Queen extends Piece{
        private final static int[] PossibleMoveVectors = {-9, -8, -7 -1, 1, 7, 8, 9};
    
    Queen (int position, Side pieceSide) {
        super(position, pieceSide);
    }
    
    @Override
    public Collection<Move> legalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final int offset : PossibleMoveVectors) {
            int pieceDestination = this.position;
            while(BoardUtil.isValidTile(pieceDestination)) {
                pieceDestination += offset;
                if(!PieceUtil.isOutOfBounds(this.position, pieceDestination, 1)) {
                break;
                } 
                if(BoardUtil.isValidTile(pieceDestination)) {
                    final Tile destinationTile = board.getTile(pieceDestination);
                    if(destinationTile.occupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, pieceDestination));
                    } else {
                        final Piece pieceAtDestination = destinationTile.getPiece();
                        final Side pieceSide = pieceAtDestination.getPieceSide();
                        if(this.pieceSide != pieceSide) {
                            legalMoves.add(new Move.AttackMove(board, this, pieceDestination, pieceAtDestination));
                        }
                        break;
                    }
                }  
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
    
}
