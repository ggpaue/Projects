/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package con4;

/**
 *
 * @author fengzhu
 */


//store information about the current game configuration
public class BoardConf {
    int numRow = 5;
    int numCol = 4;
    public int[][] boardCurrent; //current board configuration
    boolean isHumanTurn = true; //human ture
    boolean isGameCont = true; //the game is still on
    public static int depth = 13; //maximun depth of search for alpha beta prunning algorithm
    public BoardConf()
    {
        boardCurrent = new int[numRow][numCol];
        for ( int i =0; i < numRow; i++)
        {
            for ( int j=0; j < numCol; j++)
            {
                boardCurrent[i][j] = 0;
            }
        }
            
    }
    
}
