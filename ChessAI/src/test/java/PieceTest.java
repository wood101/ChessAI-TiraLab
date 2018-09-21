/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fi.helsinki.chessai.Side;
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
        boardBuilder.setPiece(new Knight(20, Side.BLACK));
        Board board = boardBuilder.build();
        Collection<Move> moves = board.getTile(20).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 8);
    }

    @Test
    public void TestMoveListForBishop() {
        Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new Bishop(12, Side.BLACK));
        Board board = boardBuilder.build();
        Collection<Move> moves = board.getTile(12).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 9);
    }

    @Test
    public void TestMoveListForRook() {
        Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new Rook(12, Side.BLACK));
        Board board = boardBuilder.build();
        Collection<Move> moves = board.getTile(12).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 14);
    }
    
    @Test
    public void TestMoveListForQueen() {
        Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new Queen(12, Side.BLACK));
        Board board = boardBuilder.build();
        Collection<Move> moves = board.getTile(12).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 23);
    }
    
    @Test
    public void TestMoveListForKing() {
        Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new King(26, Side.WHITE));
        Board board = boardBuilder.build();
        Collection<Move> moves = board.getTile(26).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 8);
    }
    
    @Test
    public void TestAttackMoveWithKing() {
        Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new King(26, Side.WHITE));
        boardBuilder.setPiece(new Bishop(25, Side.BLACK));
        Board board = boardBuilder.build();
        Collection<Move> moves = board.getTile(26).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 8);
    }

    @Test
    public void TestAttackMoveWithKnight() {
        Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new Knight(0, Side.WHITE));
        boardBuilder.setPiece(new Bishop(10, Side.BLACK));
        Board board = boardBuilder.build();
        Collection<Move> moves = board.getTile(0).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 2);
    }
    
    @Test
    public void TestAttackMoveWithVectorMovement() {
        Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new Bishop(0, Side.WHITE));
        boardBuilder.setPiece(new Bishop(63, Side.BLACK));
        Board board = boardBuilder.build();
        Collection<Move> moves = board.getTile(0).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 7);
    }
    
    @Test
    public void TestMoveOnAlly() {
        Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new King(26, Side.WHITE));
        boardBuilder.setPiece(new Bishop(25, Side.WHITE));
        Board board = boardBuilder.build();
        Collection<Move> moves = board.getTile(26).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 7);
    }

    @Test
    public void TestLegalMovesForKnight() {
        Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new Knight(12, Side.BLACK));
        Board board = boardBuilder.build();
        Collection<Move> moves = board.getTile(12).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 6);
    }
    
    @Test
    public void TestBottomRightCornerMoves() {
        Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new King(63, Side.WHITE));
        Board board = boardBuilder.build();
        Collection<Move> moves = board.getTile(63).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 3);
    }

    @Test
    public void TestTopLeftCornerMoves() {
        Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new King(0, Side.WHITE));
        Board board = boardBuilder.build();
        Collection<Move> moves = board.getTile(0).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 3);
    }       
}
