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
 * Utility class for the pieces
 * @author janne
 */
public class PieceUtil {
    
    /**
     * Checks if the piece moved out of bound on the right or left side.
     * @param position Position of the piece
     * @param pieceDestination Destination of the piece
     * @param maxDistance The maximum distance of columns the piece can move at once
     * @return True if the piece made an illegal move
     */
    public static boolean isOutOfBounds(int position, int pieceDestination, int maxDistance) {
        int currentColumn = (position + 1) % 8;
        int destinationColumn = (pieceDestination + 1) % 8;
        if(destinationColumn == 0) {
            destinationColumn = 8;
        }
        if(currentColumn == 0) {
            currentColumn = 8;
        }
        return Math.abs ( currentColumn - destinationColumn ) > maxDistance;
    }
    
    /**
     * Gets the legal moves for all pieces that move according to a vector.
     * @param board current playing board
     * @param piece the piece that move list is generated for
     * @param vectors the vectors the piece can move
     * @return list of all the possible moves
     */
    public static Collection<Move> getLegalVectorMoves(Board board, Piece piece, int[] vectors) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final int offset : vectors) {
            int pieceDestination = piece.position + offset;
            while(BoardUtil.isValidTile(pieceDestination) && !PieceUtil.isOutOfBounds(pieceDestination - offset, pieceDestination, 1)) {
                    final Tile destinationTile = board.getTile(pieceDestination);
                    if(!destinationTile.occupied()) {
                        legalMoves.add(new Move.RegularMove(board, piece, pieceDestination));
                    } else {
                        final Piece pieceAtDestination = destinationTile.getPiece();
                        final Side pieceSide = pieceAtDestination.getPieceSide();
                        if(piece.getPieceSide() != pieceSide) {
                            legalMoves.add(new Move.AttackMove(board, piece, pieceDestination, pieceAtDestination));
                        }
                        break;
                    }
                pieceDestination += offset;
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
