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
import tao.app.agplan.ui.Calendar;
import tao.app.agplan.ui.Calendar.OnDateChangeListener;

public class MainWin extends Activity implements OnClickListener
{
	LinearLayout ly_sidebar,ly_main,ly_mask;
	Button btn_opensidebar,btn_addnewtask;
	TextView txv_date;
	Calendar calendar;
	
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
		
		txv_date=(TextView)findViewById(R.id.txv_date);
		
		calendar=(Calendar)findViewById(R.id.calendar);
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
				tao.app.agplan.var.Info.w=w;
				tao.app.agplan.var.Info.h=h;
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
		
		calendar.setOnDateChangeListener(new OnDateChangeListener()
		{
			public void onSelectedDayChange(Calendar view, int year, int month, int dayOfMonth)
			{
				txv_date.setText(year+"Äê"+month+"ÔÂ"+dayOfMonth+"ÈÕ");
			}
		});
	}
	
	private void Init()
	{
		tao.app.agplan.var.Info.is_opensidebar=false;
		tao.app.agplan.var.Info.is_running=true;
	}
	
	private void slideout()
	{
		if(tao.app.agplan.var.Info.is_opensidebar==false)
		{
			ly_mask.setX(0);
			ly_sidebar.setX(-1*ly_sidebar.getWidth());
			
			ObjectAnimator oa=ObjectAnimator.ofFloat(ly_sidebar, "X",0);
			oa.setDuration(300);
			oa.start();
			
			tao.app.agplan.var.Info.is_opensidebar=true;
		}
	}
	
	private void slideback()
	{
		if(tao.app.agplan.var.Info.is_opensidebar)
		{
			ObjectAnimator oa=ObjectAnimator.ofFloat(ly_sidebar, "X",-1*ly_sidebar.getWidth());
			oa.setDuration(200);
			oa.start();
			
			ly_mask.setX(-1*w);
			
			tao.app.agplan.var.Info.is_opensidebar=false;
		}
	}
	
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_opensidebar:
			slideout();
			break;
			
		case R.id.btn_addnewtask:
			startActivity(new Intent(this, NewTaskWin.class));
			overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			break;
			
		case R.id.ly_mask:
			slideback();
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
				slideback();
				return true;
			}
			
			moveTaskToBack(false);
			
			return true;
        }
        return super.onKeyDown(keyCode, event);
	}
}
