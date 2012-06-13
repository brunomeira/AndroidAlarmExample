package com.alarmexample;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StartScreenActivity extends ListActivity {
	public static final String PREFS_NAME = "alarmPreferences";
	public static final Intent[] intents = new Intent[5];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.getSharedPreferences(PREFS_NAME, 0).edit().clear().commit();
		loadApplicationData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadApplicationData();
	}

	/**
	 * It gets the preferences previously registered by the user and loads it on
	 * the screen
	 */
	private void loadApplicationData() {
		String[] alarmTimes = new String[5];

		SharedPreferences preferences = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);

		for (int i = 0; i < 5; i++) {
			alarmTimes[i] = preferences.getString("timeAlarm" + i, "00:00");
		}

		setListAdapter(new ArrayAdapter<String>(this, R.layout.initial,
				alarmTimes));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent myIntent = new Intent(view.getContext(),
						AlarmExampleAppActivity.class);
				myIntent.putExtra("alarmNumber", position);
				startActivityForResult(myIntent, 0);
			}
		});

	}
}
