package com.rock.database;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public abstract class BaseDbHelper extends SQLiteOpenHelper {

	public BaseDbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public BaseDbHelper(Context context,String tabName) {
		this(context, tabName, null, 1);
		if (!isTableExist(tabName)) {
			//当表不存在时，新建数据库表
			createTable(getWritableDatabase());
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

	/**
	 * 判断表是否存在
	 * 
	 * @param tabName
	 *            表名
	 * @return
	 */
	public boolean isTableExist(String tabName) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db
				.rawQuery(
						"select name from sqlite_master where type='table' order by name",
						null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(0);
			if (name.equals(tabName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 在这里添加建表语句
	 * @param db
	 */
	protected abstract void createTable(SQLiteDatabase db);

}
