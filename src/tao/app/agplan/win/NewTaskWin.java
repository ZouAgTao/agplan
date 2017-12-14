package tao.app.agplan.win;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import tao.app.agplan.R;


public class NewTaskWin extends Activity
{
	Button btn_addtask;
	EditText edt_title,edt_content;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newtaskwin);
		
		
		btn_addtask=(Button)findViewById(R.id.btn_addtask);
		edt_title=(EditText)findViewById(R.id.edt_title);
		edt_content=(EditText)findViewById(R.id.edt_content);
	}
	
	protected void onStart()
	{
		super.onStart();
		
		btn_addtask.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				tao.app.agplan.store.SQLiteDateBaseStore.puttask(edt_title.getText().toString(), edt_content.getText().toString());
				finish();
			}
		});
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
