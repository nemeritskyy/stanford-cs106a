package com.shpp.p2p.cs.anemeritskyy.assignment3;


import acm.util.RandomGenerator;
import com.shpp.cs.a.console.TextProgram;

/**
 * Casino Bernulli
 * <p>
 * Two people play: the lucky one and the sweaty one.
 * The lucky person leaves the casino when he earns $20 or more.
 * The sweaty one puts $1 on the table, and the lucky one starts flipping a coin.
 * If it is an eagle, then the sweaty person adds exactly the same amount to the amount on the table.
 * If it's a tail, everything on the table goes to the lucky person.
 * If the lucky winner ends up with less than $20,
 * then the game repeats itself.
 */
public class Assignment3Part5 extends TextProgram {
    RandomGenerator randomGenerator = RandomGenerator.getInstance();
    public final static int FINALLY_LUCKY_BALANCE = 20;

    @Override
    public void run() {
        int luckyBalance = 0;
        int totalGames = 0;

        // when balance < FINALLY_LUCKY_BALANCE, start new game
        while (luckyBalance < FINALLY_LUCKY_BALANCE) {
            int gameBalance = 1;

            // the sweaty person adds exactly the same amount to the amount on the table
            while (isEagle()) {
                gameBalance += gameBalance;
            }

            luckyBalance += gameBalance;
            totalGames++;
            println("This game, you earned $" + gameBalance);
            println("Your total is $" + luckyBalance);
        }
        println("It took " + totalGames + " games to earn $" + FINALLY_LUCKY_BALANCE);
    }

    /**
     * Tossing a coin, and returning true if fell out eagle
     *
     * @return - random boolean result
     */
    private boolean isEagle() {
        return randomGenerator.nextBoolean();
    }
}
