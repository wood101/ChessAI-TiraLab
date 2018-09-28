/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Tile;
import fi.helsinki.chessai.utility.BoardUtility;
import fi.helsinki.chessai.utility.MyList;

/**
 * The class for the king piece
 * @author janne
 */
public class King extends Piece{
    private final static int[] PossibleMoves = {-9, -8, -7, -1, 1, 7, 8, 9};
    
    /**
     * Constructor
     * @param position the position of the piece on the board
     * @param pieceSide The colour of the piece
     * @param firstMove
     */
    public King (final int position, final Side pieceSide, final boolean firstMove) {
        super(PieceType.KING, position, pieceSide, firstMove);
    }
    
     /**
     * Legal moves for the king piece.
     * @param board
     * @return returns legal moves
     */
    @Override
    public MyList<Move> getLegalMoves(Board board) {
        int pieceDestination;
        final MyList<Move> legalMoves = new MyList<>();
        
        for(final int offset : PossibleMoves) {
            pieceDestination = this.position + offset;     
            if(BoardUtility.isValidTile(pieceDestination) && !BoardUtility.isOutOfBounds(this.position, pieceDestination, 1)) {
                
                final Tile destinationTile = board.getTile(pieceDestination);
                
                if(!destinationTile.occupied()) {
                    legalMoves.add(new Move.RegularMove(board, this, pieceDestination));
                } else {
                    final Piece pieceAtDestination = destinationTile.getPiece();
                    if(this.pieceSide != pieceAtDestination.getPieceSide()) {
                        legalMoves.add(new Move.AttackMove(board, this, pieceDestination, pieceAtDestination));
                    }
                }
            }
        }
        return legalMoves;
    }
    
    @Override
    public King movePiece(final Move move) {
        return new King(move.getDestination(), move.getMovedPiece().getPieceSide(), move.getMovedPiece().isFirstMove());
    }
    
    @Override
    public String toString() {
        return PieceType.KING.toString();
    }
}
