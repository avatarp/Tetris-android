package blocks;

public class BlockI extends Block {
    @Override
    public void arrange()
    {
        position[0][0]=true;
        position[0][1]=true;
        position[0][2]=true;
        position[0][3]=true;
    }

    public BlockI(Byte newColor){
        arrange();
        color=newColor;
    }
}
