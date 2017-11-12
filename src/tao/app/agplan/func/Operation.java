package tao.app.agplan.func;

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
}
