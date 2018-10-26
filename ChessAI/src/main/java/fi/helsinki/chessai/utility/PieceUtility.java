package fi.helsinki.chessai.utility;

/*
 * To change piece license header, choose License Headers in Project Properties.
 * To change piece template file, choose Tools | Templates
 * and open the template in the editor.
 */
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.player.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Tile;
import fi.helsinki.chessai.board.pieces.Piece;

/**
 * Utility class for the pieces
 * @author janne
 */
public class PieceUtility {
    
    
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
    
    /**
     * Legal moves for a piece that doesn't move in a vector.
     * @param board
     * @param piece
     * @param moves
     * @param maxDistance Maximum distance of columns the piece can move
     * @return returns legal moves
     */
    public static MyList<Move> getLegalSingleMoves(Board board, Piece piece, int[] moves, int maxDistance) {
        final MyList<Move> legalMoves = new MyList<>();
        for(final int offset : moves) {
            int pieceDestination = piece.getPosition() + offset;    
            if(BoardUtility.isValidTile(pieceDestination) && !BoardUtility.isOutOfBounds(piece.getPosition(), pieceDestination, maxDistance)) {
                final Tile destinationTile = board.getTile(pieceDestination);
                if(!destinationTile.occupied()) {
                    legalMoves.add(new Move.RegularMove(board, piece, pieceDestination));
                } else {
                    final Piece pieceAtDestination = destinationTile.getPiece();
                    if(piece.getPieceSide() != pieceAtDestination.getPieceSide()) {
                        legalMoves.add(new Move.AttackMove(board, piece, pieceDestination, pieceAtDestination));
                    }
                }
            }
        }
        return legalMoves;
    }

}
