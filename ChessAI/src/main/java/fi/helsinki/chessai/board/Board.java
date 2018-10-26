package fi.helsinki.chessai.board;

import fi.helsinki.chessai.player.Side;
import fi.helsinki.chessai.board.pieces.*;
import fi.helsinki.chessai.player.BlackPlayer;
import fi.helsinki.chessai.player.Player;
import fi.helsinki.chessai.player.WhitePlayer;
import fi.helsinki.chessai.utility.MyList;

/**
 * The class for the chessboard.
 * @author janne
 */
public final class Board {
    private final MyList<Tile> gameboard;
    private final MyList<Piece> whitePieces;
    private final MyList<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;
    private final Pawn enPassantPawn;
    
    /**
     * Constructor utilizing the builder class.
     * @param builder 
     */
    private Board(final Builder builder) {
        this.gameboard = createBoard(builder);
        this.enPassantPawn = builder.enPassantPawn;
        this.whitePieces = activePieces(this.gameboard, Side.WHITE);
        this.blackPieces = activePieces(this.gameboard, Side.BLACK);
        final MyList<Move> whiteLegalMoves = legalMovesForPieces(this.whitePieces);
        final MyList<Move> blackLegalMoves = legalMovesForPieces(this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, whiteLegalMoves, blackLegalMoves);
        this.blackPlayer = new BlackPlayer(this, blackLegalMoves, whiteLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }
    
    /**
     * Returns the tile on a given coordinate.
     * @param tileCoordinate
     * @return 
     */
    public Tile getTile(final int tileCoordinate) {
        return gameboard.get(tileCoordinate);
    }
    
    /**
     * Creates the game board of out tiles.
     * @param builder
     * @return List of tiles
     */
    private static MyList<Tile> createBoard(final Builder builder) {
        final MyList<Tile> list = new MyList<>();
        for (int i = 0; i < 64; i++) {
            list.add(Tile.createTile(i, null));
        }
        for(Piece piece : builder.boardConfig) {
            list.set(piece.getPosition(), Tile.createTile(piece.getPosition(), piece));
        }
        return list;
    }
    
    /**
     * Creates the standard chess game board with the pieces.
     * @return the game board
     */
    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        //Black pieces
        builder.setPiece(new King(4, Side.BLACK, true, false));
        builder.setPiece(new Queen(3, Side.BLACK, true));
        builder.setPiece(new Knight(1, Side.BLACK, true));
        builder.setPiece(new Knight(6, Side.BLACK, true));
        builder.setPiece(new Rook(0, Side.BLACK, true));
        builder.setPiece(new Rook(7, Side.BLACK, true));
        builder.setPiece(new Bishop(2, Side.BLACK, true));
        builder.setPiece(new Bishop(5, Side.BLACK, true));
        for(int i = 8; i <= 15; i++) {
            builder.setPiece(new Pawn(i, Side.BLACK, true));
        }
        //White pieces
        builder.setPiece(new King(60, Side.WHITE, true, false));
        builder.setPiece(new Queen(59, Side.WHITE, true));
        builder.setPiece(new Knight(57, Side.WHITE, true));
        builder.setPiece(new Knight(62, Side.WHITE, true));
        builder.setPiece(new Rook(56, Side.WHITE, true));
        builder.setPiece(new Rook(63, Side.WHITE, true));
        builder.setPiece(new Bishop(61, Side.WHITE, true));
        builder.setPiece(new Bishop(58, Side.WHITE, true));
        for(int i = 48; i <= 55; i++) {
            builder.setPiece(new Pawn(i, Side.WHITE, true));
        }
        builder.setMoveMaker(Side.WHITE);
        builder.setEnPassantPawn(null);
        return builder.build();
    }
    
    
    /**
     * Lists the pieces currently on the game board.
     * @param gameboard 
     * @param side 
     * @return 
     */
    public MyList<Piece> activePieces(final MyList<Tile> gameboard, final Side side) {
        final MyList<Piece> activePieces = new MyList<>();
        
        for(final Tile tile : gameboard) {
            if(tile.occupied()) {
                final Piece piece = tile.getPiece();
                if(piece.getPieceSide() == side) {
                    activePieces.add(piece);
                }
            }
        }
        return activePieces;
    }
    
    /**
     * Returns all black pieces
     * @return 
     */
    public MyList<Piece> getBlackPieces() {
        return this.blackPieces;
    }
    
    /**
     * Returns all white pieces
     * @return 
     */
    public MyList<Piece> getWhitePieces() {
        return this.whitePieces;
    }
    
    /**
     * Gives all moves on the board.
     * @return 
     */
    public MyList<Move> getAllLegalMoves() {
        MyList<Move> list = this.whitePlayer.getLegalMoves();
        list.addAll(this.blackPlayer.getLegalMoves());
        return list;
    }    
    
    /**
     * Gives the list of possible legal moves.
     * @param pieces
     * @return 
     */
    private MyList<Move> legalMovesForPieces(MyList<Piece> pieces) {
        final MyList<Move> legalMoves = new MyList<>(); 
        for (final Piece piece : pieces) {
            legalMoves.addAll(piece.getLegalMoves(this));
        }
        return legalMoves;
    }

    /**
     * Returns the white player.
     * @return 
     */
    public Player getWhitePlayer() {
        return this.whitePlayer;
    }
    
    /**
     * Returns the black player.
     * @return 
     */
    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    /**
     * Returns the player whose turn it is.
     * @return 
     */
    public Player currentPlayer() {
        return this.currentPlayer;
    }
    
    /**
     * Returns the player whose turn it is.
     * @return 
     */
    public Pawn getEnPassantPawn() {
        return this.enPassantPawn;
    }
    
    /**
     * Returns all the pieces currently on the board.
     * @return 
     */
    public MyList<Piece> getAllPieces() {
        final MyList<Piece> allPieces = new MyList<>();
        Piece piece = null;
        for(final Tile tile : gameboard) {
            if(tile.occupied()) {
                piece = tile.getPiece();
                allPieces.add(piece);
            }
        }
        return allPieces;
    }

    /**
     * Class to build the chessboard.
     */
    public static class Builder {
        
        private MyList<Piece> boardConfig;
        private Side nextMoveMaker;
        private Pawn enPassantPawn;
        
        public Builder() {
            this.boardConfig = new MyList<>();
        }
        /**
         * Place a piece on the game board.
         * @param piece the piece to be placed
         * @return 
         */
        public Builder setPiece(final Piece piece) {
            this.boardConfig.add(piece);
            return this;
        }
        
        /**
         * Sets whose turn it is at the moment.
         * @param nextMoveMaker
         * @return 
         */
        public Builder setMoveMaker(final Side nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }
        
        /**
         * Sets the en passant pawn if that move was executed.
         * @param enPassantPawn
         * @return 
         */
        public Builder setEnPassantPawn(final Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
            return this;
        }
        
        public Board build() {
            return new Board(this);
        }
    }
    
    
    /**
     * Represents the chessboard as a String.
     * @return 
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 64; i++) {
            final String tileText = this.gameboard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if((i + 1) % 8 == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }
    

}
