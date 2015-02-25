package com.example.dialogwidget;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void OnClickPress(View v){
		switch(v.getId()){
		case R.id.btn_normal_dialog:
		     new CustomDialog.Builder(this).setTitle("提示").setMessage("对话框测试?").setPositiveButton("确定", null).setNegativeButton("取消", null).show(); 
		     break;
		case R.id.btn_progress_dialog:
			 new MyProgressDialog(this).show();
		     break;
		default:
			break;
		}
	}

}
