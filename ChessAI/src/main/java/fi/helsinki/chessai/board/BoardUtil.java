/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board;

import java.util.List;

/**
 *
 * @author janne
 */
public class BoardUtil {
    
    public static final int NumTiles = 64;
    public static final int NumTilesInRow = 8;
    
    private BoardUtil() {
        throw new RuntimeException("Utility class only");
    }
    
    public static boolean isValidTile(int coordinate) {
        return coordinate >=0 && coordinate < 64;
    }
}
