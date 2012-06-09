package com.alarmexample.listeners;

import com.alarmexample.AlarmExampleAppActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ListenAlarmSoundListener implements OnItemSelectedListener {
	private static int NUMBER_FIRED = 0;

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		MediaPlayer mediaPlayer = createAlarm(pos, view);
		SharedPreferences preferences = view.getContext().getSharedPreferences(
				AlarmExampleAppActivity.PREFS_NAME, 0);
		boolean turnOn = mediaPlayer != null
				&& (preferences.getBoolean("dontStartSong", false) == false);
		if (turnOn) {
			mediaPlayer.start();
		}
		NUMBER_FIRED++;
		if (NUMBER_FIRED == 5) {
			SharedPreferences.Editor editor = preferences.edit();
			editor.putBoolean("dontStartSong", false);
			editor.commit();
			NUMBER_FIRED = 0;
		}
	}

	/**
	 * This method creates an instance of an object that represents the alarm
	 * sound.
	 * 
	 * @param position
	 *            of the selected element at Alarm sound Spinner
	 * @param view
	 *            of the Activity
	 * @return An object of MediaPlayer, in case of a valid value was selected
	 */
	private MediaPlayer createAlarm(final int position, final View view) {
		MediaPlayer mediaPlayer = null;

		switch (position) {
		case 1:
			mediaPlayer = MediaPlayer.create(
					view.getContext(),
					view.getResources().getIdentifier("normal", "raw",
							"com.alarmexample"));
			break;
		case 2:
			mediaPlayer = MediaPlayer.create(
					view.getContext(),
					view.getResources().getIdentifier("talkative", "raw",
							"com.alarmexample"));
			break;
		case 3:
			mediaPlayer = MediaPlayer.create(
					view.getContext(),
					view.getResources().getIdentifier("coolestalarmclock",
							"raw", "com.alarmexample"));
			break;
		}
		return mediaPlayer;
	}

	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
