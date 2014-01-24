/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package con4;

/**
 *
 * @author fengzhu
 */


//human  -- black balls 1
//computer -- red balls  2



import java.util.Scanner;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


//for GUI
class GUI //implements ActionListener, ItemListener 
{
	// create objects
	public static BoardConf board;
	static JFrame frameMainWindow;
	static JFrame frameWin;
	
	static JPanel panelBoardNumbers;
        static JPanel panelBoardNumbersB;
	static JLayeredPane layeredGameBoard;
        
        static ImageIcon redIcon = new ImageIcon("images/Red.gif");
	
        static ImageIcon blankIcon = new ImageIcon("images/Blank.gif");
	
        static ImageIcon blackIcon = new ImageIcon("images/Black.gif");
	static JLabel blackIconLabel = new JLabel(blackIcon);
	
	
        
        //constructor
        /**
         * 
         * @param d maximum depth of search
         * @param r r == 0 or 1, means computer move first. others, human move first.
         */
        public GUI(int d, int r)
        {
            board = new BoardConf();
            board.depth = d;
            if ( r == 0)
            {
                board.boardCurrent[4][0] = 2;
            }
            else if ( r == 1)
            {
                board.boardCurrent[4][3] = 2;
            }
            
        }
        
        //create the game board
	public static JLayeredPane createLayeredBoard() {
		layeredGameBoard = new JLayeredPane();
		layeredGameBoard.setPreferredSize(new Dimension(345, 420));
		layeredGameBoard.setBorder(BorderFactory.createTitledBorder("Connect 4"));

		ImageIcon imageBoard = new ImageIcon("images/Board.gif");
                //System.out.println("load borad image");
                //System.out.println(imageBoard.getIconWidth());
		JLabel imageBoardLabel = new JLabel(imageBoard);

		imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
		layeredGameBoard.add(imageBoardLabel, new Integer (0), 1);

		return layeredGameBoard;
	}
        
        //start a new game
	public static void createNewGame() {
		
                
		if (frameMainWindow != null) frameMainWindow.dispose();
		frameMainWindow = new JFrame("Connect Four- human black 1, c r 2");
		frameMainWindow.setBounds(100, 100, 400, 300);
		
		Component compMainWindowContents = createContentComponents();
		frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

		frameMainWindow.addWindowListener(new WindowAdapter() {
            @Override
			public void windowClosing(WindowEvent e) {
			System.exit(0);
			}
		});
              
		frameMainWindow.pack();
		frameMainWindow.setVisible(true);
                redrawBoard(board.boardCurrent);
                
	}
        
        //paint a red piece in (row,column)
	public static void paintRed(int row, int col) {
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		JLabel redIconLabel = new JLabel(redIcon);
		redIconLabel.setBounds(27 + xOffset, 27 + yOffset, redIcon.getIconWidth(),redIcon.getIconHeight());
		layeredGameBoard.add(redIconLabel, new Integer(0), 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}
        
        //paint a blank piece in (row,column)
        public static void paintBlank(int row, int col) {
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		JLabel blankIconLabel = new JLabel(blankIcon);
		blankIconLabel.setBounds(27 + xOffset, 27 + yOffset, redIcon.getIconWidth(),redIcon.getIconHeight());
		layeredGameBoard.add(blankIconLabel, new Integer(0), 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}
        
        //paint a black piece in (row,column)
	public static void paintBlack(int row, int col) {
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		JLabel blackIconLabel = new JLabel(blackIcon);
		blackIconLabel.setBounds(27 + xOffset, 27 + yOffset, blackIcon.getIconWidth(),blackIcon.getIconHeight());
		layeredGameBoard.add(blackIconLabel, new Integer(0), 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}
        
        
        //to redraw the game board using current game configuration
	public static void redrawBoard(int[][] a) 
        {
            int numRow = a.length;
            int numCol = a[0].length;
            
            for ( int i = 0; i < numRow; i++ )
            {
                for ( int j = 0; j < numCol; j++ )
                {
                    if ( a[i][j] == 0 )
                    {
                        paintBlank(i,j);
                    }
                    else if ( a[i][j] == 1)
                    {
                        paintBlack(i,j);
                    }
                    else // == 2
                    {
                        paintRed(i,j);
                    }
                }
            }

	}

	/**
	 * @return Component - Returns a component to be drawn by main window
	 * @see main()
	 * This function creates the main window components.
	 */
        
        
        //to show the game is over, disallow any further movement of pieces
        public static void showGameOver(int goal, BoardConf board)
        {
            if ( goal == 1 )
            {
                System.out.println("1 a goal: black human win");
                board.isGameCont = false;
                //System.exit(1);


            }
            else if (goal == 2 )
            {
                System.out.println("2 a goal: computer red win");
                board.isGameCont = false;
                //System.exit(1);

            }
            else if (goal == 3 )
            {
                System.out.println("3 a goal: draw");
                board.isGameCont = false;
                //System.exit(1);

            }
        }
        
        
        
        //create buttons for dropping a piece from top and popping out a piece from bottom
	public static Component createContentComponents() {

		// create panels to hold and organize board numbers
		panelBoardNumbers = new JPanel();
		panelBoardNumbers.setLayout(new GridLayout(1, 7, 4, 4));
		JButton buttonCol0 = new JButton("0");
		buttonCol0.addActionListener(new ActionListener() { //event listener for the top first botton
            @Override
			public void actionPerformed(ActionEvent e) {
				if ( board.isHumanTurn && board.isGameCont) // check if is human turn and the game is still on
                                {
                                    int goal = GoalTest.isGoal(GUI.board.boardCurrent); // perform goal test
                                    showGameOver(goal,board); //show the result
                                    if( goal == 0) //human can move
                                    {
                                    
                                        if ( !AI.isColumnFull(board.boardCurrent, 0)) // check whether it is allowed to drop a piece from top
                                        {
                                            AI.addColumnTop(board.boardCurrent, 0, 1); //dorp a piece
                                            
                                            redrawBoard(board.boardCurrent); //redraw the board
                                            board.isHumanTurn = false;
                                        }
                                        computerMove(); //running alpha beta prunning algorithm for computer move.
                                        goal = GoalTest.isGoal(GUI.board.boardCurrent);
                                        showGameOver(goal,board);
                                    }
                                }
			}
		});
		JButton buttonCol1 = new JButton("1");
		buttonCol1.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				if ( board.isHumanTurn && board.isGameCont)
                                {
                                    int goal = GoalTest.isGoal(GUI.board.boardCurrent);
                
                                    showGameOver(goal,board);
                                    if( goal == 0) //human can move
                                    {
                                    
                                        if ( !AI.isColumnFull(board.boardCurrent, 1))
                                        {
                                            AI.addColumnTop(board.boardCurrent, 1, 1);
                                            redrawBoard(board.boardCurrent);
                                            board.isHumanTurn = false;
                                            computerMove();
                                            goal = GoalTest.isGoal(GUI.board.boardCurrent);
                
                                            showGameOver(goal,board);
                                        }
                                    }
                                }
			}
		});
		JButton buttonCol2 = new JButton("2");
		buttonCol2.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				if ( board.isHumanTurn && board.isGameCont)
                                {
                                    int goal = GoalTest.isGoal(GUI.board.boardCurrent);
                                    showGameOver(goal,board);
                                    if( goal == 0) //human can move
                                    {
                                        if ( !AI.isColumnFull(board.boardCurrent, 2))
                                        {
                                            AI.addColumnTop(board.boardCurrent, 2, 1);
                                            redrawBoard(board.boardCurrent);
                                            board.isHumanTurn = false;
                                            computerMove();
                                            goal = GoalTest.isGoal(GUI.board.boardCurrent);
                                            showGameOver(goal,board);
                                        }
                                    }
                                }
			}
		});
		JButton buttonCol3 = new JButton("3");
		buttonCol3.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				if ( board.isHumanTurn && board.isGameCont)
                                {
                                    int goal = GoalTest.isGoal(GUI.board.boardCurrent);
                                    showGameOver(goal,board);
                                    if( goal == 0) //human can move
                                    {
                                        if ( !AI.isColumnFull(board.boardCurrent, 3))
                                        {
                                            AI.addColumnTop(board.boardCurrent, 3, 1);
                                            redrawBoard(board.boardCurrent);
                                            board.isHumanTurn = false;
                                            computerMove();
                                            goal = GoalTest.isGoal(GUI.board.boardCurrent);
                                            showGameOver(goal,board);
                                        }
                                    }
                                }
			}
		});
		
		
		panelBoardNumbers.add(buttonCol0);
		panelBoardNumbers.add(buttonCol1);
		panelBoardNumbers.add(buttonCol2);
		panelBoardNumbers.add(buttonCol3);
		
		
                
                
                
                
                
                // create bottom panels to hold and organize board numbers
		panelBoardNumbersB = new JPanel();
		panelBoardNumbersB.setLayout(new GridLayout(1, 7, 4, 4));
		JButton buttonCol0B = new JButton("0");
		buttonCol0B.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                             if ( board.isHumanTurn && board.isGameCont)
                             {
                                int goal = GoalTest.isGoal(GUI.board.boardCurrent);
                                showGameOver(goal,board);
                                if ( goal == 0)
                                {
                                    if ( AI.isColumnBottomRemove(board.boardCurrent, 0, 1) )
                                    {
                                        AI.columnBottomRemove(board.boardCurrent, 0);
                                        redrawBoard(board.boardCurrent);
                                        board.isHumanTurn = false;
                                        computerMove();
                                        goal = GoalTest.isGoal(GUI.board.boardCurrent);
                                        showGameOver(goal,board);
                                    }
                                }
                             }
			}
		});
                
		JButton buttonCol1B = new JButton("1");
		buttonCol1B.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
			if ( board.isHumanTurn && board.isGameCont)
                         {
                            int goal = GoalTest.isGoal(GUI.board.boardCurrent);
                            showGameOver(goal,board);
                            if ( goal == 0)
                            {
                                if ( AI.isColumnBottomRemove(board.boardCurrent, 1, 1) )
                                {
                                    AI.columnBottomRemove(board.boardCurrent, 1);
                                    redrawBoard(board.boardCurrent);
                                    board.isHumanTurn = false;
                                    computerMove();
                                    goal = GoalTest.isGoal(GUI.board.boardCurrent);
                                    showGameOver(goal,board);
                                }
                            }
                         }
			}
		});
		JButton buttonCol2B = new JButton("2");
		buttonCol2B.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
			if ( board.isHumanTurn && board.isGameCont)
                         {
                            int goal = GoalTest.isGoal(GUI.board.boardCurrent);
                            showGameOver(goal,board);
                            if ( goal == 0)
                            {
				if ( AI.isColumnBottomRemove(board.boardCurrent, 2, 1) )
                                {
                                    AI.columnBottomRemove(board.boardCurrent, 2);
                                    redrawBoard(board.boardCurrent);
                                    board.isHumanTurn = false;
                                    computerMove();
                                    goal = GoalTest.isGoal(GUI.board.boardCurrent);
                                    showGameOver(goal,board);
                                }
                            }
                         }
			}
		});
		JButton buttonCol3B = new JButton("3");
		buttonCol3B.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
			if ( board.isHumanTurn && board.isGameCont)
                         {
                            int goal = GoalTest.isGoal(GUI.board.boardCurrent);
                            showGameOver(goal,board);
                            if ( goal == 0)
                            {
				if ( AI.isColumnBottomRemove(board.boardCurrent, 3, 1) )
                                {
                                    AI.columnBottomRemove(board.boardCurrent, 3);
                                    redrawBoard(board.boardCurrent);
                                    board.isHumanTurn = false;
                                    computerMove();
                                    goal = GoalTest.isGoal(GUI.board.boardCurrent);
                                    showGameOver(goal,board);
                                }
                            }
                         }
			}
		});
		
		
		panelBoardNumbersB.add(buttonCol0B);
		panelBoardNumbersB.add(buttonCol1B);
		panelBoardNumbersB.add(buttonCol2B);
		panelBoardNumbersB.add(buttonCol3B);
                
                
                
                
                

		// create game board with pieces
		layeredGameBoard = createLayeredBoard();

		// create panel to hold all of above
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder( BorderFactory.createEmptyBorder(5, 5, 5, 5) );
		//panelMain.setLayout(new GridLayout(1, 2, 4, 4));

		// add objects to pane
		panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
                panelMain.add(panelBoardNumbersB, BorderLayout.SOUTH);
		panelMain.add(layeredGameBoard, BorderLayout.CENTER);
                
                
		return panelMain;
	}
        
        
        
        
        //perform alpha beta search for computer to choose the next best move
        public static void computerMove()
        {
            
            if (board.isGameCont) // check if the game is still on
            {
                int goal = GoalTest.isGoal(board.boardCurrent); // goal test

                if ( goal == 1 )
                {
                    System.out.println("1 a goal: black human win");
                    //GUI.board.isGameCont = false;
                    board.isGameCont = false;
                    board.isHumanTurn = false;
                    

                }
                else if (goal == 2 )
                {
                    System.out.println("2 a goal: computer red win");
                    //GUI.board.isGameCont = false;
                    board.isGameCont = false;
                    board.isHumanTurn = false;
                    
                }
                else if (goal == 3 )
                {
                    System.out.println("3 a goal: draw");
                    //GUI.board.isGameCont = false;
                    board.isGameCont = false;
                    board.isHumanTurn = false;
                    
                }
                else // no one wins
                {
                    AI ai = new AI(GUI.board.boardCurrent); // initialize the board
                    int[] column = ai.alphaBeta(board.depth); //alpha beta search
                    
                    if ( column[1] == 0 ) //add a ball from top
                    {
                        AI.addColumnTop(GUI.board.boardCurrent, column[0],2 );
                    }
                    else //pop out a ball from bottom
                    {
                        AI.columnBottomRemove(GUI.board.boardCurrent, column[0]);
                    }
                    
                    GUI.redrawBoard(GUI.board.boardCurrent);

                    GUI.board.isHumanTurn = true;
                }
                //break;
            }
        }
        
        
        
       /*
	public static void gameOver() {
            
                //System.out.println(board.movelist);
                
                panelBoardNumbers.setVisible(false);
		frameWin = new JFrame("You Win!");
		frameWin.setBounds(300, 300, 220, 120);
		JPanel winPanel = new JPanel(new BorderLayout());
		winPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

		//ImageIcon winIcon = new ImageIcon("images/win.gif");
		//JLabel winLabel = new JLabel(winIcon);
		JLabel winLabel;
		if (board.winner == 1) {
			winLabel = new JLabel("Player 1 wins!");
			winPanel.add(winLabel);
		} else if (board.winner == 2) {
			winLabel = new JLabel("Player 2 wins!");
			winPanel.add(winLabel);
		} else {
			winLabel = new JLabel("Nobody Win! - You both loose!");
			winPanel.add(winLabel);
		}
		winPanel.add(winLabel, BorderLayout.NORTH);
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameWin.setVisible(false);
			}
		});
		winPanel.add(okButton, BorderLayout.SOUTH);
		frameWin.getContentPane().add(winPanel, BorderLayout.CENTER);
		frameWin.setVisible(true);
	}
*/	
}


public class Con4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int r = 3; //first computer choose to move first, randomly place the first piece on either of the corners of the board
        int difficultyLevel = 3;
        int depth = 14;
        Scanner scanner = new Scanner( System.in ); //for user input
        String input;
        
        System.out.println("Choose who plays first: 1 (Human first), 2 (computer first)");
        System.out.print("Which one: ");
        input = scanner.nextLine(); //read a line
        int whoFirst = Integer.parseInt(input);//convert to int
        if (whoFirst == 2) //computer to move first
        {
            Random generator = new Random();
            r = generator.nextInt(2);
        }
        
        
        System.out.println("Difficulty level: 1 (easiest), 2, 3 (hardest)");
        System.out.print("difficulty level: ");
        input = scanner.nextLine();
        difficultyLevel = Integer.parseInt(input);
        
        if ( difficultyLevel == 1)
        {
            depth = 3; //maximum search depth for alpha beta prunning algorithm
        }
        else if (difficultyLevel == 2)
        {
            depth = 7;
        }
        else
        {
            depth = 14;
        }
        
        final GUI gui = new GUI(depth,r); //initialize the GUI object
        //gui.createNewGame();
        
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                gui.createNewGame();
            }
        }
                );
    }
}
