import java.util.Random;

import blocks.Block;
import blocks.BlockI;
import blocks.BlockJ;
import blocks.BlockL;
import blocks.BlockO;
import blocks.BlockS;
import blocks.BlockT;
import blocks.BlockZ;

public class Engine {

private Block nextBlock;
private Block currBlock;
int score;
Grid well;

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

    nextBlock.coordY=0;
    nextBlock.coordX=4;

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

    if(collision) {
        return false;
    }

    return true;
}

    int run()
    {
        while (spawn())
        {









        }
        return score;
    }


}
