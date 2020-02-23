package com.example.tetris.engine;

import android.content.Intent;
import android.os.Looper;

import com.example.tetris.DatabaseHandler;
import com.example.tetris.GameStats;
import com.example.tetris.activities.GameActivity;
import com.example.tetris.activities.GameOverActivity;
import com.example.tetris.blocks.Block;
import com.example.tetris.blocks.BlockI;
import com.example.tetris.blocks.BlockJ;
import com.example.tetris.blocks.BlockL;
import com.example.tetris.blocks.BlockO;
import com.example.tetris.blocks.BlockS;
import com.example.tetris.blocks.BlockT;
import com.example.tetris.blocks.BlockZ;

import java.util.Random;

public class Engine extends Thread {

    Block nextBlock;
    Block currBlock;
    Grid well;
    private long waitTime;
    public Boolean isRunning = true;

    static final private Byte wellTop=0;
    static final private Byte wellMid=3;

    public GameStats stats;
    private Byte multiplier = 1;

    public Engine(){
        generateNextBlock();
        well=new Grid();
        waitTime = 1000;
        stats=new GameStats();
    }

    public void togglePause() {
        isRunning = !isRunning;
    }

    public int getMultiplier() {
        return multiplier;
    }

    private void countRotationLeft() { stats.incRotLeft(); }

    private void countRotationRight() { stats.incRotRight(); }

    public void moveLeft() {
        currBlock.coordX--;
        if(isColliding()) {
            currBlock.coordX++;
            return;
        }
        stats.incMovesLeft();
    }

    public void moveRight() {
        currBlock.coordX++;
        if(isColliding()) {
            currBlock.coordX--;
            return;
        }
        stats.incMovesRight();
    }

    public void rotateLeft(){
        if (isRunning) {
            currBlock.rotateLeft();
            if (isColliding()) {
                currBlock.rotateRight();
            } else {
                countRotationLeft();
            }
        }
    }

    public void rotateRight(){
        if (isRunning) {
            currBlock.rotateRight();
            if (isColliding()) {
                currBlock.rotateLeft();
            } else {
                countRotationRight();
            }
        }
    }

    private void generateNextBlock() {
        Random rand=new Random();
        Byte color = (byte) ( 1 + rand.nextInt(3));

        switch ( rand.nextInt(7)) {
            case 0: nextBlock = new BlockI(color);  break;
            case 1: nextBlock = new BlockJ(color);  break;
            case 2: nextBlock = new BlockL(color);  break;
            case 3: nextBlock = new BlockZ(color);  break;
            case 4: nextBlock = new BlockS(color);  break;
            case 5: nextBlock = new BlockT(color);  break;
            case 6: nextBlock = new BlockO(color);  break;
        }
        nextBlock.coordY = wellTop;
        nextBlock.coordX = wellMid;
    }

    public boolean dropDown() {
        currBlock.coordY++;
        if(isColliding()){
            currBlock.coordY--;
            return false;
        }
        return true;
    }

    public void dropToBottom() {
        if (isRunning) {
            togglePause();
            while (true) {
                if (dropDown() == false) {
                    break;
                }
            }
            if (!isRunning) {
                togglePause();
            }
        }

    }

    private boolean spawnNextBlock() {
        if(currBlock==null && nextBlock==null) {
            generateNextBlock();
            currBlock=nextBlock;
            generateNextBlock();
        }
        else {
            currBlock=nextBlock;
            generateNextBlock();
        }
        //if new block is colliding with existing blocks its game over.
        return !isColliding();

    }

    private boolean isColliding() {
        for(Integer i=0;i<4;i++){
            for(Integer j =0;j<4;j++) {
                //check for blocks hitting the bottom
                if (currBlock.coordY == 20 && (
                                currBlock.getPosition()[0][0] ||
                                currBlock.getPosition()[0][1] ||
                                currBlock.getPosition()[0][2] ||
                                currBlock.getPosition()[0][3])) {
                    return true;
                } else if (currBlock.coordY == 19 && (
                                currBlock.getPosition()[1][0] ||
                                currBlock.getPosition()[1][1] ||
                                currBlock.getPosition()[1][2] ||
                                currBlock.getPosition()[1][3])) {
                    return true;
                } else if (currBlock.coordY == 18 && (
                                currBlock.getPosition()[2][0] ||
                                currBlock.getPosition()[2][1] ||
                                currBlock.getPosition()[2][2] ||
                                currBlock.getPosition()[2][3])) {
                    return true;
                } else if (currBlock.coordY == 17 && (
                                currBlock.getPosition()[3][0] ||
                                currBlock.getPosition()[3][1] ||
                                currBlock.getPosition()[3][2] ||
                                currBlock.getPosition()[3][3])) {
                    return true;
                }

                //check for out of bounds
                if (currBlock.getPosition()[i][j] == true) {
                    //outside left side
                    if (currBlock.coordX + j < 0) { return true; }
                    //outside right side
                    if (currBlock.coordX + j > 9) { return true; }
                    //outside bottom
                    if (currBlock.coordY + i > 19) { return true; }
                    //outside top
                    if (currBlock.coordY + i < 0) { return true; }
                }

                //check the grid for other blocks
                if (currBlock.getPosition()[i][j] &&
                        well.getGameGrid()[currBlock.coordY + i][currBlock.coordX + j] > 0) {
                    return true; }
            }
        }
     return false;
    }

    public void runLogic(final GameActivity gameActivityObj) {

        synchronized (this) {

            Integer newScore;
            while (spawnNextBlock()) {

                gameActivityObj.triggerUIupdate();
                try { wait(waitTime); }
                catch (InterruptedException e) { e.printStackTrace(); }

                while (dropDown()) {

                    boolean pauseCaught = false;

                    while (isRunning == false) {
                        try { pauseCaught = true; wait(100); }
                        catch (InterruptedException e) { e.printStackTrace(); }
                    }

                    gameActivityObj.triggerUIupdate();
                    try { wait(waitTime); } catch (Exception ex) { ex.printStackTrace();}

                    while (isRunning == false && pauseCaught == false) {
                        try { wait(100); }
                        catch (InterruptedException e) { e.printStackTrace(); }
                    }

                }

                stats.incScore(1);
                well.addBlock(currBlock);
                stats.incBlocks();
                newScore = well.updateGrid();

                if (waitTime > 100) { waitTime -= newScore; //speeding up the game on line clear
                    if (waitTime < 100) { waitTime = 100; }
                }

                stats.incLinesCleared(newScore / 10);

                if (newScore > 0) { newScore *= multiplier; multiplier++; }
                else { if (multiplier > 1) {multiplier--;} }

                stats.incScore(newScore);

            }

            //onGameOver
            isRunning = false;
            gameActivityObj.triggerUIupdate();
            DatabaseHandler databaseHandler = new DatabaseHandler(gameActivityObj);
            Looper.prepare();
            if (stats.getScore() > 10) {databaseHandler.addScore(stats);}

            Intent GameOverIntent = new Intent(gameActivityObj, GameOverActivity.class);
            GameOverIntent.putExtra("newHighScore", stats.getScore());
            gameActivityObj.finish();
            gameActivityObj.startActivity(GameOverIntent);

        }
    }

}