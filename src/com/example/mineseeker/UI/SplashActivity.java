package com.example.mineseeker.UI;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class SplashActivity extends Activity {

	private static long delay = 4100;
	private boolean clickedSkipButton = false;
	private Button skip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		startAnimation();
		blockTextAnimation();
		setupSkipButton();
		Timer runSplash = new Timer();
		TimerTask showSplash = new TimerTask() {

			@Override
			public void run() {
				if (!clickedSkipButton) {
					startActivity(new Intent(SplashActivity.this,
							MenuActivity.class));
				}
				finish();
			}
		};
		runSplash.schedule(showSplash, delay);
	}

	private void setupSkipButton() {

		skip = (Button) findViewById(R.id.skipButton);

		skip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("skip", "Menu screen");
				clickedSkipButton = true;
				startActivity(new Intent(SplashActivity.this,
						MenuActivity.class));
				finish();
			}
		});

	}

	private void startAnimation() {
		ImageView logo = (ImageView) findViewById(R.id.LogoImage);
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		logo.startAnimation(fadeIn);

	}

	private void blockTextAnimation() {
		final int MOVELEFT = 200;
		final int MOVERIGHT = 200;
		ImageView redCake = (ImageView) findViewById(R.id.redCake);
		ImageView greenCake = (ImageView) findViewById(R.id.greenCake);
		TranslateAnimation movingRightAnimation = new TranslateAnimation(0,
				MOVERIGHT, 0, 0);
		TranslateAnimation movingLeftAnimation = new TranslateAnimation(
				MOVELEFT, 0, 0, 0);
		movingRightAnimation.setDuration(4000);
		movingRightAnimation.setFillAfter(true);
		movingLeftAnimation.setDuration(4000);
		movingLeftAnimation.setFillAfter(true);
		redCake.startAnimation(movingRightAnimation);
		greenCake.startAnimation(movingLeftAnimation);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
}
