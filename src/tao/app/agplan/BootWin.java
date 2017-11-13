package tao.app.agplan;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class BootWin extends Activity
{
	boolean isFirstOpen=true;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bootwin);
	}
	
	protected void onStart()
	{
		super.onStart();
		//这里检查和初始化
		Calendar c=Calendar.getInstance();
		tao.app.agplan.var.Info.year=c.get(Calendar.YEAR);
		tao.app.agplan.var.Info.month=c.get(Calendar.MONTH)+1;
		tao.app.agplan.var.Info.dayofmonth=c.get(Calendar.DAY_OF_MONTH);
		tao.app.agplan.var.Info.dayofweek=c.get(Calendar.DAY_OF_WEEK);
		tao.app.agplan.var.Info.dayofyear=c.get(Calendar.DAY_OF_YEAR);
		
		isFirstOpen=false;//这里处理是不是第一次使用本软件【需修改】
	}
	
	protected void onResume()
	{
		super.onResume();
		//这里处理启动判断
		
		Intent intent;
		if(isFirstOpen)
		{
			intent=new Intent(BootWin.this, tao.app.agplan.win.WelcomeActivity.class);
		}
		else
		{
			intent=new Intent(BootWin.this, tao.app.agplan.win.MainWin.class);
		}
		
		if(tao.app.agplan.var.Info.is_running)
		{
			startActivity(intent);

			finish();
			return;
		}
		
		new Handler().postDelayed(new Runnable()
		{
			public void run()
			{
				Intent intent;
				if(isFirstOpen)
				{
					intent=new Intent(BootWin.this, tao.app.agplan.win.WelcomeActivity.class);
				}
				else
				{
					intent=new Intent(BootWin.this, tao.app.agplan.win.MainWin.class);
				}
				startActivity(intent);

				finish();
				overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			}
		},2000);
	}
	
	
	
	protected void onDestroy()
	{
		//这里做最终启动处理
		
		super.onDestroy();
	}
}
