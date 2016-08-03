import java.awt.*;
import java.awt.event.*;
import java.io.Console;
import java.util.*;

import javax.swing.*;


public class BattleShips extends JFrame implements ActionListener {
	static ArrayList<Point> targets1 = new ArrayList<Point>();
	static ArrayList<Point> targets2 = new ArrayList<Point>();
	static ArrayList<Point> AttemptedShots1 = new ArrayList<Point>();
	static ArrayList<Point> AttemptedShots2 = new ArrayList<Point>();

	JLabel row1 = new JLabel();
	JLabel row2 = new JLabel();
	JLabel notes = new JLabel(" ");
	JLabel explanation = new JLabel("Voer hier je schiet coordinaten in (x , y)");
	JButton start = new JButton("Start");
	JButton dispFriendlyMap = new JButton("Display your map");
	JButton dispShotsMap = new JButton("Display opposing players map");
	JButton clearScreen = new JButton("Clear Console");
	JTextField fireLocationx = new JTextField(2);
	JTextField fireLocationy = new JTextField(2);
	JButton nextTurn = new JButton("Next Turn");
	
	public BattleShips() {
		super("Player 1");
		setSize(250,250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//ActionListeners
		start.addActionListener(this);
		fireLocationx.addActionListener(this);
		fireLocationy.addActionListener(this);
		clearScreen.addActionListener(this);
		dispShotsMap.addActionListener(this);
		dispFriendlyMap.addActionListener(this);
		
		nextTurn.addActionListener(this);
		
		//Layouts
		FlowLayout flow = new FlowLayout();
		BorderLayout border = new BorderLayout();
		setLayout(border);
		
		//setting up frame
		add(notes,BorderLayout.NORTH);
		
		row1.add(fireLocationx);
		row1.add(fireLocationy);
		row1.add(nextTurn);
		row1.add(start);
		row1.add(explanation);
		row1.setLayout(flow);
		row1.add(dispFriendlyMap);
		row1.add(dispShotsMap);
		row1.add(clearScreen);
		add(row1,BorderLayout.CENTER);
		
		
		//add(fireLocation,BorderLayout.SOUTH);
		//add(nextTurn,BorderLayout.SOUTH);
		setVisible(true);
		if (overTurn == 0) {
			nextTurn.setEnabled(false);
			dispFriendlyMap.setEnabled(false);
			dispShotsMap.setEnabled(false);
		}
	}
	int myRandomx;
	int myRandomy;
	
	public void CreateTargets2() {
		for (int x = 0; x<3 ;x++){
		do {
			double randomx = Math.random()*10;
			myRandomx = (int) randomx;
			} while (myRandomx == 10 || myRandomx == 0);
		do {
			double randomy = Math.random()*10;
			myRandomy = (int) randomy;
			} while (myRandomy==10 || myRandomx == 0);
		Point target = new Point(myRandomx,myRandomy);
		targets2.add(x, target);
		}
		System.out.println("Player 2 targets");
		for ( Point targets : targets2) {
			System.out.println(targets);
		}
	}
	public void CreateTargets1() {
		for (int x = 0; x<3 ;x++){
			do {
				double randomx = Math.random()*10;
				myRandomx = (int) randomx;
				} while (myRandomx == 10 || myRandomx ==0);
			do {
				double randomy = Math.random()*10;
				myRandomy = (int) randomy;
				} while (myRandomy==10 || myRandomy == 0);
		Point target = new Point(myRandomx,myRandomy);
		targets1.add(x, target);
	
		}
		System.out.println("Player 1 targets:");
		for ( Point targets : targets1) {
			System.out.println(targets);
		}
	}
	public void FriendlyMapPlayer1() {
		int player2Win = 0;
		System.out.println("\n*  1  2  3  4  5  6  7  8  9");
		for( int column = 1 ; column < 10 ; column++) {
			for( int row = 1 ; row < 10 ; row++) {
				if (row == 1) {
					System.out.print(column + " ");
				}
				System.out.print(" ");
				Point cell = new Point(row, column);
				//display the shots attempted by player 2 at player 1
				if (AttemptedShots2.indexOf(cell) > -1) {
						System.out.print("O");
				} else {
					if (targets1.indexOf(cell) > -1) {
						System.out.print("X");
						player2Win++;
					} else {
						System.out.print(".");
					} 
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
		if (player2Win == 0) {
			nextTurn.setEnabled(false);
			dispFriendlyMap.setEnabled(false);
			dispShotsMap.setEnabled(false);
			System.out.println("Player 2 has won!!!");
		}
	}
	public void FriendlyMapPlayer2() {
		int player1Win=0;
		//Still need to add the attempted shots markers
		System.out.println("\n*  1  2  3  4  5  6  7  8  9");
		for( int column = 1 ; column < 10 ; column++) {
			for( int row = 1 ; row < 10 ; row++) {
				if (row == 1) {
					System.out.print(column + " ");
				}
				System.out.print(" ");
				Point cell = new Point(row, column);
				if(AttemptedShots1.indexOf(cell) > -1){
					System.out.print("O");
				}else{
				if (targets2.indexOf(cell) > -1) {
					System.out.print("X");
					player1Win++;
				} else {
					System.out.print(".");
				}
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
		if (player1Win == 0) {
			nextTurn.setEnabled(false);
			dispFriendlyMap.setEnabled(false);
			dispShotsMap.setEnabled(false);
			System.out.println("Player 1 has won!!!");
		}
	}
	int playerTurn = 0;
	int overTurn = 0;
	public void NextTurn() {
		if (playerTurn==0) {
			System.out.println("It is player 1's turn");
			System.out.println("This is what your fleet looks like");
			FriendlyMapPlayer1();
			playerTurn=1;
		} else {System.out.println("It is player 2's turn"); 
		playerTurn=0;
		System.out.println("This is what your fleet looks like");
		FriendlyMapPlayer2();
		}
	}
	int xShot;
	int yShot;
	int a =0;
	public void  OppMap1() { //the map with the attempted shots of player 1
		System.out.println("\n*  1  2  3  4  5  6  7  8  9");
		for( int column = 1 ; column < 10 ; column++) {
			for( int row = 1 ; row < 10 ; row++) {
				if (row == 1) {
					System.out.print(column + " ");
				}
				System.out.print(" ");
				Point cell = new Point(row, column);
				if (AttemptedShots1.indexOf(cell) > -1) {
					System.out.print("O");
				} else {
					System.out.print(".");
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void  OppMap2() { //the map with the attempted shots of player 1
		System.out.println("\n*  1  2  3  4  5  6  7  8  9");
		for( int column = 1 ; column < 10 ; column++) {
			for( int row = 1 ; row < 10 ; row++) {
				if (row == 1) {
					System.out.print(column + " ");
				}
				System.out.print(" ");
				Point cell = new Point(row, column);
				if (AttemptedShots2.indexOf(cell) > -1) {
					System.out.print("O");
				} else {
					System.out.print(".");
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void Shoot() {
		xShot = Integer.parseInt(fireLocationx.getText());
		yShot = Integer.parseInt(fireLocationy.getText());
		Point shot = new Point(xShot,yShot);
		
		if (playerTurn == 1){
			System.out.println("Player 1 shot at (" + xShot + "," + yShot + ")");
			AttemptedShots1.add(a,shot);
			a++;
		} else { AttemptedShots2.add(a,shot);
		System.out.println("Player 2 shot at (" + xShot + "," + yShot + ")");
		}
	}
	public void ClearConsole() {
        for (int m = 0; m <20 ; m++) {
        	System.out.println();
        }
}
	public void actionPerformed(ActionEvent buttonInput) {
		String cmd = buttonInput.getActionCommand();
		
		if (cmd=="Next Turn" && overTurn==1) {
			ClearConsole();
			Shoot();
			NextTurn();
		}
		if (cmd=="Start") {
			CreateTargets1();
			CreateTargets2();
			System.out.println("Targets created");
			overTurn=1;
			if (overTurn == 1){
			start.setEnabled(false);
			nextTurn.setEnabled(true);
			dispFriendlyMap.setEnabled(true);
			dispShotsMap.setEnabled(true);
			}else {start.setEnabled(true);}
		}
		if (cmd == "Clear Console"){
			ClearConsole();
		}
		if (cmd == "Display your map") {
			//display current player's map
			System.out.println("This is what your fleet looks like");
			if (playerTurn == 1) {
				System.out.println("Player 1 friendly map");
				FriendlyMapPlayer1();
			} else {
				System.out.println("Player 2 friendly map");
				FriendlyMapPlayer2();
			}
		}
		if (cmd == "Display opposing players map") {
			//display attempted shots map
			System.out.println("This is where you have previously shot");
			if (playerTurn == 1) {
				System.out.println("Player 1's attempted shots map");
				OppMap2();
			} else {
				System.out.println("Player 2's attempted shots map");
				OppMap1();
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BattleShips frame = new BattleShips();
		
		
	}

}
