package blocks;

public class BlockO extends Block {
    @Override
    public void arrange()
    {
        position[0][0]=true;
        position[0][1]=true;
        position[1][0]=true;
        position[1][1]=true;
    }

    public BlockO(Byte newColor){
        arrange();
        color=newColor;
    }
}
