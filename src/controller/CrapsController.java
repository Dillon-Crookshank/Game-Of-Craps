/*
 * TCSS 305 Assignment 5 - Craps
 * Autumn 2022
 */

package controller;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigInteger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.CrapsModel;
import view.BankPanel;
import view.BetPanel;
import view.CrapsMenu;
import view.DicePanel;
import view.ResultsPanel;


/**
 * The controller to the Craps project. Initializes view and model instances and handles
 * the transfer of data between them.
 *
 * @author Dillon Crookshank
 * @version 1.0
 */
public final class CrapsController {

    /** The title of the window. */
    private static final String TITLE = "Game of Craps";

    /** The pixel padding added to the minimum window size. */
    private static final int PADDING = 50;

    /** The instance of the model. */
    private final CrapsModel myCrapsModel;

    /** The main window that holds the game. */
    private final JFrame myWindow;

    /** The menu bar. */
    private final CrapsMenu myMenu;

    /** The panel that displays the dice and lets the player roll the dice. */
    private final DicePanel myDicePanel;

    /** The panel that displays the number of wins the player and the house has. */
    private final ResultsPanel myResultsPanel;

    /** The panel where you can set a Total Bank amount. */
    private final BankPanel myBankPanel;

    /** The panel where you can set a Total Bet amount. */
    private final BetPanel myBetPanel;

    /**
     * The constructor for the CrapsController. Opens a new "Game Of Craps" window.
     */
    public CrapsController() {
        myCrapsModel = new CrapsModel();

        //Initialize the components on the main panel.
        myMenu = new CrapsMenu();

        final JPanel mainPanel = new JPanel(new GridBagLayout());
        myMenu.setStartEnabled(false);
        myMenu.setResetEnabled(false);

        myDicePanel = new DicePanel();
        myDicePanel.addToPanel(mainPanel);
        myDicePanel.setEnabled(false);

        myBankPanel = new BankPanel();
        myBankPanel.addToPanel(mainPanel);

        myBetPanel = new BetPanel();
        myBetPanel.addToPanel(mainPanel);
        myBetPanel.setEnabled(false);

        myResultsPanel = new ResultsPanel();
        myResultsPanel.addToPanel(mainPanel);
        myResultsPanel.setEnabled(false);

        //Initialize the window
        myWindow = new JFrame(TITLE);

        addListeners();

        EventQueue.invokeLater(() -> {
            //Add Swing components to window
            myWindow.setContentPane(mainPanel);
            myWindow.setJMenuBar(myMenu);

            myWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            //Set the minimum size to the packed window + padding
            myWindow.pack();
            myWindow.setMinimumSize(new Dimension(
                    myWindow.getWidth() + PADDING,
                    myWindow.getHeight() + PADDING));

            //do a bit of math so that the window opens up in the middle of the screen.
            final Dimension position = Toolkit.getDefaultToolkit().getScreenSize();
            position.setSize((int) (position.getWidth() - myWindow.getWidth()) / 2,
                    (int) (position.getHeight() - myWindow.getHeight()) / 2);
            myWindow.setLocation((int) position.getWidth(), (int) position.getHeight());

            //Show window
            myWindow.setVisible(true);
        });
    }

    /**
     * The main method, creates an instance of the CrapsController.
     * @param theArgs Specifications sent from the system.
     */
    public static void main(final String[] theArgs) {
        new CrapsController();
    }


    /**
     * Initializes all listeners.
     */
    private void addListeners() {
        addDicePanelListeners();
        addBankPanelListeners();
        addBetPanelListeners();
        addResultsPanelListeners();
        addMenuBarListeners();
        addModelPropertiesListeners();

        myWindow.addWindowListener(new WindowExitListener());
    }

    /**
     * Initializes listeners on the Dice Panel.
     */
    private void addDicePanelListeners() {
        myDicePanel.attachListener(theEvent -> {
            //Only possible ActionEvent on the Dice Panel is the Roll Dice button.

            myCrapsModel.doRoundStep();

            //Check if the round has ended
            if (myCrapsModel.isRoundOver()) {
                //Send the winner to the results panel to be displayed
                myResultsPanel.setWinner(myCrapsModel.getWinner());

                myResultsPanel.setEnabled(true);
                myDicePanel.setEnabled(false);

                //Update the win totals accordingly
                if (myCrapsModel.getWinner().equals(CrapsModel.PLAYER)) {
                    myResultsPanel.incrementPlayerWinTotal();
                } else {
                    myResultsPanel.incrementHouseWinTotal();
                }

                //Revalidate window so JLabel will show
                myWindow.revalidate();
            }
        });
    }

    /**
     * Initializes listeners on the Bank Panel.
     */
    private void addBankPanelListeners() {
        myBankPanel.attachListener(theEvent -> {
            //Only possible ActionEvent on the Bank Panel is setting the Bank total.

            //Check if the text in the field is numerical.
            if (isNumerical(myBankPanel.getBankField().trim())) {
                //Parse String into a BigInteger
                final BigInteger bank = new BigInteger(myBankPanel.getBankField());

                //Send the bank total to the model.
                myCrapsModel.setBankTotal(bank);

                myBankPanel.setEnabled(false);
                myBetPanel.setEnabled(true);

                myMenu.setResetEnabled(true);
            } else {
                myBankPanel.reset();
            }
        });
    }

    /**
     * Initializes listeners on the Bet Panel.
     */
    private void addBetPanelListeners() {
        myBetPanel.attachListener(theEvent -> {
            switch (theEvent.getActionCommand()) {
                case BetPanel.COMMAND_SET -> {

                    //Validate the text in the field before sending it to the model.
                    if (isNumerical(myBetPanel.getBetField().trim())) {
                        //Parse String into a BigInteger
                        BigInteger bet = new BigInteger(myBetPanel.getBetField());

                        bet = bet.max(BigInteger.ZERO);
                        bet = bet.min(myCrapsModel.getBank());

                        myCrapsModel.setBet(bet);
                        myMenu.setStartEnabled(true);

                    } else {
                        myBetPanel.reset();
                    }
                }
                case BetPanel.COMMAND_ALL_IN -> {
                    //send the current bank total back to the model to be the bet.
                    myCrapsModel.setBet(myCrapsModel.getBank());
                    myMenu.setStartEnabled(true);
                }

                default -> {
                    //Error
                }
            }
        });
    }

    /**
     * Initializes listeners on the Results Panel.
     */
    private void addResultsPanelListeners() {
        myResultsPanel.attachListener(theEvent -> {
            //Only possible ActionEvent on the Results Panel is the Play Again Button.

            //Get ready for a new round.
            myDicePanel.reset();
            myBetPanel.reset();

            //Soft reset the fields in the model to prepare for another round.
            myCrapsModel.softReset();

            myResultsPanel.setEnabled(false);
            myBetPanel.setEnabled(true);
        });
    }

    /**
     * Initializes listeners on the Menu Bar.
     */
    private void addMenuBarListeners() {
        myMenu.attachListener(theEvent -> {
            switch (theEvent.getActionCommand()) {
                case CrapsMenu.START_COMMAND -> {
                    //Begin the round.
                    myBetPanel.setEnabled(false);
                    myDicePanel.setEnabled(true);

                    myMenu.setStartEnabled(false);

                }
                case CrapsMenu.RESET_COMMAND -> {
                    //Reset the view
                    myDicePanel.reset();
                    myDicePanel.setEnabled(false);
                    myBetPanel.reset();
                    myBetPanel.setEnabled(false);
                    myBankPanel.reset();
                    myBankPanel.setEnabled(true);
                    myResultsPanel.reset();
                    myResultsPanel.setEnabled(false);

                    myMenu.setStartEnabled(false);
                    myMenu.setResetEnabled(false);

                    myCrapsModel.softReset();
                }
                case CrapsMenu.EXIT_COMMAND -> exitConfirmation();

                case CrapsMenu.ABOUT_COMMAND -> {
                    //initiate about window
                    final String message = """
                            Author: Dillon Crookshank
                            Application Version: 1.0
                            JDK Version: 19""";

                    JOptionPane.showMessageDialog(
                            myWindow,
                            message,
                            "About",
                            JOptionPane.INFORMATION_MESSAGE);

                }
                case CrapsMenu.RULES_COMMAND -> {
                    //initiate rules window
                    final String message = """
                            Once you set a Bank total and a Bet, you must start the 
                            game from the Game menu to begin the round.
                            
                            On the first roll:
                                If the dice sum is 7 or 11, the player wins.
                                If the dice sum is 2, 3 or 12 the house wins.
                                If the dice sum is anything else, that sum becomes the Point.
                                    Continue rolling given the Point.
                                    Now the player must roll the Point total to win.
                                    If a 7 is rolled before the Point, the house wins.""";

                    JOptionPane.showMessageDialog(
                            myWindow,
                            message,
                            "Rules",
                            JOptionPane.INFORMATION_MESSAGE);

                }

                default -> {
                    //Error
                }
            }
        });
    }

    /**
     * Initializes model listeners.
     */
    private void addModelPropertiesListeners() {
        myCrapsModel.addPropertyChangeListener(theEvent -> {
            //Whenever fields are changed in the model, update the view accordingly.
            switch (theEvent.getPropertyName()) {
                case CrapsModel.PROPERTY_DICE ->
                        myDicePanel.setDiceValues(myCrapsModel.getDice1(),
                                myCrapsModel.getDice2());

                case CrapsModel.PROPERTY_POINT ->
                        myDicePanel.setPointValue(myCrapsModel.getPoint());

                case CrapsModel.PROPERTY_BANK ->
                        myBankPanel.setBankField(myCrapsModel.getBank().toString());

                case CrapsModel.PROPERTY_BET -> {
                    myBetPanel.setBetField(myCrapsModel.getBet().toString());
                    myBankPanel.setBankField(
                            myCrapsModel.getBank().subtract(myCrapsModel.getBet()).toString());
                }

                default -> {
                    //Error
                }
            }
        });
    }

    /**
     * Checks if a given String consists only of numerical characters.
     * Used to validate textField input.
     * @param theString The String to be checked.
     * @return True if the given string is numerical.
     */
    private boolean isNumerical(final String theString) {
        boolean isNum = true;
        for (final char c : theString.toCharArray()) {
            if (!Character.isDigit(c)) {
                isNum = false;
            }
        }
        return isNum;
    }

    /**
     * Creates a JOptionPane that asks the user if they want to exit
     * and closes the main window if 'yes' is selected.
     */
    private void exitConfirmation() {
        if (JOptionPane.showConfirmDialog(myWindow, "You sure that you want to exit?",
                "Exit Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            myWindow.dispose();
        }
    }

    /**
     * Listens for the close window button,
     * and confirms with the user to exit the window.
     */
    private class WindowExitListener extends WindowAdapter implements WindowListener {
        @Override
        public void windowClosing(final WindowEvent theEvent) {
            exitConfirmation();
        }
    }
}
