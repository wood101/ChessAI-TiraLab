package fi.helsinki.chessai.gui;

import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.MoveTransition;
import fi.helsinki.chessai.board.Tile;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.board.pieces.Piece;
import fi.helsinki.chessai.player.ai.MiniMax;
import fi.helsinki.chessai.player.ai.MoveStrategy;
import fi.helsinki.chessai.utility.BoardUtility;
import fi.helsinki.chessai.utility.MyList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;


/**
 * This class creates a visible table to play on.
 * @author janne
 */
public class Table extends Observable {
    private final JFrame frame;
    private BoardPanel panel;
    private Board chessBoard;
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private GameSetup gameSetup;
    private final static Dimension FRAME = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL = new Dimension(400, 350);
    private final static Dimension TILE_PANEL = new Dimension(10, 10);
    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
    private static final Table INSTANCE = new Table();
    /**
     * Constructor
     */
    private Table() {
        this.frame = new JFrame("Chess");
        this.frame.setLayout(new BorderLayout());
        this.frame.setJMenuBar(createMenu());
        this.frame.setSize(FRAME);
        this.addObserver(new TableGameAIWatcher());
        this.chessBoard = Board.createStandardBoard();
        this.gameSetup = new GameSetup(this.frame).getGameSetup();
        this.panel = new BoardPanel();
        this.frame.add(panel, BorderLayout.CENTER);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static Table get() {
        return INSTANCE;
    }
    
    /**
     * Initializes the chess board for a new game.
     */
    private void initializeGame() {
        this.chessBoard = Board.createStandardBoard();
        this.gameSetup = new GameSetup(this.frame).getGameSetup();
        this.panel = new BoardPanel();
        this.frame.add(panel, BorderLayout.CENTER);
    }
    
    /**
     * Populates the menu bar with things.
     * @param menu 
     */
    private JMenuBar createMenu() {
        JMenuBar menu = new JMenuBar();
        menu.add(createFileMenu());
        menu.add(createOptionsMenu());
        return menu;
    }
    
    /**
     * Creates the file menu for the menu bar
     * @return 
     */
    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        fileMenu.add(exitMenuItem);
        final JMenuItem restartMenuItem = new JMenuItem("Restart");
        restartMenuItem.addActionListener((ActionEvent e) -> {
            initializeGame();
        });
        fileMenu.add(restartMenuItem);
        return fileMenu;
    }
    
    /**
     * Creates an options menu.
     * @return 
     */
    private JMenu createOptionsMenu() {
        final JMenu optionsMenu = new JMenu("Options");
        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");
        setupGameMenuItem.addActionListener((ActionEvent e) -> {
            Table.get().getGameSetup().promptUser();
            Table.get().setUpUpdate(Table.get().getGameSetup());
        });
        optionsMenu.add(setupGameMenuItem);
        return optionsMenu;
    }
    
    /**
     * Gives a popup message window if the game has ended.
     */
    private void checkGameEnd() {
        if(Table.get().getGameBoard().currentPlayer().isInCheckMate()) {
            JOptionPane.showMessageDialog(Table.get().getBoardPanel(), "Game Over: " +
            Table.get().getGameBoard().currentPlayer() + " is in checkmate!",
            "Game Over", JOptionPane.INFORMATION_MESSAGE);
            initializeGame();
        }
        if(Table.get().getGameBoard().currentPlayer().isInStaleMate()) {
            JOptionPane.showMessageDialog(Table.get().getBoardPanel(), "Game Over: the game ended in a stalemate!",
            "Game Over", JOptionPane.INFORMATION_MESSAGE);
            initializeGame();
        }
    }

    private GameSetup getGameSetup() {
        return this.gameSetup;
    }
    
    private Board getGameBoard() {
        return this.chessBoard;
    }
    
    /**
     * Sets the observers.
     * @param gameSetup 
     */
    private void setUpUpdate(GameSetup gameSetup) {
        setChanged();
        notifyObservers(gameSetup);
    }
    
    /**
     * Updates the board to a new board.
     * @param board 
     */
    private void updateGameBoard(Board board) {
        this.chessBoard = board;
    }

    private BoardPanel getBoardPanel() {
        return this.panel;
    }
    
    /**
     * Notifies the computer of a move that happened.
     * @param playerType 
     */
    public void updateAfterMove(PlayerType playerType) {
        setChanged();
        notifyObservers(playerType);
    }
    
    /**
     * Makes the table class visible.
     */
    public void show() {
        Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
    }


    /**
     * Class for the AI reacting to the game.
     */
    private static class TableGameAIWatcher implements Observer {
        /**
         * Activates the AI when its turn comes.
         * @param o
         * @param o1 
         */
        @Override
        public void update(Observable o, Object o1) {
            if(Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer()) && !Table.get().getGameBoard().currentPlayer().isInCheckMate() && !Table.get().getGameBoard().currentPlayer().isInStaleMate()) {
                final AIThinkTank thinkTank = new AIThinkTank();
                thinkTank.execute();
            }
            Table.get().checkGameEnd();
        }
        
    }
    /**
     * Class that activates the algorithm of the AI after rendering player movement.
     */
    private static class AIThinkTank extends SwingWorker<Move, String> {
        public AIThinkTank() {
        }
        /**
         * Returns the result of the work that the algorithm does.
         * @return
         * @throws Exception 
         */
        @Override
        protected Move doInBackground() throws Exception {
            final MoveStrategy miniMax = new MiniMax(Table.get().getGameSetup().getSearchDepth());
            final Move bestMove = miniMax.execute(Table.get().getGameBoard());
            return bestMove;
        }
        
        /**
         * Uses the best move to update the game state.
         */
        @Override
        public void done() {
            try {
                final Move bestMove = get();
                Table.get().updateGameBoard(Table.get().getGameBoard().currentPlayer().makeMove(bestMove).getTransitionBoard());
                Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
                Table.get().updateAfterMove(PlayerType.COMPUTER);
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Class for the 8 x 8 grid of panels for chess.
     */
    private class BoardPanel extends JPanel {
        final MyList<TilePanel> boardTiles;
        
        BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new MyList<>();
            for(int i = 0; i < 64; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL);
            validate();
        }
        
        /**
         * Draws a visible board.
         * @param board 
         */
        private void drawBoard(Board board) {
            removeAll();
            for(final TilePanel tilePanel : boardTiles) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }
    
    /**
     * Enum for the different types of players.
     */
    enum PlayerType {
        HUMAN,
        COMPUTER
    }
    
    /**
     * Class for a single panel of the grid.
     */
    private class TilePanel extends JPanel {
        private final int tileId;
        TilePanel(BoardPanel panel, int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            
            addMouseListener(new MouseListener() {
                /**
                 * Clicking a piece and a valid move causes the piece to move to a new position.
                 * @param me 
                 */
                @Override
                public void mouseClicked(final MouseEvent me) {
                    if(isRightMouseButton(me)) {
                        clearState();
                    } else if(isLeftMouseButton(me))
                        if(sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileId);
                            humanMovedPiece = sourceTile.getPiece();
                            if(humanMovedPiece == null) {
                                sourceTile = null;
                            }
                        } else if (!humanMovedPiece.getPieceSide().equals(chessBoard.currentPlayer().getSide())){
                            clearState();
                        } else {
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            MoveTransition transition;
                            if(!move.equals(Move.NULL_MOVE)) {
                                transition = chessBoard.currentPlayer().makeMove(move);
                                if(transition.getMoveStatus().isDone()) {
                                    chessBoard = (Board) transition.getBoard();
                                }
                            }
                            clearState();
                        }
                    SwingUtilities.invokeLater(() -> {
                        panel.drawBoard(chessBoard);
                        Table.get().updateAfterMove(PlayerType.HUMAN);
                    });
                }

                @Override
                public void mousePressed(final MouseEvent me) {
                }

                @Override
                public void mouseReleased(final MouseEvent me) {
                }
                
                /**
                 * Gives borders on tiles with pieces on when hovered over with the mouse.
                 * @param me 
                 */
                @Override
                public void mouseEntered(final MouseEvent me) {
                    if(chessBoard.getTile(tileId).occupied()) {
                        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
                        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                        setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
                        panel.drawBoard(chessBoard);
                    }
                }
                
                /**
                 * Removes the border when not hovering over a piece.
                 * @param me 
                 */
                @Override
                public void mouseExited(final MouseEvent me) {
                    setBorder(null);
                    panel.drawBoard(chessBoard);
                }
                
                /**
                 * Checks if the user is clicking the right mouse button.
                 * @param me
                 * @return 
                 */
                private boolean isRightMouseButton(MouseEvent me) {
                    return me.getButton() == MouseEvent.BUTTON3;
                }
                
                /**
                 * Checks if the user is clicking the left mouse button.
                 * @param me
                 * @return 
                 */
                private boolean isLeftMouseButton(MouseEvent me) {
                    return me.getButton() == MouseEvent.BUTTON1;
                }
            });
            validate();
        }
        
        /**
         * Clears all mouse selections.
         */
        private void clearState() {
            sourceTile = null;
            destinationTile = null;
            humanMovedPiece = null;
        }
        
        /**
         * Gives every tile with a piece on it the correct icon.
         * @param board 
         */
        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if(board.getTile(this.tileId).occupied()) {
                try {
                    BufferedImage image = ImageIO.read(new File(getClass().getClassLoader().getResource("images/pieces/" + board.getTile(this.tileId).getPiece().getPieceSide().toString().substring(0, 1) +
                            board.getTile(tileId).getPiece().toString() + ".gif").getFile()));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        /**
         * Highlights the moves of a selected piece.
         * @param board 
         */
        private void highlightLegalMoves(final Board board) {
            for(final Move move : pieceLegalMoves(board)) {
                if(move.getDestination() == this.tileId) {
                    setBackground(Color.decode("#F5DA81"));
                }
            }
        }
        
        /**
         * Returns the legal moves of a piece for highlighting purposes.
         * @param board
         * @return 
         */
        private MyList<Move> pieceLegalMoves(Board board) {
            if(humanMovedPiece != null && humanMovedPiece.getPieceSide() == board.currentPlayer().getSide()) {
                return humanMovedPiece.getLegalMoves(board);
            }
            return new MyList<>();
        }     
        
        /**
         * Assigns tiles the right color to create a checkered board.
         */
        private void assignTileColor() {
            int rowNumber = BoardUtility.getRow(this.tileId - 1);
            if(rowNumber == 1 || rowNumber == 3 || rowNumber == 5 || rowNumber == 7){
                setBackground(this.tileId % 2 == 0 ? darkTileColor : lightTileColor);
            } else {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            }
        }

        /**
         * Draws a single tile.
         * @param board 
         */
        private void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightLegalMoves(board);
            validate();
            repaint();
        }


    }
}
