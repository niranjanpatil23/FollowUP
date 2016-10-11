
package com.example.timemanager;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AllFields extends Activity implements OnClickListener {
	Database db = new Database(this);

	Button done;
	Menu Menu;
	ListView ls;
	BaseAdapter b;
	TextView tv_msg;
	
	int i = 0;
	String fld_name[] = new String[100];
	String fld_unit[] = new String[100];
	String fld_target[] = new String[100];
	ArrayList<String[]> Fields = new ArrayList<String[]>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_fields);

		tv_msg = (TextView) findViewById(R.id.textMSG);
		done = (Button)findViewById(R.id.but_reset);
		done.setOnClickListener(this);
		
		//del = (MenuItem) findViewById(R.id.add_del);
		//del.setOnClickListener(this);
		ls = (ListView)findViewById(R.id.listView_fldunt);
		display();
		if(i == 0)
		{
			//write tap + code here
			tv_msg.setTextSize(25);
			tv_msg.setText("Tap + to Add Fields");
		}
		else
		{
			//disable tap+ here
			tv_msg.setText("");
		}

	}
	void display()
	{
		i = 0;
		db.open();
		Cursor c = db.getAllRecord();
		if(c.moveToFirst())
		{
			do
			{
				fld_name[i] = c.getString(0);
				fld_unit[i] = c.getString(1);
				fld_target[i] = c.getString(2);
				i++;
			}
			while(c.moveToNext());
		}

		b = new BaseAdapter() {

			@Override
			public View getView(int pos, View arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				LayoutInflater lin = getLayoutInflater();
				View v = lin.inflate(R.layout.lv_allflds,null);
				TextView t = (TextView) v.findViewById(R.id.tv_fldnm);
				TextView t1 = (TextView) v.findViewById(R.id.tv_dash);
				TextView t2 = (TextView) v.findViewById(R.id.tv_trgtsubflds);
				t.setText(fld_name[pos]);
				t1.setText(fld_unit[pos]);
				t2.setText('['+fld_target[pos]+']');
				return v;
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return i;
			}
		};
		ls.setAdapter(b);
		ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getBaseContext(),FldDial1.class);
				in.putExtra("Check", 1);
				in.putExtra("Field Name", fld_name[position]);
				in.putExtra("Field Unit", fld_unit[position]);
				in.putExtra("Field Target", fld_target[position]);
				startActivity(in);
			}
		});
		ls.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				openAlert(fld_name[position]);
				//Toast.makeText(getBaseContext(), "delete" +position, Toast.LENGTH_LONG).show();
				return true;
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		this.Menu = menu;
		getMenuInflater().inflate(R.menu.all_fields, menu);  
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{

		case R.id.but_reset:
			db.open();
			db.insertRecord1(1);
			db.close();
			Intent i1 = new Intent(this,Config.class);
			startActivity(i1);
			break;
			/*	//if get text button is save
			//Toast.makeText(this, "in items", Toast.LENGTH_LONG).show();
			if((done.getText().toString()).equals("SAVE"))
			{
				//Toast.makeText(this, "on save", Toast.LENGTH_LONG).show();
				MenuItem mndel = Menu.findItem(R.id.add_del);
				MenuItem mnadd = Menu.findItem(R.id.add_flds);
				done.setText("RESET");
				mndel.setVisible(false);
				mnadd.setVisible(false);
				
				Intent i1 = new Intent(this,Config.class);
				startActivity(i1);
				disable dustbin
			 	disable add(+) button
				change text to "reset"
			}
			// if gettext button is delete
			if((done.getText().toString()).equals("DELETE"))
			{
				//change settext delete to save
				done.setText("SAVE");
			}
			//if gettext is reset
			if((done.getText().toString()).equals("RESET"))
			{
					change settext to save
				enable add(+) button
				enable dustbin
			}*/
			

		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub


		switch (item.getItemId()) {

		case R.id.add_flds:
			Intent i = new Intent(this,FldDial1.class);
			startActivity(i);
			return true;
		case R.id.add_del:
			done.setText("DELETE");
			return true;
		case android.R.id.home:
			
			db.open();
			db.insertRecord1(1);
			db.close();
			Intent homeIntent = new Intent(this, Config.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private Boolean exit = false;
	@Override
	public void onBackPressed() {
		/*if (exit){
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);
			finish();  
		}
		else {
			Toast.makeText(this, "Press Back again to Exit.",
					Toast.LENGTH_SHORT).show();
			exit = true;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					exit = false;
				}
			}, 3 * 1000);
		}*/
		db.open();
		db.insertRecord1(1);
		db.close();
		Intent homeIntent = new Intent(this, Config.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);




	}
	
	private void openAlert(final String fn) {
		//String fin = fn;
		 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AllFields.this);
		 alertDialogBuilder.setTitle(this.getTitle()+ " decision");
		 alertDialogBuilder.setMessage("       Do you want to delete?");
		 // set positive button: Yes message
		 alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// go to a new activity of the app
				//	Toast.makeText(getApplicationContext(), "You chose a positive answer", 
					//		Toast.LENGTH_LONG).show();
					db.deleteRecord(fn);
					display();
				}
			  });
		 // set negative button: No message
		 alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// cancel the alert box and put a Toast to the user
					dialog.cancel();
				}
			});
		/* // set neutral button: Exit the app message
		 alertDialogBuilder.setNeutralButton("Exit the app",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// exit the app and go to the HOME
					AllFields.this.finish();
				}
			});*/
		 
		 AlertDialog alertDialog = alertDialogBuilder.create();
		 // show alert
		 alertDialog.show();
	}
}