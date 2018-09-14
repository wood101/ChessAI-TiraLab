/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.pieces.*;
import java.util.Collection;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author janne
 */
public class PieceTest {
    
    @Test
    public void TestMoveListForKnight() {
    Board.Builder boardBuilder = new Board.Builder();
    boardBuilder.setPiece(new Knight(12, Side.BLACK));
    Board board = boardBuilder.build();
    Collection<Move> moves = board.getTile(12).getPiece().getLegalMoves(board);
    assertTrue(moves.size() == 6);
    }

    @Test
    public void TestMoveListForBishop() {
    Board.Builder boardBuilder = new Board.Builder();
    boardBuilder.setPiece(new Bishop(8, Side.BLACK));
    Board board = boardBuilder.build();
    Collection<Move> moves = board.getTile(8).getPiece().getLegalMoves(board);
    System.out.println(moves.size());
    assertTrue(moves.size() == 7);
    }
}
