package fi.helsinki.chessai.player.ai;

import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.board.MoveTransition;

/**
 * Class for the Minimax algorithm.
 * @author janne
 */
public class MiniMax implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private final int searchDepth;
    
    public MiniMax(final int searchDepth) {
        this.evaluator = new StandardEvaluator();
        this.searchDepth = searchDepth;
    }
    
    /**
     * Returns the best move that the algorithm got.
     * @param board
     * @return 
     */
    @Override
    public Move execute(Board board) {
        final long startTime = System.currentTimeMillis();
        Move bestMove = null;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int value; 
        for(final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                value = board.currentPlayer().getSide().isWhite() ? min(moveTransition.getTransitionBoard(), searchDepth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE) : max(moveTransition.getTransitionBoard(), searchDepth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
                
                if (board.currentPlayer().getSide().isWhite() && value >= max) {
                    max = value;
                    bestMove = move;
                } else if (board.currentPlayer().getSide().isBlack() && value <= min) {
                    min = value;
                    bestMove = move;                    
                }
            }
        }
        final long executionTime = System.currentTimeMillis() - startTime;
        return bestMove;
    }
    
    /**
     * Returns true if the game state is at an end.
     * @param board
     * @return 
     */
    private boolean gameEnd(Board board) {
        return board.currentPlayer().isInCheckMate() || board.currentPlayer().isInStaleMate();
    }    
    
    /**
     * The min portion of the algorithm.
     * @param board
     * @param searchDepth
     * @param alpha
     * @param beta
     * @return 
     */
    public int min(final Board board, final int searchDepth, int alpha, int beta) {
        if(searchDepth == 0 || gameEnd(board)) {
            return this.evaluator.evaluate(board, searchDepth);
        }
        int val = Integer.MAX_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()) {
                val = Math.min(val, max(moveTransition.getTransitionBoard(), searchDepth - 1, alpha, beta));
                beta = Math.min(beta, val);
                if(alpha >= beta) return val;
            }
        }
        return val;
    }
    
     /**
     * The max portion of the algorithm.
     * @param board
     * @param searchDepth
     * @param alpha
     * @param beta
     * @return 
     */
    public int max(final Board board, final int searchDepth, int alpha, int beta) {
        if(searchDepth == 0 || gameEnd(board)) {
            return this.evaluator.evaluate(board, searchDepth);
        }
        int val = Integer.MIN_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()) {
                val = Math.max(val, min(moveTransition.getTransitionBoard(), searchDepth - 1, alpha, beta));
                alpha = Math.max(alpha, val);
                if(alpha >= beta) return val;
            }
        }
        return val;   
    }
    
    @Override
    public String toString() {
        return "MiniMax";
    }
}
