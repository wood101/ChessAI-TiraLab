package fi.helsinki.chessai.utility;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Tile;
import fi.helsinki.chessai.board.pieces.Piece;

/**
 * Utility class for the pieces
 * @author janne
 */
public class PieceUtil {
    
    
    /**
     * Gets the legal moves for all pieces that move according to a vector.
     * @param board current playing board
     * @param piece the piece that move list is generated for
     * @param vectors the vectors the piece can move
     * @return list of all the possible moves
     */
    public static MyList<Move> getLegalVectorMoves(Board board, Piece piece, int[] vectors) {
        final MyList<Move> legalMoves = new MyList<>();
        for(final int offset : vectors) {
            int pieceDestination = piece.getPosition() + offset;
            while(BoardUtility.isValidTile(pieceDestination) && !BoardUtility.isOutOfBounds(pieceDestination - offset, pieceDestination, 1)) {
                    final Tile destinationTile = board.getTile(pieceDestination);
                    if(!destinationTile.occupied()) {
                        legalMoves.add(new Move.RegularMove(board, piece, pieceDestination));
                    } else {
                        final Piece pieceAtDestination = destinationTile.getPiece();
                        final Side pieceSide = pieceAtDestination.getPieceSide();
                        if(piece.getPieceSide() != pieceSide) {
                            legalMoves.add(new Move.AttackMove(board, piece, pieceDestination, pieceAtDestination));
                        }
                        break;
                    }
                pieceDestination += offset;
            }
        }
        return legalMoves;
    }


}
