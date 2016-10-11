package com.example.timemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.SmsManager;
import android.widget.Toast;
public class Database {
	public static final String KEY_FIELD_NAME = "Field_Name";
	public static final String KEY_FIELD_UNIT = "Field_Unit";
	public static final String KEY_FIELD_TARGET = "Field_Target";
	public static final String KEY_FIELD_TIME = "Field_Time";
	public static final String KEY_FIELD_DATE = "Field_Date";
	public static final String KEY_FLAG = "Field_Flag";
	private static final String DATABASE_NAME = "Smart_Scheduler_DB";
	private static final String DATABASE_TABLE = "Fields_Record";
	private static final String DATABASE_TABLE1 = "Flags";
	private static final String DATABASE_CREATE = 	"Create table if not exists Fields_Record(Field_Name VARCHAR primary key," + "Field_Unit VARCHAR, Field_Target VARCHAR, Field_Time VARCHAR,Field_Date VARCHAR);";
	private static final String DATABASE_CREATE1 = 	"Create table if not exists Flags(Field_Flag integer);";
	private static final int DATABASE_VERSION = 1;
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	public Database(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try
			{
				
				db.execSQL(DATABASE_CREATE);
				db.execSQL(DATABASE_CREATE1);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public Database open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	public void close()
	{
		DBHelper.close();
	}
	public long insertRecord(String fn, String fu, String tg, String ft)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_FIELD_NAME, fn);
		initialValues.put(KEY_FIELD_TARGET, tg);
		initialValues.put(KEY_FIELD_UNIT, fu);
		initialValues.put(KEY_FIELD_TIME, ft);
		initialValues.put(KEY_FIELD_DATE, 0);
		return db.insert(DATABASE_TABLE, null,initialValues);
		
	}
	public void insertRecord1(int flg)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_FLAG, flg);
		db.insert(DATABASE_TABLE1, null,initialValues);
	}
	public void updateRecord(String fn,String ft)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_FIELD_TIME, ft);
	//	initialValues.put(KEY_FIELD_DATE, dt);
	//	String where="ID = ?";
		db.update(DATABASE_TABLE, initialValues, KEY_FIELD_NAME + "=?", new String[] {fn});
		
	}
	public void updateallRecord(String ft, String dt)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_FIELD_TIME, ft);
		initialValues.put(KEY_FIELD_DATE, dt);
		db.update(DATABASE_TABLE, initialValues, null, null);
		
	}
	
	public void updateRecord1(String cfn,String fn,String fu,String tg,String ft)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_FIELD_NAME, fn);
		initialValues.put(KEY_FIELD_UNIT, fu);
		initialValues.put(KEY_FIELD_TARGET, tg);
		initialValues.put(KEY_FIELD_TIME, ft);
	//	String where="ID = ?";
		db.update(DATABASE_TABLE, initialValues, KEY_FIELD_NAME + "=?", new String[] {cfn});
		
	}
	/*public Cursor getRecord2(long grpid)
	{
			Cursor cr = db.query(true,DATABASE_TABLE, new String[]{KEY_Grp_Id,KEY_EXPENSE_ID,KEY_EXPENSE_NAME},KEY_Grp_Id +  "=" + grpid, null, null, null, null, null);
			if(cr != null)
			{
				cr.moveToFirst();
			}
			return cr;
	  }*/
	
	public void deleteRecord(String fn)
	{
		String[] whereargs = new String[]{fn};
		db.delete(DATABASE_TABLE, KEY_FIELD_NAME + "=?", whereargs);
	}
	public void deleteAllRecord()
	{
		db.delete(DATABASE_TABLE, null, null);
	}
	public void deleteAllRecord1()
	{
		db.delete(DATABASE_TABLE1, null, null);
	}
	public Cursor getAllRecord()
	{

		return db.query(DATABASE_TABLE, new String[]{KEY_FIELD_NAME,KEY_FIELD_UNIT,KEY_FIELD_TARGET, KEY_FIELD_TIME,KEY_FIELD_DATE},null, null, null, null, null);
	
	}
	public Cursor get_Flag()
	{
		return db.query(DATABASE_TABLE1, new String[]{KEY_FLAG},null, null, null, null, null);
	}

}


