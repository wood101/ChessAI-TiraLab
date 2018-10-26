package fi.helsinki.chessai.player;

import fi.helsinki.chessai.player.BlackPlayer;
import fi.helsinki.chessai.player.Player;
import fi.helsinki.chessai.player.WhitePlayer;
import fi.helsinki.chessai.utility.BoardUtility;

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

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return whitePlayer;
        }

        @Override
        public Boolean isPromotionRow(int coordinate) {
            return BoardUtility.getRow(coordinate) == 1;
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

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return blackPlayer;
        }

        @Override
        public Boolean isPromotionRow(int coordinate) {
            return BoardUtility.getRow(coordinate) == 8;
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

    /**
     * Chooses the next player after a move.
     * @param whitePlayer
     * @param blackPlayer
     * @return 
     */
    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
    
    /**
     * Returns true if the pawn is on a row that causes a promotion.
     * @param coordinate
     * @return 
     */
    public abstract Boolean isPromotionRow(int coordinate);
}
