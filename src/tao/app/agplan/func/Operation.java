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
		if(month>12)
		{
			month=1;
		}
		if(month<1)
		{
			month=12;
		}
		
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
//		int date=calendar.getActualMaximum(Calendar.DATE);
//		if(month==2)
//		{
//			date++;
//		}
//		return date;
		
		return calendar.getActualMaximum(Calendar.DATE);
	}
	
	public static void addDate()
	{
		int year=tao.app.agplan.var.Info.s_year;
		int month=tao.app.agplan.var.Info.s_month;
		int day=tao.app.agplan.var.Info.s_dayofmonth;
		
		month++;
		
		if(month>12)
		{
			year++;
			month=1;
		}
		
		int yue=howmuchday(year,month);
		
		while(day>yue)
		{
			day--;
		}
		
		tao.app.agplan.var.Info.s_year=year;
		tao.app.agplan.var.Info.s_month=month;
		tao.app.agplan.var.Info.s_dayofmonth=day;
	}
	
	public static void minusDate()
	{
		int year=tao.app.agplan.var.Info.s_year;
		int month=tao.app.agplan.var.Info.s_month;
		int day=tao.app.agplan.var.Info.s_dayofmonth;
		
		month--;
		
		if(month<1)
		{
			year--;
			month=12;
		}
		
		int yue=howmuchday(year,month);
		
		while(day>yue)
		{
			day--;
		}
		
		tao.app.agplan.var.Info.s_year=year;
		tao.app.agplan.var.Info.s_month=month;
		tao.app.agplan.var.Info.s_dayofmonth=day;
	}
}
