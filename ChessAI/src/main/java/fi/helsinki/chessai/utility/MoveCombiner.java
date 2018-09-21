/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.utility;

import fi.helsinki.chessai.board.pieces.Move;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class for a utility method that combines two lists of moves into one.
 * @author janne
 */
public class MoveCombiner {
    
    public static Collection<Move> combineMoves(Collection<Move> first, Collection<Move> second) {
        Stream<Move> allMoves = Stream.concat(first.stream(), second.stream());
        Collection<Move> moves = allMoves.collect(Collectors.toList());
        return moves;
    }
}
