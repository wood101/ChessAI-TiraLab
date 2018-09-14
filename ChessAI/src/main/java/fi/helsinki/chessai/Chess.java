
import fi.helsinki.chessai.board.Board;

/**
 * 
 * @author janne
 */
public class Chess {
    
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
    }
}
