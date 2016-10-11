package com.example.timemanager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ResetActivity extends Activity implements OnClickListener {
	Button reset;
	Database db = new Database(this);
	String fld_name[] = new String[100];
	String fld_unit[] = new String[100];
	String fld_target[] = new String[100];
	int i = 0;
	BaseAdapter b;
	ListView ls;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset);
		reset = (Button) findViewById(R.id.but_reset);
		reset.setOnClickListener(this);
		ls = (ListView)findViewById(R.id.listView_fld_reset);
		display();
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
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reset, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		openAlert();
	}
	private void openAlert() {
		//String fin = fn;
		 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ResetActivity.this);
		 alertDialogBuilder.setTitle(this.getTitle()+ " decision");
		 alertDialogBuilder.setMessage("       Do you want to reset all fields?");
		 // set positive button: Yes message
		 alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// go to a new activity of the app
				//	Toast.makeText(getApplicationContext(), "You chose a positive answer", 
					//		Toast.LENGTH_LONG).show();
					db.open();
					db.deleteAllRecord();
					db.deleteAllRecord1();
					db.close();
					Intent i = new Intent(getBaseContext(),AllFields.class);
					startActivity(i);
				}
			  });
		 // set negative button: No message
		 alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// cancel the alert box and put a Toast to the user
					dialog.cancel();
					display();
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
