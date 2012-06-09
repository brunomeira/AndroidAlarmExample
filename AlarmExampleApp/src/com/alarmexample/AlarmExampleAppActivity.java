package com.alarmexample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alarmexample.listeners.ListenAlarmSoundListener;

public class AlarmExampleAppActivity extends Activity {
	private static final String PREFS_NAME = "alarmPreferences";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mountSpinners();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		saveApplicationState();
	}

	/**
	 * It gets the preferences registered by the user and store it in a
	 * SharedPreferences object.
	 */
	private void saveApplicationState() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

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
		if (((CheckBox) v).isChecked()) {
			Toast.makeText(AlarmExampleAppActivity.this, "Selected",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(AlarmExampleAppActivity.this, "Not selected",
					Toast.LENGTH_SHORT).show();
		}
	}

}