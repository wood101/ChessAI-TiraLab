/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Board.Builder;
import fi.helsinki.chessai.board.pieces.*;
import fi.helsinki.chessai.utility.MyList;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author janne
 */
public class PieceMoveListTest {
    Builder boardBuilder;
    Board board;
    
    @Before
    public void setUp() {
        boardBuilder = new Builder();
        boardBuilder.setMoveMaker(Side.WHITE);
    }
    
    @Test
    public void TestMoveListForKnight() {
        boardBuilder.setPiece(new Knight(20, Side.BLACK, true));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(20).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 8);
    }

    @Test
    public void TestMoveListForBishop() {
        boardBuilder.setPiece(new Bishop(12, Side.BLACK, true));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(12).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 9);
    }

    @Test
    public void TestMoveListForRook() {
        boardBuilder.setPiece(new Rook(12, Side.BLACK, true));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(12).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 14);
    }
    
    @Test
    public void TestMoveListForQueen() {
        boardBuilder.setPiece(new Queen(12, Side.BLACK, true));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(12).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 23);
    }
    
    @Test
    public void TestMoveListForKing() {
        boardBuilder.setPiece(new King(26, Side.WHITE, true, false));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(26).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 8);
    }
    
    @Test
    public void TestAttackMoveWithKing() {
        boardBuilder.setPiece(new King(26, Side.WHITE, true, false));
        boardBuilder.setPiece(new Bishop(25, Side.BLACK, true));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(26).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 8);
    }

    @Test
    public void TestAttackMoveWithKnight() {
        boardBuilder.setPiece(new Knight(0, Side.WHITE, true));
        boardBuilder.setPiece(new Bishop(10, Side.BLACK, true));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(0).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 2);
    }
    
    @Test
    public void TestAttackMoveWithVectorMovement() {
        boardBuilder.setPiece(new Bishop(0, Side.WHITE, true));
        boardBuilder.setPiece(new Bishop(63, Side.BLACK, true));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(0).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 7);
    }
    
    @Test
    public void TestMoveOnAlly() {
        boardBuilder.setPiece(new King(26, Side.WHITE, true, false));
        boardBuilder.setPiece(new Bishop(25, Side.WHITE, true));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(26).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 7);
    }

    @Test
    public void TestLegalMovesForKnight() {
        boardBuilder.setPiece(new Knight(12, Side.BLACK, true));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(12).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 6);
    }
    
    @Test
    public void TestBottomRightCornerMoves() {
        boardBuilder.setPiece(new King(63, Side.WHITE, true, false));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(63).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 3);
    }

    @Test
    public void TestTopLeftCornerMoves() {
        boardBuilder.setPiece(new King(0, Side.WHITE, true, false));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(0).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 3);
    }
    
    @Test
    public void TestPawnAttack() {
        boardBuilder.setPiece(new Pawn(5, Side.BLACK, false));
        boardBuilder.setPiece(new Pawn(12, Side.WHITE, false));
        boardBuilder.setPiece(new Pawn(3, Side.BLACK, false));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(12).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 3);
    }
    
    @Test
    public void TestPawnJump() {
        boardBuilder.setPiece(new Pawn(22, Side.WHITE, true));
        boardBuilder.setPiece(new Pawn(21, Side.WHITE, false));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(22).getPiece().getLegalMoves(board);
        MyList<Move> moves2 = board.getTile(21).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 2);
        assertTrue(moves2.size() == 1);
    }
    
    @Test
    public void TestEnPassantRight() {
        Pawn pawn = new Pawn(23, Side.BLACK, false);
        boardBuilder.setPiece(pawn);
        boardBuilder.setPiece(new Pawn(22, Side.WHITE, false));
        boardBuilder.setEnPassantPawn(pawn);
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(22).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 2);
    } 
    
    @Test
    public void TestEnPassantLeft() {
        Pawn pawn = new Pawn(22, Side.BLACK, false);
        boardBuilder.setPiece(pawn);
        boardBuilder.setPiece(new Pawn(23, Side.WHITE, false));
        boardBuilder.setEnPassantPawn(pawn);
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(23).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 2);
    }    
    
    @Test
    public void TestCastlingForBlack() {
        boardBuilder.setPiece(new Rook(7, Side.BLACK, true));
        boardBuilder.setPiece(new Rook(0, Side.BLACK, true));
        boardBuilder.setPiece(new King(4, Side.BLACK, true, false));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(4).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 7);
    }  
    
    @Test
    public void TestCastlingForWhite() {
        boardBuilder.setPiece(new Rook(63, Side.WHITE, true));
        boardBuilder.setPiece(new Rook(56, Side.WHITE, false));
        boardBuilder.setPiece(new King(59, Side.WHITE, true, false));
        board = boardBuilder.build();
        MyList<Move> moves = board.getTile(59).getPiece().getLegalMoves(board);
        assertTrue(moves.size() == 6);
    } 
}
