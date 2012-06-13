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
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alarmexample.listeners.ListenAlarmSoundListener;
import com.alarmexample.receiver.TimeReceiver;

public class AlarmExampleAppActivity extends Activity {
	private int alarmNumber = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		mountSpinner();
		loadApplicationData();
	}

	private void mountSpinner() {
		Spinner spinner = getSpinner();

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.alarm_sounds,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new ListenAlarmSoundListener());
	}

	private void loadApplicationData() {
		TimePicker alarmTime = ((TimePicker) findViewById(R.id.timepicker));
		SharedPreferences preferences = getLocalSharedPreferences();
		alarmNumber = getIntent().getIntExtra("alarmNumber", 0);

		int selectedSound = preferences.getInt("sound" + alarmNumber, 0);
		String time = preferences.getString("timeAlarm" + alarmNumber, "00:00");
		boolean isActivated = preferences.getBoolean("isActive" + alarmNumber,
				false);

		getCheckBox().setChecked(isActivated);
		getSpinner().setSelection(selectedSound);
		
		int timeHour = Integer.parseInt(time.substring(0, time.indexOf(":")));
		int timeMinutes = Integer
				.parseInt(time.substring(time.indexOf(":") + 1));
		alarmTime.setIs24HourView(true);
		alarmTime.setCurrentHour(timeHour);
		alarmTime.setCurrentMinute(timeMinutes);

	}

	public void save(final View view) {
		if (fieldsValidated()) {
			saveSharedPreferences();
			enableOrDisableAlarm();
			finish();
		} else {
			Toast.makeText(this, R.string.invalidFields, Toast.LENGTH_SHORT)
					.show();
		}
	}

	private boolean fieldsValidated() {
		return getSelectedSoundPosition() > 0;
	}

	private void saveSharedPreferences() {
		SharedPreferences.Editor editor = getLocalSharedPreferences().edit();
		String time = getHour() + ":" + getMinute();

		editor.putBoolean("dontStartSong", true);
		editor.putBoolean("isActive" + alarmNumber, isActivated());
		editor.putInt("sound" + alarmNumber, getSelectedSoundPosition());
		editor.putString("timeAlarm" + alarmNumber, time);

		editor.commit();
	}

	private void enableOrDisableAlarm() {
		if (isActivated()) {
			enableAlarm();
		} else {
			disableAlarm();
		}
	}

	private void enableAlarm() {
		PendingIntent sender = null;
		Intent intent = null;
		Calendar alarmTime = prepareAlarmTime();

		intent = StartScreenActivity.intents[alarmNumber] = new Intent(this,
				TimeReceiver.class);
		intent.putExtra("position", getSelectedSoundPosition());

		sender = PendingIntent.getBroadcast(this, alarmNumber, intent,
				PendingIntent.FLAG_ONE_SHOT);

		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), sender);
	}

	private void disableAlarm() {
		Intent intent = StartScreenActivity.intents[alarmNumber];

		if (intent != null) {
			PendingIntent sender = PendingIntent.getBroadcast(this,
					alarmNumber, intent, PendingIntent.FLAG_ONE_SHOT);
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.cancel(sender);
		}
	}

	private Calendar prepareAlarmTime() {
		Calendar actualTime = Calendar.getInstance();
		Calendar timeToTriggerAlarm = Calendar.getInstance();

		timeToTriggerAlarm.set(Calendar.HOUR_OF_DAY, getHour());
		timeToTriggerAlarm.set(Calendar.MINUTE, getMinute());
		timeToTriggerAlarm.set(Calendar.SECOND, 00);

		if (actualTime.after(timeToTriggerAlarm)) {
			timeToTriggerAlarm.add(Calendar.DAY_OF_MONTH, 1);
		}

		return timeToTriggerAlarm;
	}

	private int getHour() {
		TimePicker alarmTime = ((TimePicker) findViewById(R.id.timepicker));
		return alarmTime.getCurrentHour();
	}

	private int getMinute() {
		TimePicker alarmTime = ((TimePicker) findViewById(R.id.timepicker));
		return alarmTime.getCurrentMinute();
	}

	public SharedPreferences getLocalSharedPreferences() {
		return getSharedPreferences(StartScreenActivity.PREFS_NAME,
				MODE_PRIVATE);
	}

	private int getSelectedSoundPosition() {
		return getSpinner().getSelectedItemPosition();
	}

	private boolean isActivated() {
		return getCheckBox().isChecked();
	}

	private CheckBox getCheckBox() {
		return ((CheckBox) findViewById(R.id.check));
	}

	private Spinner getSpinner() {
		return ((Spinner) findViewById(R.id.spinner1));
	}

}