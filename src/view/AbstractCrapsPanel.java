/*
 * TCSS 305 Assignment 5 - Craps
 * Autumn 2022
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


/**
 * The AbstractPanel class is used to simplify the code of all the
 * subcomponents inside the main window.
 *
 * @author Dillon Crookshank
 * @version 1.0
 */
public abstract class AbstractCrapsPanel extends JPanel {

    /** The universal font used throughout the entire GUI. */
    public static final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

    /**
     * Used for textFields that are initially empty.
     * This specifies a default/preferred width.
     */
    public static final int DEFAULT_COLUMNS = 6;

    /** The default padding put around every single component. */
    public static final Insets DEFAULT_INSETS = new Insets(5, 5, 5, 5);

    /** Used when placing components on the panel. */
    protected final GridBagConstraints myConstraints;

    /** This panels x-coordinate on a GridBagLayout Panel. */
    private final int myGridX;

    /** This panels y-coordinate on a GridBagLayout Panel. */
    private final int myGridY;

    /** This panels grid width on a GridBagLayout Panel. */
    private final int myGridW;

    /** This panels grid height on a GridBagLayout Panel. */
    private final int myGridH;

    /**
     * Creates a panel with a GridBagLayout that has a Titled Border.
     * @param theName The name to appear on the Border.
     */
    public AbstractCrapsPanel(
            final String theName,
            final int theGridX,
            final int theGridY,
            final int theGridW,
            final int theGridH) {

        super(new GridBagLayout());

        // Initialize a GridBagLayout.
        myConstraints = new GridBagConstraints();
        myConstraints.insets = DEFAULT_INSETS;

        // Create a border with the given name.
        final Border panelBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                theName,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                FONT);
        setBorder(panelBorder);

        myGridX = theGridX;
        myGridY = theGridY;
        myGridW = theGridW;
        myGridH = theGridH;
    }

    /**
     * Used in the Controller package, helps to quickly
     * add an action listener to components of the panel.
     */
    public abstract void attachListener(ActionListener theListener);

    /**
     * Adds 'this' panel to the given panel,
     * using 'this' panels predefined gridBagLayout fields.
     * The given panel must have a GridBagLayout.
     * @param thePanel The panel that will contain the instance of this Panel.
     */
    public final void addToPanel(final JPanel thePanel) {
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = myGridX;
        constraints.gridy = myGridY;
        constraints.gridwidth = myGridW;
        constraints.gridheight = myGridH;

        constraints.insets = DEFAULT_INSETS;

        thePanel.add(this, constraints);
    }

    /**
     * A helper method used to quickly make static JLabels.
     * @param theText The text to appear on the label.
     * @return A reference to the JLabel.
     */
    protected final JLabel createLabel(final String theText) {
        final JLabel label = new JLabel(theText);
        label.setFont(FONT);
        return label;
    }
}
