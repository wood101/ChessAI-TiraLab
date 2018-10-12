# Design document

Chess AI is supposed to be a playable chess game, where you can play against an artificial intelligence and pit two AIs against each other. Programming language used is java.

## Algorithm design

The program will use the [Minimax](https://en.wikipedia.org/wiki/Minimax) algorithm for implementing the AI. It simply put chooses makes the AI choose the move that the opponent has the worst reply to. [Alpha-beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning) will be used to speed up the AI.

## Input / Output

The chess pieces are moved with the mouse by clicking the piece and a legal spot to move to. The AI simply moves by itself in response on its turn.

## Time complexity

According to [this](https://cis.temple.edu/~vasilis/Courses/CIS603/Lectures/l7.html),
the Minimax algorithm has a time complexity of O(b^m), where b is the number of possible moves and m is the maximum depth of the tree that the algorithm travels in finding the solution.

With alpha-beta pruning the average time complexity is cut down to Ω(b^(m/2). Worst case stays the same.
