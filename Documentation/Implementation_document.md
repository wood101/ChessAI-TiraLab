# Implementation

## Chess engine

The chess engine itself draws a interactable chess board, where you can move the pieces with the mouse. You can choose to play against another player by taking turns on the same computer or against a bot. You can also pit two bots against each other. When a piece is selected the possible moves are highlighted.

## Bot

The bot uses the minimax algorith with alphabeta pruning to perform a limited depth search of the best move. Pieces are evaluated by their type and position on the board. Some positional values will change when the game has progressed enough.