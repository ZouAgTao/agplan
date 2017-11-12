package tao.app.agplan;

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
		//������ͳ�ʼ��
		isFirstOpen=false;//���ﴦ���ǲ��ǵ�һ��ʹ�ñ���������޸ġ�
	}
	
	protected void onResume()
	{
		super.onResume();
		//���ﴦ�������ж�
		
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
		//������������������
		
		super.onDestroy();
	}
}
