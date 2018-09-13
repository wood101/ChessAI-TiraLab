/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

/**
 *
 * @author janne
 */
public class PieceUtil {
    
    public static boolean isOutOfBounds(int position, int pieceDestination, int maxDistance) {
        int currentColumn = position % 8;
        int destinationColumn = pieceDestination % 8;
        return Math.abs ( currentColumn - destinationColumn ) > maxDistance;
    }
}
