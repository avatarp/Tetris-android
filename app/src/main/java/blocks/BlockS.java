package blocks;

public class BlockS extends Block {
    @Override
    public void arrange()
    {
        position[0][2]=true;
        position[0][1]=true;
        position[1][1]=true;
        position[0][2]=true;
    }

    public BlockS(Byte newColor){
        arrange();
        color=newColor;
    }
}
