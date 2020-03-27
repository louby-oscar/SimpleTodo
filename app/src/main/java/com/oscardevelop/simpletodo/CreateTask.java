package com.oscardevelop.simpletodo;


import android.app.*;
import android.os.*;
import android.support.v7.app.*;
import java.text.*;
import java.util.*;
import android.text.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.support.design.widget.*;
import android.content.*;
import android.support.v7.app.ActionBar;

public class CreateTask extends AppCompatActivity
{
	EditText ed, ej;
	Button btn;
    DatePickerDialog picker;
	private TodoData dat;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);
		
		ActionBar actionBar = getSupportActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);
		
		//cld = Calendar.getInstance();

		ed = (TextInputEditText) findViewById(R.id.date);
		ej = (TextInputEditText) findViewById(R.id.taskname);
		
		//tv.setText(cld.get(Calendar.DAY_OF_MONTH) +"/" + cld.get(Calendar.MONTH) + "/"+ cld.get(Calendar.YEAR));


		ed.setInputType(InputType.TYPE_NULL);
        ed.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				    final Calendar cldr = Calendar.getInstance();



					int day = cldr.get(Calendar.DAY_OF_MONTH);
					int month = cldr.get(Calendar.MONTH);
					int year = cldr.get(Calendar.YEAR);
					// date picker dialog
					picker = new DatePickerDialog(CreateTask.this,
                        new DatePickerDialog.OnDateSetListener() {
							@Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                                ed.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
								
								

							}		
                            
                        }, year, month, day);

					picker.show();


				}
			});

		Button btn = (Button) findViewById(R.id.btn_login);
		btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					
					String em = ed.getText().toString().trim();
					String ef = ej.getText().toString().trim();

					Db use = new Db();
					dat = new TodoData(CreateTask.this);

					use.setTaskName(ef);
					use.setDate(em);


					dat.addUser(use);


					// Snack Bar to show success message that record saved successfully


					finish();
					startActivity(new Intent(CreateTask.this, MainActivity.class));
					
					
					}
				});
			
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.



		int id = item.getItemId();
		switch (id) {
			case android.R.id.home:
				onBackPressed();
				return true;


		}

		return super.onOptionsItemSelected(item);
	}
	
	
}
