/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package con4;

/**
 *
 * @author fengzhu
 */
public class AI 
{
    final int numCol = 4;
    final int numRow = 5;
    int[][] board;
    private final int POS_INFINITY;
    private final int NEG_INFINITY;
    private int maxDepth; // maximum depth of tree
    private int totalNodes; // total number of nodes generated
    private int totalMaxPruning; //number of times pruning occured within the MaxValue function
    private int totalMinPruning; //number of times pruning occured within the MinValue function
    
    
    
    
    //constructor to initialize board[][] to in order to do alpha beta prunning search
    public AI(int[][]a )
    {
        board = new int[5][4];
        for ( int i = 0; i < numRow; i++)
        {
            for ( int j = 0; j< numCol; j++)
            {
                board[i][j] = a[i][j];
            }
        }
        
        POS_INFINITY = Integer.MAX_VALUE;
        NEG_INFINITY = Integer.MIN_VALUE;
        
    }
    
    //utility
    /**
     * 
     * @param num 1: human win, 2:computer win,  3:draw
     * @return utility negative infinity if human win, 
     *                 positive infinity if computer win, 
     *                 0 if draw
     */
    public static int utility(int num)
    {
        
        if ( num == 1)
        {
            return Integer.MIN_VALUE;
        }
        else if ( num == 2)
        {
            return Integer.MAX_VALUE;
        }
        else // num == 3
        {
            return 0;
        }
    }
    
    
    /**
     * 
     * @param j column number
     * @return true if the column is full, false otherwise
     */
    public boolean isFull(int j )
    {
        if (board[0][j] != 0 )
            return true;
        else
            return false;
    }
    
    
    
    /**
     * 
     * @param j column number, a - matrix
     * @return true if the column is full, false otherwise
     */
    public static boolean isColumnFull(int[][]a, int j )
    {
        if (a[0][j] != 0 )
            return true;
        else
            return false;
    }
    
    /**
     * 
     * @param a matrix
     * @param j column
     * @param player human 1, computer 2
     * @return true if add column in the top successfully. false, otherwise.
     */
    public static boolean addColumnTop( int[][]a, int j, int player)
    {
        if ( !isColumnFull(a,j) )
        {
            for ( int i = 4; i >= 0; i-- )
            {
                if( a[i][j] == 0 )
                {
                    a[i][j] = player;
                    return true;
                }
            }
            return false;
        }
        else
            return false;
    }
    
    /**
     * 
     * @param j remove the top piece from column j
     */
    public void unmove(int j)
    {
        for(int i = 0; i < numRow; i++) //perform search for the 1st non-zero element 
        {
            if(board[i][j] != 0){
                board[i][j] = 0;
                break;
            }
        }
    }
    
    
    /**
     * 
     * @param a matrix
     * @param j column
     * @param player 1 is human, 2 is computer
     * @return true if player can pop out the bottom piece from column j. false otherwise
     */
    public static boolean isColumnBottomRemove(int[][] a, int j, int player)
    { 
        if ( a[4][j] == player )
            return true;
        else
            return false;
    
    }
    
    
    /**
     * 
     * @param a matrix
     * @param j column number, to pop the bottom piece
     */
    public static void columnBottomRemove( int[][] a, int j )
    {
        for ( int i =4; i >= 1; i-- )
        {
            a[i][j] = a[i-1][j];
        }
        a[0][j] = 0; 
    }
    
    
    
    /**
     * 
     * @param a matrix
     * @param j column
     * @param player 1 is human, 2 is computer. add this player's piece to the bottom of column j.
     */
    public static void columnBottomAdd(int[][] a, int j, int player)
    {
        for ( int i = 1; i <= 4; i++)
        {
            a[i-1][j] = a[i][j];
        }
        a[4][j] = player;
    }
    
    
    
    
    //alpha beta pruning algorithm
    /**
     * 
     * @param depth maximum depth to search
     * @return array of size two. a[0] the index for computer to move.
     * a[1] is computer column move to or down: a[1]==0 means add ball from top
     * a[1] == 1 means pop out its own ball from bottom
     */
    public int[] alphaBeta(int depth)
    {
        
        maxDepth = 0; // maximum depth of tree
        totalNodes = 1; // total number of nodes generated
        totalMaxPruning = 0; //number of times pruning occured within the MaxValue function
        totalMinPruning = 0;
        
        
        int alpha = NEG_INFINITY;
        int beta = POS_INFINITY;
        int maxIndex[] = new int[2];
        maxIndex[0] = 0;
        maxIndex[1] = 0;
        
        int v = NEG_INFINITY;
        for( int j =0; j < numCol; j++)
        {
            int level = 0;
            if ( !isFull(j) ) // for the pieces add to the top
            {
                totalNodes++;
                addColumnTop(board,j,2);
                int vMin = MinValue(board,alpha,beta,depth-1,level+1) ;
                if (vMin > v)
                {
                    maxIndex[0] = j;
                    maxIndex[1] = 0;
                    v = vMin;
                }
                /*
                if (v >= beta)
                {
                    return maxIndex;
                }
                */
                alpha = Math.max(alpha,v);
                
                unmove(j);
            }
            
            
            if ( isColumnBottomRemove(board,j,2) ) //for the pieces can be popped from bottom.
            {
                totalNodes++;
                columnBottomRemove(board,j);
                int vMin = MinValue(board,alpha,beta,depth-1,level+1) ;
                if (vMin > v)
                {
                    maxIndex[0] = j;
                    maxIndex[1] = 1;
                    v = vMin;
                }
                /*
                if (v >= beta)
                {
                    return maxIndex;
                }
                */
                alpha = Math.max(alpha,v);
                
                columnBottomAdd(board,j,2);
            }
            
            
        }
        
        System.out.println("--------------------------------");
        System.out.print("Maximum depth of tree: ");
        System.out.println(maxDepth);
        System.out.print("total # of nodes generated: ");
        System.out.println(totalNodes);
        System.out.print("# of prunning MAX-VALUE: ");
        System.out.println(totalMaxPruning);
        System.out.print("# of prunning MIN-VALUE: ");
        System.out.println(totalMinPruning);
        
        
        return maxIndex;
    }
    
   
    //MAX-VALUE Function
    public int MaxValue(int[][] state,int alpha, int beta, int depth,int level)
    {
        int goal = GoalTest.isGoal(state);
        
        if ( goal== 1 || goal == 2 || goal == 3)
        {
//            System.out.print("Level: ");
//            System.out.println(level);
//            System.out.print("utility: ");
//            System.out.println(utility(goal));
//            GoalTest.printArray(state);
            if ( level > maxDepth )
            {
                maxDepth = level;
            }
            return utility(goal);
        }
        
        if ( depth == 0)
        {
            maxDepth = BoardConf.depth;
            return myHuristic(state,2);
        }
        
        int v = NEG_INFINITY;
        
        for( int j =0; j < numCol; j++)
        {
            if ( !isFull(j) )
            {
                totalNodes++;
                addColumnTop(board,j,2);
                
                //GoalTest.printArray(state);
                
                v = Math.max(v, MinValue(board,alpha,beta,depth-1,level+1)) ;
                
                
                
                
                if (v >= beta)
                {
                    totalMaxPruning++;
                    unmove(j);
                    return v;
                }
                
                alpha = Math.max(alpha,v);
                
                unmove(j);
            }
            
            if ( isColumnBottomRemove(board,j,2) )
            {
                totalNodes++;
                columnBottomRemove(board,j);
                v = Math.max(v, MinValue(board,alpha,beta,depth-1,level+1)) ;
                
                if (v >= beta)
                {
                    totalMaxPruning++;
                    columnBottomAdd(board,j,2);
                    return v;
                }
                
                alpha = Math.max(alpha,v);
                
                columnBottomAdd(board,j,2);
            }
            
            
        }
        return v;
        
        
    }
    
    
    //MIN-VALUE Function
    public int MinValue(int[][] state,int alpha, int beta, int depth, int level)
    {
        
        int goal = GoalTest.isGoal(state);
        
        if ( goal== 1 || goal == 2 || goal == 3)
        {
//            System.out.print("Level: ");
//            System.out.println(level);
//            System.out.print("utility: ");
//            System.out.println(utility(goal));
//            GoalTest.printArray(state);
            if ( level > maxDepth )
            {
                maxDepth = level;
            }
            return utility(goal);
        }
        
        if ( depth == 0)
        {
            maxDepth = BoardConf.depth;
            return myHuristic(state,1);
        }
        
        int v = POS_INFINITY;
        
        for( int j =0; j < numCol; j++)
        {
            if ( !isFull(j) )
            {
                totalNodes++;
                addColumnTop(board,j,1);
                
                
                //GoalTest.printArray(state);
                v = Math.min(v, MaxValue(board,alpha,beta,depth-1,level+1)) ;
                
                if (v <= alpha)
                {
                    totalMinPruning++;
                    unmove(j);
                    return v;
                }
                
                beta = Math.min(beta,v);
                
                unmove(j);
            }
            
            if ( isColumnBottomRemove(board,j,1) )
            {
                totalNodes++;
                columnBottomRemove(board,j);
                v = Math.min(v, MaxValue(board,alpha,beta,depth-1,level+1)) ;
                
                if (v <= alpha)
                {
                    totalMinPruning++;
                    columnBottomAdd(board,j,1);
                    return v;
                }
                
                beta = Math.min(beta,v);
                
                columnBottomAdd(board,j,1);
            }
        }
        return v;
    }
    
    
    
    
    //huristic function to estimate the score
    /**
     * 
     * @param a matrix
     * @param player 1 is human, 2 is computer
     * @return estimated score. 10 points for any two pieces in a row/column/diagonal (no opponent's pieces)
     * 30 points for any three pieces in a row
     */
    public static int myHuristic(int a[][], int player)
    {
        
        int twoScore = 10;
        int threeScore = 30;
        
        int opponent;
        if ( player == 1)
        {
            opponent = 2;
        }
        else //player ==2
        {
            opponent = 1;
        }
        
        //check two or three pieces in a row;
        int num = 0;
        int score = 0;
        boolean isScore = true;
        for (int i = 0; i < 5; i++) //loop through rows
        {
            isScore = true;
            num = 0; //number of pieces in a row
            for ( int j = 0; j < 4; j++) //loop through columns
            {
                if (a[i][j] == player) //check number of pieces of row
                {
                    num++;
                }
                else if (a[i][j] == opponent) //if opponent has been in the row, this row has no score, because no way to win
                {
                    isScore = false;
                    break;
                }
            }
            
            if (isScore) //calculate the score
            {
                if ( num == 2) // two pieces in a row
                {
                    score += twoScore;
                }
                else if (num == 3) // 3 pieces in a rwo
                {
                    score += threeScore;
                }
                
            }
            
        }
        
        //System.out.print("row score: ");
        //System.out.println(score);
        //check two or three pieces in a column
        for ( int j = 0; j < 4; j++ ) //loop through column
        {
            isScore = true;
            num = 0;
            for ( int i = 4; i >= 1; i--) //loop through rows
            {
                if ( i != 4 && a[i][j] == opponent) //opponent is occured (not in the bottom), no way to win, go to next column
                {
                    isScore = false;
                    break;
                }
                
                if (a[i][j] == player ) //check for number of pieces in a row
                {
                    num++;
                }
            }
            
            if (isScore) // calculate the score
            {
                if (a[0][j] != opponent )
                {
                    if ( num == 2) // two pieces in a row
                    {
                        score += twoScore;
                    }
                    else if (num == 3) // 3 pieces in a rwo
                    {
                        score += threeScore;
                    }
                }
            }
            
        }
                
        //System.out.print("row score + column: ");
        //System.out.println(score);
        
        //
        //check two or three pieces in diagonal
        //store the 4 diagonals into a 4x4 array, then check two or three pieces in a row (same as the row check)
        int[][] tem = {{a[0][0],a[1][1],a[2][2],a[3][3]},
                       {a[1][0],a[2][1],a[3][2],a[4][3]},
                       {a[3][0],a[2][1],a[1][2],a[0][3]},
                       {a[4][0],a[3][1],a[2][2],a[1][3]}
        };
        for (int i = 0; i < 4; i++)
        {
            isScore = true;
            num = 0; //number of pieces in a row
            for ( int j = 0; j < 4; j++)
            {
                if (tem[i][j] == player)
                {
                    num++;
                }
                else if (tem[i][j] == opponent)
                {
                    isScore = false;
                    break;
                }
            }
            
            if (isScore)
            {
                if ( num == 2) // two pieces in a row
                {
                    score += twoScore;
                }
                else if (num == 3) // 3 pieces in a rwo
                {
                    score += threeScore;
                }
                
            }
            
        }
        
        return score;
    }
    
}
