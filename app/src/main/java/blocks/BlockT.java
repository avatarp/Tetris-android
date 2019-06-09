package blocks;

public class BlockT extends Block{
        @Override
        public void arrange()
        {
                position[0][0]=true;
                position[0][1]=true;
                position[0][2]=true;
                position[1][1]=true;
        }

        public BlockT(Byte newColor){
                arrange();
                color=newColor;
        }

}
