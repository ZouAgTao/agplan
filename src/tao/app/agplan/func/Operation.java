package tao.app.agplan.func;

import java.util.Calendar;

public class Operation
{
	public static void delay(long time)
	{
		try
		{
			Thread.sleep(time);
		}catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public static int howmuchday(int year,int month)
	{
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		
		return calendar.getActualMaximum(Calendar.DATE);
	}
}
