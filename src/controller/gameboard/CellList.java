package controller.gameboard;

import controller.map.Cell;

import java.util.ArrayList;

/**
 * Keeps track of where the mines are.
 * <p>
 * Contains convenience methods for checking if one's neighbor is a mine.
 */
public class CellList extends ArrayList<Cell> {

    public CellList() {
        super();

    }

    /**
     * Gives the offset of a neighboring square. If you give me i's index,
     * I can tell you where it's eastern neighbor is by getting the Cell located
     * at the index resultant from adding E's value to i's.
     */
    public enum Direction {
        NW(-21),
        N(-20),
        NE(-19),

        W(-1),

        E(+1),

        SW(+19),
        S(+20),
        SE(+21);


        private final int offset; //number of squares away from a cell at the center of a square of cells

        Direction(int offset) {
            this.offset = offset;
        }
    }

    /**
     * Goes through all the children and determines how many of
     * each cell's neighbors are bombs.
     */
    public void plotHints() {

        int size = this.size();

        for (int i = 0; i < size; i++) {
            //don't give a hint to cells that are mines!
            if (!get(i).isAMine()) {
                //counts how many mines are touching this cell
                int count = 0;

                //Count how many neighbor's have bombs.
                for (Direction d : Direction.values()) {
                    try {
                        if (neighborOnThe(d, i).isAMine()) count++;
                    } catch (IndexOutOfBoundsException ioobe) {
//                        System.err.println("No neighbor!");
                        //if there is no cell there, then I guess
                        //there can't be a mine there, either, can there?
                    }
                }

                //Give cell, it's number hint
                this.get(i).setHint(String.valueOf(count));
            }
        }

    }

    /**
     * Determines if this cell's neighbor has a mine.
     *
     * @param direction  the direction our neighbor lives
     * @param middleCell cell we start from
     * @return middle cell's neighbor on the {direction} side
     * @throws IndexOutOfBoundsException when there is no neighbor, like when you're on the edge of the map
     */
    private Cell neighborOnThe(Direction direction, int middleCell) throws IndexOutOfBoundsException {
//        System.out.println("Checking neighbor at " + (direction.offset + middleCell));
        return this.get(direction.offset + middleCell);
    }
}
