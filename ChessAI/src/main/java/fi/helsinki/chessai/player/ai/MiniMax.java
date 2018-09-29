package fi.helsinki.chessai.player.ai;

import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.board.MoveTransition;

/**
 *
 * @author janne
 */
public class MiniMax implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private final int searchDepth;
    
    public MiniMax(final int searchDepth) {
        this.evaluator = new StandardEvaluator();
        this.searchDepth = searchDepth;
    }
    
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
    
    private boolean gameEnd(Board board) {
        return board.currentPlayer().isInCheckMate() || board.currentPlayer().isInStaleMate();
    }    
    
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
