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
    long waitTime;

public Engine(){
    generateNextBlock();
    score=0;
    well=new Grid();
    waitTime = 1000;
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
    nextBlock.coordY=0;
    nextBlock.coordX=4;
}

    public boolean isColliding()
{

    int y=currBlock.coordY;
    int x=currBlock.coordX;
    Byte[][] temp = well.getGameGrid();
    Boolean[][] tempPosition=currBlock.getPosition();
    for(Integer i=0;i<4;i++){
        for(Integer j =0;j<4;j++) {

            if (currBlock.coordY == 20 && (currBlock.getPosition()[0][0] || currBlock.getPosition()[0][1] || currBlock.getPosition()[0][2] || currBlock.getPosition()[0][3])) {
                return true;
            } else if (currBlock.coordY == 19 && (currBlock.getPosition()[1][0] || currBlock.getPosition()[1][1] || currBlock.getPosition()[1][2] || currBlock.getPosition()[1][3])) {
                return true;
            } else if (currBlock.coordY == 18 && (currBlock.getPosition()[2][0] || currBlock.getPosition()[2][1] || currBlock.getPosition()[2][2] || currBlock.getPosition()[2][3])) {
                return true;
            } else if (currBlock.coordY == 17 && (currBlock.getPosition()[3][0] || currBlock.getPosition()[3][1] || currBlock.getPosition()[3][2] || currBlock.getPosition()[3][3])) {
                return true;
            }


            if(tempPosition[i][j] && temp[y + i][x + j]>0)
            { return true; }


        }
    }


 return false;
}


    public boolean spawn()
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

    nextBlock.coordY=0; //top
    nextBlock.coordX=4; //middle

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

    /*todo remove or reimplement runLogic()
    public int runLogic()
    {
       score++;
       Integer totalBlocks=0;
       synchronized (this){
        while (spawn())
        {

            while(dropDown())
            {
                try {
                    wait(waitTime);
                } catch (InterruptedException e) { }
            }

            well.addBlock(currBlock);
            totalBlocks++;

            Integer addedScore = well.updateGrid();
            score += addedScore;
            if (waitTime > 100) {
                waitTime -= addedScore;
            }

        }
       }
           return score;
    }
    */
public void moveLeft(){
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
public void moveRight(){
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