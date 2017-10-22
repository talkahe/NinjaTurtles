package com.jorge.ninjaturtles.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MazeGenerator {

    // Size 
    private int width;
    private int height;

    // Starting and ending positions 
    private int startingX;
    private int startingY;
    private int endingX;
    private int endingY;

    // Offset 
    private float xOffset;
    private float yOffset;

    // Blocked Tiles 
    private boolean blocked[][];
    private ArrayList < Tile > tiles;

    // Possible Directions (used during maze generation) 
    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;

    public MazeGenerator(int width, int height) {

        this.width = width;
        this.height = height;
        startingX = 1;
        startingY = 0;
        endingX = width - 3;
        endingY = height - 2;

        // Start the Rectangles Array List 
        tiles = new ArrayList < > ();

        // Setup the Map 
        blocked = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                blocked[i][j] = true;
            }
        }

        // Starting and ending positions are not blocked! 
        //blocked[startingX][startingY] = false;
        //blocked[endingX][endingY] = false;
    }


    /**
     * Runs the algorithm to generate the Maze 
     * @return 
     */
    public boolean[][] createMaze() {

        // Basic settings 
        int back;
        String possibleDirections;
        Point pos = new Point(1, 1); // Leave the 0, 0 for the outer wall 

        // Possible movement list 
        List < Integer > moves = new ArrayList < > ();
        moves.add(pos.y + (pos.x * width));

        // Are there still available movements? 
        while (!moves.isEmpty()) {

            // Possible Directions 
            possibleDirections = "";

            if ((pos.y + 2 < height) && (blocked[pos.x][pos.y + 2]) && (pos.y + 2 != height - 1)) {
                possibleDirections += DOWN;
            }

            if ((pos.y - 2 >= 0) && (blocked[pos.x][pos.y - 2]) && (pos.y - 2 != height - 1)) {
                possibleDirections += UP;
            }

            if ((pos.x - 2 >= 0) && (blocked[pos.x - 2][pos.y]) && (pos.x - 2 != width - 1)) {
                possibleDirections += LEFT;
            }

            if ((pos.x + 2 < width) && (blocked[pos.x + 2][pos.y]) && (pos.x + 2 != width - 1)) {
                possibleDirections += RIGHT;
            }

            // Check if found any possible movements 
            if (possibleDirections.length() > 0) {

                // Get a random direction 
                switch (possibleDirections.charAt(new java.util.Random().nextInt(possibleDirections.length()))) {

                    case '0': // North 
                        blocked[pos.x][pos.y - 2] = false;
                        blocked[pos.x][pos.y - 1] = false;
                        pos.y -= 2;
                        break;

                    case '1': // South 
                        blocked[pos.x][pos.y + 2] = false;
                        blocked[pos.x][pos.y + 1] = false;
                        pos.y += 2;
                        break;

                    case '2': // West 
                        blocked[pos.x - 2][pos.y] = false;
                        blocked[pos.x - 1][pos.y] = false;
                        pos.x -= 2;
                        break;

                    case '3': // East 
                        blocked[pos.x + 2][pos.y] = false;
                        blocked[pos.x + 1][pos.y] = false;
                        pos.x += 2;
                        break;
                }

                // Add a new possible movement 
                moves.add(pos.y + (pos.x * width));

            } else {

                // There are no more possible movements 
                back = moves.remove(moves.size() - 1);
                pos.x = back / width;
                pos.y = back % width;
            }
        }
        return this.blocked;
    }

    public boolean[][] getBlocked() {
        return blocked;
    }


    public int getStartingX() {
        return startingX;
    }

    public void setStartingX(int startingX) {
        this.startingX = startingX;
    }

    public int getStartingY() {
        return startingY;
    }

    public void setStartingY(int startingY) {
        this.startingY = startingY;
    }

    public int getEndingX() {
        return endingX;
    }

    public void setEndingX(int endingX) {
        this.endingX = endingX;
    }

    public int getEndingY() {
        return endingY;
    }

    public void setEndingY(int endingY) {
        this.endingY = endingY;
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    @Override
    public String toString() {

        String fullMaze = "";

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if (blocked[i][j]) {
                    fullMaze += "*";
                } else {
                    fullMaze += " ";
                }
            }

            fullMaze += "\n";
        }

        return fullMaze;
    }
}