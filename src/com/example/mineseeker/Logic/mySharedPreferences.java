package com.example.mineseeker.Logic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author Leon This is my sharedPreference class!
 * 
 */
public class mySharedPreferences {

	Activity content;

	public mySharedPreferences(Activity content) {
		this.content = content;
	}

	public int getData(String fileName, String index) {
		SharedPreferences prefs = content.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		int extraction = prefs.getInt(index, 0);
		return extraction;
	}

	public void storeData(String fileName, String index, int data) {
		SharedPreferences prefs = content.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(index, data);
		editor.commit();
	}
}
