/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.board;

/**
 * Class for the transitioning of an old board to the new board because of piece movement.
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

    public Object getBoard() {
        return this.board;
    }
}
