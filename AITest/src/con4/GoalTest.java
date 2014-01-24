/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package con4;

/**
 *
 * @author fengzhu
 */



//test for goal
public class GoalTest 
{
    /**
     * 
     * @param a 5x4 array
     * @return 0 : no one is winning
     *         1 : 1 is winning (human)
     *         2 : 2 is winning (computer)
     *         3 : it is a draw
     */
    public static int isGoal(int[][] a)
    {
        int numRow = a.length;
        int numCol = a[0].length;
        /*
        System.out.print("rows: ");
        System.out.println(numRow);
        System.out.print("column: ");
        System.out.println(numCol);
        */
        
        
        
        
        int initialValue = 0;//winning value
        boolean isWin = false;
        
        //check if the four number are same in a row 
        for ( int i = 0; i < numRow; i++) //for each row
        {
            boolean isFourInRow = true;
            initialValue = a[i][0];  //all the values should be same in this row
            if ( initialValue != 0 ) 
            {
                for ( int j =0; j < numCol; j++ ) //in this row, check whether all these four values are same
                {
                    if ( initialValue != a[i][j] ) //not same, break
                    {
                        isFourInRow = false;
                        break;
                    }
                }
                if ( isFourInRow == true )
                {
                    return initialValue;
                }
            }
        }
        
        //check if 4 same numbers(row index 0 -3) are in a column
        for ( int j = 0; j < numCol; j++ )
        {
            boolean isFourInRow = true;
            initialValue = a[0][j];
            if ( initialValue != 0 ) 
            {
                for ( int i =0; i < 4; i++ ) //in this column, check whether all these four values are same
                {
                    if ( initialValue != a[i][j] ) //not same
                    {
                        isFourInRow = false;
                        break;
                    }
                }
                if ( isFourInRow == true )
                {
                    return initialValue;
                }
            }
        }
        
        
        //check if 4 same numbers(row index 1 -4) are in a column
        for ( int j = 0; j < numCol; j++ )
        {
            boolean isFourInRow = true;
            initialValue = a[1][j];
            if ( initialValue != 0 ) 
            {
                for ( int i =1; i < numRow; i++ ) //in this column, check whether all these four values are same
                {
                    if ( initialValue != a[i][j] ) //not same
                    {
                        isFourInRow = false;
                        break;
                    }
                }
                if ( isFourInRow == true )
                {
                    return initialValue;
                }
            }
        }
        
        
        
        
        //check if 4 same numbers in 45 degree diagonal
        if ( a[0][3] != 0 && a[0][3] == a[1][2] && a[0][3] == a[2][1] && a[0][3] == a[3][0] )
        {
            return a[0][3];
        }
        if (a[1][3] != 0 && a[1][3] == a[2][2] && a[1][3] == a[3][1] && a[1][3] == a[4][0])
        {
            return a[1][3];
        }
        
        
        //check if 4 same numbers are in 135 degree diagonal
        if ( a[0][0] != 0 && a[0][0] == a[1][1] && a[0][0] == a[2][2] && a[0][0] == a[3][3] )
        {
            return a[0][0];
        }
        if ( a[1][0] != 0 && a[1][0] == a[2][1] && a[1][0] == a[3][2] && a[1][0] == a[4][3] )
        {
            return a[1][0];
        }
        
        
        //chek if the game is draw
        boolean isDraw = true;
        search:
        for ( int i = 0; i < numRow; i++ )
        {
            for ( int j = 0; j < numCol; j++ )
            {
                if (a[i][j] == 0) // the game board is not full, not draw
                {
                    isDraw = false;
                    break search;
                }
            }
        }
        
        if ( isDraw )
        {
            return 3;
        }
        
        
        
        return 0; // no wins, no draw
    }
    
    
    
    
    
    
    //print out array for testing only
    public static void printArray( int[][] a)
    {
//        System.out.print("rows: ");
//        System.out.println(a.length);
//        System.out.print("column: ");
//        System.out.println(a[0].length);
        
        
        int numRow = a.length;
        int numCol = a[0].length;
        for ( int i =0; i< numRow; i++)
        {
            for ( int j =0; j < numCol; j++ )
            {
                System.out.print(a[i][j]);
                System.out.print(" ");
                
            }
            System.out.println(" ");
        }
        System.out.println("----------------------");
    }
    
}
