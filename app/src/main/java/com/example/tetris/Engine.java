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

public class Engine {

private Block nextBlock;
private Block currBlock;
int score;
Grid well;
long waitTime=2000;
boolean steeringTime=true;


private void generateNextBlock()
{
    Random rand=new Random();
    int n = rand.nextInt(7);
    Byte color = (byte) rand.nextInt(4);
    switch (n)
    {
        case 0:nextBlock=new BlockI(color);
        case 1:nextBlock=new BlockJ(color);
        case 2:nextBlock=new BlockL(color);
        case 3:nextBlock=new BlockO(color);
        case 4:nextBlock=new BlockS(color);
        case 5:nextBlock=new BlockT(color);
        case 6:nextBlock=new BlockZ(color);
    }
}

private boolean isColliding()
{
    boolean collision=false;

    int y=nextBlock.coordY;
    int x=nextBlock.coordX;
    for(int i=0;i<4;i++){
        for(int j =0;j<4;j++) {
            if(nextBlock.getPosition()[i][j] &&  well.getGameGrid()[y + i][x + j]>0)
            {
                collision=true;
            }
        }
    }

 return collision;
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

    nextBlock.coordY=0; //top
    nextBlock.coordX=4; //middle

    return isColliding();

}

private boolean dropDown()
{
    currBlock.coordY--;
    if(isColliding()){
        currBlock.coordY++;
        return false;
    }

    return true;
}


public int run()
{
    while (spawn())
    {
        while(dropDown())
        {


        }
        well.addBlock(currBlock);
        score+=well.updateGrid();

    }
       return score;
}


}