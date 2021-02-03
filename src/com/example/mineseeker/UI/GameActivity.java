package com.example.mineseeker.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.example.mineseeker.Logic.MineGenerator;
import com.example.mineseeker.Logic.mySharedPreferences;

public class GameActivity extends Activity {

	private static int NUM_ROWS;
	private static int NUM_COLS;
	private static int NUM_MINES;
	private Button[][] buttons;
	private boolean[][] clickedButton;
	private mySharedPreferences pref;
	private MineGenerator gen;
	private int minesFound;
	private int scan;
	// for the dialog
	private final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		// restore data from option menu or game history------------------
		pref = new mySharedPreferences(GameActivity.this);
		pref.storeData("Options", "runCount",
				pref.getData("Options", "runCount") + 1);
		// ---------------------------------------------------------------
		// Initialize row, column and mine size
		getBoardSize();
		getMineSize();
		// ---------------------------------------------------------------
		buttons = new Button[NUM_ROWS][NUM_COLS];
		clickedButton = new boolean[NUM_ROWS][NUM_COLS];
		gen = new MineGenerator(NUM_ROWS, NUM_COLS, NUM_MINES);
		generateButtons();
		scoreKeeper();
		// alertDialog

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	private void generateButtons() {

		TableLayout table = (TableLayout) findViewById(R.id.activity_game_Layout);

		for (int row = 0; row < NUM_ROWS; row++) {
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new TableLayout.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT,
					TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
			table.addView(tableRow);

			for (int col = 0; col < NUM_COLS; col++) {
				final int FINAL_ROW = row;
				final int FINAL_COL = col;
				buttons[row][col] = new Button(this);
				clickedButton[row][col] = false;
				Button button = buttons[row][col];
				LayoutParams params = new TableRow.LayoutParams(
						TableRow.LayoutParams.MATCH_PARENT,
						TableRow.LayoutParams.MATCH_PARENT, 1.0f);
				params.setMargins(6, 10, 6, 10);
				button.setLayoutParams(params);
				button.setPadding(0, 0, 0, 0);
				// button.setBackgroundResource(R.drawable.bag);
				button.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						gridButtonClicked(FINAL_ROW, FINAL_COL);
					}

				});
				tableRow.addView(button);
			}
		}
	}

	private void gridButtonClicked(int row, int col) {
		Button button = buttons[row][col];
		lockButtonSizes();
		if (!gen.isMine(row, col) && !clickedButton[row][col]) {
			clickedButton[row][col] = true;
			scan++;
		}
		if (gen.isMine(row, col) && !gen.isFound(row, col)) {
			buttonModiflier(button, randomCake());
			minesFound++;
			gen.foundMine(row, col);
		}
		buttonUpdater();
		scoreKeeper();

	}

	private void buttonUpdater() {
		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				if (clickedButton[row][col]) {
					buttons[row][col].setText(String.valueOf(gen.countMine(row,
							col)));
				}
			}
		}
	}

	private void buttonModiflier(Button button, int id) {
		int newWidth = button.getWidth();
		int newHeight = button.getHeight();
		Bitmap originalBitmap = BitmapFactory
				.decodeResource(getResources(), id);
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap,
				newWidth, newHeight, true);
		Resources resource = getResources();
		button.setBackground(new BitmapDrawable(resource, scaledBitmap));
	}

	private void lockButtonSizes() {
		for (int row = 0; row != NUM_ROWS; row++) {
			for (int col = 0; col != NUM_COLS; col++) {
				Button button = buttons[row][col];
				int width = button.getWidth();
				button.setMinWidth(width);
				button.setMaxWidth(width);
				int height = button.getHeight();
				button.setMinHeight(height);
				button.setMaxHeight(height);
			}
		}
	}

	private void getBoardSize() {
		switch (pref.getData("Options", "boardSizeSelection")) {
		case 0:
			NUM_ROWS = 3;
			NUM_COLS = 4;
			break;
		case 1:
			NUM_ROWS = 4;
			NUM_COLS = 6;
			break;
		case 2:
			NUM_ROWS = 6;
			NUM_COLS = 10;
			break;
		}
	}

	private void getMineSize() {
		switch (pref.getData("Options", "mineSizeSelection")) {
		case 0:
			NUM_MINES = 6;
			break;
		case 1:
			NUM_MINES = 10;
			break;
		case 2:
			NUM_MINES = 15;
			break;
		case 3:
			NUM_MINES = 20;
			break;
		}
		pref.storeData("Game", "numberOfMines", NUM_MINES);
		minesFound = 0;
		scan = 0;
	}

	private void scoreKeeper() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle("You have found all the goodies!");
		alertDialogBuilder.setIcon(R.drawable.girl);
		alertDialogBuilder.setMessage("Congradulations, You Win!")
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						GameActivity.this.finish();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();

		String message = "Found " + minesFound + " of " + NUM_MINES + " mines";
		TextView textView = (TextView) findViewById(R.id.minesFound);
		textView.setText(message);
		if (minesFound == NUM_MINES) {
			alertDialog.show();
		}
		message = "# of Scan used: " + scan;
		textView = (TextView) findViewById(R.id.scanUsed);
		textView.setText(message);
	}

	private int randomCake() {

		int cake = (int) (Math.random() * 20);

		switch (cake) {
		case 0:
			return R.drawable.cake_1;
		case 1:
			return R.drawable.cake_2;
		case 2:
			return R.drawable.cake_3;
		case 3:
			return R.drawable.cake_4;
		case 4:
			return R.drawable.cake_5;
		case 5:
			return R.drawable.cake_6;
		case 6:
			return R.drawable.cake_7;
		case 7:
			return R.drawable.cake_8;
		case 8:
			return R.drawable.cake_9;
		case 9:
			return R.drawable.cake_10;
		case 10:
			return R.drawable.cake_11;
		case 11:
			return R.drawable.cake_12;
		case 12:
			return R.drawable.cake_13;
		case 13:
			return R.drawable.cake_14;
		case 14:
			return R.drawable.cake_15;
		case 15:
			return R.drawable.cake_16;
		case 16:
			return R.drawable.cake_17;
		case 17:
			return R.drawable.cake_18;
		case 18:
			return R.drawable.cake_19;
		default:
			return R.drawable.cake_1;
		}
	}
}
