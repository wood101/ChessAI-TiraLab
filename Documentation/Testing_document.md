# Testing

The most important thing in a chess bot is the results and speed in which it achieves said results. This document will contain tests that compare the bots speed at different stages of the game and with different search depths of the minimax tree. 

This graph shows the time in milliseconds it took for the AI to calculate the next move after white moved from E2 to E4. Black always moved from E7 to E5.

<img src="https://raw.githubusercontent.com/wood101/ChessAITiraLab/blob/master/Documentation/Graphs/Pruning.png">

The game gets unplayably slow if the search depth is over 5 for a minimax algorithm with pruning and with a search depth over 4 without pruning.
