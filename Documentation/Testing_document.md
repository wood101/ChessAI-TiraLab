# Testing

## Test coverage

Jacoco test coverage:

<img src="https://raw.githubusercontent.com/wood101/ChessAITiraLab/master/Documentation/Graphs/jacoco.png">

## Speed testing

This graph shows the time in milliseconds it took for the AI to calculate the next move after white moved from E2 to E4. Black always moved from E7 to E5.

<img src="https://raw.githubusercontent.com/wood101/ChessAITiraLab/master/Documentation/Graphs/Pruning.png">

The game gets unplayably slow if the search depth is over 5 for a minimax algorithm with pruning and with a search depth over 4 without pruning.
