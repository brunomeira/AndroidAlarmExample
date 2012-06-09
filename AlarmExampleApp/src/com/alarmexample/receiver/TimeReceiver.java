package com.alarmexample.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

public class TimeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ctx, Intent intent) {
		Bundle bundle = intent.getExtras();
		MediaPlayer mediaPlayer = null;

		int position = bundle.getInt("position");

		switch (position) {
		case 1:
			mediaPlayer = MediaPlayer.create(ctx, ctx.getResources()
					.getIdentifier("normal", "raw", "com.alarmexample"));
			break;
		case 2:
			mediaPlayer = MediaPlayer.create(ctx, ctx.getResources()
					.getIdentifier("talkative", "raw", "com.alarmexample"));
			break;
		case 3:
			mediaPlayer = MediaPlayer.create(
					ctx,
					ctx.getResources().getIdentifier("coolestalarmclock",
							"raw", "com.alarmexample"));
			break;
		}

		Toast.makeText(ctx, "It's time to WAKE UP!!!!", Toast.LENGTH_SHORT)
				.show();
	}

}
