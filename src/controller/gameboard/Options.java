package controller.gameboard;

/**
 * Created by School on 4/27/2017.
 */
public class Options {
    private static int cellCount = 400;

    private static double bombPercentage = 0.03;

    private static int bombCount = (int) (cellCount * bombPercentage);
    
    private static int mustRevealCount = (cellCount - bombCount);


    public static int getCellCount() {
        return cellCount;
    }

    public static void setCellCount(int cellCount) {
        Options.cellCount = cellCount;
    }

    public static double getBombPercentage() {
        return bombPercentage;
    }

    public static void setBombPercentage(double bombPercentage) {
        Options.bombPercentage = bombPercentage;
    }

    public static int getBombCount() {
        return bombCount;
    }

    public static void setBombCount(int bombCount) {
        Options.bombCount = bombCount;
    }

    public static int getMustRevealCount() {
        return mustRevealCount;
    }

    public static void setMustRevealCount(int mustRevealCount) {
        Options.mustRevealCount = mustRevealCount;
    }
}
