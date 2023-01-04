/*
 * TCSS 305 Assignment 5 - Craps
 * Autumn 2022
 */

package view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


/**
 * The Menu to the main Craps window.
 *
 * @author Dillon Crookshank
 * @version 0.1
 */
public class CrapsMenu extends JMenuBar {

    /** The universal font used throughout the entire GUI. */
    public static final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

    /** The command attached to the Start option. */
    public static final String START_COMMAND = "Start";

    /** The command attached to the Reset option. */
    public static final String RESET_COMMAND = "Reset";

    /** The command attached to the Exit option. */
    public static final String EXIT_COMMAND = "Exit";

    /** The command attached to the About option. */
    public static final String ABOUT_COMMAND = "About";

    /** The command attached to the Rules option. */
    public static final String RULES_COMMAND = "Rules";

    /** The name of the Game menu. */
    public static final String GAME_MENU_NAME = "Game";

    /** the name of the Help menu. */
    public static final String HELP_MENU_NAME = "Help";

    /** The Start MenuItem. */
    private final JMenuItem myStartOption;

    /** The Reset MenuItem. */
    private final JMenuItem myResetOption;

    /** The Exit MenuItem. */
    private final JMenuItem myExitOption;

    /** The About MenuItem. */
    private final JMenuItem myAboutOption;

    /** The Rules MenuItem. */
    private final JMenuItem myRulesOption;

    /**
     * The lone constructor to the CrapsMenu.
     */
    public CrapsMenu() {
        super();

        final JMenu gameMenu = new JMenu(GAME_MENU_NAME);
        gameMenu.setFont(FONT);

        final JMenu helpMenu = new JMenu(HELP_MENU_NAME);
        helpMenu.setFont(FONT);

        myStartOption = new JMenuItem(START_COMMAND);
        myStartOption.setFont(FONT);
        myStartOption.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        myResetOption = new JMenuItem(RESET_COMMAND);
        myResetOption.setFont(FONT);
        myResetOption.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));

        myExitOption = new JMenuItem(EXIT_COMMAND);
        myExitOption.setFont(FONT);
        myExitOption.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));

        myAboutOption = new JMenuItem(ABOUT_COMMAND);
        myAboutOption.setFont(FONT);
        myAboutOption.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        myRulesOption = new JMenuItem(RULES_COMMAND);
        myRulesOption.setFont(FONT);
        myRulesOption.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));


        add(gameMenu);
        add(helpMenu);

        gameMenu.add(myStartOption);
        gameMenu.add(myResetOption);
        gameMenu.add(myExitOption);

        helpMenu.add(myAboutOption);
        helpMenu.add(myRulesOption);
    }

    /**
     * Sets the enabled field of the Start MenuItem to the given boolean.
     * @param theIsEnabled True if the menuItem should be enabled, false otherwise.
     */
    public void setStartEnabled(final boolean theIsEnabled) {
        myStartOption.setEnabled(theIsEnabled);
    }

    /**
     * Sets the enabled field of the Start MenuItem to the given boolean.
     * @param theIsEnabled True if the menuItem should be enabled, false otherwise.
     */
    public void setResetEnabled(final boolean theIsEnabled) {
        myResetOption.setEnabled(theIsEnabled);
    }

    /**
     * Add a listener to all the MenuItems of the Menu.
     * @param theListener The listener from the controller package.
     */
    public void attachListener(final ActionListener theListener) {
        myStartOption.addActionListener(theListener);
        myResetOption.addActionListener(theListener);
        myExitOption.addActionListener(theListener);
        myAboutOption.addActionListener(theListener);
        myRulesOption.addActionListener(theListener);
    }
}
