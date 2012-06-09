package com.alarmexample;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.alarmexample.listeners.ListenAlarmSoundListener;
import com.alarmexample.receiver.TimeReceiver;

public class AlarmExampleAppActivity extends Activity {
	public static final String PREFS_NAME = "alarmPreferences";
	private static final int REQUEST_CODE = 192837;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mountSpinners();
		loadApplicationData();
	}

	/**
	 * This method creates and populates all the Spinners in the screen. The
	 * Spinners are used to set the kind of sound that the alarm will do.
	 */
	private void mountSpinners() {
		Spinner[] spinners = { (Spinner) findViewById(R.id.spinner1),
				(Spinner) findViewById(R.id.spinner2),
				(Spinner) findViewById(R.id.spinner3),
				(Spinner) findViewById(R.id.spinner4),
				(Spinner) findViewById(R.id.spinner5) };

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.alarm_sounds,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		for (Spinner spinner : spinners) {
			spinner.setAdapter(adapter);
			spinner.setOnItemSelectedListener(new ListenAlarmSoundListener());
		}
	}

	/**
	 * It gets the preferences previously registered by the user and loads it on
	 * the screen
	 */
	private void loadApplicationData() {
		SharedPreferences preferences = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		((EditText) findViewById(R.id.text1)).setText(preferences.getString(
				"timeAlarm1", ""));
		((Spinner) findViewById(R.id.spinner1)).setSelection(preferences
				.getInt("sound1", 0));
		((CheckBox) findViewById(R.id.check1)).setChecked(preferences
				.getBoolean("isActive1", false));

		((EditText) findViewById(R.id.text2)).setText(preferences.getString(
				"timeAlarm2", ""));
		((Spinner) findViewById(R.id.spinner2)).setSelection(preferences
				.getInt("sound2", 0));
		((CheckBox) findViewById(R.id.check2)).setChecked(preferences
				.getBoolean("isActive2", false));

		((EditText) findViewById(R.id.text3)).setText(preferences.getString(
				"timeAlarm3", ""));
		((Spinner) findViewById(R.id.spinner3)).setSelection(preferences
				.getInt("sound3", 0));
		((CheckBox) findViewById(R.id.check3)).setChecked(preferences
				.getBoolean("isActive3", false));

		((EditText) findViewById(R.id.text4)).setText(preferences.getString(
				"timeAlarm4", ""));
		((Spinner) findViewById(R.id.spinner4)).setSelection(preferences
				.getInt("sound4", 0));
		((CheckBox) findViewById(R.id.check4)).setChecked(preferences
				.getBoolean("isActive4", false));

		((EditText) findViewById(R.id.text5)).setText(preferences.getString(
				"timeAlarm5", ""));
		((Spinner) findViewById(R.id.spinner5)).setSelection(preferences
				.getInt("sound5", 0));
		((CheckBox) findViewById(R.id.check5)).setChecked(preferences
				.getBoolean("isActive5", false));

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		saveApplicationState();
	}

	/**
	 * It gets the preferences registered by the user on the screen and store it
	 * in a SharedPreferences object.
	 */
	private void saveApplicationState() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		editor.putBoolean("dontStartSong", true);

		editor.putString("timeAlarm1", ((EditText) findViewById(R.id.text1))
				.getText().toString());
		editor.putInt("sound1", ((Spinner) findViewById(R.id.spinner1))
				.getSelectedItemPosition());
		editor.putBoolean("isActive1",
				((CheckBox) findViewById(R.id.check1)).isChecked());

		editor.putString("timeAlarm2", ((EditText) findViewById(R.id.text2))
				.getText().toString());
		editor.putInt("sound2", ((Spinner) findViewById(R.id.spinner2))
				.getSelectedItemPosition());
		editor.putBoolean("isActive2",
				((CheckBox) findViewById(R.id.check2)).isChecked());

		editor.putString("timeAlarm3", ((EditText) findViewById(R.id.text3))
				.getText().toString());
		editor.putInt("sound3", ((Spinner) findViewById(R.id.spinner3))
				.getSelectedItemPosition());
		editor.putBoolean("isActive3",
				((CheckBox) findViewById(R.id.check3)).isChecked());

		editor.putString("timeAlarm4", ((EditText) findViewById(R.id.text4))
				.getText().toString());
		editor.putInt("sound4", ((Spinner) findViewById(R.id.spinner4))
				.getSelectedItemPosition());
		editor.putBoolean("isActive4",
				((CheckBox) findViewById(R.id.check4)).isChecked());

		editor.putString("timeAlarm5", ((EditText) findViewById(R.id.text5))
				.getText().toString());
		editor.putInt("sound5", ((Spinner) findViewById(R.id.spinner5))
				.getSelectedItemPosition());
		editor.putBoolean("isActive5",
				((CheckBox) findViewById(R.id.check5)).isChecked());

		editor.commit();
	}

	/**
	 * Activate or Deactivate the alarm.
	 * 
	 * @param v
	 */
	public void activateAlarm(final View v) {
		CheckBox checkBox = (CheckBox) v;
		if (checkBox.isChecked()) {
			Calendar rightNow = Calendar.getInstance();
			rightNow.add(Calendar.MINUTE, 1);
			Intent intent = new Intent(getApplicationContext(),
					TimeReceiver.class);

			intent.putExtra("position", 1);

			PendingIntent sender = PendingIntent.getBroadcast(this,
					REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			System.out.println("asd");
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.set(AlarmManager.RTC_WAKEUP, rightNow.getTimeInMillis(), sender);
		}
		/*
		 * 
		 * Toast.makeText(AlarmExampleAppActivity.this, "Selected",
		 * Toast.LENGTH_SHORT).show(); } else {
		 * Toast.makeText(AlarmExampleAppActivity.this, "Not selected",
		 * Toast.LENGTH_SHORT).show(); }
		 */
	}

	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		editor.putBoolean("dontStartSong", true);
	}
}