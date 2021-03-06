package com.example.timemanager;

import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.util.Calendar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimeP extends Activity {

	
	/* OUTPUT:
	 * hrs in int hour
	 * mins in int min
	 * AM/PM in String format */
	TimePicker timePicker1;
	TextView time;
	String sendTime;
	Calendar calendar;
	int timepos;
	String format = "";
	Button b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_p);
		
		Intent i = getIntent();
		timepos = i.getIntExtra("position", 0);
		b = (Button) findViewById(R.id.btn_tmpc);
		timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
		//  time = (TextView) findViewById(R.id.textView1);
		calendar = Calendar.getInstance();


		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int min = calendar.get(Calendar.MINUTE);
				hour = timePicker1.getCurrentHour();
				min = timePicker1.getCurrentMinute();
				showTime(hour,min);	
				Intent returnIntent = new Intent();
				returnIntent.putExtra("return-position", timepos);
				returnIntent.putExtra("send-time", sendTime);
				setResult(RESULT_OK,returnIntent);
				/*Toast.makeText(getBaseContext(), "after set", Toast.LENGTH_LONG).show();*/
				finish();
				
			}
		});	
	}

	public void showTime(int hour, int min) {
		if (hour == 0) {
			hour += 12;
			format = "AM";
		} else if (hour == 12) {
			format = "PM";
		} else if (hour > 12) {
			hour -= 12;
			format = "PM";
		} else {
			format = "AM";
		}
		sendTime = Integer.toString(hour) + ":" + Integer.toString(min) + " " + format;
	//	time.setText(new StringBuilder().append(hour).append(" : ").append(min)
	//			.append(" ").append(format));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time, menu);
		return true;
	}

}
