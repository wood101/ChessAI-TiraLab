/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board.pieces;

import fi.helsinki.chessai.board.Board;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author janne
 */
public abstract class Piece {
    protected final int position;
    protected final Side pieceSide;
    
    Piece(final int position, final Side pieceSide) {
        this.pieceSide = pieceSide;
        this.position = position;
    }
    
    public Side getPieceSide() {
        return this.pieceSide;
    }
    
    public abstract Collection<Move> legalMoves(final Board board);
}
