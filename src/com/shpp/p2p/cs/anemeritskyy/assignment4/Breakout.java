package com.shpp.p2p.cs.anemeritskyy.assignment4;

import acm.graphics.*;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Press mouse button to start game,
 * paddle position is changed relative as cursor position
 */
public class Breakout extends WindowProgram {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */
    private static final int NTURNS = 3;

    /**
     * Font for label
     */
    private static final String FONT = "Verdana-30";

    /**
     * Ball size, calculated from ball radius
     */
    private static final int BALL_SIZE = BALL_RADIUS * 2;

    /**
     * Delay in milliseconds for ball movement
     */
    private static final int DELAY = 5;

    /**
     * Default game colors
     */
    private final static Color[] COLORS = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};

    /**
     * Array for brick storage
     */
    private final static GRect[][] BRICKS = new GRect[NBRICKS_PER_ROW][NBRICK_ROWS];

    /**
     * Array for hearts images storage
     */
    private final GImage[] HEARTS = new GImage[NTURNS];

    /**
     * Start bug mode, drawing all intercepted dots
     */
    private final static boolean DEBUG_MODE = false;

    /**
     * Shift the ball by coordinates
     */
    private double vx, vy;

    /**
     * Object of paddle
     */
    private GRect paddle = null;

    /**
     * Current game status
     */
    private boolean isPlay = false;

    /**
     * Count of left lives
     */
    private int livesLeft = NTURNS;

    /**
     * Count of left bricks in the frame
     */
    private int totalBricks;

    /**
     * Winner animation object
     */
    private GImage winnerAnimation = null;

    /**
     * Dynamic calculation of brick width
     */
    private int brickWidth;

    /**
     * Initialize starting direction of the ball and calculate total bricks in the frame
     */
    @Override
    public void init() {
        RandomGenerator randomGenerator = RandomGenerator.getInstance();//
        vy = 1;
        vx = randomGenerator.nextDouble(1, 3);
        if (randomGenerator.nextBoolean(0.5))
            vx = -vx;
        calculateTotalBricks();
    }

    /**
     * Main method of this game, use methods of rendering and game behavior
     */
    @Override
    public void run() {
        brickWidth = (getWidth() - BRICK_SEP * (NBRICKS_PER_ROW)) / NBRICKS_PER_ROW; // late init because width is dynamic
        fillBricks();
        drawPaddle();
        drawHearts();
        addMouseListeners();
        waitForPlay();
    }

    /**
     * Wait for started new game based on boolean variable isPlay
     * also used delay 1sec between checks
     */
    private void waitForPlay() {
        while (livesLeft > 0) {
            if (isPlay) {
                GOval ball = drawBall();
                moveBallToObstacle(ball);
            }
            pause(1000);
        }
    }

    /**
     * Calculate total bricks in the game
     */
    private void calculateTotalBricks() {
        totalBricks = NBRICK_ROWS * NBRICKS_PER_ROW;
    }

    /**
     * Draw images of hearts based on count of available games
     * hearts array already created
     */
    private void drawHearts() {
        for (int i = 0; i < HEARTS.length; i++) {
            GImage heart = new GImage("src/com/shpp/p2p/cs/anemeritskyy/assignment4/assets/heart.png");
            heart.setLocation(getWidth() - (heart.getSize().getWidth() + BRICK_SEP) * (i + 1), BRICK_SEP);
            add(heart);
            HEARTS[i] = heart;
        }
    }

    /**
     * Fills the frame with bricks
     */
    private void fillBricks() {
        for (int i = 0; i < BRICKS.length; i++) {
            for (int j = 0; j < BRICKS[i].length; j++) {
                drawBrick(i, j);
            }
        }
    }

    /**
     * Draw filled brick
     * From the top to bottom fill any two row by new color
     * but if count of rows more than colors of colors *2 we used last color
     *
     * @param position of brick
     * @param row      number
     */
    private void drawBrick(int position, int row) {
        GRect brick = new GRect(
                BRICK_SEP + BRICK_SEP * position + brickWidth * position,
                BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * row,
                brickWidth,
                BRICK_HEIGHT
        );
        brick.setFilled(true);
        brick.setColor(COLORS[Math.min(row / 2, COLORS.length - 1)]);
        add(brick);
    }

    /**
     * Move paddle when mouse is moved
     *
     * @param mouseEvent the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        movePaddle(mouseEvent.getX());
    }

    /**
     * Move ball to obstacle, if intersect with obstacle
     * change coordinates according to the theory of reflection
     * also removed intersection bricks
     * check frame borders
     *
     * @param ball in the current game
     */
    private void moveBallToObstacle(GOval ball) {
        while (!isLoose(ball) && !isWinner(ball)) {

            moveToNextLocation(ball);

            Set<GObject> collidingObjectsWithBall = getCollidingObjectsWithBall(ball);
            removeCollidingBricks(ball, collidingObjectsWithBall);

            checkFrameBorders(ball);
            pause(DELAY);
        }
    }

    /**
     * Move ball to the next location using vx and vy coordinates
     *
     * @param ball in the current game
     */
    private void moveToNextLocation(GOval ball) {
        ball.setLocation(ball.getX() + vx, ball.getY() + vy);
    }

    /**
     * If coordinates of the ball is outside of frame
     * - stop game using boolean variable isPlay
     * - remove ball from frame
     * - reduce count of lives and remove heart icon from window
     *
     * @param ball in the current game
     * @return true when game is loose
     */
    private boolean isLoose(GOval ball) {
        if (ball.getY() > getHeight()) {
            isPlay = false;
            remove(ball);
            livesLeft--;
            remove(HEARTS[livesLeft]);
            if (livesLeft == 0) {
                writeLabel("Game Over");
            }
            return true;
        }
        return false;
    }

    /**
     * If all bricks are knocked out then player won
     * - stop game using boolean variable isPlay
     * - remove ball from frame
     * - draw winner animation
     *
     * @param ball in the current game
     */
    private boolean isWinner(GOval ball) {
        if (winnerAnimation != null)
            return true;
        if (totalBricks == 0) {
            isPlay = false;
            remove(ball);
            drawWinnerAnimation();
            vy = 1;
            return true;
        }
        return false;
    }

    /**
     * Checking if ball intersect with bricks
     * - remove bricks
     * - change coordinates according to the theory of reflection
     * - change total count of remaining bricks
     *
     * @param ball                     in the current game
     * @param collidingObjectsWithBall is collection of intersect objects
     */
    private void removeCollidingBricks(GOval ball, Set<GObject> collidingObjectsWithBall) {
        if (!collidingObjectsWithBall.isEmpty()) {
            Iterator<GObject> iterator = collidingObjectsWithBall.iterator();
            double differentBetweenY = 0;

            while (iterator.hasNext()) {
                GObject collidingBrick = iterator.next();
                iterator.remove();
                differentBetweenY = (collidingBrick.getY() + BRICK_HEIGHT - ball.getY());

                if (collidingBrick != paddle) { // if object is brick remove it
                    remove(collidingBrick);
                    totalBricks--;
                }
            }

            if (ballInFrontOfPaddle(ball)) {
                if (isSideOfBrick(differentBetweenY)) {
                    vx = -vx;
                } else {
                    vy = -vy;
                }
            }
        }
    }

    /**
     * Check position of ball relative the paddle
     *
     * @param ball in the current game
     * @return boolean status when ball in front of paddle
     */
    private boolean ballInFrontOfPaddle(GOval ball) {
        return ball.getY() + BALL_SIZE < paddle.getY();
    }

    /**
     * Check if current position of the ball in side of brick
     *
     * @param differentBetweenY different between two objects by y-axis
     * @return true if side of current brick
     */
    private static boolean isSideOfBrick(double differentBetweenY) {
        return differentBetweenY > 1 && differentBetweenY < BALL_SIZE + BRICK_HEIGHT;
    }

    /**
     * Draw winner animation in the middle of frame
     */
    private void drawWinnerAnimation() {
        GImage winner = new GImage("src/com/shpp/p2p/cs/anemeritskyy/assignment4/assets/win.gif");
        winner.setLocation((getWidth() - winner.getWidth()) / 2, (getHeight() - winner.getHeight()) / 2);
        winnerAnimation = winner;
        add(winner);
    }

    /**
     * If player have any lives he needs to press any mouse button
     * - if player is winner and pressed the button, remove winner animation and fill frame bricks
     * - if player loose game and have any lives, he can play one more game
     *
     * @param mouseEvent the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (winnerAnimation != null) {
            remove(winnerAnimation);
            winnerAnimation = null;
            fillBricks();
            calculateTotalBricks();
        }
        if (!isPlay && livesLeft > 0) {
            isPlay = true;
        }
    }

    /**
     * Write label in the middle of frame
     *
     * @param message text current label
     */
    private void writeLabel(String message) {
        GLabel label = new GLabel(message);
        label.setFont(FONT);
        label.setLocation((getWidth() - label.getWidth()) / 2, (getHeight() - label.getHeight()) / 2);
        add(label);
    }

    /**
     * Check intercept of ball with sides and top of border
     * if ball intercepted change coordinates according to the theory of reflection
     *
     * @param ball in the current game
     */
    private void checkFrameBorders(GOval ball) {
        if (ball.getY() <= 0)  //top
            vy = -vy;

        if (ball.getX() <= 0 || ball.getX() >= getWidth() - BALL_SIZE)  //left or right
            vx = -vx;
    }

    /**
     * Draw a ball in the middle of frame
     */
    private GOval drawBall() {
        GOval ball = new GOval((double) (getWidth() - BALL_SIZE) / 2, (double) (getHeight() - BALL_SIZE) / 2,
                BALL_SIZE, BALL_SIZE);
        ball.setFilled(true);
        ball.setColor(Color.BLACK);
        add(ball);
        return ball;
    }

    /**
     * Move paddle based on coordinates of cursor
     * center the paddle based of cursor position
     * also use edge coordinates for both sides
     *
     * @param cursorOffsetX x coordinate of cursor
     */
    private void movePaddle(int cursorOffsetX) {
        if (cursorOffsetX > PADDLE_WIDTH / 2) {
            paddle.setLocation(Math.min(cursorOffsetX - PADDLE_WIDTH / 2, getWidth() - PADDLE_WIDTH), paddle.getY());
        } else {
            paddle.setLocation(0, paddle.getY());
        }
    }

    /**
     * Draw a paddle using based sizes and y coordinate position
     */
    private void drawPaddle() {
        GRect paddle = new GRect((double) (getWidth() - PADDLE_WIDTH) / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT,
                PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFilled(true);
        paddle.setColor(Color.BLACK);
        add(paddle);
        this.paddle = paddle;
    }

    /**
     * If object intersected with any side of ball, add it to our set collection
     *
     * @param ball in the current game
     * @return collection of unique objects intersected with ball
     */
    private Set<GObject> getCollidingObjectsWithBall(GOval ball) {
        Set<GObject> collidingObjectsWithBall = new HashSet<>();
        checkNextBallPosition(collidingObjectsWithBall, ball.getX(), ball.getY()); // top left
        checkNextBallPosition(collidingObjectsWithBall, ball.getX() + BALL_SIZE, ball.getY()); // top right
        checkNextBallPosition(collidingObjectsWithBall, ball.getX(), ball.getY() + BALL_SIZE); // bottom left
        checkNextBallPosition(collidingObjectsWithBall, ball.getX() + BALL_SIZE, ball.getY() + BALL_SIZE); // bottom right
        return collidingObjectsWithBall;
    }

    /**
     * Check objects at the same position of the ball direction
     *
     * @param collidingObjectsWithBall set of objects on the same coordinates of the ball with next step
     * @param x-axis                   one of the edges of a square describing the ball
     * @param y-axis                   one of the edges of a square describing the ball
     */
    private void checkNextBallPosition(Set<GObject> collidingObjectsWithBall, double x, double y) {
        GObject collidingObject = getElementAt(x + vx, y + vy);

        if (collidingObject != null
                && !(collidingObject instanceof GImage) // skip images as hearts
                && collidingObject.getColor() != Color.BLUE) { // for debug mode
            if (DEBUG_MODE)
                debugDrawPixel(x + vx, y + vy);
            collidingObjectsWithBall.add(collidingObject);
        }
    }

    /**
     * Draw dot where two objects are intersected in debug mode
     *
     * @param x dot x position
     * @param y dot y position
     */
    private void debugDrawPixel(double x, double y) {
        GRect gRect = new GRect(x, y, 2, 2);
        gRect.setFilled(true);
        gRect.setColor(Color.BLUE);
        add(gRect);
    }
}