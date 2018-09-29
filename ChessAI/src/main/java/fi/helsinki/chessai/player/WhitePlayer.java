/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.player;

import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.Tile;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.board.pieces.Piece;
import fi.helsinki.chessai.board.pieces.Piece.PieceType;
import fi.helsinki.chessai.board.pieces.Rook;
import fi.helsinki.chessai.utility.MyList;

/**
 * The class for the player of the black pieces.
 * @author janne
 */
public class WhitePlayer extends Player {
    
    public WhitePlayer(Board board, MyList<Move> whiteLegalMoves, MyList<Move> blackLegalMoves) {
        super(board, whiteLegalMoves, blackLegalMoves);
        
    }

    @Override
    public MyList<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Side getSide() {
        return Side.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }

    @Override
    protected MyList<Move> kingCastles(MyList<Move> currectPlayerMoves, MyList<Move> opponentMoves) {
        final MyList<Move> kingCastles = new MyList<>();
        //King side castle
        if(this.playerKing.isFirstMove() && !this.isInCheck()) {
            if(!this.board.getTile(61).occupied() && !this.board.getTile(62).occupied()) {
                final Tile rookTile = this.board.getTile(63);
                if(rookTile.occupied() && rookTile.getPiece().isFirstMove()) {
                    if(Player.attacksOnTile(61, opponentMoves).isEmpty() && Player.attacksOnTile(62, opponentMoves).isEmpty() && rookTile.getPiece().getPieceType() == PieceType.ROOK) {
                        kingCastles.add(new Move.CastleMove(this.board, this.playerKing, 62, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 61));   
                    }
                    
                }
            }
            //Queen side castle
            if(!this.board.getTile(59).occupied() && !this.board.getTile(58).occupied() && !this.board.getTile(57).occupied()) {
                final Tile rookTile = this.board.getTile(56);
                if(rookTile.occupied() && rookTile.getPiece().isFirstMove()) {
                    if(Player.attacksOnTile(58, opponentMoves).isEmpty() && Player.attacksOnTile(59, opponentMoves).isEmpty() && rookTile.getPiece().getPieceType() == Piece.PieceType.ROOK) {
                        kingCastles.add(new Move.CastleMove(this.board, this.playerKing, 58, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 59));    
                    }
                    
                }
            }
        }
        return kingCastles;
    }
}
