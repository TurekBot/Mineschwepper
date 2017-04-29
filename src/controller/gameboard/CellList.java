package controller.gameboard;

import controller.map.Cell;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Keeps track of where the mines are.
 * <p>
 * Contains convenience methods for checking if one's neighbor is a mine.
 */
public class CellList extends ArrayList<Cell> {

    public CellList(GameBoard gameBoard) {
        super();

        //Let another class (Gameboard) borrow some of our functionality.
        shareCodeWith(gameBoard);

    }

    /**
     * Defines a method for another class; this is done this way to avoid
     * code reuse and total inefficiency.
     *
     * @param gameBoard the class we're going to share this code with
     */
    private void shareCodeWith(GameBoard gameBoard) {
        //This method (gameBoard.exploreEmptiness) will pass all of this enclosed
        //code back to gameBoard.

        Callback<Cell, Void> codeToPass;

        codeToPass = new Callback<Cell, Void>() {
            @Override
            public Void call(Cell you) {
                //First reveal yourself
                gameBoard.reveal(you);

                //Check all 8 neighbors around you
                for (Direction d : Direction.values()) {
                    try {
                        //Get your neighbor
                        Cell neighbor = neighborOnThe(d, you);

                        //Only consider the neighbor if they are visible
                        if (neighbor.isVisible()) {
                            //Case: Contents equals 0
                            if (Objects.equals(neighbor.getContents().getText(), "0")) {
                                //hopefully this will call a new stack frame of this call back and perform recursion
                                this.call(neighbor);
                            }

                            //Case: Contents is Non-0
                            else {
                                //Just reveal them, but don't reveal their neighbors
                                gameBoard.reveal(neighbor);
                            }
                        }

                    } catch (IndexOutOfBoundsException ioobe) {
                        System.out.println("No neighbor there.");
                    }


                }//End of For-Each Loop

                return null;
            }
        };

        gameBoard.defineExploreEmptiness(codeToPass);
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


        public final int offset; //number of squares away from a cell at the center of a square of cells

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
                        if (neighborOnThe(d, get(i)).isAMine()) count++;
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
    private Cell neighborOnThe(Direction direction, Cell middleCell) throws IndexOutOfBoundsException {
//        System.out.println("Checking neighbor at " + (direction.offset + middleCell));

        //If we're on the edge, don't consider our "neighbor" that is wrapped around onto the other side

        //On the west side
        if (hasFalseWesternNeighbor(direction, this.indexOf(middleCell))) {
            throw new IndexOutOfBoundsException("False Western Neighbor");
        }

        //On the east side
        if (hasFalseEasternNeighbor(direction, this.indexOf(middleCell))) {
            throw new IndexOutOfBoundsException("False Eastern Neighbor");
        }

        //Otherwise, assuming our neighbor is real, return them
        return this.get(direction.offset + this.indexOf(middleCell));
    }

    /**
     * Checks if our supposed neighbor is real or not.
     *
     * @param direction  the direction our supposed neighbor lives in
     * @param middleCell us
     * @return true if our neighbor isn't our neighbor
     */
    private boolean hasFalseWesternNeighbor(Direction direction, int middleCell) {

        boolean imOnTheWesternEdge;

        boolean wereCheckingAWesternNeighbor;


        //We know we're on the west edge if our location modulo 20 is 0.
        // (We have to check if we're at 0 because apparently you can't modulo by 0. Pft: Maths.)
        imOnTheWesternEdge = (middleCell == 0) || ((middleCell % 20) == 0);

        wereCheckingAWesternNeighbor = ((direction == Direction.NW) ||
                (direction == Direction.W) ||
                (direction == Direction.SW));

        return imOnTheWesternEdge && wereCheckingAWesternNeighbor;
    }

    /**
     * Checks if our supposed neighbor is real or not.
     *
     * @param direction  the direction our supposed neighbor lives in
     * @param middleCell us
     * @return true if our neighbor isn't our neighbor
     */
    private boolean hasFalseEasternNeighbor(Direction direction, int middleCell) {

        boolean imOnTheEasternEdge = false;

        boolean wereCheckingAnEasternNeighbor;


        //We know we're on the east edge if our location modulo 20 is 19.
        // (We have to check if we're at 0 because apparently you can't modulo by 0. Pft: Maths.)
        if (middleCell != 0) {
            imOnTheEasternEdge = ((middleCell % 20) == 19);
        }

        wereCheckingAnEasternNeighbor = ((direction == Direction.NE) ||
                (direction == Direction.E) ||
                (direction == Direction.SE));

        return imOnTheEasternEdge && wereCheckingAnEasternNeighbor;
    }

}
