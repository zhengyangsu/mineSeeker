package com.example.mineseeker.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

	private Button play;
	private Button config;
	private Button help;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		Buttons();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	private void Buttons() {

		play = (Button) findViewById(R.id.play);
		config = (Button) findViewById(R.id.config);
		help = (Button) findViewById(R.id.help);

		play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("Play Button", "Play game");
				startActivity(new Intent(MenuActivity.this, GameActivity.class));
			}
		});

		config.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("config Button", "Config options");
				startActivity(new Intent(MenuActivity.this,
						OptionActivity.class));
			}
		});

		help.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("help Button", "Help screen");
				startActivity(new Intent(MenuActivity.this, HelpActivity.class));
			}
		});

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
