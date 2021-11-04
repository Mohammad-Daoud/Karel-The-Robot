import stanford.karel.SuperKarel;

public class ShortestPath extends SuperKarel {
    private int width = 1;
    private int height = 1;
    private int x = 1;
    private int y = 1;

    private int steps = 1; // represent the Karel Steps that he move (it will be used in all operations that contain Karel moves)
    private String worldCase;

    private final String EVEN_EVEN = "EVEN_EVEN";
    private final String EVEN_ODD = "EVEN_ODD";
    private final String ODD_EVEN = "ODD_EVEN";
    private final String ODD_ODD = "ODD_ODD";
    private final String PERFECT_ODD = "PERFECT_ODD";
    private final String H_FILL = "H_FILL";
    private final String V_FILL = "V_FILL";

    public void run() {

        int beepers = 200;
        setBeepersInBag(beepers);
        findSize();
        worldCase = checkMap();
        System.out.println(worldCase);
        fastFill();
        System.out.println(width + " " + height);
        System.out.println(" x= " + getX() + " y= " + getY());
        System.out.println("Karel has been moves " + getSteps() + " steps.");

        resetAll();


    }

    /**
     * @return ODD_ODD if karel is dealing with <tt>width == 3</tt> and <tt>height == 3</tt> or the <tt>width</tt> and <tt>height</tt>
     * are odd and not equally.<tt>width == 1 || width = 2 </tt> and <tt>height > 6</tt>
     * H_FILL if karel is dealing with
     */

    public String checkMap() {

        if (width == 3 && height == 3)
            return ODD_ODD;
        else if (height <= 2 && width <= 2)
            throw new IllegalArgumentException("this type of map cannot be divided !! : width = " + width + " height = " + height);
        else if ((height == 1 || height == 2) && width > 3)
            return H_FILL;
        else if ((width == 1 || width == 2) && height > 3)
            return V_FILL;
        else if (width % 2 == 0 && height % 2 == 0)
            return EVEN_EVEN;
        else if (width % 2 == 0)
            return EVEN_ODD;
        else if (height % 2 == 0)
            return ODD_EVEN;
        else if (width == height)
            return PERFECT_ODD;
        else
            return ODD_ODD;
    }


    /**
     * findSize() will make Karel knows the size of the map, So he will know the map that deals with.
     */

    public void findSize() {
        correctDirection();
        while (frontIsClear() && facingEast()) {
            move();
            stepsCounter();
            width++;
            XYCoordinates();

        }
        turnLeft();
        while (frontIsClear() && facingNorth()) {
            move();
            stepsCounter();
            height++;
            XYCoordinates();
        }
        System.out.println(" x= " + getX() + " y= " + getY());
    }

    /**
     * to find x and y points
     */

    public void XYCoordinates() {
        if (facingEast())
            x++;
        if (facingWest())
            x--;
        if (facingNorth())
            y++;
        if (facingSouth())
            y--;
    }

    public void correctDirection() {

        if (facingWest())
            turnAround();
        else if (facingNorth())
            turnRight();
        else if (facingSouth())
            turnLeft();

    }

    public void fastMoveToCenter(boolean isVerticalMove) {
        int temp = getSteps();
        resetStepCounter();
        if (isVerticalMove) {
            for (; steps < (height + 1) / 2; steps++) {
                beeperPutter();
                move();
                XYCoordinates();
                System.out.println(" x= " + getX() + " y= " + getY());
            }
        } else {
            for (; steps < (width + 1) / 2; steps++) {
                beeperPutter();
                move();
                XYCoordinates();
                System.out.println(" x= " + getX() + " y= " + getY());
            }
        }
        steps += temp - 1;
        beeperPutter();
        System.out.println(getX() + " " + getY());

    }

    public void fastFill() {
        if (worldCase.equals(EVEN_EVEN)) {

            turnLeft();
            fastMoveToCenter(false);
            System.out.println(" x= " + getX() + " y= " + getY());

            turnLeft();
            fastMoveToCenter(true);
            turnLeft();
            fastMoveToCenter(false);
            turnRight();
            move();
            stepsCounter();
            XYCoordinates();

            turnRight();
            fastMoveToCenter(false);
            turnLeft();

            fastMoveToCenter(true);
            turnRight();

            move();
            XYCoordinates();
            stepsCounter();

            turnRight();
            fastMoveToCenter(true);
            turnLeft();
            fastMoveToCenter(false);
            turnRight();

            move();
            stepsCounter();
            XYCoordinates();

            turnRight();
            fastMoveToCenter(false);

            turnLeft();
            fastMoveToCenter(true);
            turnLeft();

            returnHome();


        } else if (worldCase.equals(EVEN_ODD)) {
            turnAround();
            fastMoveToCenter(true);

            //second
            turnRight();
            fastMoveToCenter(false);
            turnRight();
            fastMoveToCenter(false);
            turnLeft();
            move();
            XYCoordinates();
            stepsCounter();
            turnLeft();
            fastMoveToCenter(true);
            fastMoveToCenter(true);
            turnLeft();
            move();
            XYCoordinates();
            stepsCounter();
            turnLeft();
            fastMoveToCenter(true);
            turnLeft();
            returnHome();

        } else if (worldCase.equals(ODD_EVEN)) {
            turnLeft();
            fastMoveToCenter(false);
            turnLeft();
            fastMoveToCenter(true);
            turnLeft();
            fastMoveToCenter(true);
            turnRight();
            move();
            turnRight();
            fastMoveToCenter(false);
            fastMoveToCenter(false);
            turnRight();
            move();
            turnRight();
            fastMoveToCenter(true);

            turnRight();
            move();
            XYCoordinates();

            fastMoveToCenter(true);
            turnRight();
            fastMoveToCenter(false);
            turnAround();
        }
    }

    public void stepsCounter() {
        steps++;
    }

    public int getSteps() {
        return steps;
    }

    public void resetStepCounter() {
        steps = 1;
    }

    public void resetAll() {
        this.steps = 1;
        this.width = 1;
        this.height = 1;
        this.x = 1;
        this.y = 1;
    }

    public void beeperPutter() {
        if (!beepersPresent() && worldCasePoints())
            putBeeper();
    }

    private boolean worldCasePoints() {
        int widthRightCenter = width / 2 + 1;
        int eWidthCenter = width / 2;
        int x = width - widthRightCenter;
        int wGap = (x + 1) / 2;
        int wRightPoint = widthRightCenter + wGap;
        int wLeftPoint;
        if (width % 2 == 0)
            wLeftPoint = eWidthCenter - wGap;
        else
            wLeftPoint = widthRightCenter - wGap;

        int heightRightCenter = height / 2 + 1;
        int eHeightCenter = height / 2;
        int x1 = height - heightRightCenter;
        int hGap = (x1 + 1) / 2;
        int hRightPoint = heightRightCenter + hGap;
        int hLeftPoint;
        if (height % 2 == 0)
            hLeftPoint = eHeightCenter - hGap;
        else
            hLeftPoint = heightRightCenter - hGap;

        switch (worldCase) {
            case PERFECT_ODD:
                return getX() == getY() ||
                        getX() + getY() == width + 1 ||
                        getX() == (width + 1) / 2 ||
                        getY() == (height + 1) / 2;

            case ODD_ODD:
                return getX() == (width + 1) / 2 ||
                        getY() == (height + 1) / 2;

            case EVEN_EVEN:
                return getX() == width / 2 ||
                        getX() == width / 2 + 1 ||
                        getY() == height / 2 ||
                        getY() == height / 2 + 1;

            case ODD_EVEN:
                return getX() == width / 2 + 1 ||
                        getY() == height / 2 ||
                        getY() == height / 2 + 1;

            case EVEN_ODD:
                return getX() == width / 2 ||
                        getX() == width / 2 + 1 ||
                        getY() == height / 2 + 1;
            case H_FILL:
                if (width > 2 && width <= 6)
                    if (width % 2 == 0)
                        return getX() == width / 2 ||
                                getX() == width / 2 + 1;
                if (width % 4 == 0)
                    return getX() == width / 2 ||
                            getX() == width / 2 + 1 ||
                            getX() == wRightPoint ||
                            getX() == wLeftPoint;
                if (width % 2 == 0)
                    return (getX() == width && getY() == height) ||
                            (getX() == width && getY() == 1) ||
                            (getX() == 1 && getY() == 1) ||
                            (getX() == 1 && getY() == height) ||
                            getX() == width / 2 ||
                            getX() == width / 2 + 1 ||
                            getX() == wRightPoint ||
                            getX() == wLeftPoint;

                else if (width == 7)
                    return getX() == (width + 1) / 2 ||
                            getX() == wRightPoint ||
                            getX() == wLeftPoint;

                else return (getX() == width && getY() == height) ||
                            (getX() == width && getY() == 1) ||
                            (getX() == 1 && getY() == 1) ||
                            (getX() == 1 && getY() == height) ||
                            getX() == (width + 1) / 2 ||
                            getX() == wRightPoint ||
                            getX() == wLeftPoint;

            case V_FILL:
                if (height > 2 && height <= 6)
                    if (height % 2 == 0)
                        return getY() == height / 2 ||
                                getY() == height / 2 + 1;

                    else return getY() == height / 2 + 1;
                if (height % 4 == 0)
                    return getY() == height / 2 ||
                            getY() == height / 2 + 1 ||
                            getY() == hRightPoint ||
                            getY() == hLeftPoint;
                if (height % 2 == 0)
                    return (getX() == width && getY() == height) ||
                            (getX() == width && getY() == 1) ||
                            (getX() == 1 && getY() == 1) ||
                            (getX() == 2 && getY() == height) ||
                            (getX() == 1 && getY() == height) ||
                            getY() == height / 2 ||
                            getY() == height / 2 + 1 ||
                            getY() == hRightPoint ||
                            getY() == hLeftPoint;

                else if (width == 7)
                    return getX() == (width + 1) / 2 ||
                            getX() == wRightPoint ||
                            getX() == wLeftPoint;
                else return (getX() == width && getY() == height) ||
                            (getX() == width && getY() == 1) ||
                            (getX() == 1 && getY() == 1) ||
                            (getX() == 1 && getY() == height) ||
                            (getX() == 2 && getY() == height) ||
                            getY() == (height + 1) / 2 ||
                            getY() == hRightPoint ||
                            getY() == hLeftPoint;
            default:
                return false;
        }

    }


    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }


    public void returnHome() {
        while (frontIsClear()) {
            beeperPutter();
            move();
            XYCoordinates();
            stepsCounter();
        }
        turnLeft();
        beeperPutter();
        while (frontIsClear()) {
            move();
            XYCoordinates();
            stepsCounter();
        }
        turnLeft();
    }
}