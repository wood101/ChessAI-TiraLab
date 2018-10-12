package fi.helsinki.chessai.board;

/**
 * Class for the transitioning of an old board to the new board because of piece movement.
 * @author janne
 */
public class MoveTransition {
    private final Board board;
    private final Move move;
    private final MoveStatus moveStatus;
    
    public MoveTransition(final Board board, final Move move, final MoveStatus moveStatus) {
        this.board = board;
        this.move = move;
        this.moveStatus = moveStatus;
    }
    
    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public Board getTransitionBoard() {
        return this.board;
    }    
    
    public Object getBoard() {
        return this.board;
    }
    
    /**
     * Enum for the status of the move
     * @author janne
     */
    public enum MoveStatus {
        DONE {
            @Override
            public boolean isDone() {
                return true;
            }
        },
        ILLEGAL_MOVE{
            @Override
            public boolean isDone() {
                return false;
            }
        },
        INCHECK{
            @Override
            public boolean isDone() {
                return false;
            }
        };

        /**
         * Returns true if the move is valid.
         * @return 
         */
        public abstract boolean isDone();
    }    
}
