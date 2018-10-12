/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.utility;

import java.util.Arrays;
import java.util.List;

/**
 * Utility for the board class.
 * @author janne
 */
public class BoardUtility {
    public static final List<String> ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    public static final MyList<String> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();


    
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
        return row + 1;
    }

    
    /**
     * Returns the column of the tile.
     * @param tileId
     * @return 
     */
    public static int getColumn(int tileId) {
        int column = (tileId + 1) % 8;
        if(column == 0) {
            return 8;
        }
        return column;
    }
    
    /**
     * Initializes the map for the algebraic notations of the board.
     * @return 
     */
    private static MyList<String> initializePositionToCoordinateMap() {
        final MyList<String> positionToCoordinate = new MyList<>();
        for (int i = 0; i < 64; i++) {
            positionToCoordinate.add(ALGEBRAIC_NOTATION.get(i));
        }
        return positionToCoordinate;
    }

    /**
     * Initializes the algebraic notation list.
     * @return 
     */
    private static List<String> initializeAlgebraicNotation() {
        List<String> list = Arrays.asList(new String[]{
                "A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8",
                "A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7",
                "A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6",
                "A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5",
                "A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4",
                "A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3",
                "A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2",
                "A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1"
        });
        return list;
    }
    
    /**
     * Gives the coordinate of an algebraic notation position.
     * @param position
     * @return 
     */
    public static int getCoordinateAtPosition(final String position) {
        for (int i = 0; i < 64; i++) {
            if(POSITION_TO_COORDINATE.get(i).equals(position)) return i;
        }
        throw new RuntimeException("Game board does not fit standards");
    }

    /**
     * Gives the algebraic notation position of a coordinate.
     * @param coordinate
     * @return 
     */
    public static String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION.get(coordinate);
    }
}
