package tao.app.agplan.win;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import tao.app.agplan.R;

public class NewTaskWin extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newtaskwin);
	}
	
	protected void onStart()
	{
		super.onStart();
	}
	
	protected void onResume()
	{
		super.onResume();
	}
	
	protected void onDestroy()
	{
		super.onDestroy();
	}
	
}
