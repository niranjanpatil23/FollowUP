package com.example.timemanager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Config extends Activity implements OnClickListener {
	Database db = new Database(this);	
	Date d = new Date();
	int dd,mmm,yy;
	int set_pos;
	int set_pos1;
	TextView tv_dt;
	int i = 0;
	int flag = 0;
	String fld_name[] = new String[100];
	String fld_unit[] = new String[100];
	String fld_target[] = new String[100];
	String fld_time[] = new String[100];
	String fld_date;
	String new_time = "--:--:--";
	ListView ls_main;
	BaseAdapter b;
	String formattedDate;
	ImageButton config;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        /*db.open();
        db.insertRecord1(0);
        db.close();*/
        tv_dt = (TextView) findViewById(R.id.text_dt);
        display();
        if(i != 0)
        {
        	//disable tap+ here
        	 Calendar c = Calendar.getInstance();
             //  System.out.println("Current time => " + c.getTime());

               SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
               formattedDate = df.format(c.getTime());
              // Toast.makeText(this, formattedDate, Toast.LENGTH_LONG).show();
               config = (ImageButton) findViewById(R.id.btn_config);
               config.setOnClickListener(this);
               
               tv_dt.setTextSize(15);
               tv_dt.setText(formattedDate);
              // Toast.makeText(this, "in", Toast.LENGTH_LONG).show();
               db.open();
               Cursor cr = db.getAllRecord();
               cr.moveToFirst();
               fld_date = cr.getString(4);
            //   Toast.makeText(this, fld_date, Toast.LENGTH_LONG).show();
               if(!(fld_date.equals(formattedDate)))
               {
            	   db.updateallRecord(new_time, formattedDate);
            	   display();
               }
               db.close();
        	 try
             {
             	String destpath = "/data/data/" + getPackageName() + "/databases/Smart_Scheduler_DB";
             	File f = new File(destpath);
             	if(!f.exists())
             	{
             		Toast.makeText(this, "in does not exit", Toast.LENGTH_LONG).show();
             	}
             
            	
             }
             catch(Exception e)
             {
             	e.printStackTrace();
             }
        	 
        }
        else
        {
        	//write tap + code in this part
        	tv_dt.setTextSize(25);
        	tv_dt.setText("Tap + to Configure");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	        
	        	//Toast.makeText(getBaseContext(), "back", Toast.LENGTH_LONG).show();
	        	set_pos = data.getIntExtra("return-position", 0);
	        	fld_time[set_pos] = data.getStringExtra("send-time");
	        	db.open();
	        	db.updateRecord(fld_name[set_pos], fld_time[set_pos]);
	        	db.close();
	        	//displaying contents of expenses
	        	display();
	        }
	 }
     	if (requestCode == 2) {
	        if(resultCode == RESULT_OK){
	        
	        	//Toast.makeText(getBaseContext(), "back", Toast.LENGTH_LONG).show();
	        	set_pos1 = data.getIntExtra("return-position_num", 0);
	        	fld_time[set_pos1] = data.getStringExtra("return_number");
	        	db.open();
	        	db.updateRecord(fld_name[set_pos1], fld_time[set_pos1]);
	        	db.close();
	        	display();
	        }
     	}
     	if (requestCode == 3) {
	        if(resultCode == RESULT_OK){
	        
	        	//Toast.makeText(getBaseContext(), "back", Toast.LENGTH_LONG).show();
	        	set_pos1 = data.getIntExtra("return-position", 0);
	        	fld_time[set_pos1] = data.getStringExtra("units-value");
	        	db.open();
	        	db.updateRecord(fld_name[set_pos1], fld_time[set_pos1]);
	        	db.close();
	        	display();
	        }
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
				fld_time[i] = c.getString(3);
				i++;
			}
			while(c.moveToNext());
		}
		ls_main = (ListView)findViewById(R.id.listView_entflddata);
		b = new BaseAdapter() {
			
			@Override
			public View getView(int pos, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				LayoutInflater lin = getLayoutInflater();
				View v = lin.inflate(R.layout.lv_main,null);
				TextView t = (TextView) v.findViewById(R.id.tv_fld);
				TextView t1 = (TextView) v.findViewById(R.id.tv_trgtsub);
				TextView t2 = (TextView) v.findViewById(R.id.tv_act);
				t.setText(fld_name[pos]);
				t1.setText('['+fld_target[pos]+' '+fld_unit[pos]+ ']');
				t2.setText(fld_time[pos]);
				return v;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return i;
			}
		};
		ls_main.setAdapter(b);
		ls_main.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
		//		Toast.makeText(getBaseContext(), "on click", 1500).show();
				String check = fld_unit[pos];
			//	Toast.makeText(getBaseContext(), check, 1500).show();
				if(check.equals("am") || check.equals("pm"))
				{
					Toast.makeText(getBaseContext(), "on click", 1500).show();
					Intent i1 = new Intent(getBaseContext(),TimeP.class);
					i1.putExtra("position", pos);
					startActivityForResult(i1, 1);
				}
				if(check.equals("hours"))
				{
					Intent i1 = new Intent(getBaseContext(),NumberP.class);
					i1.putExtra("position", pos);
					startActivityForResult(i1, 2);
				}
				if(check.equals("units"))
				{
					Intent i1 = new Intent(getBaseContext(),DecNumberP.class);
					i1.putExtra("position", pos);
					startActivityForResult(i1, 3);
				}
			}
		});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
    	case R.id.itemSett:
    		db.open();
    		Cursor cr = db.get_Flag();
    		if(cr != null && cr.getCount() > 0)
    		{
    			cr.moveToFirst();
    			flag = Integer.parseInt(cr.getString(0));
    		}
    		else
    			flag = 0;
    		if(flag == 0)
    		{
    			Intent i = new Intent(this,AllFields.class);
    			startActivity(i);
    		}
    		else
    		{
    			Intent i = new Intent(this,ResetActivity.class);
    			startActivity(i);
    		}
    		return true;
    	
    	default:
    		return super.onOptionsItemSelected(item);
    		
		}
	}
	
	private Boolean exit = false;
	@Override
    public void onBackPressed() {
        if (!exit){
        	Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            finish();  
        }
     /*   else {
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

    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		/*Intent i = new Intent(this,AllFields.class);
		startActivity(i);*/
		
	}
	
}
