package com.example.timemanager;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TimePicker;

public class NumberP extends Activity implements OnClickListener {

	
	/* OUTPUT:
	 * number / total hrs in int newVal*/

	TimePicker np;
	TextView tv1;
	Button done;
	String  val;
	int numpos;
	int hour,min,unit;
	
	TimePicker timePicker1;
	TextView time;
	String sendTime;
	Calendar calendar;
	int timepos;
	String finaltime;
	String format = "";
	Button b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_p);
		
		np = (TimePicker) findViewById(R.id.numberPicker1);
		np.setIs24HourView(true);
		calendar = Calendar.getInstance();
		
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		min = calendar.get(Calendar.MINUTE);
		hour = np.getCurrentHour();
		min = np.getCurrentMinute();
		finaltime = Integer.toString(hour) + ":" + Integer.toString(min) + " " + "hours";
		Intent i = getIntent();
		numpos = i.getIntExtra("position", 0);
		
		done = (Button)findViewById(R.id.btn_nmpc);
		done.setOnClickListener(this);
	/*	np.setOnValueChangedListener(new OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				// TODO Auto-generated method stub


				val = Integer.toString(newVal);
				
			//	tv1.setText(New.concat(String.valueOf(newVal)));
				//tv2.setText(New.concat(String.valueOf(newVal)));
			}
		});*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.number, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
			Intent returnIntent = new Intent();
			returnIntent.putExtra("return-position_num", numpos);
			returnIntent.putExtra("return_number", finaltime);
			setResult(RESULT_OK,returnIntent);
			finish();
		
	}

}
