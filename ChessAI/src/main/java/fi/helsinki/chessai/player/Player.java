/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.player;

import fi.helsinki.chessai.board.MoveTransition;
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.pieces.King;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.board.pieces.Piece;
import fi.helsinki.chessai.board.pieces.Piece.PieceType;
import fi.helsinki.chessai.gui.Table;
import fi.helsinki.chessai.utility.MyList;

/**
 * Class for the players of the game.
 * @author janne
 */
public abstract class Player {
    
    protected final Board board;
    protected final King playerKing;
    protected final MyList<Move> legalMoves;
    private final boolean isInCheck;
    private final MyList<Move> castleMoves;
    
    /**
     * Constructor
     * @param board
     * @param legalMoves legal moves for the current player
     * @param opponentMoves legal moves of the opponent of the current player
     */
    Player(final Board board, final MyList<Move> legalMoves, final MyList<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.castleMoves = kingCastles(legalMoves, opponentMoves);
        legalMoves.addAll(castleMoves);
        this.legalMoves = legalMoves;
        this.isInCheck = !Player.attacksOnTile(this.playerKing.getPosition(), opponentMoves).isEmpty();
        
    }
    private King establishKing() {
        for(final Piece piece : getActivePieces()) {
            if(piece.getPieceType() == PieceType.KING) {
                return (King) piece;
            }
        }
        //throw new RuntimeException("Game doesn't function without a king");
        
        //For testing
        return new King(-666, Side.BLACK, true, false);
    }
    
    public King getPlayersKing() {
        return this.playerKing;
    }
    
    public MyList<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public MyList<Move> getCastleMoves() {
        return this.castleMoves;
    }
    
    public boolean isCastled() {
        return this.playerKing.isCastled();
    }    
    
    /**
     * Returns all the attacks on a specific tile.
     * @param position
     * @param moves
     * @return 
     */
    public static MyList<Move> attacksOnTile(int position, MyList<Move> moves) {
        MyList<Move> attackMoves = new MyList<>();
        for(final Move move : moves) {
            if(position == move.getDestination()) {
                attackMoves.add(move);
            }
        }
        return attackMoves;
    }
    
    /**
     * Returns true if a move is legal.
     * @param move
     * @return 
     */
    public boolean isLegalMove(final Move move) {
        return this.legalMoves.contains(move);
    }
    
    /**
     * Returns true if a player is in check.
     * @return 
     */
    public boolean isInCheck() {
        return this.isInCheck;
    }
    
    /**
     * Returns true if a player has won.
     * @return 
     */
    public boolean isInCheckMate() {
        return this.isInCheck && !hasMoves();
    }
    
    /**
     * Returns true if the game ended in a draw.
     * @return 
     */
    public boolean isInStaleMate() {
        return !this.isInCheck && !hasMoves() || Table.get().checkBoardRepetition() || this.getActivePieces().size() == 1 && this.getOpponent().getActivePieces().size() == 1;
    }
    
    /**
     * Same as the previous method, but without board repetition check. It slows down the AI.
     * @return 
     */
    public boolean isInStaleMateVsHuman() {
        return !this.isInCheck && !hasMoves() || this.getActivePieces().size() == 1 && this.getOpponent().getActivePieces().size() == 1;
    }    
    
    /**
     * Returns true if the king is able to escape check.
     * @return 
     */
    private boolean hasMoves() {
        for(final Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDone()) {
                return true;
            }  
        }
        return false;
    }    
    
    /**
     * Creates a new board with updated positions if the move is valid.
     * @param move
     * @return 
     */
    public MoveTransition makeMove(final Move move) {
        if(!isLegalMove(move)) {
            return new MoveTransition(this.board, move, MoveTransition.MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionBoard = move.execute();
        final MyList<Move> kingAttacks = Player.attacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayersKing().getPosition(), transitionBoard.currentPlayer().getLegalMoves());
        if(!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveTransition.MoveStatus.INCHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveTransition.MoveStatus.DONE);
    }
    
    
    public abstract MyList<Piece> getActivePieces();
    public abstract Side getSide();
    public abstract Player getOpponent();
    
    /**
     * Returns a list of moves that enables the king to castle.
     * @param currectPlayerMoves
     * @param opponentMoves
     * @return 
     */
    protected abstract MyList<Move> kingCastles(MyList<Move> currectPlayerMoves, MyList<Move> opponentMoves);
}
