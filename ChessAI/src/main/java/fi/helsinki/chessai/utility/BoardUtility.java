/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.utility;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility for the board class.
 * @author janne
 */
public class BoardUtility {
    public static final List<String> ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();


    
    private BoardUtility() {
        throw new RuntimeException("Utility class only");
    }
    
    /**
     * Checks if the piece moved out of bound on the right or left side.
     * @param position Position of the piece
     * @param pieceDestination Destination of the piece
     * @param maxDistance The maximum distance of columns the piece can move at once
     * @return True if the piece made an illegal move
     */
    public static boolean isOutOfBounds(int position, int pieceDestination, int maxDistance) {
        int currentColumn = getColumn(position);
        int destinationColumn = getColumn(pieceDestination);
        return Math.abs(currentColumn - destinationColumn) > maxDistance;
    }

    /**
     * Checks if the tile exists on the board.
     * @param coordinate coordinate of the tile
     * @return True if it does, false if it doesn't
     */
    public static boolean isValidTile(int coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }   
    
    /**
     * Returns the row of the tile.
     * @param tileId
     * @return 
     */
    public static int getRow(int tileId) {
        int row = (int) Math.ceil((tileId + 1) / 8);
        if(row < 9) return row;
        return -999;
    }

    
    /**
     * Returns the column of the tile.
     * @param tileId
     * @return 
     */
    public static int getColumn(int tileId) {
        if(tileId < 64 && tileId >= 0) {
            int column = (tileId + 1) % 8;
            if(column == 0) {
                return 8;
            }
            return column;
        }
        return -999;
    }
    
    /**
     * Initializes the map for the algebraic notations of the board.
     * @return 
     */
    private static Map<String, Integer> initializePositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = 0; i < 64; i++) {
            positionToCoordinate.put(ALGEBRAIC_NOTATION.get(i), i);
        }
        return positionToCoordinate;
    }

    /**
     * Initializes the algebraic notation list.
     * @return 
     */
    private static List<String> initializeAlgebraicNotation() {
        List<String> list = Arrays.asList(new String[]{
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
        });
        return list;
}
    
}
