package controller.gameboard;

/**
 * Created by School on 4/27/2017.
 */
public class Options {
    private static int cellCount = 400;

    private static double bombPercentage = 0.25;

    private static int bombCount = (int) (cellCount * bombPercentage);


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
}
