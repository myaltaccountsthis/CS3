public class FireModel
{
    public static int SIZE = 47;
    private FireCell[][] myGrid;
    private FireView myView;

    public FireModel(FireView view)
    {
        myGrid = new FireCell[SIZE][SIZE];
        int setNum = 0;
        for (int r=0; r<SIZE; r++)
        {
            for (int c=0; c<SIZE; c++)
            {
                myGrid[r][c] = new FireCell();
            }
        }
        myView = view;
        myView.updateView(myGrid);
    }

    /*
        recursiveFire method here
     */
    private void recursiveFire(int row, int col) {
        for (int i = -1; i < 3; i++) {
            int r = i % 2 + row;
            int c = (i - 1) % 2 + col;
            if (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                FireCell cell = myGrid[r][c];
                if (cell.getStatus() == FireCell.GREEN) {
                    cell.setStatus(FireCell.BURNING);
                    recursiveFire(r, c);
                }
            }
        }
    }

    public void solve()
    {
        // student code here
        for (int i = 0; i < SIZE; i++)
            if (myGrid[SIZE - 1][i].getStatus() == FireCell.GREEN)
                recursiveFire(SIZE - 1, i);

        boolean onFire = false;
        for (int i = 0; i < SIZE; i++)
            if (myGrid[0][i].getStatus() == FireCell.BURNING) {
                onFire = true;
                break;
            }

        if (onFire)
            System.out.println("Onett is in trouble!");
        else
            System.out.println("Onett is safe.");

        myView.updateView(myGrid);
    }

}
