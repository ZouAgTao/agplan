package tao.app.agplan.store;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteDateBaseStore
{
	static SQLiteDatabase db;
	
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
	
	
	//�����ǲ���ʱʹ�õĺ�����������ɺ�Ҫɾ����Test��
	public static void insertTest()
	{
		ContentValues cv=new ContentValues();
		cv.put("year", "2017");
		cv.put("month", "11");
		cv.put("day", "18");
		cv.put("title", "��һ�γɹ�ʹ��SQLite��������Ŀ");
		cv.put("content", "��һ�γɹ�ʹ��SQLite��������Ŀ���о��ø��˺ø��ˣ���ɹ��ֽ���СС��һ��");
		db.insert("task", null,cv);
	}
}
