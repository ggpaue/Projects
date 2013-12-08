/*
 * pTicTacToe.java
 *
 * Copyright(c) 2001, Particle Corp.
 */

/*
 * NOTE: This program intentionally uses only JDK 1.0 libs,
 * in order to be compatible with all java enabled browsers.
 * (yes, even Netscape3!)
 */

import java.lang.Thread;
import java.applet.Applet;
import java.awt.*;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;



/**
 * the main game class. represents the board, has
 * alpha-beta proc, etc.
 *
 * @version 0.0.1
 * @author Guanpeng Gao.
 */
class pTicTacToeBoard {

    // some useful statics
    public static final int EMPTY = 0;
    public static final int X = 1;
    public static final int O = 2;
    public static final int DRAW = 3;

    // helper for toString method
    public static char[] to_string = {'#','X','O'};

    // board
    private int[][] board;

    private int best_i;
    private int best_j;

    /**
     * default constructor; allocate mem, and
     * init the board to empty
     */
    public pTicTacToeBoard(){
        board = new int[4][5];
        for(int i=0;i<4;i++)
            for(int j=0;j<5;j++)
                board[i][j] = EMPTY;
    }

    /**
     * copy constructor; allocate mem, and
     * initialization the board to the passed board.
     */
    public pTicTacToeBoard(pTicTacToeBoard b){
        board = new int[4][5];
        for(int i=0;i<4;i++)
            for(int j=0;j<5;j++)
                board[i][j] = b.board[i][j];
    }

    /**
     * set value at a specific board location
     */
    public void setValOnBoard(int row,int col,int val){
        board[row][col] = val;
    }

    /**
     * get value at a specific board location.
     */
    public int getValOnBoard(int row,int col){
        return board[row][col];
    }

    // enable quicker end-game check 
    private static int[][][] wingamechecks = {
        { {0,0},{0,1},{0,2},{0,3} },
        { {0,1},{0,2},{0,3},{0,4} },
        { {1,0},{1,1},{1,2},{1,3} },
        { {1,1},{1,2},{1,3},{1,4} },
        { {2,0},{2,1},{2,2},{2,3} },
        { {2,1},{2,2},{2,3},{2,4} },
        { {3,0},{3,1},{3,2},{3,3} },
        { {3,1},{3,2},{3,3},{3,4} },
        { {0,0},{1,0},{2,0},{3,0} },
        { {0,1},{1,1},{2,1},{3,1} },
        { {0,2},{1,2},{2,2},{3,2} },
        { {0,3},{1,3},{2,3},{3,3} },
        { {0,4},{1,4},{2,4},{3,4} },
        { {0,0},{1,1},{2,2},{3,3} },
        { {0,1},{1,2},{2,3},{3,4} },
        { {3,0},{2,1},{1,2},{0,3} },
        { {3,1},{2,2},{1,3},{0,4} },
    };


    /**
     * see if game is over. returns 0 if not, else returns
     * winner, or DRAW if game ended in draw.
     */
    public int getGameWinner(){
        // check for actual winner
        for(int i = 0;i < 17;i++){
            int a = board[wingamechecks[i][0][0]][wingamechecks[i][0][1]];
            int b = board[wingamechecks[i][1][0]][wingamechecks[i][1][1]];
            int c = board[wingamechecks[i][2][0]][wingamechecks[i][2][1]];
            int d = board[wingamechecks[i][3][0]][wingamechecks[i][3][1]];
            if(a != EMPTY && a == b && b == c && c==d){
                return a;
            }
        }
        // no winner, but maybe a draw
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 5; j++)
                if(board[i][j] == EMPTY)
                    return 0;
        // draw ... (no spaces left for a move)
        return DRAW;
    }
    
    public static int count(int row, int startCol, int endCol, pTicTacToeBoard bo) {
    	int m = 0;
    	for(int i = 0; i < row; i++) {
    		for(int j = startCol; j < endCol; j++) {
    			if(bo.getValOnBoard(i, j) == X){
    				m++;
    			}
    		}
    	}
    	return m;
    }

    /**
     * alpha beta procedure
     */
    public static int alphaBeta(int l,int ply,int alpha,int beta,pTicTacToeBoard board){
        int winner = board.getGameWinner();
        if(winner != 0){
            if(winner == DRAW)
                alpha = 0;
            else
                alpha = winner == l ? 1000:-1000;
//            System.out.println("winner spoted:"+winner+"; alpha:"+alpha+"; depth:"+ply);
        } else {
            board.best_i = -1;
            board.best_j = -1;
            
            int m = count(4, 0, 4,board);
            int n = count(4, 1, 5,board);
            if(m > n) {
            
            	for(int i = 0; i < 4; i++) {
                    for(int j = 0; j < 4; j++) {
                    	if(board.getValOnBoard(i,j) == EMPTY){
                    		
                    		// make move, and recursively evaluate it
                    		pTicTacToeBoard moveboard = new pTicTacToeBoard(board);
                    		moveboard.setValOnBoard(i,j,l);
                    		// our negated beta becomes the other guy's alpha, etc.	
                    		int val = alphaBeta(l == X ? O:X, ply - 1, -beta, -alpha, moveboard);
                    		if(ply == 0) return -alpha;
                    		// make sure that we always have a move...
                    		if(board.best_i == -1) {
                    			board.best_i = i;
                    			board.best_j = j;
                    		}

                    		// is this a better move?
                    		if(val > alpha) {
                    			alpha = val;
                    			board.best_i = i;
                    			board.best_j = j;
                    		}
                    		if(alpha >= beta)
                    			break;  // cutoff
                    	}
                    }
                }
            	
            } else {
            	for(int i = 0; i < 4; i++) {
                    for(int j = 1; j < 5; j++) {
                    	if(board.getValOnBoard(i,j) == EMPTY){
                    		
                    		// make move, and recursively evaluate it
                    		pTicTacToeBoard moveboard = new pTicTacToeBoard(board);
                    		moveboard.setValOnBoard(i,j,l);
                    		// our negated beta becomes the other guy's alpha, etc.	
                    		int val = alphaBeta(l == X ? O:X, ply - 1, -beta, -alpha, moveboard);
                    		if(ply == 0) return -alpha;
                    		// make sure that we always have a move...
                    		if(board.best_i == -1) {
                    			board.best_i = i;
                    			board.best_j = j;
                    		}

                    		// is this a better move?
                    		if(val > alpha) {
                    			alpha = val;
                    			board.best_i = i;
                    			board.best_j = j;
                    		}
                    		if(alpha >= beta)
                    			break;  // cutoff
                    	}
                    }
                }
            }
            // for all children
              
        }
      
        
        return -alpha;
    }

    public int makeMove(int l,int skill){
        int val = alphaBeta(l, skill, -100, 100, this);
        //System.out.println("val: "+val+" ("+best_i+","+best_j+")");
        setValOnBoard(best_i,best_j,l);
        return getGameWinner();
    }

    /**
     * return str representation of the board
     */
    public String toString(){
        StringBuffer s = new StringBuffer();
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++)
                s.append(to_string[board[i][j]]);
            s.append('\n');
        }
        return s.toString();
    }

}

/**
 * Applet helper class. doesn't have any real logic in it,
 * (leaving all the game stuff to the board class).
 * Provides UI for the game.
 *
 * <p>August 15th, 2001 - Fixed image download problem. Images
 * would not be donwloaded until they were being displayed. On
 * a slow connection, there was a noticable delay abetween the
 * time you clicked a mouse, and the time an image would appear.
 * Fix involved invoking a MediaTracker to download the images
 * in a seperate thread.
 *
 * @version 0.0.1
 * @author Alex S.
 */
public class pTicTacToe extends Applet implements Runnable {

    private Image imageX,imageO,imageXWins,imageOWins,imageXODraw;
    private pTicTacToeBoard board;

    // track loading of images
    private MediaTracker tracker;

    private Thread m_thread;

    public int skill;
    
    public void init(){
    	
    	int difficultyLevel = 3;
    	Scanner scanner = new Scanner(System.in); //for user input
        String input;
        
        System.out.println("Choose who plays first: 1 (Human first), 2 (Computer first)");
        System.out.print("Which one: ");
        input = scanner.nextLine(); //read a line
        int whoFirst = Integer.parseInt(input);//convert to int
        System.out.println("Difficulty level: 1 (easiest), 2 (medium), 3 (hardest)");
        System.out.print("difficulty level: ");
        
        input = scanner.nextLine();
        difficultyLevel = Integer.parseInt(input);
        if (difficultyLevel == 1) {
            skill = 3; //maximum search depth for alpha beta pruning algorithm
        } else if (difficultyLevel == 2) {
            skill = 7;
        } else {
            skill = 10;
        }
        
        
        // initialize media tracker
        tracker = new MediaTracker(this);

        setBackground(Color.white);
        imageO = getImage(getCodeBase(), "pTicTacToe_O.gif");
        imageX = getImage(getCodeBase(), "pTicTacToe_X.gif");
        imageXWins = getImage(getCodeBase(),"pTicTacToe_XWins.gif");
        imageOWins = getImage(getCodeBase(),"pTicTacToe_OWins.gif");
        imageXODraw = getImage(getCodeBase(),"pTicTacToe_Draw.gif");
        
        // track image
        tracker.addImage(imageO,0);
        tracker.addImage(imageX,1);
        tracker.addImage(imageXWins,2);
        tracker.addImage(imageOWins,3);
        tracker.addImage(imageXODraw,4);

        board = new pTicTacToeBoard();
/*
        try{
            skill = Integer.parseInt(getParameter("skill"));
        } catch(Throwable e) {
            System.out.println(e.toString());
            skill = 7;
        }
    */    
        
        if (whoFirst == 2) { //computer to move first      
            Random generator = new Random();
            int i = generator.nextInt(4);
            int j = generator.nextInt(5);
            board.setValOnBoard(i, j, 2);
        } 
    }

    /**
     * start image download thread
     */
    public void start(){
        m_thread = new Thread(this);
        m_thread.start();
    }

    public void stop(){
        m_thread = null;
    }

    /**
     * download all images while the player makes a move...
     */
    public void run(){
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        Dimension d = size();
        int xoff = d.width/5;
        int yoff = d.height/4;
        g.drawLine(xoff, 0, xoff, d.height);
        g.drawLine(2*xoff, 0, 2*xoff, d.height);
        g.drawLine(3*xoff, 0, 3*xoff, d.height);
        g.drawLine(4*xoff, 0, 4*xoff, d.height);
        g.drawLine(0, yoff, d.width, yoff);
        g.drawLine(0, 2*yoff, d.width, 2*yoff);
        g.drawLine(0, 3*yoff, d.width, 3*yoff);
        
        for (int r = 0 ; r < 4 ; r++) {
            for (int c = 0 ; c < 5 ; c++) {
                int t = board.getValOnBoard(r,c);
                if (t == pTicTacToeBoard.O){
                    g.drawImage(imageO, c*xoff+1, r*yoff+1, xoff-1, yoff-1, this);
                } else if (t == pTicTacToeBoard.X) {
                    g.drawImage(imageX, c*xoff+1, r*yoff+1, xoff-1, yoff-1, this);
                }
            }
        }
        
        int winner = board.getGameWinner();
        if(winner != 0){
            if(winner == pTicTacToeBoard.DRAW) {
                g.drawImage(imageXODraw,d.width/3, d.height/3,d.width/3, d.height/3, this);
            } else if(winner == pTicTacToeBoard.X) {
                g.drawImage(imageXWins, d.width/3, d.height/3,d.width/3, d.height/3, this);
            } else if(winner == pTicTacToeBoard.O) {
                g.drawImage(imageOWins,d.width/3, d.height/3,d.width/3, d.height/3, this);
            }
        }
    }


    public boolean mouseUp(Event evt, int x, int y) {
    	// if there was a winner...
        if(board.getGameWinner() != 0){
            board = new pTicTacToeBoard();
            repaint();
        } else {
            Dimension d = size();
            // Figure out the row/column
            int c = (x * 5) / d.width;
            int r = (y * 4) / d.height;
            if(board.getValOnBoard (r,c) == pTicTacToeBoard.EMPTY) {
                board.setValOnBoard (r,c,pTicTacToeBoard.X);
                repaint();
                
                int winner = board.getGameWinner();
                // if there was a winner...
                if(winner == 0){
                	System.out.print(skill);
                    board.makeMove(pTicTacToeBoard.O,skill);
                    repaint();
                } 
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int r = 3; //first computer choose to move first, randomly place the first piece on either of the corners of the board
        int difficultyLevel = 3;
        int depth = 14;
        Scanner scanner = new Scanner(System.in); //for user input
        String input;
        
        System.out.println("Choose who plays first: 1 (Human first), 2 (Computer first)");
        System.out.print("Which one: ");
        input = scanner.nextLine(); //read a line
        int whoFirst = Integer.parseInt(input);//convert to int
        if (whoFirst == 2) { //computer to move first      
            Random generator = new Random();
            r = generator.nextInt(2);
        }
        
        
        System.out.println("Difficulty level: 1 (easiest), 2 (medium), 3 (hardest)");
        System.out.print("difficulty level: ");
        input = scanner.nextLine();
        difficultyLevel = Integer.parseInt(input);
        
        if ( difficultyLevel == 1) {
            depth = 3; //maximum search depth for alpha beta prunning algorithm
        } else if (difficultyLevel == 2) {
            depth = 7;
        } else {
            depth = 14;
        }
        
                
    }
}
