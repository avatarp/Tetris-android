package com.example.tetris.blocks;

public abstract class Block {
    Boolean[][] position;
    Byte color;
    public Byte coordX;
    public Byte coordY;

    public Boolean[][] getPosition() {
        return position;
    }

    public Byte getColor() {
        return color;
    }

    public abstract void arrange();

    Block() {
        position = new Boolean[4][4];
        coordY = 0;
        coordX = 4;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                position[i][j] = false;
            }
        }
    }


    public void rotateLeft() {
        int N = 4;

        for (int x = 0; x < N / 2; x++) {
            // Consider elements in group of 4 in
            // current square
            for (int y = x; y < N - x - 1; y++) {
                // store current cell in temp variable
                Boolean temp = position[x][y];

                // move values from right to top
                position[x][y] = position[y][N - 1 - x];

                // move values from bottom to right
                position[y][N - 1 - x] = position[N - 1 - x][N - 1 - y];

                // move values from left to bottom
                position[N - 1 - x][N - 1 - y] = position[N - 1 - y][x];

                // assign temp to left
                position[N - 1 - y][x] = temp;
            }
        }

    }

    public void rotateRight() {
        int N = 4;
        for (int i = 0; i < N / 2; i++) {
            for (int j = i; j < N - i - 1; j++) {

                // Swap elements of each cycle
                // in clockwise direction
                Boolean temp = position[i][j];
                position[i][j] = position[N - 1 - j][i];
                position[N - 1 - j][i] = position[N - 1 - i][N - 1 - j];
                position[N - 1 - i][N - 1 - j] = position[j][N - 1 - i];
                position[j][N - 1 - i] = temp;
            }
        }

    }

}
