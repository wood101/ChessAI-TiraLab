/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.player;

import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Tile;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.board.pieces.Piece;
import fi.helsinki.chessai.board.pieces.Rook;
import fi.helsinki.chessai.utility.MyList;

/**
 * The class for the player of the black pieces.
 * @author janne
 */
public class BlackPlayer extends Player {
    
    public BlackPlayer(Board board, MyList<Move> blackLegalMoves, MyList<Move> whiteLegalMoves) {
        super(board, blackLegalMoves, whiteLegalMoves);
    }

    @Override
    public MyList<Piece> getActivePieces() {
        return board.getBlackPieces();
    }

    @Override
    public Side getSide() {
        return Side.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }
    
    @Override
    protected MyList<Move> kingCastles(MyList<Move> currectPlayerMoves, MyList<Move> opponentMoves) {
        final MyList<Move> kingCastles = new MyList<>();
        //King side castle
        if(this.playerKing.isFirstMove() && !this.isInCheck()) {
            if(!this.board.getTile(5).occupied() && !this.board.getTile(6).occupied()) {
                final Tile rookTile = this.board.getTile(7);
                if(rookTile.occupied() && rookTile.getPiece().isFirstMove()) {
                    if(Player.attacksOnTile(5, opponentMoves).isEmpty() && Player.attacksOnTile(6, opponentMoves).isEmpty() && rookTile.getPiece().getPieceType() == Piece.PieceType.ROOK){
                        kingCastles.add(new Move.CastleMove(this.board, this.playerKing, 6, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 5));    
                    }
                }
            }
            //Queen side castle
            if(!this.board.getTile(1).occupied() && !this.board.getTile(2).occupied() && !this.board.getTile(3).occupied()) {
                final Tile rookTile = this.board.getTile(0);
                if(rookTile.occupied() && rookTile.getPiece().isFirstMove()) {
                    if(Player.attacksOnTile(2, opponentMoves).isEmpty() && Player.attacksOnTile(3, opponentMoves).isEmpty() && rookTile.getPiece().getPieceType() == Piece.PieceType.ROOK){
                        kingCastles.add(new Move.CastleMove(this.board, this.playerKing, 2, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 3));   
                    }
                   
                }
            }
        }
        return kingCastles;
    }
    
    @Override
    public String toString() {
        return "Black player";
    }
}
