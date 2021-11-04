import stanford.karel.SuperKarel;
/**
 * Atypon homework assignment
 *
 * @author mohammad daoud
 */
public class Homework extends SuperKarel {
    /**
     * width will represent the width of the map -- the assignment in run method to avoid the null pointer exception
     * height will represent the height of the map -- the assignment in run method to avoid the null pointer exception
     * each stepCounter++ is for counting the Karel moves
     */
    private int width;
    private int height;
    private int stepsCounter = 1; // represent the Karel Steps that he move (it will be used in all operations that contain Karel moves)
    private String worldCase;
    private final String evenEven = "evenEven";
    private final String evenOdd = "evenOdd";
    private final String oddEven = "oddEven";
    private final String oddOdd = "oddOdd";

    public void run() {

        width = getWorld().getColumns();
        height = getWorld().getRows();
        setBeepersInBag(width * height);
        worldCase = checkMap();
        moveToCenter(true);
        fillVerticalLines();
        moveToCenter(false);
        fillHorizontalLines();
        returnHome();

        resetStepCounter();

    }


    /**
     * checkMap method is for knowing the size of the map before starting,
     * so, the method will return a string value in the following cases : (cuz each situation has unique technique or algorithm )
     * 1- if the map size is even * even the method will return "evenEven" which mean that width and height are even.
     * 2- if the map size is even * odd the method will return "evenOdd" which mean that only width is even.
     * 3- if the map size is odd * even the method will return "oddEven" which mean that only height is even.
     * 4- if the map size is odd * odd the method will return "oddOdd" which mean that width and height are odd.
     */

    public String checkMap() {
        if (width % 2 == 0 && height % 2 == 0)
            return evenEven;
        else if (width % 2 == 0)
            return evenOdd;
        else if (height % 2 == 0)
            return oddEven;
        else
            return oddOdd;
    }

    /**
     * the moveToCenter() method will make Karel move to the center of map
     * but in case of odd scenario the center of line will be (n + 1) / 2 ;
     */

    public void moveToCenter(boolean isVertical) {

        if (isVertical) {
            if (worldCase.equals(evenEven) || worldCase.equals(evenOdd))
                for (; stepsCounter < width / 2; stepsCounter++) {
                    while (beepersPresent())
                        pickBeeper();
                    move();
                }
            else
                for (; stepsCounter < (width + 1) / 2; stepsCounter++) {
                    while (beepersPresent())
                        pickBeeper();
                    move();
                }
        } else {
            int temp = getSteps();
            resetStepCounter();
            if (worldCase.equals(evenEven) || worldCase.equals(oddEven))
                for (; stepsCounter < height / 2; stepsCounter++) {
                    while (beepersPresent())
                        pickBeeper();
                    move();
                }
            else
                for (; stepsCounter < (height + 1) / 2; stepsCounter++) {
                    while (beepersPresent())
                        pickBeeper();
                    move();
                }
            stepsCounter += temp - 1;
        }
    }

    /**
     * the fillVerticalLines method will make Karel fill center line with beepers vertically (bottom up approach)
     * so, in ("evenEven" and "evenOdd") situation the vertical line will fill in same role
     * because the Width is even
     */

    public void fillVerticalLines() {
        if (worldCase.equals(evenEven) || worldCase.equals(evenOdd))
            verticalEvenWidth();
        else if (worldCase.equals(oddEven))
            verticalOddWidth();
    }


    /**
     * moveToWall method will make Karel moves forward until it is blocked by a wall .
     */
    private void moveToWall() {
        while (frontIsClear()) {
            move();
            while (beepersPresent())
                pickBeeper();
            stepsCounter++;
        }
    }

    /**
     * fillHorizontalLine method will make Karel fill center line with beepers horizontally (right -> left approach)
     */
    public void fillHorizontalLines() {

        if (worldCase.equals(evenEven) || worldCase.equals(oddEven))
            horizontalEvenHeight();
        else if (worldCase.equals(evenOdd))
            horizontalOddHeight();
    }

    public void horizontalEvenHeight() {
        while (frontIsClear()) {
            if (!beepersPresent())
                putBeeper();
            move();
            stepsCounter++;
            if (!beepersPresent())
                putBeeper();
            if (worldCase.equals(oddEven))
                turnRight();
            else
                turnLeft();
            if (frontIsBlocked())
                break;
            move();
            stepsCounter++;
            if (!beepersPresent())
                putBeeper();
            if (worldCase.equals(oddEven))
                turnRight();
            else
                turnLeft();
            move();
            stepsCounter++;
            if (!beepersPresent())
                putBeeper();
            if (worldCase.equals(oddEven))
                turnLeft();
            else
                turnRight();
            if (frontIsBlocked())
                break;
            move();
            stepsCounter++;

            if (!beepersPresent())
                putBeeper();
            if (worldCase.equals(oddEven))
                turnLeft();
            else
                turnRight();
        }
    }

    public void horizontalOddHeight() {
        turnLeft();
        while (frontIsClear()) {
            if (!beepersPresent())
                putBeeper();
            move();
        }
        if (!beepersPresent())
            putBeeper();
    }

    public void verticalEvenWidth() {
        if (!beepersPresent())
            putBeeper();
        turnLeft();
        while (facingNorth() && frontIsClear()) {
            if (!beepersPresent())
                putBeeper();
            move();
            stepsCounter++;
        }
        if (!beepersPresent())
            putBeeper();
        turnRight();
        move();
        stepsCounter++;
        turnRight();
        while (facingSouth() && frontIsClear()) {
            if (!beepersPresent())
                putBeeper();
            move();
            stepsCounter++;
        }
        if (!beepersPresent())
            putBeeper();
        turnLeft();
        moveToWall();
        turnLeft();
    }

    public void verticalOddWidth() {
        turnLeft();
        while (frontIsClear()) {
            if (!beepersPresent())
                putBeeper();
            move();
        }
        if (!beepersPresent())
            putBeeper();
        turnRight();
        moveToWall();
        turnRight();
    }

    /**
     * returnHome method will make Karel moves forward until it is return to his origin point (1,1) .
     */

    public void returnHome() {
        turnLeft();
        moveToWall();
        turnLeft();
    }
    public int getStepsCounter() {
        return stepsCounter;
    }

    // the resetStepCounter will reset the Karel moves counter to 1
    public void resetStepCounter() {
        this.stepsCounter = 1;
    }

}




