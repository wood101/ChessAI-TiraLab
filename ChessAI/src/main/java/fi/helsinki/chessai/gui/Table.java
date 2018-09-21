package fi.helsinki.chessai.gui;

import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * This class creates a visible table to play on.
 * @author janne
 */
public class Table {
    private final JFrame frame;
    
    /**
     * Constructor
     */
    public Table() {
        this.frame = new JFrame("chess");
        final JMenuBar menu = new JMenuBar();
        populateMenu(menu);
        this.frame.setJMenuBar(menu);
        this.frame.setSize(600, 600);
        this.frame.setVisible(true);
    }
    
    /**
     * Populates the menubar with things.
     * @param menu 
     */
    private void populateMenu(JMenuBar menu) {
        menu.add(createFileMenu());
    }
    
    /**
     * Creates the file menu for the menu bar
     * @return 
     */
    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            System.out.println("open pgn");
            }
        });
        fileMenu.add(openPGN);
        return fileMenu;
    }
}
