/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fi.helsinki.chessai.board.Board;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author janne
 */
public class BoardTest {
    Board board;
    
    @Before
    public void setUp() {
        board = Board.createStandardBoard();
    }
    
    @Test
    public void testPieceSetup() {
        assertTrue(board.getTile(0).toString().equals("r"));
        assertTrue(board.getTile(1).toString().equals("n"));
        assertTrue(board.getTile(55).toString().equals("P"));
        assertTrue(board.getTile(59).toString().equals("Q"));
        assertTrue(board.getTile(4).toString().equals("k"));
        assertTrue(board.getTile(2).toString().equals("b"));
        assertTrue(board.getTile(1).getPiece().getPieceSide().isBlack());
    }
}
