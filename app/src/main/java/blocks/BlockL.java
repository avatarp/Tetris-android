package blocks;

public class BlockL extends Block {
    @Override
    public void arrange()
    {
        position[0][0]=true;
        position[0][1]=true;
        position[0][2]=true;
        position[1][2]=true;
    }

    public BlockL(Byte newColor){
        arrange();
        color=newColor;
    }
}
