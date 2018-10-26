# Implementation

## Class diagram

<img src="https://raw.githubusercontent.com/wood101/ChessAITiraLab/master/Documentation/Graphs/class_diagram.png">

Most inner classes are not included, because it would make the diagram unreadably messy. Under Chess pieces class are all the pieces (rook, king, pawn etc.).

## Chess engine

The chess engine itself draws a interactable chess board, where you can move the pieces with the mouse. You can play against another player or against a bot. Bots can also play against each other.

## Bot

The bot uses the [Minimax algorith](https://en.wikipedia.org/wiki/Minimax) with [alpha-beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning) to perform a limited depth search of the best move. Pieces are evaluated by their type. The evaluation is also affected by the number of moves required to reach a chessmate, castling, number of moves possible moves in total. Evaluation also has a random number (between 0-9) attached to make bot vs. bot games more interesting.