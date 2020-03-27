package com.oscardevelop.simpletodo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by lalit on 9/12/2016.
 */
public class TodoData extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TaskManager.db";

    // User table name
    private static final String TABLE_USER = "todo";

    // User Table Columns names
    private static final String COLUMN_ID = "todo_id";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_DATE = "date";

	private int position;
    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
	+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TASK_NAME + " VARCHAR,"
	+ COLUMN_DATE + " VARCHAR" + ");";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


	private List<Db> lis = new ArrayList<>();
	
	
	private String DELETE_USER = "DELETE FROM " + TABLE_USER + " WHERE " +  TABLE_USER + " . " + COLUMN_ID;

    /**
     * Constructor
     * 
     * @param context
     */
    public TodoData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }
	
    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(Db user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, user.getTaskName());
        values.put(COLUMN_DATE, user.getDate());
        
		
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Db> getAllUser() {
        // array of columns to fetch
        String[] columns = {
			COLUMN_ID,
			COLUMN_TASK_NAME,
			COLUMN_DATE
        };
        // sorting orders
        String sortOrder =
			COLUMN_TASK_NAME + " ASC";
        List<Db> userList = new ArrayList<Db>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
								 columns,    //columns to return
								 null,        //columns for the WHERE clause
								 null,        //The values for the WHERE clause
								 null,       //group the rows
								 null,       //filter by row groups
								 sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Db user = new Db();
                user.setTaskName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
                user.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                // Adding user record to list
                userList.add(user);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

	@Override
	public void onConfigure(SQLiteDatabase db)
	{
		// TODO: Implement this method
		super.onConfigure(db);


	}
	
	public void updateTask(Db task) {
        SQLiteDatabase  database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TASK_NAME, task.getTaskName());
        contentValues.put(COLUMN_DATE, task.getDate());
        database.update(TABLE_USER, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(task.getId())});
        database.close();
    }
	
	

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteTask(Db task) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id		  
	    db.execSQL(DELETE_USER + " = " + task.getId());


    }

	
    

    
}
