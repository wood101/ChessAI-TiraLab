package fi.helsinki.chessai.player.ai;

import fi.helsinki.chessai.board.Board;

/**
 * Interface for board evaluators.
 * Just in case there are more than one
 * @author janne
 */
public interface BoardEvaluator {
    int evaluate(Board board, int depth);
}
