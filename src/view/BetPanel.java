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
 * The BetPanel subcomponent that holds any betting related GUI components.
 *
 * @author Dillon Crookshank
 * @version 0.1
 */
public final class BetPanel extends AbstractCrapsPanel {

    /** The Listener Command for when the user sets the bet. */
    public static final String COMMAND_SET = "Set Bet";

    /** The Listener Command for when the user is going all in on a bet. */
    public static final String COMMAND_ALL_IN = "All In!";

    /** The title of the panel. */
    private static final String TITLE = "Bet";

    /** The BetPanels x-coordinate on a GridBagLayout Panel. */
    private static final int GRID_X = 2;

    /** The BetPanels y-coordinate on a GridBagLayout Panel. */
    private static final int GRID_Y = 0;

    /** The BetPanels grid width on a GridBagLayout Panel. */
    private static final int GRID_W = 1;

    /** The BetPanels grid height on a GridBagLayout Panel. */
    private static final int GRID_H = 1;

    /** The textField that lets you manually change your bet. */
    private final JTextField myBetTotal;

    /** The button that sets your bet. */
    private final JButton mySetButton;

    /** The button that sets your bet to the bank total. */
    private final JButton myAllInButton;

    /**
     * Creates an AbstractPanel with a TitledBorder with the name "Bet".
     */
    public BetPanel() {
        super(TITLE, GRID_X, GRID_Y, GRID_W, GRID_H);

        myBetTotal = new JTextField(DEFAULT_COLUMNS);
        myBetTotal.setActionCommand(COMMAND_SET);
        myBetTotal.setFont(FONT);

        mySetButton = new JButton(COMMAND_SET);
        mySetButton.setFont(FONT);

        myAllInButton = new JButton(COMMAND_ALL_IN);
        myAllInButton.setFont(FONT);

        layoutComponents();
    }

    /**
     * Adds the textField and the buttons to the BetPanel.
     * Is called in the parent class constructor.
     */
    private void layoutComponents() {
        final JPanel totalBetPanel = new JPanel();

        totalBetPanel.add(createLabel("$ "));
        totalBetPanel.add(myBetTotal);

        myConstraints.gridx = 0;
        myConstraints.gridy = 0;
        add(totalBetPanel, myConstraints);

        myConstraints.gridy = 1;
        add(mySetButton, myConstraints);

        myConstraints.gridy = 2;
        add(myAllInButton, myConstraints);
    }

    /**
     * Adds the listener from the controller package to the textField and buttons.
     * @param theListener The ActionListener from the controller package.
     */
    @Override
    public void attachListener(final ActionListener theListener) {
        myBetTotal.addActionListener(theListener);
        mySetButton.addActionListener(theListener);
        myAllInButton.addActionListener(theListener);
    }

    /**
     * Returns the String currently stored in the bet textField.
     * @return The string currently in the textField
     */
    public String getBetField() {
        if (myBetTotal.getText().equals("")) {
            return "-1";
        }
        return myBetTotal.getText();
    }

    /**
     * Sets the bet textField to the given String.
     * @param theTotal The new value of the textField
     */
    public void setBetField(final String theTotal) {
        myBetTotal.setText(theTotal);
    }

    /**
     * Resets the text field to be empty.
     */
    public void reset() {
        myBetTotal.setText("");
    }

    /**
     * Sets the editable field in the text field
     * and the enabled field in the button to the given boolean.
     * @param theIsEnabled true if this panels components
     *                     should be enabled/editable, false otherwise
     */
    public void setEnabled(final boolean theIsEnabled) {
        myBetTotal.setEditable(theIsEnabled);
        mySetButton.setEnabled(theIsEnabled);
        myAllInButton.setEnabled(theIsEnabled);
    }
}
