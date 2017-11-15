package tao.app.agplan.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("ClickableViewAccessibility")
public class Calendar extends View
{
	int width=0;
	int height=0;
	int gw=0;
	int gh=0;
	int x=0;
	int y=0;
	int cx=3;
	int cy=3;
	int yue=31;
	int dow=0;
	int lx=0;
	int ly=0;
	
	float r=0;
	
	long from=0;
	long left=0;
	long right=0;
	
	boolean first=true;
	boolean start=true;
	boolean[] has_task=new boolean[31];
	
	String weekname[]={"日","一","二","三","四","五","六"};
	
	Paint text;
	Paint redtext;
	Paint line;
	Paint today;
	Paint choose;
	Paint taskpoint;
	
	private OnDateChangeListener listener;
	
	public interface OnDateChangeListener
	{
		void onSelectedDayChange(Calendar view, int year, int month, int dayOfMonth);
	}
	
	public void setOnDateChangeListener(OnDateChangeListener listener)
	{
		this.listener=listener;
	}
	
	private void Init()
	{
		getcxandcy();
		getTaskPoint();
		MakePaint();
		other();
	}

	private void other()
	{
		first=true;
		start=true;
	}

	private void MakePaint()
	{
		text=new Paint();
		text.setTextAlign(Paint.Align.CENTER);
		text.setColor(Color.BLACK);
		text.setStrokeWidth(1);
		text.setStyle(Paint.Style.FILL_AND_STROKE);
		text.setAntiAlias(true);
		
		line=new Paint(text);
		line.setARGB(255, 25, 160, 240);
		line.setStrokeWidth(5);
		
		today=new Paint(text);
		today.setStrokeWidth(3);
		today.setARGB(255, 25, 160, 240);
		
		choose=new Paint(today);
		choose.setAlpha(140);
		choose.setStyle(Paint.Style.FILL_AND_STROKE);
		
		taskpoint=new Paint(choose);
		taskpoint.setAlpha(255);
		
		today.setStyle(Paint.Style.STROKE);
	}
	
	public void updateInfo()
	{
		getcxandcy();
		getTaskPoint();
	}
	
	public void updateView()
	{
		invalidate();
	}
	
	private void getTaskPoint()
	{
		//这里到时候做好了store部分要修改成真的
		yue=tao.app.agplan.func.Operation.howmuchday(tao.app.agplan.var.Info.s_year,tao.app.agplan.var.Info.s_month);
		
		//模拟部分
		boolean a=true;
		for(int i=0;i<yue;i++)
		{
			has_task[i]=a;
			a=!a;
		}
		//模拟部分
		
	}
	
	private void getcxandcy()
	{
		int cur=1;
		int t_y=tao.app.agplan.var.Info.s_year;
		int t_m=tao.app.agplan.var.Info.s_month;
		int t_d=tao.app.agplan.var.Info.s_dayofmonth;
		
		dow=(1+2*t_m+3*(t_m+1)/5+t_y+t_y/4-t_y/100+t_y/400)%7;
		dow++;
		
		if(dow==7)
		{
			dow=0;
		}
		
		for(int i=dow+1;i<=7;i++,cur++)
		{
			if(cur==t_d)
			{
				cx=i;
				cy=2;
				return;
			}
		}
		for(int i=3;i<=7;i++)
		{
			
			for(int j=1;j<=7;j++,cur++)
			{
				if(cur==t_d)
				{
					cx=j;
					cy=i;
					return;
				}
			}
		}
	}
	
	private void d_week(Canvas c,Paint p)
	{
		y=gh/7*5;
		
		c.drawText(weekname[0],x,y,redtext);
		x+=gw;
		
		for(int i=1;i<=5;i++,x+=gw)
		{
			c.drawText(weekname[i],x,y,p);
		}
		
		c.drawText(weekname[6],x,y,redtext);
	}
	
	private void d_line(Canvas c, Paint p)
	{
		if(!first)
		{
			c.drawLine(0, 5, width, 5, p);
			c.drawLine(0, gh, width, gh, p);
			c.drawLine(0, height-3, width, height-3, p);
			c.drawLine(3, 0, 3, height, p);
			c.drawLine(width-3, 0, width-3, height, p);
			return;
		}
		
		if(start)
		{
			left=System.currentTimeMillis();
			start=false;
		}
		
		right=System.currentTimeMillis();
		
		float per=((float)(right-left))/3000;
		
		if(per>1)
		{
			first=false;
		}
		
		c.drawLine(0, 5, width*per, 5, p);
		c.drawLine(0, gh, width*per, gh, p);
		c.drawLine(0, height-3, width*per, height-3, p);
		c.drawLine(3, 0, 3, height*per, p);
		c.drawLine(width-3, 0, width-3, height*per, p);
		
		invalidate();
	}
	
	private void d_day(Canvas c, Paint p)
	{
		int cur=1;
		int t_y=tao.app.agplan.var.Info.s_year;
		int t_m=tao.app.agplan.var.Info.s_month;
		
		dow=(1+2*t_m+3*(t_m+1)/5+t_y+t_y/4-t_y/100+t_y/400)%7;
		dow++;
		
		if(dow==7)
		{
			dow=0;
		}
		
		x=gw/2+gw*dow;
		y=gh/5*3+gh;
		
		for(int i=dow+1;i<=7;i++,x+=gw,cur++)
		{
			c.drawText(cur+"", x, y, p);
			
			if(has_task[cur-1])
			{
				c.drawCircle(x,y+gh/8 ,gh/15,taskpoint);
			}
		}
		
		x=gw/2;
		y=y+gh;
		
		yue=tao.app.agplan.func.Operation.howmuchday(t_y, t_m);
		
		int dom=tao.app.agplan.var.Info.dayofmonth;
		
		for(int i=3;i<=7;i++,y+=gh)
		{
			for(int j=1;j<=7;j++,x+=gw,cur++)
			{
				if(cur>yue)
				{
					break;
				}
				
				if((cur==dom)&&(tao.app.agplan.var.Info.s_month==tao.app.agplan.var.Info.month)&&(tao.app.agplan.var.Info.s_year==tao.app.agplan.var.Info.year))
				{
					c.drawCircle((float)((j-0.5)*gw),(float)((i-0.5)*gh),r, today);
				}
				
				c.drawText(cur+"", x, y, p);
				
				if(has_task[cur-1])
				{
					c.drawCircle(x,y+gh/8,gh/15,taskpoint);
				}
			}
			
			x=gw/2;
			
			if((cur-1)>=yue)
			{
				break;
			}
		}
		
		x=gw/2;
		y=gh/2;
	}
	
	private void d_choose(Canvas c, Paint p)
	{
		float rad=(System.currentTimeMillis()-from)*(float)0.25;
		
		if(rad>r)
		{
			c.drawCircle((float)((cx-0.5)*gw),(float)((cy-0.5)*gh),r, p);
			return;
		}
		
		if((lx==cx)&&(ly==cy))
		{
			c.drawCircle((float)((cx-0.5)*gw),(float)((cy-0.5)*gh),r, p);
			return;
		}
		
		if((lx!=cx)||(ly!=cy))
		{
			c.drawCircle((float)((lx-0.5)*gw),(float)((ly-0.5)*gh),(r-rad), p);
		}
		
		c.drawCircle((float)((cx-0.5)*gw),(float)((cy-0.5)*gh),rad, p);
		
		invalidate();
	}
	
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		d_week(canvas,text);
		d_day(canvas,text);
		d_choose(canvas,choose);
		d_line(canvas,line);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		width=MeasureSpec.getSize(widthMeasureSpec);
		height=MeasureSpec.getSize(heightMeasureSpec);
		gw=width/7;
		gh=height/7;
		x=gw/2;
		y=gh/2;
		
		r=((float)gh)/13*6;
		
		today.setStrokeWidth(gw/15);
		text.setTextSize(width/15);
		
		redtext=new Paint(text);
		redtext.setColor(Color.RED);
	}
	
	public Calendar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		Init();
	}
	public Calendar(Context context)
	{
		this(context, null);
	}
	
	public boolean onTouchEvent(MotionEvent event)
	{
		switch(event.getAction())
		{	
		case MotionEvent.ACTION_UP:
			
			int mx=(int)(event.getX())/gw+1;
			int my=(int)(event.getY())/gh+1;
			
			if((my<=1)||(my>7))
			{
				break;
			}
			
			int week=dow;
			
			if((my==2)&&(mx<week+1))
			{
				break;
			}
			
			if((35-week-yue)<0)
			{
				if((mx>(week+yue-35))&&(my==7))
				{
					break;
				}
			}
			else
			{
				if(my==7)
				{
					break;
				}
				
				if((mx>(week+yue-28))&&(my==6))
				{
					break;
				}
			}
			
			lx=cx;
			ly=cy;
			
			cx=mx;
			cy=my;
			
			setCheckedDate();
			
			from=System.currentTimeMillis();
			
			updateInfo();
			
			invalidate();
			
			if(listener!=null)
			{
				listener.onSelectedDayChange(this, tao.app.agplan.var.Info.s_year,tao.app.agplan.var.Info.s_month,tao.app.agplan.var.Info.s_dayofmonth);
			}
			
			break;
			
		}
		
		return true;
	}
	
	private void setCheckedDate()
	{
		int cur=1;
		
		if(dow==7)
		{
			dow=0;
		}
		
		for(int i=dow+1;i<=7;i++,cur++)
		{
			if((i==cx)&&(cy==2))
			{
				tao.app.agplan.var.Info.s_dayofmonth=cur;
				return;
			}
		}
		for(int i=3;i<=7;i++)
		{
			
			for(int j=1;j<=7;j++,cur++)
			{
				if((i==cy)&&(j==cx))
				{
					tao.app.agplan.var.Info.s_dayofmonth=cur;
					return;
				}
			}
		}
		
	}

}
