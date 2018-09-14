package fi.helsinki.chessai.board.pieces;

/**
 * Enum for the different sides of the pieces in chess.
 * @author janne
 */
public enum Side {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }
    };
    
    /**
     * Gives the direction the pawn moves.
     * @return -1(up) for white and 1(down) for black
     */
    public abstract int getDirection();
    
    /**
     * Returns true if the pieces side is white.
     * @return 
     */
    public abstract boolean isWhite();
    
        /**
     * Returns true if the pieces side is black.
     * @return 
     */
    public abstract boolean isBlack();
}
