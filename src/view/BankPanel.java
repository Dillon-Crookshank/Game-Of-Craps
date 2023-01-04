/*
 * TCSS 305 Assignment 5 - Craps
 * Autumn 2022
 */

package view;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The BankPanel subcomponent that holds any bank related GUI components.
 *
 * @author Dillon Crookshank
 * @version 0.1
 */
public final class BankPanel extends AbstractCrapsPanel {

    /** The title of the panel. */
    private static final String TITLE = "Bank";

    /** The BankPanels x-coordinate on a GridBagLayout Panel. */
    private static final int GRID_X = 2;

    /** The BankPanels y-coordinate on a GridBagLayout Panel. */
    private static final int GRID_Y = 1;

    /** The BankPanels grid width on a GridBagLayout Panel. */
    private static final int GRID_W = 1;

    /** The BankPanels grid height on a GridBagLayout Panel. */
    private static final int GRID_H = 1;

    /** The textField that lets you change the total amount in the bank. */
    private final JTextField myBankTotal;

    /**
     * The button that sets the total amount in
     * the bank to what's currently in the textField.
     */
    private final JButton mySetButton;

    /**
     * Creates an AbstractPanel with a TitledBorder with the name "Bank".
     */
    public BankPanel() {
        super(TITLE, GRID_X, GRID_Y, GRID_W, GRID_H);

        myBankTotal = new JTextField(DEFAULT_COLUMNS);
        myBankTotal.setFont(FONT);

        mySetButton = new JButton("Set Bank");
        mySetButton.setFont(FONT);

        layoutComponents();
    }

    /**
     * Places the textField and button components on the BankPanel.
     * Is called in the parent class constructor.
     */
    private void layoutComponents() {
        final JPanel bankTotalPanel = new JPanel();
        bankTotalPanel.add(createLabel("$ "));
        bankTotalPanel.add(myBankTotal);

        myConstraints.gridx = 0;
        myConstraints.gridy = 0;
        myConstraints.ipadx = 10;
        myConstraints.ipady = 10;
        add(bankTotalPanel, myConstraints);

        mySetButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        myConstraints.gridx = 0;
        myConstraints.gridy = 1;
        myConstraints.ipadx = 0;
        myConstraints.ipady = 0;
        add(mySetButton, myConstraints);
    }

    /**
     * Adds the listener from the controller to the textField and button components.
     * @param theListener The ActionListener from the controller package.
     */
    @Override
    public void attachListener(final ActionListener theListener) {
        mySetButton.addActionListener(theListener);
        myBankTotal.addActionListener(theListener);
    }

    /**
     * Returns the String currently stored in the bank textField.
     * @return The string currently in the textField
     */
    public String getBankField() {
        if (myBankTotal.getText().equals("")) {
            return "0";
        }
        return myBankTotal.getText();
    }

    /**
     * Sets the textField to the given String.
     * @param theTotal The new value of the textField
     */
    public void setBankField(final String theTotal) {
        myBankTotal.setText(theTotal);
    }

    /**
     * Resets the Bank text field to be empty.
     */
    public void reset() {
        myBankTotal.setText("");
    }

    /**
     * Sets the editable field in the text field and
     * the enabled field in the button to the given boolean.
     * @param theIsEnabled true if this panels components should
     *                     be enabled/editable, false otherwise.
     */
    public void setEnabled(final boolean theIsEnabled) {
        myBankTotal.setEditable(theIsEnabled);
        mySetButton.setEnabled(theIsEnabled);
    }
}
