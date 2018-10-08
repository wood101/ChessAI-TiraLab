package fi.helsinki.chessai.player.ai;

import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.pieces.Piece;
import fi.helsinki.chessai.player.Player;

/**
 * Standard evaluator of board states.
 * @author janne
 */
public final class StandardEvaluator implements BoardEvaluator {

    private static final int CHECK_SCORE = 50;
    private static final int CHECK_MATE_SCORE = 10000;
    private static final int DEPTH_SCORE = 100;
    private static final int CASTLE_SCORE = 60;
    private final static int MOBILITY_MULTIPLIER = 2;
    private final int STALEMATE_SCORE = -100;

    public StandardEvaluator() {
    }

    /**
     * Returns the evaluation of players score.
     * @param board
     * @param depth
     * @return 
     */
    @Override
    public int evaluate(Board board, int depth) {
        return scorePlayer(board, board.getWhitePlayer(), depth) - scorePlayer(board, board.getBlackPlayer(), depth);
    }

    /**
     * Returns the current total score of the player.
     * @param board
     * @param player
     * @param depth
     * @return 
     */
    private int scorePlayer(Board board, Player player, int depth) {
        return pieceValue(player) + mobility(player) + check(player) + checkMate(player, depth) + castled(player) /*+ staleMate(player) +*/ + (int) (Math.random() * 10);
    }

    /**
     * Returns the score of a single piece.
     * @param player
     * @return 
     */
    private static int pieceValue(Player player) {
        int score = 0;
        for(Piece piece : player.getActivePieces()) {
            score += piece.getPieceValue();
        }
        return score;
    }

    /**
     * The score for having more possible moves.
     * @param player
     * @return 
     */
    private static int mobility(final Player player) {
        return MOBILITY_MULTIPLIER * mobilityRatio(player);
    }
    
    /**
     * Returns the ratio of your number of moves versus opponents number of moves.
     * @param player
     * @return 
     */
    private static int mobilityRatio(final Player player) {
        return (int)((player.getLegalMoves().size() * 100.0f) / player.getOpponent().getLegalMoves().size());
}

    /**
     * Returns bonus score if the opponent is in check.
     * @param player
     * @return 
     */
    private static int check(Player player) {
        return player.getOpponent().isInCheck() ? CHECK_SCORE : 0;
    }
    /**
     * Returns bonus score if the opponent is in checkmate.
     * @param player
     * @param depth Bonus score is added the sooner this can be achieved.
     * @return 
     */
    private static int checkMate(Player player, int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_SCORE * depthScore(depth) : 0;
    }

    /**
     * Depth score for the checkMate method.
     * @param depth
     * @return 
     */
    private static int depthScore(int depth) {
        return depth == 0 ? 1 : DEPTH_SCORE * depth;
    }
    
    /**
     * Returns bonus score if the king is castled.
     * @param player
     * @return 
     */
    private static int castled(Player player) {
        return player.isCastled() ? CASTLE_SCORE : 0;
    }

    /**
     * Negative score for stalemate so the games stay more exiting.
     * Seems to slow down the algorithm quite a bit.
     * @param player
     * @return 
     */
    private int staleMate(Player player) {
        return player.isInStaleMate()? STALEMATE_SCORE : 0;
    }
    
}
