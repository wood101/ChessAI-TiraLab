package fi.helsinki.chessai.gui;

import fi.helsinki.chessai.board.Board;
import fi.helsinki.chessai.board.MoveTransition;
import fi.helsinki.chessai.board.Tile;
import fi.helsinki.chessai.board.Move;
import fi.helsinki.chessai.board.pieces.Piece;
import fi.helsinki.chessai.utility.BoardUtility;
import fi.helsinki.chessai.utility.MyList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 * This class creates a visible table to play on.
 * @author janne
 */
public class Table {
    private final JFrame frame;
    private final BoardPanel panel;
    private Board chessBoard;
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private final static Dimension FRAME = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL = new Dimension(400, 350);
    private final static Dimension TILE_PANEL = new Dimension(10, 10);
    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
    /**
     * Constructor
     */
    public Table() {
        this.frame = new JFrame("chess");
        this.frame.setLayout(new BorderLayout());
        final JMenuBar menu = createMenu();
        this.frame.setJMenuBar(menu);
        this.frame.setSize(FRAME);
        this.chessBoard = Board.createStandardBoard();
        this.panel = new BoardPanel();
        this.frame.add(panel, BorderLayout.CENTER);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Populates the menu bar with things.
     * @param menu 
     */
    private JMenuBar createMenu() {
        JMenuBar menu = new JMenuBar();
        menu.add(createFileMenu());
        return menu;
    }
    
    /**
     * Creates the file menu for the menu bar
     * @return 
     */
    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }
    
    /**
     * Class for the 8 x 8 grid of panels for chess.
     */
    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;
        
        BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
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
                        if(sourceTile == null || humanMovedPiece != chessBoard.getTile(tileId).getPiece() && chessBoard.getTile(tileId).occupied() && chessBoard.getTile(tileId).getPiece().getPieceSide() == humanMovedPiece.getPieceSide()) {
                            sourceTile = chessBoard.getTile(tileId);
                            humanMovedPiece = sourceTile.getPiece();
                            if(humanMovedPiece == null) {
                                sourceTile = null;
                            }
                        } else {
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if(transition.getMoveStatus().isDone()) {
                                chessBoard = (Board) transition.getBoard();
                            }
                        clearState();
                        }
                    SwingUtilities.invokeLater(() -> {
                        panel.drawBoard(chessBoard);
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
        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightLegalMoves(board);
            validate();
            repaint();
        }


    }
}
