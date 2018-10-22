package fi.helsinki.chessai.gui;

import fi.helsinki.chessai.Side;
import fi.helsinki.chessai.gui.Table.PlayerType;
import fi.helsinki.chessai.player.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class for the setup option.
 * @author janne
 */
class GameSetup extends JDialog {

    private PlayerType whitePlayerType;
    private PlayerType blackPlayerType;
    private final JSpinner searchDepthSpinner;
    private boolean vsAI;

    private static final String HUMAN_TEXT = "Human";
    private static final String COMPUTER_TEXT = "Computer";

    /**
     * Constructor that creates the visible buttons and text.
     * @param frame
     */
    GameSetup(final JFrame frame) {
        whitePlayerType = PlayerType.HUMAN;
        blackPlayerType = PlayerType.HUMAN;
        final JPanel myPanel = new JPanel(new GridLayout(0, 1));
        final JRadioButton whiteHumanButton = new JRadioButton(HUMAN_TEXT);
        final JRadioButton whiteComputerButton = new JRadioButton(COMPUTER_TEXT);
        final JRadioButton blackHumanButton = new JRadioButton(HUMAN_TEXT);
        final JRadioButton blackComputerButton = new JRadioButton(COMPUTER_TEXT);
        whiteHumanButton.setActionCommand(HUMAN_TEXT);
        
        final ButtonGroup whiteGroup = new ButtonGroup();
        whiteGroup.add(whiteHumanButton);
        whiteGroup.add(whiteComputerButton);
        whiteHumanButton.setSelected(true);

        final ButtonGroup blackGroup = new ButtonGroup();
        blackGroup.add(blackHumanButton);
        blackGroup.add(blackComputerButton);
        blackComputerButton.setSelected(true);

        getContentPane().add(myPanel);
        myPanel.add(new JLabel("White player"));
        myPanel.add(whiteHumanButton);
        myPanel.add(whiteComputerButton);
        myPanel.add(new JLabel("Black player"));
        myPanel.add(blackHumanButton);
        myPanel.add(blackComputerButton);

        this.searchDepthSpinner = addLabeledSpinner(myPanel, "Search Depth of AI", new SpinnerNumberModel(3, 1, 9, 1));

        final JButton cancelButton = new JButton("Cancel");
        final JButton okButton = new JButton("OK");

        okButton.addActionListener((ActionEvent e) -> {
            if(whiteComputerButton.isSelected()) {
                whitePlayerType = PlayerType.COMPUTER;
                Table.get().updateAfterMove(whitePlayerType);
            } else {
                whitePlayerType = PlayerType.HUMAN;
            }
            blackPlayerType = blackComputerButton.isSelected() ? PlayerType.COMPUTER : PlayerType.HUMAN;
            GameSetup.this.setVisible(false);
        });

        cancelButton.addActionListener((ActionEvent e) -> {
            GameSetup.this.setVisible(false);
        });

        myPanel.add(cancelButton);
        myPanel.add(okButton);

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
        
        vsAI = checkIfAIvsAI();
    }
    
    /**
     * Turns the setup bar visible.
     */
    void promptUser() {
        setVisible(true);
        repaint();
    }

    /**
     * True if player is an AI.
     * @param player
     * @return 
     */
    boolean isAIPlayer(final Player player) {
        if(player.getSide() == Side.WHITE) {
            return getWhitePlayerType() == PlayerType.COMPUTER;
        }
        return getBlackPlayerType() == PlayerType.COMPUTER;
    }

    PlayerType getWhitePlayerType() {
        return this.whitePlayerType;
    }

    PlayerType getBlackPlayerType() {
        return this.blackPlayerType;
    }

    /**
     * Creates the spinner where you can input a number.
     * @param c
     * @param label
     * @param model
     * @return 
     */
    private static JSpinner addLabeledSpinner(final Container c,
                                              final String label,
                                              final SpinnerModel model) {
        final JLabel l = new JLabel(label);
        c.add(l);
        final JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        c.add(spinner);
        return spinner;
    }
    /**
     * Search depth of the minimax algorithm.
     * @return 
     */
    public int getSearchDepth() {
        return (Integer)this.searchDepthSpinner.getValue();
    }
    
    public GameSetup getGameSetup() {
        return this;
    }

    public boolean vsAI() {
        return vsAI;
    }

    /**
     * Returns true if the computer is playing against a computer player.
     * @return 
     */
    private boolean checkIfAIvsAI() {
        return whitePlayerType == PlayerType.COMPUTER && blackPlayerType == PlayerType.COMPUTER;
    }
}