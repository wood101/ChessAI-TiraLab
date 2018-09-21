package fi.helsinki.chessai.gui;

import fi.helsinki.chessai.board.Board;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * This class creates a visible table to play on.
 * @author janne
 */
public class Table {
    private final JFrame frame;
    private final BoardPanel panel;
    private final Board chessBoard;
    private final static Dimension FRAME = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL = new Dimension(400, 350);
    private final static Dimension TILE_PANEL = new Dimension(10, 10);
    private Color lightTileColor = Color.decode("#FFFACD");
    private Color darkTileColor = Color.decode("#593E1A");
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
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Populates the menubar with things.
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
    }
    
    private class TilePanel extends JPanel {
        private final int tileId;
        TilePanel(BoardPanel panel, int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            validate();
        }
        
        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if(board.getTile(this.tileId).occupied()) {
                try {
                    BufferedImage image = ImageIO.read(new File(getClass().getClassLoader().getResource("images/" + board.getTile(this.tileId).getPiece().getPieceSide().toString().substring(0, 1) +
                            board.getTile(tileId).getPiece().toString() + ".gif").getFile()));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        private void assignTileColor() {
            if(Math.ceil((this.tileId) / 8) == 1 || Math.ceil((this.tileId) / 8) == 3 || Math.ceil((this.tileId) / 8) == 5 || Math.ceil((this.tileId) / 8) == 7){
                setBackground(this.tileId % 2 == 0 ? darkTileColor : lightTileColor);
            } else {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            }
        }
    }
}
