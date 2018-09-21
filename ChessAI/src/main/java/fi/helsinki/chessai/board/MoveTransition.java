/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board;

import fi.helsinki.chessai.board.pieces.Move;

/**
 * Class for the active movement happening on the board.
 * @author janne
 */
public class MoveTransition {
    private final Board board;
    private final Move move;
    private final MoveStatus moveStatus;
    
    public MoveTransition(final Board board, final Move move, final MoveStatus moveStatus) {
        this.board = board;
        this.move = move;
        this.moveStatus = moveStatus;
    }
    
    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
