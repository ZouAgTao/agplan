package tao.app.agplan.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import tao.app.agplan.R;

public class TestWin extends Activity implements OnClickListener
{
	EditText edt_year,edt_month,edt_day,edt_title;
	TextView txv_show;
	Button btn_putin,btn_getout;
	
	@SuppressLint("SdCardPath")
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.testwin);
		
		edt_day=(EditText)findViewById(R.id.edt_day);
		edt_month=(EditText)findViewById(R.id.edt_month);
		edt_year=(EditText)findViewById(R.id.edt_year);
		edt_title=(EditText)findViewById(R.id.edt_title);
		
		txv_show=(TextView)findViewById(R.id.txv_show);
		
		btn_putin=(Button)findViewById(R.id.btn_putin);
		btn_getout=(Button)findViewById(R.id.btn_getout);
		
		SQLiteDatabase db=openOrCreateDatabase("agplan", Context.MODE_PRIVATE, null);
		if(db!=null)
		{
			tao.app.agplan.test.DBO.CreatDB(db);
			tao.app.agplan.test.DBO.CreatTable();
			btn_putin.setOnClickListener(this);
			btn_getout.setOnClickListener(this);
		}
	}
	
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_putin:
			tao.app.agplan.test.DBO.InsertData(edt_year.getText().toString(), edt_month.getText().toString(), edt_day.getText().toString(), edt_title.getText().toString());
			edt_year.setText("");
			edt_month.setText("");
			edt_day.setText("");
			edt_title.setText("");
			break;
			
		case R.id.btn_getout:
			Cursor cur=tao.app.agplan.test.DBO.Query();
			
			if(cur.moveToFirst())
			{
				for(int i=0;i<cur.getCount();i++)
				{
					cur.moveToPosition(i);
					txv_show.setText(txv_show.getText().toString()+"\n"+cur.getString(1)+cur.getString(2)+cur.getString(3)+cur.getString(4));
				}
			}
			
			cur.close();
			
			break;

		default:
			break;
		}
	}
}
