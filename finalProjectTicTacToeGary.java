package finalProject;
/* File name: FinalProjectTicTacToeGary.java
 * Name: Gary Fang
 * Submission Date: May 25, 2022
 */

import java.util.*;
import java.io.*;

public class finalProjectTicTacToeGary {

	public static String[] line1 = { "1", " | ", "2", " | ", "3" };
	public static String[] line2 = { "4", " | ", "5", " | ", "6" };
	public static String[] line3 = { "7", " | ", "8", " | ", "9" };
	public static boolean winGame = false;
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception {

		// Declare variables
		String nameAns;
		String name = "";
		String response;

		boolean playAgain = true;
		boolean playerTurn = true;

		int move = 0;
		int turns = 0;
		int win = 0;
		int lose = 0;
		int draw = 0;

		// Welcome message
		System.out.println("Welcome to Tic Tac Toe by Gary Fang! ");
		System.out.println();

		// Call gameRules method
		gameRules();
		sc = new Scanner(System.in);

		// Ask user for name
		do {
			System.out.println();
			System.out.println();
			System.out
					.print("Would you like to have a file with your name to store the score? (Enter 'yes' or 'no'): ");
			nameAns = sc.next();

			if (nameAns.equals("yes")) {

				// Ask user for name and store in variable
				System.out.print("Please enter your name (no spaces): ");
				name = sc.next();

				// Create file with the name
				fileCreation(name);
				System.out.println("Your file has been created. ");

			} else if (nameAns.equals("no")) {

			} else {
				System.out.println("Please enter a valid option! ");
			} // end if

		} while (!nameAns.equals("yes") && !nameAns.equals("no"));

		// Actual game starts
		while (playAgain == true) {
			printGrid();

			// Loop until the game ends
			while (turns < 9 && winGame == false) {

				// Player move
				if (playerTurn == true) {
					System.out.print("Player move. Enter the box you want to put in: ");

					// Loop until move is valid
					do {
						try {
							move = sc.nextInt();
							if (move < 1 || move > 9) {
								System.out.print("Invalid input! Please enter an integer between 1-9: ");

							} else if (checkTaken(move) == true) {
								System.out.print("Invalid input! Please choose an empty box!: ");
							} // end if

							// Catches if user enters a wrong type
						} catch (Exception e) {
							System.out.print("Invalid input! Please enter an integer!: ");
							sc.nextLine();
						}
					} while (move < 1 || move > 9 || checkTaken(move) == true);

					makeTurn("X", move);
					playerTurn = false;

					// Computer move
				} else {
					System.out.println("Computer move. ");

					// Generate random number
					int randNum = (int) (Math.random() * 9) + 1;

					// Regenerate a number is box already taken
					while (checkTaken(randNum) == true) {
						randNum = (int) (Math.random() * 9) + 1;
					} // end while

					makeTurn("O", randNum);
					playerTurn = true;
				} // end if

				// Call checkWin method
				checkWin();
				// Print game grid
				printGrid();

				turns++;
			} // end while

			// Check final game result

			if (turns == 9 && winGame == false) {
				System.out.println("The game is draw! ");
				draw++;
			} else if (playerTurn == false) {
				System.out.println("The player won!");
				win++;
			} else if (playerTurn == true) {
				System.out.println("The player lost! ");
				lose++;
			} // end if

			// Ask user if they want to play again
			// Loop until user enters a valid response
			do {
				System.out.print("Do you want to play again? (Enter 'yes' to continue, 'no' to exit): ");
				response = sc.next();
				if (response.equals("yes")) {
					playAgain = true;
				} else if (response.equals("no")) {
					playAgain = false;
				} else {
					System.out.print("Please enter a valid response! ");
				}
			} while (!response.equals("yes") && !response.equals("no"));

			// Reset game board and counters
			if (playAgain == true) {
				// Reset game board
				line1[0] = "1";
				line1[2] = "2";
				line1[4] = "3";
				line2[0] = "4";
				line2[2] = "5";
				line2[4] = "6";
				line3[0] = "7";
				line3[2] = "8";
				line3[4] = "9";

				winGame = false;
				playerTurn = true;
				turns = 0;

				// Create a large space between old game and new game
				for (int i = 0; i < 20; i++) {
					System.out.println();
				} // end for
			} // end if

		} // end while

		// Call writeScore method and write score in user's own file
		System.out.println();
		if (nameAns.equals("yes")) {
			writeScore(name, win, lose, draw);
			System.out.println("Your score has been stored in the file! ");
		} // end if

		// Bye message
		System.out.println("Game ended. Thank you for playing and hope you enjoyed the game! ");

		sc.close();
	}// end main

	// gameRules method
	public static void gameRules() throws Exception {

		File file = new File("TicTacToeGameRules.txt");
		PrintWriter pw = new PrintWriter(file);
		sc = new Scanner(file);

		// Write rules in file
		pw.println("Rules of Tic Tac Toe: ");
		pw.println("1. Players play on a 3x3 grid against the computer. ");
		pw.println("2. Players and computer take turns plotting 'X' or 'O' on the grid. ");
		pw.println("3. First player to get 3 in a row wins, could be a horizontal, vertical, or diagonal line. ");
		pw.println("4. If all boxes are filled out and no one has 3 in a row, it's a draw. ");
		pw.println("5. The grid: ");

		// Write grid example
		pw.println();
		pw.println("1 | 2 | 3");
		pw.println("- + - + -");
		pw.println("4 | 5 | 6");
		pw.println("- + - + -");
		pw.println("7 | 8 | 9");

		// Write example of what user should enter
		pw.println();
		pw.println("The player enters the integer each time for coordinates. For example: ");
		pw.println("Enter '7' to plot in the bottom left corner");
		pw.println("Enter '3' to plot in the top right corner");
		pw.close();

		// Read and output the rules in file to console
		while (sc.hasNext()) {
			System.out.println(sc.nextLine());
		} // end while

		sc.close();
	}// end gameRules

	// fileCreation method
	public static void fileCreation(String name) throws Exception {
		// Create file with the player's name
		File file = new File(name + "ScoreSheet.txt");
		PrintWriter pw = new PrintWriter(file);
		pw.close();
	}// end fileCreation

	// printGrid method
	public static void printGrid() {
		System.out.println();

		// Print top row of board
		for (int i = 0; i < line1.length; i++) {
			System.out.print(line1[i]);
		} // end for
		System.out.println();
		System.out.println("- + - + -");

		// Print middle row of board
		for (int i = 0; i < line2.length; i++) {
			System.out.print(line2[i]);
		} // end for
		System.out.println();
		System.out.println("- + - + -");

		// Print bottom row of board
		for (int i = 0; i < line3.length; i++) {
			System.out.print(line3[i]);
		} // end for
		System.out.println();
		System.out.println();
	}// end printGrid

	// makeTurn method
	public static void makeTurn(String character, int move) {

		// Check to see which grid did the player chose
		if (move == 1) {
			line1[0] = character;
		} else if (move == 2) {
			line1[2] = character;
		} else if (move == 3) {
			line1[4] = character;
		} else if (move == 4) {
			line2[0] = character;
		} else if (move == 5) {
			line2[2] = character;
		} else if (move == 6) {
			line2[4] = character;
		} else if (move == 7) {
			line3[0] = character;
		} else if (move == 8) {
			line3[2] = character;
		} else if (move == 9) {
			line3[4] = character;
		} // end if

	}// end makeTurn

	// checkWin method
	public static void checkWin() {

		// Horizontal lines
		if (line1[0] == line1[2] && line1[2] == line1[4]) {
			winGame = true;
		} // end if
		if (line2[0] == line2[2] && line2[2] == line2[4]) {
			winGame = true;
		} // end if
		if (line3[0] == line3[2] && line3[2] == line3[4]) {
			winGame = true;
		} // end if

		// Vertical lines
		if (line1[0] == line2[0] && line2[0] == line3[0]) {
			winGame = true;
		} // end if
		if (line1[2] == line2[2] && line2[2] == line3[2]) {
			winGame = true;
		} // end if
		if (line1[4] == line2[4] && line2[4] == line3[4]) {
			winGame = true;
		} // end if

		// Diagonals
		if (line1[0] == line2[2] && line2[2] == line3[4]) {
			winGame = true;
		} // end if
		if (line1[4] == line2[2] && line2[2] == line3[0]) {
			winGame = true;
		} // end if

	}// end checkWin

	// checkTaken method
	public static boolean checkTaken(int move) {
		// Returns FALSE if the grid is NOT taken, returns TRUE if the grid IS taken

		if (move == 1) {
			if (line1[0].equals("1")) {
				return false;
			} // end if
		} else if (move == 2) {
			if (line1[2].equals("2")) {
				return false;
			} // end if
		} else if (move == 3) {
			if (line1[4].equals("3")) {
				return false;
			} // end if
		} else if (move == 4) {
			if (line2[0].equals("4")) {
				return false;
			} // end if
		} else if (move == 5) {
			if (line2[2].equals("5")) {
				return false;
			} // end if
		} else if (move == 6) {
			if (line2[4].equals("6")) {
				return false;
			} // end if
		} else if (move == 7) {
			if (line3[0].equals("7")) {
				return false;
			} // end if
		} else if (move == 8) {
			if (line3[2].equals("8")) {
				return false;
			} // end if
		} else if (move == 9) {
			if (line3[4].equals("9")) {
				return false;
			} // end if
		} // end if

		return true;
	}// end checkTaken

	// writeScore method
	public static void writeScore(String name, int win, int lose, int draw) throws Exception {

		// Create file with user's name
		File file = new File(name + "ScoreSheet.txt");
		PrintWriter pw = new PrintWriter(file);

		// Write user's scores in their own file
		pw.println(name + "'s Scoresheet: ");
		pw.println();
		pw.println("Wins: ");
		pw.println(win);
		pw.println();
		pw.println("Loses:");
		pw.println(lose);
		pw.println();
		pw.println("Draws: ");
		pw.println(draw);

		pw.close();
	}// end writeScore
}
