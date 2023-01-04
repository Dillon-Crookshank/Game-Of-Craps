/*
 * TCSS 305 Assignment 5 - Craps
 * Autumn 2022
 */

package view;

import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The DicePanel subcomponent that holds any dice related GUI components.
 *
 * @author Dillon Crookshank
 * @version 0.1
 */
public final class DicePanel extends AbstractCrapsPanel {

    /** The title of the panel. */
    private static final String TITLE = "Dice";

    /** The DicePanels x-coordinate on a GridBagLayout Panel. */
    private static final int GRID_X = 0;

    /** The DicePanels y-coordinate on a GridBagLayout Panel. */
    private static final int GRID_Y = 0;

    /** The DicePanels grid width on a GridBagLayout Panel. */
    private static final int GRID_W = 2;

    /** The DicePanels grid height on a GridBagLayout Panel. */
    private static final int GRID_H = 1;

    /** The button that lets you roll the dice. */
    private final JButton myRollButton;

    /** Displays the value facing up on the first die. */
    private final JTextField myDice1Value;

    /** Displays the value facing up on the second die. */
    private final JTextField myDice2Value;

    /** The label that holds the icon for the first dice. */
    private final JLabel myDice1Display;

    /** The label that holds the icon for the second dice. */
    private final JLabel myDice2Display;

    /** A list that holds all the dice face icons for easy access. */
    private final List<ImageIcon> myDiceFaces;

    /** Displays the sum of the faces of the dice. */
    private final JTextField myDiceTotal;

    /**
     * Displays the value that the player must
     * roll before rolling a 7 to win.
     */
    private final JTextField myPointValue;

    /**
     * Creates an AbstractPanel with a TitledBorder with the name "Dice".
     */
    public DicePanel() {
        super(TITLE, GRID_X, GRID_Y, GRID_W, GRID_H);

        myRollButton = new JButton("Roll Dice");
        myRollButton.setFont(FONT);

        myDice1Value = new JTextField(1);
        myDice1Value.setFont(FONT);
        myDice1Value.setEditable(false);

        myDice2Value = new JTextField(1);
        myDice2Value.setFont(FONT);
        myDice2Value.setEditable(false);

        myDiceFaces = new LinkedList<>();

        //Fill the list with all the dice icons
        for (int n = 1; n <= 6; n++) {
            myDiceFaces.add(new ImageIcon(String.format("res\\Dice%d.png", n)));
        }

        myDice1Display = new JLabel(myDiceFaces.get(0));
        myDice2Display = new JLabel(myDiceFaces.get(0));

        myDiceTotal = new JTextField(DEFAULT_COLUMNS);
        myDiceTotal.setFont(FONT);
        myDiceTotal.setEditable(false);

        myPointValue = new JTextField(DEFAULT_COLUMNS);
        myPointValue.setFont(FONT);
        myPointValue.setEditable(false);

        layoutComponents();
    }

    /**
     * Adds the Button and the textFields to the DicePanel.
     * Is called in the parent class constructor.
     */
    private void layoutComponents() {
        myConstraints.gridx = 0;
        myConstraints.gridy = 0;
        add(myRollButton, myConstraints);

        final JPanel diceDisplay = new JPanel();
        diceDisplay.add(myDice1Display);
        diceDisplay.add(myDice2Display);

        myConstraints.gridy = 1;
        add(diceDisplay, myConstraints);

        final JPanel totalPanel = new JPanel();
        totalPanel.add(createLabel("Total: "));
        totalPanel.add(myDiceTotal);

        myConstraints.gridy = 2;
        add(totalPanel, myConstraints);

        final JPanel pointPanel = new JPanel();
        pointPanel.add(createLabel("Point: "));
        pointPanel.add(myPointValue);

        myConstraints.gridy = 3;
        add(pointPanel, myConstraints);
    }

    /**
     * Adds the listener from the controller package to the button.
     * @param theListener The ActionListener from the controller package.
     */
    @Override
    public void attachListener(final ActionListener theListener) {
        myRollButton.addActionListener(theListener);
    }

    /**
     * Sets the text in both the dice textFields to the given values.
     * The total textField is calculated automatically given the two dice values.
     * @param theDice1Value The value of the first Die.
     * @param theDice2Value the value of the second Die.
     */
    public void setDiceValues(final int theDice1Value, final int theDice2Value) {
        myDice1Display.setIcon(myDiceFaces.get(theDice1Value - 1));
        myDice2Display.setIcon(myDiceFaces.get(theDice2Value - 1));

        myDiceTotal.setText(String.valueOf(theDice1Value + theDice2Value));
    }

    /**
     * Sets the text in the point textField to the given value.
     * @param thePointValue The new point value to be displayed
     */
    public void setPointValue(final int thePointValue) {
        myPointValue.setText(String.valueOf(thePointValue));
    }

    /**
     * Resets the textFields in the DicePanel to be blank.
     */
    public void reset() {
        myDice1Display.setIcon(myDiceFaces.get(0));
        myDice2Display.setIcon(myDiceFaces.get(0));
        myDiceTotal.setText("");
        myPointValue.setText("");
    }

    /**
     * Sets the enabled field in the Button to the given boolean.
     * @param theIsEnabled True if this panels components
     *                     should be enabled, false otherwise
     */
    @Override
    public void setEnabled(final boolean theIsEnabled) {
        myRollButton.setEnabled(theIsEnabled);
    }
}
