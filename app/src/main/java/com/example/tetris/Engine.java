package com.example.tetris;

import android.util.Log;

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
int score;
public Grid well;
long waitTime;
public Byte getPosY(){return currBlock.coordY;}

public Engine(){
    generateNextBlock();
    score=0;
    well=new Grid();
    waitTime=200;
}

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
        case 3:nextBlock=new BlockZ(color);
        case 4:nextBlock=new BlockS(color);
        case 5:nextBlock=new BlockT(color);
        case 6:nextBlock=new BlockO(color);
    }
    nextBlock.coordY=0;
    nextBlock.coordX=4;
}

private boolean isColliding()
{

    int y=currBlock.coordY;
    int x=currBlock.coordX;
    Byte[][] temp = well.getGameGrid();
    Boolean[][] tempPosition=currBlock.getPosition();
    for(Integer i=0;i<4;i++){
        for(Integer j =0;j<4;j++) {
       // Log.d("debug","i:"+i.toString());
         //   Log.d("debug","j:"+j.toString());
            if(tempPosition[i][j] && temp[y + i][x + j]>0)
            { return true; }
            if(y+i>20||y+i<0)
            { return true; }
            if(x+j>14||x+j<0)
            {return true;}
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


public int runLogic()
{
   score++;
   Integer totalBlocks=0;
   synchronized (this){
    while (spawn())
    {
        Log.d("debug","Spawn is true");
        while(dropDown())
        {
            Log.d("debug","dropdown is true");
            Log.d("debug","currposY:"+currBlock.coordY.toString());
            Log.d("debug","currposX:"+currBlock.coordX.toString());

            try {
                wait(waitTime);
            } catch (InterruptedException e) {

            }
        }
        Log.d("debug","add block");
        well.addBlock(currBlock);
        totalBlocks++;
        Log.d("debug","Blocks:"+totalBlocks.toString());
        score+=well.updateGrid();

    }
   }
       return score;
}
//todo collision detection for moving left/right
public void moveLeft(){
    currBlock.coordX--;
}
public void moveRight(){
    currBlock.coordX++;
}


}