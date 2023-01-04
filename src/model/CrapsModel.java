/*
 * TCSS 305 Assignment 5 - Craps
 * Autumn 2022
 */

package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigInteger;
import java.util.Random;

/**
 * The model to the Craps project. Handles all the backend logic of the game.
 *
 * @author Dillon Crookshank
 * @version 1.0
 */
public final class CrapsModel {

    /** Used by a PropertyChangeListener to indicate a change in the Dice fields. */
    public static final String PROPERTY_DICE = "Dice Change";

    /** Used by a PropertyChangeListener to indicate a change in the Point field. */
    public static final String PROPERTY_POINT = "Point Change";

    /** Used by a PropertyChangeListener to indicate a change in the Bank fields. */
    public static final String PROPERTY_BANK = "Bank Change";

    /** Used by a PropertyChangeListener to indicate a change in the Bet fields. */
    public static final String PROPERTY_BET = "Bet Change";

    /** Name of the User player. */
    public static final String PLAYER = "Player";

    /** Name of the House player. */
    public static final String HOUSE = "House";

    /** Random number generator used to roll the Dice. */
    private static final Random RANDOM = new Random();

    /** The maximum value that a Die can roll. */
    private static final int DICE_MAXIMUM = 6;

    /** The minimum value that a Die can roll. */
    private static final int DICE_MINIMUM = 1;

    /** Used to alert listeners of a property change. */
    private final PropertyChangeSupport myPCS;

    /** The number currently facing up on the first Die. */
    private int myDice1;

    /** The number currently facing up on the second Die. */
    private int myDice2;

    /**
     * The value that the player must roll to win a round,
     * if they did not win the first round.
     */
    private int myPoint;

    /** The amount of currency that is currently in the bank. */
    private BigInteger myBank;

    /** The amount of currency that is currently being bet. */
    private BigInteger myBet;

    /** A flag used to indicate that a round has ended. */
    private boolean myRoundEndFlag;

    /** Stores the name of the player that won the last round. */
    private String myLastWinner;

    /**
     * The lone constructor for the CrapsModel.
     */
    public CrapsModel() {
        myPCS = new PropertyChangeSupport(this);
        softReset();
    }

    /**
     * Takes a single round step. A round step consists of rolling
     * the dice, and checking if anyone has won.
     * If anyone has won, the RoundEndFlag is set, the lastWinner is set,
     * and the bank is updated accordingly.
     */
    public void doRoundStep() {
        rollDice();

        if (myPoint == -1) {
            if (getDiceTotal() == 7 || getDiceTotal() == 11) {
                //player wins
                setBankTotal(getBank().add(getBet()));
                setBet(BigInteger.ZERO);
                myRoundEndFlag = true;
                myLastWinner = PLAYER;

            } else if (getDiceTotal() == 2 || getDiceTotal() == 3 || getDiceTotal() == 12) {
                //house wins
                setBankTotal(getBank().subtract(getBet()));
                setBet(BigInteger.ZERO);
                myRoundEndFlag = true;
                myLastWinner = HOUSE;

            } else {
                setPoint(getDiceTotal());
            }
        } else {
            if (getDiceTotal() == myPoint) {
                //player wins
                setBankTotal(getBank().add(getBet()));
                setBet(BigInteger.ZERO);
                myRoundEndFlag = true;
                myLastWinner = PLAYER;

            } else if (getDiceTotal() == 7) {
                //house wins
                setBankTotal(getBank().subtract(getBet()));
                setBet(BigInteger.ZERO);
                myRoundEndFlag = true;
                myLastWinner = HOUSE;

            }
        }
    }

    /**
     * Done in-between rounds. Resets the fields
     * without alerting any property change listeners.
     */
    public void softReset() {
        myDice1 = -1;
        myDice2 = -1;
        myPoint = -1;
        myRoundEndFlag = false;
    }

    /**
     * Returns true if the round has ended, false otherwise.
     * @return The myRoundEndFlag field.
     */
    public boolean isRoundOver() {
        return myRoundEndFlag;
    }

    /**
     * Sets random values to both Dice.
     */
    private void rollDice() {
        setDice1(RANDOM.nextInt(DICE_MINIMUM, DICE_MAXIMUM + 1));
        setDice2(RANDOM.nextInt(DICE_MINIMUM, DICE_MAXIMUM + 1));

        myPCS.firePropertyChange(PROPERTY_DICE, 0, getDiceTotal());
    }

    /**
     * Calculates the sum of the numbers currently facing up on the Dice.
     * @return The sum of the Dice, any integer in the range [2, 12].
     */
    public int getDiceTotal() {
        return getDice1() + getDice2();
    }

    /**
     * Returns the number that is currently facing up on the first Dice.
     * @return An integer from [1-6].
     */
    public int getDice1() {
        return myDice1;
    }

    /**
     * Returns the number that is currently facing up on the second Dice.
     * @return An integer from [1-6].
     */
    public int getDice2() {
        return myDice2;
    }

    /**
     * Sets the number that is facing up on the first Die.
     * @param theDiceValue The new value for the Die.
     * @throws IllegalArgumentException When given any value outside the range [1, 6].
     */
    private void setDice1(final int theDiceValue) {
        if (theDiceValue > DICE_MAXIMUM || theDiceValue < DICE_MINIMUM) {
            throw new IllegalArgumentException(
                    "Dice values must be in the range [1, 6]. Given: " + theDiceValue);
        }

        myDice1 = theDiceValue;
    }

    /**
     * Sets the number that is facing up on the second Die.
     * @param theDiceValue The new value for the Die.
     * @throws IllegalArgumentException When given any value outside the range [1, 6].
     */
    private void setDice2(final int theDiceValue) {
        if (theDiceValue > DICE_MAXIMUM || theDiceValue < DICE_MINIMUM) {
            throw new IllegalArgumentException(
                    "Dice values outside the range [1, 6]. Given: " + theDiceValue);
        }

        myDice2 = theDiceValue;
    }

    /**
     * Returns the Point value.
     * @return The myPoint field.
     */
    public int getPoint() {
        return myPoint;
    }

    /**
     * Sets the Point value.
     * @param thePoint The new value for the Point.
     * @throws IllegalArgumentException When given a value outside the range [2, 12].
     */
    private void setPoint(final int thePoint) {
        if (thePoint < DICE_MINIMUM * 2 || thePoint > DICE_MAXIMUM * 2) {
            throw new IllegalArgumentException(
                    "Point value outside the range [2, 12]. Given: " + thePoint);
        }

        myPoint = thePoint;

        myPCS.firePropertyChange(PROPERTY_POINT, 0, thePoint);
    }

    /**
     * Returns the current amount stored in the bank.
     * @return The myBank field.
     */
    public BigInteger getBank() {
        return myBank;
    }

    /**
     * Sets the Bank value.
     * @param theBank The new value for the Bank.
     * @throws IllegalArgumentException When given a value that is less than 0.
     */
    public void setBankTotal(final BigInteger theBank) {
        if (theBank.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Bank cannot be less than 0. Given: " + theBank);
        }

        myBank = theBank;

        myPCS.firePropertyChange(PROPERTY_BANK, -1, theBank);
    }

    /**
     * Returns the current bet amount.
     * @return The myBet field.
     */
    public BigInteger getBet() {
        return myBet;
    }

    /**
     * Sets the Bet value.
     * @param theBet The new value for the Bet.
     * @throws IllegalArgumentException When given a value that is less
     * than 0 or larger than what is currently in the bank.
     */
    public void setBet(final BigInteger theBet) {
        if (theBet.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Bet total cannot be less than 0. Given: " + theBet);
        }
        if (theBet.compareTo(myBank) > 0) {
            throw new IllegalArgumentException(
                    "Bet total cannot be larger than the bank total. Given: " + theBet);
        }

        myBet = theBet;

        myPCS.firePropertyChange(PROPERTY_BET, -1, theBet);
    }

    /**
     * Returns the winner of the last round.
     * @return The myLastWinner field.
     */
    public String getWinner() {
        return myLastWinner;
    }

    /**
     * Adds a PropertyChangeListener that
     * listens for changes in any field in the CrapsModel.
     * @param theListener The listener to be added to the CrapsModel.
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
    }
}
