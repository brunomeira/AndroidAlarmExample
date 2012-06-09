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
import android.widget.Toast;

import com.alarmexample.listeners.ListenAlarmSoundListener;
import com.alarmexample.receiver.TimeReceiver;

public class AlarmExampleAppActivity extends Activity {
	public static final String PREFS_NAME = "alarmPreferences";
	private Intent[] intents = new Intent[5];

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

		editor.putString("timeAlarm1", getAlarmHourByLine(1));
		editor.putInt("sound1", getSpinnerSelectedPositionByLine(1));
		editor.putBoolean("isActive1",
				((CheckBox) findViewById(R.id.check1)).isChecked());

		editor.putString("timeAlarm2", getAlarmHourByLine(2));
		editor.putInt("sound2", getSpinnerSelectedPositionByLine(2));
		editor.putBoolean("isActive2",
				((CheckBox) findViewById(R.id.check2)).isChecked());

		editor.putString("timeAlarm3", getAlarmHourByLine(3));
		editor.putInt("sound3", getSpinnerSelectedPositionByLine(3));
		editor.putBoolean("isActive3",
				((CheckBox) findViewById(R.id.check3)).isChecked());

		editor.putString("timeAlarm4", getAlarmHourByLine(4));
		editor.putInt("sound4", getSpinnerSelectedPositionByLine(4));
		editor.putBoolean("isActive4",
				((CheckBox) findViewById(R.id.check4)).isChecked());

		editor.putString("timeAlarm5", getAlarmHourByLine(5));
		editor.putInt("sound5", getSpinnerSelectedPositionByLine(5));
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
			if (fieldsValidated(checkBox)) {
				String time = null;
				PendingIntent sender = null;
				Intent intent = null;
				Calendar actualTime = Calendar.getInstance();
				Calendar timeToTriggerAlarm = Calendar.getInstance();
				switch (checkBox.getId()) {
				case R.id.check1:
					intent = intents[0] = new Intent(this, TimeReceiver.class);
					int s = getSpinnerSelectedPositionByLine(1);
					intents[0].putExtra("position",
							getSpinnerSelectedPositionByLine(1));
					time = getAlarmHourByLine(1);
					timeToTriggerAlarm.set(Calendar.HOUR_OF_DAY, getHour(time));
					timeToTriggerAlarm.set(Calendar.MINUTE, getMinute(time));
					break;

				case R.id.check2:
					intent = intents[1] = new Intent(this, TimeReceiver.class);
					intents[1].putExtra("position",
							getSpinnerSelectedPositionByLine(2));
					time = getAlarmHourByLine(2);
					timeToTriggerAlarm.set(Calendar.HOUR_OF_DAY, getHour(time));
					timeToTriggerAlarm.set(Calendar.MINUTE, getMinute(time));
					break;
				case R.id.check3:
					intent = intents[2] = new Intent(this, TimeReceiver.class);
					intents[2].putExtra("position",
							getSpinnerSelectedPositionByLine(3));
					time = getAlarmHourByLine(3);
					timeToTriggerAlarm.set(Calendar.HOUR_OF_DAY, getHour(time));
					timeToTriggerAlarm.set(Calendar.MINUTE, getMinute(time));
					break;
				case R.id.check4:
					intent = intents[3] = new Intent(this, TimeReceiver.class);
					intents[3].putExtra("position",
							getSpinnerSelectedPositionByLine(4));
					time = getAlarmHourByLine(4);
					timeToTriggerAlarm.set(Calendar.HOUR_OF_DAY, getHour(time));
					timeToTriggerAlarm.set(Calendar.MINUTE, getMinute(time));
					break;
				case R.id.check5:
					intent = intents[4] = new Intent(this, TimeReceiver.class);
					intents[4].putExtra("position",
							getSpinnerSelectedPositionByLine(5));
					time = getAlarmHourByLine(5);
					timeToTriggerAlarm.set(Calendar.HOUR_OF_DAY, getHour(time));
					timeToTriggerAlarm.set(Calendar.MINUTE, getMinute(time));

					break;
				}

				timeToTriggerAlarm.set(Calendar.SECOND, 00);
				if (actualTime.after(timeToTriggerAlarm)) {
					timeToTriggerAlarm.add(Calendar.DAY_OF_MONTH, 1);
				}
				sender = PendingIntent.getBroadcast(this, checkBox.getId(),
						intent, PendingIntent.FLAG_ONE_SHOT);

				AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
				am.set(AlarmManager.RTC_WAKEUP,
						timeToTriggerAlarm.getTimeInMillis(), sender);

			} else {
				Toast.makeText(this, R.string.invalidFields, Toast.LENGTH_SHORT)
						.show();
				checkBox.setChecked(false);
			}
		} else {
			Intent intent = null;
			switch (checkBox.getId()) {
			case R.id.check1:
				intent = intents[0];
				break;

			case R.id.check2:
				intent = intents[1];
				break;
			case R.id.check3:
				intent = intents[2];
				break;
			case R.id.check4:
				intent = intents[3];
				break;
			case R.id.check5:
				intent = intents[4];
				break;
			}
			if (intent != null) {
				PendingIntent sender = PendingIntent.getBroadcast(this,
						checkBox.getId(), intent, PendingIntent.FLAG_ONE_SHOT);
				AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
				am.cancel(sender);
			}
		}
	}

	private boolean fieldsValidated(final CheckBox checkbox) {
		boolean validated = true;
		switch (checkbox.getId()) {
		case R.id.check1:
			validated = isTimeFormat(getAlarmHourByLine(1))
					&& (getSpinnerSelectedPositionByLine(1) > 0);
			break;
		case R.id.check2:
			validated = isTimeFormat(getAlarmHourByLine(2))
					&& (getSpinnerSelectedPositionByLine(2) > 0);
			break;
		case R.id.check3:
			validated = isTimeFormat(getAlarmHourByLine(3))
					&& (getSpinnerSelectedPositionByLine(3) > 0);
			break;
		case R.id.check4:
			validated = isTimeFormat(getAlarmHourByLine(4))
					&& (getSpinnerSelectedPositionByLine(4) > 0);
			break;
		case R.id.check5:
			validated = isTimeFormat(getAlarmHourByLine(5))
					&& (getSpinnerSelectedPositionByLine(5) > 0);
			break;
		}
		return validated;
	}

	private boolean isTimeFormat(final String format) {
		return format.matches("\\d{2}:\\d{2}");
	}

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("dontStartSong", true);
		editor.commit();
	}

	private int getSpinnerSelectedPositionByLine(final int line) {
		int position = 0;
		switch (line) {
		case 1:
			position = ((Spinner) findViewById(R.id.spinner1))
					.getSelectedItemPosition();
			break;

		case 2:
			position = ((Spinner) findViewById(R.id.spinner2))
					.getSelectedItemPosition();
			break;
		case 3:
			position = ((Spinner) findViewById(R.id.spinner3))
					.getSelectedItemPosition();
			break;
		case 4:
			position = ((Spinner) findViewById(R.id.spinner4))
					.getSelectedItemPosition();
			break;
		case 5:
			position = ((Spinner) findViewById(R.id.spinner5))
					.getSelectedItemPosition();
			break;
		}
		return position;
	}

	private String getAlarmHourByLine(final int line) {
		String alarmHour = "";

		switch (line) {
		case 1:
			alarmHour = ((EditText) findViewById(R.id.text1)).getText()
					.toString();
			break;

		case 2:
			alarmHour = ((EditText) findViewById(R.id.text2)).getText()
					.toString();
			break;
		case 3:
			alarmHour = ((EditText) findViewById(R.id.text3)).getText()
					.toString();
			break;
		case 4:
			alarmHour = ((EditText) findViewById(R.id.text4)).getText()
					.toString();
			break;
		case 5:
			alarmHour = ((EditText) findViewById(R.id.text5)).getText()
					.toString();
			break;
		}
		return alarmHour;
	}

	private int getHour(final String time) {
		return Integer.parseInt(time.substring(0, 2));
	}

	private int getMinute(final String time) {
		return Integer.parseInt(time.substring(3));
	}
}