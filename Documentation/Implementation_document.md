# Implementation

## [Chess rules](https://en.wikipedia.org/wiki/Rules_of_chess)

## Chess engine

The chess engine itself draws a interactable chess board, where you can move the pieces with the mouse. You can choose to play against another player by taking turns on the same computer or against a bot. This can be found on the top bar under options. You can also set the search depth of the algorithm. I recommend three when it is bot vs. bot and four for bot vs. humans. When a piece is selected the possible moves are highlighted.

## Bot

The bot uses the minimax algorith with alpha-beta pruning to perform a limited depth search of the best move. Pieces are evaluated by their type and position on the board. Some positional values will change when the game has progressed enough. The evaluation is also affected by the number of moves required to reach a chessmate, castling, number of moves possible moves in total.