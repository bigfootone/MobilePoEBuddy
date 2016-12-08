package com.example.bigfootone.mobilepoebuddy;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Created by Bigfootone on 30/11/2016.
 */

public class UniqueItemDatabaseManager extends SQLiteOpenHelper {

    private static final String DB_PATH = "/data/data/com.example.bigfootone.mobilepoebuddy/databases/";
    private static final String DB_Name = "UniqueItemDB.s3db";
    private static final String TBL_UNIQUEINFO = "Uniques";
    private static int DB_VER = 1;

    public static final String COL_ITEMID = "ID";
    public static final String COL_CATEGORY = "Category";
    public static final String COL_SUBCATEGORY = "SubCategory";
    public static final String COL_BASETYPE = "BaseType";
    public static final String COL_UNIQUENAME = "UniqueName";
    public static final String COL_DESCRIPTION = "Description";
    public static final String COL_ARMOURVALUE = "ArmourValue";
    public static final String COL_EVASIONVALUE = "EvasionValue";
    public static final String COL_ENERGYSHIELDVALUE = "EnergyShieldValue";
    public static final String COL_FLAVOURTEXT = "FlavourText";
    public static final String COL_ITEMLEVEL = "ItemLevel";
    public static final String COL_ITEMFAVOURITE = "Favourite";
    public static final String COL_IMAGELINK = "ImageLink";

    private final Context appContext;

    public UniqueItemDatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,version);
        this.appContext = context;
        try
        {
            String path = DB_PATH + DB_Name;
            File databaseFile = new File(path);
            if(databaseFile.exists())
            {
                Log.e("Tag1", "Database exists");
            }
            else
            {
                Log.e("Tag1", "Database does not Exist");
            }
        }
        catch(SQLiteException e)
        {
            Log.e("Tag1", "Database does not exist");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        String CREATE_UNIQUEITEM_TABLE = "CREATE TABLE IF NOT EXISTS " + TBL_UNIQUEINFO + "(" + COL_BASETYPE + " VARCHAR(30)," + COL_UNIQUENAME + " VARCHAR30," + COL_DESCRIPTION + " VARCHAR(400)," + COL_ARMOURVALUE + " VARCHAR(20)," + COL_EVASIONVALUE + " VARCHAR(20)," + COL_ENERGYSHIELDVALUE + " VARCHAR(20)," + COL_FLAVOURTEXT + " VARCHAR(400)," + COL_ITEMID + " INTEGER PRIMARY KEY," + COL_ITEMLEVEL + " INTEGER," + COL_ITEMFAVOURITE + " BOOLEAN," + COL_CATEGORY + " VARCHAR(30)," + COL_SUBCATEGORY + " VARCHAR(30)," + COL_IMAGELINK + " VARCHAR(40)" + ")";
        database.execSQL(CREATE_UNIQUEITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        if(newVersion > oldVersion)
        {
            database.execSQL("DROP TABLE IF EXISTS" + TBL_UNIQUEINFO);
            onCreate(database);
        }
    }

    static public void dbCreate(Context context) throws IOException
    {
           boolean databaseExist = dbCheck();

        if(!databaseExist)
        {
            try
            {
                copyDatabaseFromAssets(context);
            }
            catch (IOException e)
            {
                throw new Error("Error Copying Database");
            }
        }
    }

    static private boolean dbCheck()
    {
        SQLiteDatabase database = null;

        try
        {
            String databasePath = DB_PATH + DB_Name;
            database = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
            database.setLocale(Locale.getDefault());
            database.setVersion(1);
        }
        catch (SQLiteException e)
        {
            Log.e("SQHelper", "Database not found");
        }

        if(database != null)
        {
            database.close();
        }

        return database != null;
    }

    static private void copyDatabaseFromAssets(Context context) throws IOException
    {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String databaseFileName = DB_PATH + DB_Name;

        try
        {
            inputStream = context.getAssets().open(DB_Name);
            File dbDir = new File(DB_PATH);
            dbDir.mkdirs();
            File dbFile = new File(dbDir, DB_Name);
            dbFile.createNewFile();
            outputStream = new FileOutputStream(dbFile);
            byte[] buffer = new byte[1024];
            int length;
            while((length = inputStream.read(buffer)) > 0)
            {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        catch (IOException e)
        {
            throw new Error("Problems copying Database");
        }
    }

    //find items from database based on the category
    public SingleUniqueItem findItem(String category)
    {
        String query = "Select * FROM " + TBL_UNIQUEINFO + " WHERE " + COL_ITEMID + " = \"" + category + "\"";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        SingleUniqueItem singleUniqueItem = new SingleUniqueItem();

        if(cursor.moveToFirst())
        {
            cursor.moveToFirst();
            singleUniqueItem.setItemBaseType(cursor.getString(0));
            singleUniqueItem.setItemUniqueName(cursor.getString(1));
            singleUniqueItem.setItemDescription(cursor.getString(2));
            singleUniqueItem.setItemArmourValue(cursor.getString(3));
            singleUniqueItem.setItemEvasionValue(cursor.getString(4));
            singleUniqueItem.setItemEnergyShieldValue(cursor.getString(5));
            singleUniqueItem.setItemFlavourText(cursor.getString(6));
            singleUniqueItem.setItemID(cursor.getInt(7));
            singleUniqueItem.setItemItemLevel(cursor.getInt(8));
            singleUniqueItem.setItemFavourite(cursor.getInt(9) == 1);
            singleUniqueItem.setItemCategory(cursor.getString(10));
            singleUniqueItem.setItemSubCategory(cursor.getString(11));
            singleUniqueItem.setItemImageLink(cursor.getString(12));
            cursor.close();
        }
        else
        {
            singleUniqueItem = null;
        }
        database.close();
        return singleUniqueItem;
    }



    //find number of rows in database
    public int getNumberOfRows(String category)
    {
        String query = "Select * FROM " + TBL_UNIQUEINFO + " WHERE " + COL_ITEMID + " = \"" + category + "\"";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        int numberOfRows = cursor.getCount();
        cursor.close();
        return numberOfRows;

    }

    public SingleUniqueItem findItemNameSearch(String category)
    {
        //category == uniqueName
        String query = "Select * FROM " + TBL_UNIQUEINFO + " WHERE " + COL_UNIQUENAME + " = \"" + category + "\"";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        SingleUniqueItem singleUniqueItem = new SingleUniqueItem();

        if(cursor.moveToFirst())
        {
            cursor.moveToFirst();
            singleUniqueItem.setItemBaseType(cursor.getString(0));
            singleUniqueItem.setItemUniqueName(cursor.getString(1));
            singleUniqueItem.setItemDescription(cursor.getString(2));
            singleUniqueItem.setItemArmourValue(cursor.getString(3));
            singleUniqueItem.setItemEvasionValue(cursor.getString(4));
            singleUniqueItem.setItemEnergyShieldValue(cursor.getString(5));
            singleUniqueItem.setItemFlavourText(cursor.getString(6));
            singleUniqueItem.setItemID(cursor.getInt(7));
            singleUniqueItem.setItemItemLevel(cursor.getInt(8));
            singleUniqueItem.setItemFavourite(cursor.getInt(9) == 1);
            singleUniqueItem.setItemCategory(cursor.getString(10));
            singleUniqueItem.setItemSubCategory(cursor.getString(11));
            singleUniqueItem.setItemImageLink(cursor.getString(12));
            cursor.close();
        }
        else
        {
            singleUniqueItem = null;
        }
        database.close();
        return singleUniqueItem;
    }

    //update an item's favourite status
    public void addToFavourites(Integer ID, Boolean value)
    {
        String query = "UPDATE " + TBL_UNIQUEINFO + " SET " + COL_ITEMFAVOURITE  + " = " + (value? 1:0) + " WHERE " + COL_ITEMID + " = " + ID;
        SQLiteDatabase database = this.getWritableDatabase();

        try
        {
            database.execSQL(query);
            Log.e("SQLHelper", "Updated");
        }
        catch(SQLiteException e)
        {
            Log.e("SQLHelper", "Could not update");
        }


        database.close();
    }

    //set all items to not be favourites
    public void clearFavourites(Integer ID)
    {
        String query = "Update " + TBL_UNIQUEINFO + " SET " + COL_ITEMFAVOURITE + " = " + (0) + " WHERE " + COL_ITEMID + " = " + ID;
        SQLiteDatabase database = this.getWritableDatabase();
        try
        {
            database.execSQL(query);
        }
        catch (SQLiteException e)
        {
            Log.e("SQHelper", "Could not remove favourites");
        }
        database.close();
    }
}
