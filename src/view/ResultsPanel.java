/*
 * TCSS 305 Assignment 5 - Craps
 * Autumn 2022
 */

package view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The ResultsPanel subcomponent that holds any game results related GUI components.
 *
 * @author Dillon Crookshank
 * @version 0.1
 */
public final class ResultsPanel extends AbstractCrapsPanel {

    /** The title of the panel. */
    private static final String TITLE = "Results";

    /** The WinTotalPanels x-coordinate on a GridBagLayout Panel. */
    private static final int GRID_X = 0;

    /** The WinTotalPanels y-coordinate on a GridBagLayout Panel. */
    private static final int GRID_Y = 1;

    /** The WinTotalPanels grid width on a GridBagLayout Panel. */
    private static final int GRID_W = 2;

    /** The WinTotalPanels grid height on a GridBagLayout Panel. */
    private static final int GRID_H = 1;

    /** Displays who won at the end of a round. */
    private final JTextField myGameResults;

    /** The button that starts another round of Craps. */
    private final JButton myPlayAgainButton;

    /** Displays the total number of wins the player has. */
    private final JTextField myPlayerWinTotal;

    /** Displays the total number of wins the house has. */
    private final JTextField myHouseWinTotal;

    /**
     * Creates an AbstractPanel with a TitledBorder with the name "Win Totals".
     */
    public ResultsPanel() {
        super(TITLE, GRID_X, GRID_Y, GRID_W, GRID_H);

        myGameResults = new JTextField("Should be Invisible");
        myGameResults.setFont(FONT);
        myGameResults.setEditable(false);

        myPlayAgainButton = new JButton("Play Again");
        myPlayAgainButton.setFont(FONT);

        myPlayerWinTotal = new JTextField(DEFAULT_COLUMNS);
        myPlayerWinTotal.setFont(FONT);
        myPlayerWinTotal.setEditable(false);

        myHouseWinTotal = new JTextField(DEFAULT_COLUMNS);
        myHouseWinTotal.setFont(FONT);
        myHouseWinTotal.setEditable(false);

        reset();

        layoutComponents();
    }

    /**
     * Adds the Button and the textFields to the WinTotalPanel.
     * Is called in the parent class constructor.
     */
    protected void layoutComponents() {
        myConstraints.gridx = 0;
        myConstraints.gridy = 0;
        add(myGameResults, myConstraints);

        myConstraints.gridx = 0;
        myConstraints.gridy = 1;
        add(myPlayAgainButton, myConstraints);

        final JPanel playerWinTotalPanel = new JPanel();
        playerWinTotalPanel.add(createLabel("Player Win Total: "));
        playerWinTotalPanel.add(myPlayerWinTotal);

        myConstraints.gridx = 1;
        myConstraints.gridy = 0;
        add(playerWinTotalPanel, myConstraints);

        final JPanel houseWinTotalPanel = new JPanel();
        houseWinTotalPanel.add(createLabel("House Win Total: "));
        houseWinTotalPanel.add(myHouseWinTotal);

        myConstraints.gridx = 1;
        myConstraints.gridy = 1;
        add(houseWinTotalPanel, myConstraints);
    }

    /**
     * Adds the listener from the controller package to the button.
     * @param theListener The ActionListener from the controller package.
     */
    @Override
    public void attachListener(final ActionListener theListener) {
        myPlayAgainButton.addActionListener(theListener);
    }

    /**
     * Increments the value stored in the Player Win Total textField by 1.
     */
    public void incrementPlayerWinTotal() {
        final int total = Integer.parseInt(myPlayerWinTotal.getText());

        myPlayerWinTotal.setText(String.valueOf(total + 1));
    }

    /**
     * Increments the value stored in the House Win Total textField by 1.
     */
    public void incrementHouseWinTotal() {
        final int total = Integer.parseInt(myHouseWinTotal.getText());

        myHouseWinTotal.setText(String.valueOf(total + 1));
    }

    /**
     * Given the name of the Player who won,
     * this updates the result label accordingly.
     * This does not enable a disabled field.
     * @param theWinner The name of the winner of the round.
     */
    public void setWinner(final String theWinner) {
        myGameResults.setText(theWinner + " Wins!");
    }

    /**
     * Sets the text in both Win Total textFields to "0".
     */
    public void reset() {
        myPlayerWinTotal.setText("0");
        myHouseWinTotal.setText("0");
    }

    /**
     * Given a boolean value, this method enables/disables the play Again button and
     * the round results text field.
     * @param theIsEnabled true if this component should be enabled, false otherwise
     */
    @Override
    public void setEnabled(final boolean theIsEnabled) {
        myPlayAgainButton.setEnabled(theIsEnabled);
        myGameResults.setVisible(theIsEnabled);
    }

}
