package com.example.mineseeker.Logic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * <pre>
 * @author Leon
 * Generates mines to a 2D array
 * with designated number
 * </pre>
 */
public class MineGenerator {

	private ArrayList<Boolean> mines;
	private Mine[][] mineLoc;
	private int[][] mineCount;
	private int row;
	private int col;
	private int numOfMines;

	public MineGenerator(int row, int col, int mines) {
		this.mines = new ArrayList<Boolean>();
		mineLoc = new Mine[row][col];
		mineCount = new int[row][col];
		this.row = row;
		this.col = col;
		this.numOfMines = mines;
		generateMine();
	}

	/**
	 * <pre>
	 * counts how many mines in that column and row
	 * and returns the count
	 * </pre>
	 */
	public int countMine(int row, int col) {
		mineLocUpdater();
		return mineCount[row][col];
	}

	/**
	 * <pre>
	 * locates how many mines in a row and column
	 * of a specific grid
	 * then store that number into mineCount
	 * </pre>
	 */
	private void mineLocUpdater() {

		int[] rowCount = new int[this.row];
		int[] colCount = new int[this.col];

		for (int row = 0; row < this.row; row++) {
			for (int col = 0; col < this.col; col++) {
				rowCount[row] += mineLoc[row][col].isMine() ? 1 : 0;
			}
		}

		for (int col = 0; col < this.col; col++) {
			for (int row = 0; row < this.row; row++) {
				colCount[col] += mineLoc[row][col].isMine() ? 1 : 0;
			}
		}

		for (int row = 0; row < this.row; row++) {
			for (int col = 0; col < this.col; col++) {
				mineCount[row][col] = rowCount[row] + colCount[col];
				if (mineLoc[row][col].isMine()) {
					mineCount[row][col]--;
				}
			}
		}

	}

	public boolean isMine(int row, int col) {
		return mineLoc[row][col].isMine();
	}

	public boolean isFound(int row, int col) {
		return mineLoc[row][col].isFound();
	}

	public void foundMine(int row, int col) {
		mineLoc[row][col].setFound();
		mineLoc[row][col].destoryMine();
	}

	/**
	 * <pre>
	 * randomly generates specific number of mines 
	 * and stores them to random position of mineLoc 2D mine array
	 * </pre>
	 */
	private void generateMine() {
		int mineLeft = numOfMines;
		for (int i = 0; i < (row * col); i++) {
			if (mineLeft > 0) {
				mines.add(true);
				mineLeft--;
			} else {
				mines.add(false);
			}
		}
		Collections.shuffle(mines);
		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				mineLoc[i][j] = new Mine(mines.get(index));
				index++;
			}
		}
	}
}
