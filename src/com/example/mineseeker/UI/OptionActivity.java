package com.example.mineseeker.UI;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.mineseeker.Logic.mySharedPreferences;

public class OptionActivity extends Activity {

	private Button reset;
	private RadioGroup boardSize;
	private RadioGroup mineSize;
	private int boardSelection;
	private int mineSelection;
	private mySharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		initialization();

		boardSize.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.board_size_one:
					mineSize.getChildAt(2).setEnabled(false);
					mineSize.getChildAt(3).setEnabled(false);
					if (mineSelection == 2 || mineSelection == 3) {
						mineSize.clearCheck();
						mineSelection = 0;
						pref.storeData("Options", "mineSizeSelection",
								mineSelection);
					}
					boardSelection = 0;
					break;
				case R.id.board_size_two:
					enableRadioGroup(mineSize);
					boardSelection = 1;
					break;
				case R.id.board_size_three:
					enableRadioGroup(mineSize);
					boardSelection = 2;
					break;
				}
				pref.storeData("Options", "boardSizeSelection", boardSelection);
			}
		});

		mineSize.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.mine_size_one:
					mineSelection = 0;
					break;
				case R.id.mine_size_two:
					mineSelection = 1;
					break;
				case R.id.mine_size_three:
					mineSelection = 2;
					break;
				case R.id.mine_size_four:
					mineSelection = 3;
					break;
				}
				pref.storeData("Options", "mineSizeSelection", mineSelection);
			}
		});

		buttons();
		RunCount();
	}

	private void initialization() {
		pref = new mySharedPreferences(OptionActivity.this);
		boardSize = (RadioGroup) findViewById(R.id.board_size_selections);
		boardSize.check(boardSize.getChildAt(
				pref.getData("Options", "boardSizeSelection")).getId());
		mineSize = (RadioGroup) findViewById(R.id.mine_size_selections);
		mineSize.check(mineSize.getChildAt(
				pref.getData("Options", "mineSizeSelection")).getId());
		boardSelection = pref.getData("Options", "boardSizeSelection");
		mineSelection = pref.getData("Options", "mineSizeSelection");
		if (boardSelection == 0) {
			mineSize.clearCheck();
			mineSize.getChildAt(2).setEnabled(false);
			mineSize.getChildAt(3).setEnabled(false);
		} else {
			enableRadioGroup(mineSize);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.option, menu);
		return true;
	}

	private void enableRadioGroup(RadioGroup group) {
		for (int i = 0; i < group.getChildCount(); i++) {
			group.getChildAt(i).setEnabled(true);
		}
	}

	private void buttons() {
		reset = (Button) findViewById(R.id.reset);
		reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("Reset Button", "Reset game");
				pref.storeData("Options", "runCount", 0);
				RunCount();
			}
		});
	}

	private void RunCount() {
		String message = (String) getResources().getText(R.string.count) + " "
				+ pref.getData("Options", "runCount");
		TextView textView = (TextView) findViewById(R.id.run_count);
		Log.i("displayRunCount", "display on");
		textView.setText(message);
	}
}
