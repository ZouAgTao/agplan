package tao.app.agplan.win;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import tao.app.agplan.BootWin;
import tao.app.agplan.R;
import tao.app.agplan.ui.Calendar;

public class WelcomeActivity extends Activity
{
	private View wel1,wel2,wel3;
	private ViewPager lyv_welcome;
	private List<View> viewList;
	
	private Button btn_startuse;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcomewin);
		
		load();
	}
	
	private void load()
	{
		lyv_welcome=(ViewPager)findViewById(R.id.lyv_welcome);
		
		LayoutInflater li=getLayoutInflater();
		
		wel1=li.inflate(R.layout.wel1,null);
		wel2=li.inflate(R.layout.wel2, null);
		wel3=li.inflate(R.layout.wel3, null);
		
		viewList=new ArrayList<View>();
		
		
		viewList.add(wel1);
		viewList.add(wel2);
		viewList.add(wel3);
		
		PagerAdapter pa=new PagerAdapter()
		{
			public boolean isViewFromObject(View argl, Object argr)
			{
				return argl==argr;
			}
			
			public int getCount()
			{
				return viewList.size();
			}
			
			public void destroyItem(ViewGroup container, int position, Object object)
			{
				container.removeView(viewList.get(position));
			}
			
			public Object instantiateItem(ViewGroup container, int position)
			{
				container.addView(viewList.get(position));
				
				
				return viewList.get(position);
			}
		};
		
		lyv_welcome.setAdapter(pa);
	}
	
	protected void onStart()
	{
		super.onStart();
		
		btn_startuse=(Button)findViewById(R.id.btn_startuse);
	}
	
	protected void onResume()
	{
		super.onResume();
		
//		btn_startuse.post(new Runnable()
//		{
//			public void run()
//			{
//				btn_startuse.setOnClickListener(new OnClickListener()
//				{
//					public void onClick(View v)
//					{
//						Intent intent=new Intent(WelcomeActivity.this,MainWin.class);
//						startActivity(intent);
//
//						finish();
//						overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
//					}
//				});
//			}
//		});
	}
	
	protected void onDestroy()
	{
		super.onDestroy();
	}
}
