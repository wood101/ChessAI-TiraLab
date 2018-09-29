package fi.helsinki.chessai.player.ai;

import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Move;

/**
 *
 * @author janne
 */
public interface MoveStrategy {
    
    Move execute(Board board);
}
