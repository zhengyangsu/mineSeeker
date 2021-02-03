package com.example.mineseeker.UI;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		setUpHyperLink();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

	private void setUpHyperLink() {
		TextView cmpt276_HyperLink = (TextView) findViewById(R.id.CMPT276_hyperlink);
		TextView source0 = (TextView) findViewById(R.id.iconSource);
		TextView source1 = (TextView) findViewById(R.id.imageSources);
		cmpt276_HyperLink.setMovementMethod(LinkMovementMethod.getInstance());
		source0.setMovementMethod(LinkMovementMethod.getInstance());
		source1.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return super.onTouchEvent(event);
	}
}
