package com.oscardevelop.simpletodo;

import android.app.*;
import android.os.*;
import android.app.AlertDialog;
import android.support.v7.app.*;
import android.view.*;
import android.content.*;
import android.support.v7.widget.*;
import java.util.*;
import android.widget.*;
import android.support.design.widget.*;
import java.text.*;
import java.util.*;

import android.text.*;


public class MainActivity extends AppCompatActivity
{
	private List<Db> task = new ArrayList<>();
	private RecyclerView mRVFishPrice;
    private Adapter mAdapter;
	private int position;
	private TodoData databaseHelper;
	TextView txt;
	private DatePickerDialog picker;
	private int rowID;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		txt = (TextView) findViewById(R.id.mainTextView);
		
		
		if(task.isEmpty()) {
			txt.setVisibility(View.GONE);
		}
		else {
			txt.setVisibility(View.VISIBLE);
			
		}
		mRVFishPrice = (RecyclerView)findViewById(R.id.recycler_view);
		mAdapter = new Adapter(MainActivity.this, task);
		mRVFishPrice.setAdapter(mAdapter);
		mRVFishPrice.setItemAnimator(new DefaultItemAnimator());
		mRVFishPrice.setNestedScrollingEnabled(true);

		mRVFishPrice.setLayoutManager(new LinearLayoutManager(MainActivity.this));
		databaseHelper = new TodoData(MainActivity.this);

		getDataFromSQLite();
		mRVFishPrice.addOnItemTouchListener(new RecyclerTouchListener(this,
												mRVFishPrice, new RecyclerTouchListener.ClickListener() {
													@Override
													public void onClick(View view, final int position) {
														showActionsDialog(position);
													}

												}));


		
		
    }
	
	private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Modifier","Supprimer"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(colors, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which == 0) {
						editDialog(position);
						}
					else if(which == 1) {
						deleteNote(position);
						
					}
				}
			});
        builder.show();
    }

	
	
	
	private void editDialog(final int position) {
		ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, viewGroup, false);

		
		final TextInputEditText ed = (TextInputEditText) dialogView.findViewById(R.id.taskname);
		final TextInputEditText em = (TextInputEditText) dialogView.findViewById(R.id.date);
		
		ed.setText(task.get(position).getTaskName());
		em.setText(task.get(position).getDate());
		em.setInputType(InputType.TYPE_NULL);
        em.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				    final Calendar cldr = Calendar.getInstance();




					int day = cldr.get(Calendar.DAY_OF_MONTH);
					int month = cldr.get(Calendar.MONTH);
					int year = cldr.get(Calendar.YEAR);
					// date picker dialog
					picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
							@Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                                em.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);



							}		

                        }, year, month, day);

					picker.show();


				}
			});
		
		Button btn = (Button) dialogView.findViewById(R.id.btn_login);

		
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it 
		final AlertDialog alertDialog = builder.create();

		btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					//Toast.makeText(MainActivity.this, "Salut", Toast.LENGTH_SHORT).show();
					
					String ek = ed.getText().toString();
					String ef = em.getText().toString();

					databaseHelper = new TodoData(MainActivity.this);

					Db todo = new Db();

					todo.setTaskName(ek);
					todo.setDate(ef);
				    todo.setId(task.get(position).getId());
					databaseHelper.updateTask(todo);

					task.set(position, todo);
					mAdapter.notifyItemChanged(position);

					
					alertDialog.dismiss();
					
					
				}
			});
		

        alertDialog.show();
		
	}
	
	
	private void deleteNote(int position) {

		 Db todo = new Db();
		 databaseHelper.deleteTask(task.get(position));
		 task.remove(position); 
		 mAdapter.notifyItemRemoved(position);
		
		 Toast.makeText(MainActivity.this, "Effacé", Toast.LENGTH_SHORT).show();
		
		


    }
	
	
	private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                task.clear();
                task.addAll(databaseHelper.getAllUser());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
	
	public void faj(View v) {
		startActivity(new Intent(this, CreateTask.class));
	}
}
