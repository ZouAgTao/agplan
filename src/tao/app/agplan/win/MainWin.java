package tao.app.agplan.win;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import tao.app.agplan.R;

public class MainWin extends Activity implements OnClickListener
{
	LinearLayout ly_sidebar,ly_main,ly_mask;
	Button btn_opensidebar,btn_addnewtask;
	TextView txv_data;
	
	int w=0;
	int h=0;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainwin);
		
		Init();
		bindID();
	}
	
	protected void onStart()
	{
		super.onStart();
		
		addListener();
	}
	
	protected void onResume()
	{
		super.onResume();
		
		modifyUI();
	}
	
	protected void onDestroy()
	{
		tao.app.agplan.var.Info.is_running=false;
		super.onDestroy();
	}
	
	private void bindID()
	{
		ly_main=(LinearLayout)findViewById(R.id.ly_main);
		ly_sidebar=(LinearLayout)findViewById(R.id.ly_sidebar);
		ly_mask=(LinearLayout)findViewById(R.id.ly_mask);
		
		btn_opensidebar=(Button)findViewById(R.id.btn_opensidebar);
		btn_addnewtask=(Button)findViewById(R.id.btn_addnewtask);
		
		txv_data=(TextView)findViewById(R.id.txv_data);
	}
	
	private void modifyUI()
	{
		btn_opensidebar.post(new Runnable()
		{
			public void run()
			{
				RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)btn_opensidebar.getLayoutParams();
				params.width=btn_opensidebar.getHeight();
				btn_opensidebar.setLayoutParams(params);
			}
		});
		
		btn_addnewtask.post(new Runnable()
		{
			public void run()
			{
				RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)btn_addnewtask.getLayoutParams();
				params.width=btn_addnewtask.getHeight();
				btn_addnewtask.setLayoutParams(params);
			}
		});
		
		ly_main.post(new Runnable()
		{
			public void run()
			{
				w=ly_main.getWidth();
				h=ly_main.getHeight();
			}
		});
		
		ly_sidebar.post(new Runnable()
		{
			public void run()
			{
				AbsoluteLayout.LayoutParams params=(AbsoluteLayout.LayoutParams)ly_sidebar.getLayoutParams();
				params.width=w/4*3;
				ly_sidebar.setLayoutParams(params);
			}
		});
	}

	private void addListener()
	{
		btn_opensidebar.setOnClickListener(this);
		btn_addnewtask.setOnClickListener(this);
		
		ly_mask.setOnClickListener(this);
	}
	
	private void Init()
	{
		tao.app.agplan.var.Info.is_opensidebar=false;
		tao.app.agplan.var.Info.is_running=true;
	}
	
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_opensidebar:
			if(tao.app.agplan.var.Info.is_opensidebar==false)
			{
				ly_sidebar.setX(-1*ly_sidebar.getWidth());
				ly_sidebar.setY(0);
				ly_mask.setX(0);
				ly_mask.setY(0);
				
				ObjectAnimator oa1=ObjectAnimator.ofFloat(ly_sidebar, "X", 0);
				oa1.setDuration(500);
				
				ObjectAnimator oa2=ObjectAnimator.ofFloat(ly_mask, "Alpha", 0,(float)0.7);
				oa2.setDuration(500);
				
				oa1.start();
				oa2.start();
				
				tao.app.agplan.var.Info.is_opensidebar=true;
			}
			break;
			
		case R.id.btn_addnewtask:
			startActivity(new Intent(this, NewTaskWin.class));
			overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			break;
			
		case R.id.ly_mask:
			if(tao.app.agplan.var.Info.is_opensidebar)
			{
				ObjectAnimator oa=ObjectAnimator.ofFloat(ly_sidebar, "X", -1*ly_sidebar.getWidth());
				oa.setDuration(500);
				
				oa.start();
				
				ly_mask.setX(-1*ly_mask.getWidth());
				
				tao.app.agplan.var.Info.is_opensidebar=false;
			}
			break;
		default:
			
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			if(tao.app.agplan.var.Info.is_opensidebar)
			{
				ObjectAnimator oa=ObjectAnimator.ofFloat(ly_sidebar, "X", -1*ly_sidebar.getWidth());
				oa.setDuration(350);
				
				oa.start();
				
				ly_mask.setX(-1*ly_mask.getWidth());
				
				tao.app.agplan.var.Info.is_opensidebar=false;
				
				return false;
			}
			
			moveTaskToBack(false);
			
			return true;
        }
        return super.onKeyDown(keyCode, event);
	}
}
