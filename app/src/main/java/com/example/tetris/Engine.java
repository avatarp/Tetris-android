package com.example.tetris;

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

    public Block nextBlock;
    public Block currBlock;
    Integer score;
    public Grid well;
    private long waitTime;
    public Boolean isRunning = true;

public Engine(){
    generateNextBlock();
    score=0;
    well=new Grid();
    waitTime = 1000;
}

    public void togglePause() {
        isRunning = isRunning != true;
    }
private void generateNextBlock()
{
    Random rand=new Random();
    int n = rand.nextInt(7);
    Byte color = (byte) rand.nextInt(3);
    color++;
    switch (n)
    {
        case 0:
            nextBlock = new BlockI(color);
            break;
        case 1:
            nextBlock = new BlockJ(color);
            break;
        case 2:
            nextBlock = new BlockL(color);
            break;
        case 3:
            nextBlock = new BlockZ(color);
            break;
        case 4:
            nextBlock = new BlockS(color);
            break;
        case 5:
            nextBlock = new BlockT(color);
            break;
        case 6:
            nextBlock = new BlockO(color);
            break;
    }
    nextBlock.coordY = 0;//top
    nextBlock.coordX = 3;//middle

}

    public boolean isColliding()
{
    for(Integer i=0;i<4;i++){
        for(Integer j =0;j<4;j++) {
            //check for blocks hitting the bottom
            if (currBlock.coordY == 20 && (currBlock.getPosition()[0][0] || currBlock.getPosition()[0][1] || currBlock.getPosition()[0][2] || currBlock.getPosition()[0][3])) {
                return true;
            } else if (currBlock.coordY == 19 && (currBlock.getPosition()[1][0] || currBlock.getPosition()[1][1] || currBlock.getPosition()[1][2] || currBlock.getPosition()[1][3])) {
                return true;
            } else if (currBlock.coordY == 18 && (currBlock.getPosition()[2][0] || currBlock.getPosition()[2][1] || currBlock.getPosition()[2][2] || currBlock.getPosition()[2][3])) {
                return true;
            } else if (currBlock.coordY == 17 && (currBlock.getPosition()[3][0] || currBlock.getPosition()[3][1] || currBlock.getPosition()[3][2] || currBlock.getPosition()[3][3])) {
                return true;
            }


            //check for out of bounds
            if (currBlock.getPosition()[i][j] == true) {
                //outside left side
                if (currBlock.coordX + j < 0) {
                    return true;
                }
                //outside right side
                if (currBlock.coordX + j > 9) {
                    return true;
                }
                //outside bottom
                if (currBlock.coordY + i > 19) {
                    return true;
                }
                //outside top
                if (currBlock.coordY + i < 0) {
                    return true;
                }
            }

            //check the grid for other blocks
            if (currBlock.getPosition()[i][j] && well.getGameGrid()[currBlock.coordY + i][currBlock.coordX + j] > 0)
            { return true; }
        }
    }


 return false;
}


    private boolean spawn()
{
    if(currBlock==null && nextBlock==null)
    {
        generateNextBlock();
        currBlock=nextBlock;
        generateNextBlock();
    }
    else
    {
        currBlock=nextBlock;
        generateNextBlock();
    }


    return !isColliding();

}

    public boolean dropDown()
{
    currBlock.coordY++;
    if(isColliding()){
        currBlock.coordY--;
        return false;
    }

    return true;
}

    public void runLogic(GameActivity gameActivityObj) {
        synchronized (this) {
            Integer totalBlocks = 0;
            Integer newScore = 0;
            while (spawn()) {
                gameActivityObj.triggerUIupdate();
                try {
                    wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (dropDown()) {
                    boolean pauseCaught = false;
                    while (isRunning == false) {
                        try {
                            pauseCaught = true;
                            wait(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    gameActivityObj.triggerUIupdate();
                    try {
                        wait(waitTime);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    while (isRunning == false && pauseCaught == false) {
                        try {
                            wait(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                score++;
                well.addBlock(currBlock);
                totalBlocks++;
                newScore = well.updateGrid();
                score += newScore;
                if (waitTime > 100) {
                    waitTime -= newScore;
                }
                if (waitTime < 100) {
                    waitTime = 100;
                }

            }
            isRunning = false;
            gameActivityObj.triggerUIupdate();
            DatabaseHandler db = new DatabaseHandler(null);

        }
    }

    public void moveLeft() {
    boolean movePossible = true;
    if (currBlock.coordX == 0 && (currBlock.getPosition()[0][0] || currBlock.getPosition()[1][0] || currBlock.getPosition()[2][0] || currBlock.getPosition()[3][0])) {
        movePossible = false;
    } else if (currBlock.coordX == -1 && (currBlock.getPosition()[0][1] || currBlock.getPosition()[1][1] || currBlock.getPosition()[2][1] || currBlock.getPosition()[3][1])) {
        movePossible = false;
    } else if (currBlock.coordX == -2 && (currBlock.getPosition()[0][2] || currBlock.getPosition()[1][2] || currBlock.getPosition()[2][2] || currBlock.getPosition()[3][2])) {
        movePossible = false;
    } else if (currBlock.coordX == -3 && (currBlock.getPosition()[0][3] || currBlock.getPosition()[1][3] || currBlock.getPosition()[2][3] || currBlock.getPosition()[3][3])) {
        movePossible = false;
    }
    if (movePossible) {
        currBlock.coordX--;
    }
}

    public void moveRight() {
    boolean movePossible = true;
    if (currBlock.coordX == 6 && (currBlock.getPosition()[0][3] || currBlock.getPosition()[1][3] || currBlock.getPosition()[2][3] || currBlock.getPosition()[3][3])) {
        movePossible = false;
    } else if (currBlock.coordX == 7 && (currBlock.getPosition()[0][2] || currBlock.getPosition()[1][2] || currBlock.getPosition()[2][2] || currBlock.getPosition()[3][2])) {
        movePossible = false;
    } else if (currBlock.coordX == 8 && (currBlock.getPosition()[0][1] || currBlock.getPosition()[1][1] || currBlock.getPosition()[2][1] || currBlock.getPosition()[3][1])) {
        movePossible = false;
    } else if (currBlock.coordX == 9 && (currBlock.getPosition()[0][0] || currBlock.getPosition()[1][0] || currBlock.getPosition()[2][0] || currBlock.getPosition()[3][0])) {
        movePossible = false;
    }
    if (movePossible) {
        currBlock.coordX++;
    }
}


}