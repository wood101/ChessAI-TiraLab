/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.player;

import fi.helsinki.chessai.board.MoveTransition;
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.MoveStatus;
import fi.helsinki.chessai.board.pieces.King;
import fi.helsinki.chessai.board.pieces.Move;
import fi.helsinki.chessai.board.pieces.Piece;
import fi.helsinki.chessai.board.pieces.Piece.PieceType;
import fi.helsinki.chessai.utility.MoveCombiner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class for the players of the game.
 * @author janne
 */
public abstract class Player {
    
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;
    
    /**
     * Constructor
     * @param board
     * @param legalMoves legal moves for the current player
     * @param opponentMoves legal moves of the opponent of the current player
     */
    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = MoveCombiner.combineMoves(legalMoves, kingCastles(legalMoves, opponentMoves));
        this.isInCheck = !Player.attacksOnTile(this.playerKing.getPosition(), opponentMoves).isEmpty();
        
    }
    private King establishKing() {
        for(final Piece piece : getActivePieces()) {
            if(piece.getPieceType() == PieceType.KING) {
                return (King) piece;
            }
        }
        //throw new RuntimeException("Game doesn't function without a king");
        return new King(0, Side.BLACK);
    }
    
    public King getPlayersKing() {
        return this.playerKing;
    }
    
    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }
    
    /**
     * Returns all the move that the opponent is able to attack with.
     * @param position
     * @param moves
     * @return 
     */
    public static Collection<Move> attacksOnTile(int position, Collection<Move> moves) {
        final Collection<Move> attackMoves = new ArrayList<>();
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
        return !this.isInCheck && !hasMoves();
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
     * Returns true if the piece has castled.
     * @return 
     */
    public boolean isCastled() {
        return false;
    }
    
    /**
     * Creates a new board with updated positions if the move is valid.
     * @param move
     * @return 
     */
    public MoveTransition makeMove(final Move move) {
        if(!isLegalMove(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionBoard = move.Execute();
        final Collection<Move> kingAttacks = Player.attacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayersKing().getPosition(), transitionBoard.currentPlayer().getLegalMoves());
        if(!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.INCHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }
    
    
    public abstract Collection<Piece> getActivePieces();
    public abstract Side getSide();
    public abstract Player getOpponent();
    
    /**
     * Returns a collection of moves that enables the king to castle.
     * @param currectPlayerMoves
     * @param opponentMoves
     * @return 
     */
    protected abstract Collection<Move> kingCastles(Collection<Move> currectPlayerMoves, Collection<Move> opponentMoves);


}
