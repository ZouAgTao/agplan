package tao.app.agplan.ui;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

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
	
	boolean[] has_task=new boolean[31];
	
	String weekname[]={"日","一","二","三","四","五","六"};
	
	Paint text;
	Paint line;
	Paint today;
	Paint choose;
	Paint taskpoint;
	
	private void Init()
	{
		getcxandcy();
		getTaskPoint();
		MakePaint();
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
		line.setStrokeWidth(3);
		
		today=new Paint(line);
		today.setARGB(255, 25, 160, 240);
		today.setStyle(Paint.Style.STROKE);
		
		choose=new Paint(today);
		choose.setARGB(125, 25, 160, 240);
		choose.setStyle(Paint.Style.FILL_AND_STROKE);
		
		taskpoint=new Paint(choose);
		taskpoint.setARGB(255, 25, 160, 240);
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
		boolean a=true;
		for(int i=0;i<yue;i++)
		{
			has_task[i]=a;
			a=!a;
		}
	}
	
	private void getcxandcy()
	{
		int cur=1;
		int t_y=tao.app.agplan.var.Info.s_year;
		int t_m=tao.app.agplan.var.Info.s_month;
		int t_d=tao.app.agplan.var.Info.s_dayofmonth;
		dow= (1+2*t_m+3*(t_m+1)/5+t_y+t_y/4-t_y/100+t_y/400)%7;
		dow++;
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
		y=gh/5*4;
		for(int i=0;i<7;i++,x+=gw)
		{
			c.drawText(weekname[i],x,y,p);
		}
	}
	
	private void d_line(Canvas c, Paint p)
	{
		c.drawLine(0, 2, width, 2, p);
		c.drawLine(0, gh, width, gh, p);
		c.drawLine(0, height-2, width, height-2, p);
		c.drawLine(2, 0, 2, height, p);
		c.drawLine(width-2, 0, width-2, height, p);
	}
	
	private void d_day(Canvas c, Paint p)
	{
		int cur=1;
		int t_y=tao.app.agplan.var.Info.s_year;
		int t_m=tao.app.agplan.var.Info.s_month;
		int t_d=tao.app.agplan.var.Info.s_dayofmonth;
		
		
		dow= (1+2*t_m+3*(t_m+1)/5+t_y+t_y/4-t_y/100+t_y/400)%7;
		dow++;
		
		x=gw/2+gw*dow;
		y=gh/5*4+gh;
		
		for(int i=dow;i<7;i++,x+=gw,cur++)
		{
			c.drawText(cur+"", x, y, p);
			if(has_task[cur-1])
			{
				c.drawCircle(x,y+gh/8 ,gh/15,taskpoint );
			}
		}
		
		x=gw/2;
		y=y+gh;
		
		yue=tao.app.agplan.func.Operation.howmuchday(t_y, t_m);
		
		for(int i=3;i<=7;i++,y+=gh)
		{
			for(int j=1;j<=7;j++,x+=gw,cur++)
			{
				if(cur==t_d)
				{
					c.drawRect((j-1)*gw+5,(i-1)*gh+5, j*gw-5, i*gh-5, today);
					
				}
				
				c.drawText(cur+"", x, y, p);
				if(has_task[cur-1])
				{
					c.drawCircle(x,y+gh/8,gh/15,taskpoint );
				}
				if(cur>=yue)
				{
					break;
				}
			}
			x=gw/2;
			if(cur>=yue)
			{
				break;
			}
		}
		
		for(int i=1;i<=6;i++)
		{
			c.drawLine(i*gw, 0, i*gw, height, p);
		}
		
		for(int i=2;i<=6;i++)
		{
			c.drawLine(0, i*gh, width, i*gh, p);
		}
		
		x=gw/2;
		y=gh/2;
	}
	
	private void d_choose(Canvas c, Paint choose)
	{
		c.drawRect((cx-1)*gw,(cy-1)*gh, cx*gw,cy*gh,choose);
	}
	
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		d_week(canvas,text);
		d_line(canvas,line);
		d_day(canvas,text);
		d_choose(canvas,choose);
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
		text.setTextSize(width/15);
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

}
