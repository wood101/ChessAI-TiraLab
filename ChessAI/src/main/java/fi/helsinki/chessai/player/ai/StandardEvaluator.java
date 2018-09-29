package fi.helsinki.chessai.player.ai;

import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.pieces.Piece;
import fi.helsinki.chessai.player.Player;

/**
 *
 * @author janne
 */
public final class StandardEvaluator implements BoardEvaluator {

    private static final int CHECK_SCORE = 50;
    private static final int CHECK_MATE_SCORE = 10000;
    private static final int DEPTH_SCORE = 100;
    private static int CASTLE_SCORE = 60;

    public StandardEvaluator() {
    }

    @Override
    public int evaluate(Board board, int depth) {
        return scorePlayer(board, board.getWhitePlayer(), depth) - scorePlayer(board, board.getBlackPlayer(), depth);
    }

    private int scorePlayer(Board board, Player player, int depth) {
        return pieceValue(player) + mobility(player) + check(player) + checkMate(player, depth) + castled(player);
    }

    private static int pieceValue(Player player) {
        int score = 0;
        for(Piece piece : player.getActivePieces()) {
            score += piece.getPieceValue();
        }
        return score;
    }

    private static int mobility(Player player) {
        return player.getLegalMoves().size();
    }

    private static int check(Player player) {
        return player.getOpponent().isInCheck() ? CHECK_SCORE : 0;
    }

    private static int checkMate(Player player, int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_SCORE * depthScore(depth) : 0;
    }

    private static int depthScore(int depth) {
        return depth == 0 ? 1 : DEPTH_SCORE * depth;
    }

    private static int castled(Player player) {
        return player.isCastled() ? CASTLE_SCORE : 0;
    }
    
}
