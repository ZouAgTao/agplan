package tao.app.agplan.store;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class SQLiteDateBaseStore
{
	static SQLiteDatabase db;
	
	public static void deltask(String title,String content)
	{
		db.execSQL("create table if not exists task(id integer primary key autoincrement,year integer,month integer,day integer,title text,content text)");
		
		db.delete("task","title=? and content=? and year=? and month=? and day=?", new String[]{title,content,String.valueOf(tao.app.agplan.var.Info.s_year),String.valueOf(tao.app.agplan.var.Info.s_month),String.valueOf(tao.app.agplan.var.Info.s_dayofmonth)});
		
		
	}
	
	public static void gettask()
	{
		db.execSQL("create table if not exists task(id integer primary key autoincrement,year integer,month integer,day integer,title text,content text)");
		
		Cursor cur=db.query("task", new String[]{"title","content"}, "year=? and month=? and day=?", new String[]{String.valueOf(tao.app.agplan.var.Info.s_year),String.valueOf(tao.app.agplan.var.Info.s_month),String.valueOf(tao.app.agplan.var.Info.s_dayofmonth)}, null, null, null);
		
		if(cur==null)
		{
			return;
		}
		
		ArrayList<String> title=new ArrayList<String>();
		ArrayList<String> content=new ArrayList<String>();
		
		if(cur.moveToFirst())
		{
			for(int i=0;i<cur.getCount();i++)
			{
				cur.moveToPosition(i);
				title.add(cur.getString(0));
				content.add(cur.getString(1));
			}
		}
		
		cur.close();
		
		tao.app.agplan.var.Task.title=new String[title.size()];
		tao.app.agplan.var.Task.content=new String[content.size()];
		title.toArray(tao.app.agplan.var.Task.title);
		content.toArray(tao.app.agplan.var.Task.content);
	}
	
	public static void puttask(String title,String content)
	{
		db.execSQL("create table if not exists task(id integer primary key autoincrement,year integer,month integer,day integer,title text,content text)");
		
		ContentValues cv=new ContentValues();
		cv.put("year", tao.app.agplan.var.Info.s_year);
		cv.put("month",tao.app.agplan.var.Info.s_month);
		cv.put("day", tao.app.agplan.var.Info.s_dayofmonth);
		cv.put("title",title);
		cv.put("content", content);
		db.insert("task", null,cv);
	}
	
	public static void openOrCreatDB(SQLiteDatabase dbname)
	{
		db=dbname;
	}
	
	public static boolean isFirstUsed()
	{
		db.execSQL("create table if not exists info(id integer primary key autoincrement,info_item text,info_value text)");
		
		Cursor cur=db.query("info",new String[]{"info_item","info_value"},"info_item=?",new String[]{"version"}, null, null, null);
		if(cur.getCount()<=0)
		{
			cur.close();
			ContentValues cv=new ContentValues();
			cv.put("info_item", "version");
			cv.put("info_value", tao.app.agplan.var.Info.version+"");
			db.insert("info", null,cv);
			return true;
		}
		
		cur.moveToFirst();
		int ver=Integer.parseInt(cur.getString(1));
		cur.close();
		
		if(ver<tao.app.agplan.var.Info.version)
		{
			ContentValues cv=new ContentValues();
			cv.put("info_value", tao.app.agplan.var.Info.version+"");
			db.update("info", cv, "info_item=?", new String[]{"version"});
			return true;
		}
		return false;
	}

	public static Cursor getcur(String table,int year,int month)
	{	
		db.execSQL("create table if not exists task(id integer primary key autoincrement,year integer,month integer,day integer,title text,content text)");
		
		return db.query(table, new String[]{"day"}, "year=? and month=?", new String[]{String.valueOf(year),String.valueOf(month)}, null, null, null);
	}
}
