package tao.app.agplan.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBO
{
	static SQLiteDatabase db;
	
	public static void CreatDB(SQLiteDatabase dba)
	{
		db=dba;
	}
	
	public static void CreatTable()
	{
		db.execSQL("create table if not exists task(id integer primary key autoincrement,year text,month text,day text,title text)");
	}
	
	public static void InsertData(String year,String month,String day,String title)
	{
		ContentValues cv=new ContentValues();
		cv.put("year",year);
		cv.put("month",month);
		cv.put("day",day);
		cv.put("title",title);
		
		db.insert("task", null, cv);
	}
	
	public static Cursor Query()
	{
		return db.query("task", null, null, null, null, null, null);
	}
}
