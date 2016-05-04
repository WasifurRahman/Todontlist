package inc.onix.todontlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by wasif on 5/4/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private String TAG ="DBHelper";


    public static String DATABASE_NAME ="task.db";
    public static String TABLE_NAME = "taskTable";
    public static String TASK_NAME = "taskName";
    public static  String TASK_CAUSE ="taskCause";
    public static String ROW_ID = "rowId";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS taskTable (rowId INTEGER PRIMARY KEY AUTOINCREMENT, taskName TEXT, taskCause TEXT)";
        db.execSQL(query);
        Log.d(TAG, "table created successfully");

    }

    public void insertTask(TaskItem newItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();

        contentValue.put(TASK_NAME,newItem.Task);
        contentValue.put(TASK_CAUSE, newItem.Cause);

        long rowId = db.insert(TABLE_NAME,null,contentValue);

        if(rowId!=-1){
            Log.d(TAG,"row inserted");
        }
        else{
            Log.d(TAG,"row not inserted");
        }


    }


    public ArrayList<TaskItem> retrieveData(){
        String query = "SELECT* FROM taskTable";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return convertCursorToArraylist(cursor);

    }

    private ArrayList<TaskItem> convertCursorToArraylist(Cursor cursor) {
        cursor.moveToFirst();

        ArrayList<TaskItem> allItems = new ArrayList<TaskItem>();
        while(cursor.isAfterLast()==false){
            int rowId = cursor.getInt(cursor.getColumnIndex(ROW_ID));
            String taskName = cursor.getString(cursor.getColumnIndex(TASK_NAME));
            String taskCause = cursor.getString(cursor.getColumnIndex(TASK_CAUSE));
            TaskItem newItem = new TaskItem(taskName,taskCause,rowId);
            allItems.add(newItem);

            cursor.moveToNext();
        }

        return  allItems;
    }

    public void deleteEntry(int rowId){
        SQLiteDatabase db = this.getWritableDatabase();
        int numRow = db.delete(TABLE_NAME,"rowId = ? ",new String[]{rowId+""});


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
