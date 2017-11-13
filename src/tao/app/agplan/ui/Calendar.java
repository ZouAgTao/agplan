package tao.app.agplan.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class Calendar extends View
{
	int width=0;
	int height=0;	
	
	private void Init()
	{
		
	}
	
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
	}
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width=MeasureSpec.getSize(widthMeasureSpec);
		height=MeasureSpec.getSize(heightMeasureSpec);
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
