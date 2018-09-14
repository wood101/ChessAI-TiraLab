/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board;

import java.util.List;
//TODO Remove this?
/**
 *Utility class for the board class.
 * @author janne
 */
public class BoardUtil {
    
    public static final int NumTiles = 64;
    
    private BoardUtil() {
        throw new RuntimeException("Utility class only");
    }
    
    /**
     * Checks if the tile exists on the board.
     * @param coordinate coordinate of the tile
     * @return True if it does, false if it doesn't
     */
    public static boolean isValidTile(int coordinate) {
        return coordinate >=0 && coordinate < 64;
    }
}
