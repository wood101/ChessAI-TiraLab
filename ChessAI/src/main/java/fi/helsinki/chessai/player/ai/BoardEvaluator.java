package fi.helsinki.chessai.player.ai;

import fi.helsinki.chessai.board.Board;

/**
 *
 * @author janne
 */
public interface BoardEvaluator {
    int evaluate(Board board, int depth);
}
