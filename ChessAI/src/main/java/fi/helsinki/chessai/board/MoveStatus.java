package fi.helsinki.chessai.board;

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
