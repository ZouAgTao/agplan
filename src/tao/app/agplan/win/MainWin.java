package tao.app.agplan.win;

import java.util.ArrayList;
import java.util.List;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.*;
import tao.app.agplan.R;
import tao.app.agplan.ui.Calendar;
import tao.app.agplan.ui.Calendar.OnDateChangeListener;

public class MainWin extends Activity implements OnClickListener
{
	LinearLayout ly_sidebar,ly_main,ly_mask;
	Button btn_opensidebar,btn_addnewtask;
	TextView txv_date;
	ListView tasklist;
	
	private Calendar c1,c2,c3;
//	private Calendar nowc;
	private ViewPager viewPager;
	private List<Calendar> viewList;
	
	int w=0;
	int h=0;
	int lastpos=Integer.MAX_VALUE/2;
	
	boolean is_jump=false;
	
	//-----此处为测试代码，开发结束后要删除【test】
	
	String[] testtext;
	ArrayAdapter<String>adpt;
	
	//------------------------------------------
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainwin);
		
		Init();
		bindID();
		viewPagerSet();
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
		winShow();
	}
	
	private void winShow()
	{
		txv_date.setText(tao.app.agplan.var.Info.s_year+"年"+tao.app.agplan.var.Info.s_month+"月"+tao.app.agplan.var.Info.s_dayofmonth+"日");
		
		//-----此处为测试代码，开发结束后要删除【test】
		
		testtext=new String[]{"任务1","任务2","任务3","任务4","任务5","任务6","任务7","任务8","任务9","任务10"};
		
		//------------------------------------------
		
		adpt=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testtext);
		
		tasklist.setAdapter(adpt);
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
		
		viewPager=(ViewPager)findViewById(R.id.viewpager);
		
		btn_opensidebar=(Button)findViewById(R.id.btn_opensidebar);
		btn_addnewtask=(Button)findViewById(R.id.btn_addnewtask);
		
		txv_date=(TextView)findViewById(R.id.txv_date);
		tasklist=(ListView)findViewById(R.id.tasklist);
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
		
		txv_date.post(new Runnable()
		{
			public void run()
			{
				txv_date.setTextSize(tao.app.agplan.var.Info.w/40);
				txv_date.setTypeface(tao.app.agplan.var.Info.font);
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
		
		txv_date.setOnClickListener(this);
		
		ly_mask.setOnClickListener(this);
	}
	
	private void Init()
	{
		tao.app.agplan.var.Info.is_opensidebar=false;
		tao.app.agplan.var.Info.is_running=true;
	}
	
	@SuppressWarnings("deprecation")
	private void viewPagerSet()
	{
		c1=new Calendar(this);
		c2=new Calendar(this);
		c3=new Calendar(this);
		
		viewList=new ArrayList<Calendar>();
		
		viewList.add(c1);
		viewList.add(c2);
		viewList.add(c3);
		
		PagerAdapter pa=new PagerAdapter()
		{
			public boolean isViewFromObject(View argl, Object argr)
			{
				return argl==argr;
			}
			
			public int getCount()
			{
				return Integer.MAX_VALUE;
			}
			
			public void destroyItem(ViewGroup container,int position,Object object)
			{
				
			}
			
			public Object instantiateItem(ViewGroup container,int position)
			{
				if(position>lastpos)
				{
					tao.app.agplan.func.Operation.addDate();
				}
				else if(position<lastpos)
				{
					tao.app.agplan.func.Operation.minusDate();
				}
				
				for(int i=0;i<3;i++)
				{
					viewList.get(i).updateInfo();
					viewList.get(i).updateView();
				}
				lastpos=position;
				
				position%=viewList.size();
				
	             if (position<0)
	             {
	                 position=viewList.size()+position;
	             }
	             
	             View view=viewList.get(position);
	             
	             ViewParent vp=view.getParent();
	             
	             if (vp!=null)
	             {
	                 ViewGroup parent=(ViewGroup)vp;
	                 parent.removeView(view);
	             }
	             
	             container.addView(view);
	             
	             is_jump=false;
	             
//	             updatelist();
	             
	             return view;
			}
		};
		
		viewPager.setAdapter(pa);
		viewPager.setCurrentItem(Integer.MAX_VALUE/2);
	}
	
//	private void updatelist()
//	{
//		txv_date.setText(tao.app.agplan.var.Info.s_year+"年"+tao.app.agplan.var.Info.s_month+"月"+tao.app.agplan.var.Info.s_dayofmonth+"日");
//		adpt.clear();
//	}
	
	private void slideout()
	{
		if(tao.app.agplan.var.Info.is_opensidebar==false)
		{
			ly_mask.setX(0);
			ly_sidebar.setX(-1*ly_sidebar.getWidth());
			
			ObjectAnimator oa=ObjectAnimator.ofFloat(ly_sidebar, "X",0);
			ObjectAnimator ob=ObjectAnimator.ofFloat(ly_main, "x", ly_sidebar.getWidth());
			
			AnimatorSet anim=new AnimatorSet();
			anim.play(ob).with(oa);
			anim.setDuration(400);
			anim.start();
			
			tao.app.agplan.var.Info.is_opensidebar=true;
		}
	}
	
	private void slideback()
	{
		if(tao.app.agplan.var.Info.is_opensidebar)
		{
			ObjectAnimator oa=ObjectAnimator.ofFloat(ly_sidebar, "X",-1*ly_sidebar.getWidth());
			ObjectAnimator ob=ObjectAnimator.ofFloat(ly_main, "x", 0);
			
			AnimatorSet anim=new AnimatorSet();
			anim.play(ob).with(oa);
			anim.setDuration(300);
			anim.start();
			
			ly_mask.setX(w);
			
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
			
		case R.id.txv_date:
			tao.app.agplan.var.Info.s_year=tao.app.agplan.var.Info.year;
			tao.app.agplan.var.Info.s_month=tao.app.agplan.var.Info.month;
			tao.app.agplan.var.Info.s_dayofmonth=tao.app.agplan.var.Info.dayofmonth;
			
			for(int i=0;i<3;i++)
			{
				viewList.get(i).updateInfo();
				viewList.get(i).updateView();
			}
			txv_date.setText(tao.app.agplan.var.Info.s_year+"年"+tao.app.agplan.var.Info.s_month+"月"+tao.app.agplan.var.Info.s_dayofmonth+"日");
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
