package fi.helsinki.chessai.player.ai;

import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Move;

/**
 * Interface for AI move generation strategies.
 * Just in case there are more than one
 * @author janne
 */
public interface MoveStrategy {
    Move execute(Board board);
}
