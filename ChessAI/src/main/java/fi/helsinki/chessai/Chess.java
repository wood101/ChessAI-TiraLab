package fi.helsinki.chessai;


import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.gui.Table;

/**
 * 
 * @author janne
 */
public class Chess {
    
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
        
        Table table = new Table();
    }
}
