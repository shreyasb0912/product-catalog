package com.shreyasbhondve.productlist.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shreyasbhondve.productlist.di.qualifier.ApplicationContext;
import com.shreyasbhondve.productlist.di.qualifier.DatabaseInfo;
import com.shreyasbhondve.productlist.pojo.Category;

import javax.inject.Inject;

public class MySqliteOpenHelper extends SQLiteOpenHelper{

    private static final String MY_LOCAL_DATABASE_NAME = "StockDatabase";
    private static final int MY_LOCAL_DATABASE_VERSION = 1;

    //Category table
    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String CATEGORY_COLUMN_CATEGORY_ID = "id";
    public static final String CATEGORY_COLUMN_CATEGORY_NAME = "name";

    //Product table
    public static final String PRODUCT_TABLE_NAME = "product";
    public static final String PRODUCT_COLUMN_PRODUCT_ID = "id";
    public static final String PRODUCT_COLUMN_PRODUCT_NAME = "name";
    public static final String PRODUCT_COLUMN_PRODUCT_DATE_ADDED = "date_added";
    public static final String PRODUCT_COLUMN_CATEGORY_ID = "cat_id";

    //Variant table
    public static final String VARIANT_TABLE_NAME = "variant";
    public static final String VARIANT_COLUMN_VARIANT_ID = "id";
    public static final String VARIANT_COLUMN_VARIANT_COLOR = "color";
    public static final String VARIANT_COLUMN_VARIANT_SIZE = "size";
    public static final String VARIANT_COLUMN_VARIANT_PRICE = "price";
    public static final String VARIANT_COLUMN_PRODUCT_ID = "prod_id";

    //Rankings table
    public static final String RANKING_TABLE_NAME = "rankings";
    public static final String RANKING_COLUMN_RANKING = "ranking";
    public static final String RANKING_COLUMN_PRODUCT_ID = "prod_id";

    @Inject
    public MySqliteOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name, @DatabaseInfo int version) {
        super(context, name, null, version);
    }


//    public MySqliteOpenHelper(Context context) {
//        super(context, MY_LOCAL_DATABASE_NAME, null, MY_LOCAL_DATABASE_VERSION);
//    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        tableCreateStatements(db);
    }

    private void tableCreateStatements(SQLiteDatabase db) {

        Log.v("database","inside tableCreateStatements");
        try {
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS "
                            + CATEGORY_TABLE_NAME + "("
                            + CATEGORY_COLUMN_CATEGORY_ID + " VARCHAR(20), "
                            + CATEGORY_COLUMN_CATEGORY_NAME + " VARCHAR(50) " + ")"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS "
                            + PRODUCT_TABLE_NAME + "("
                            + PRODUCT_COLUMN_PRODUCT_ID + " VARCHAR(20), "
                            + PRODUCT_TABLE_NAME + " VARCHAR(20), "
                            + PRODUCT_COLUMN_PRODUCT_DATE_ADDED + " VARCHAR(20), "
                            + PRODUCT_COLUMN_CATEGORY_ID + " VARCHAR(50) " + ")"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS "
                            + VARIANT_TABLE_NAME + "("
                            + VARIANT_COLUMN_VARIANT_ID + " VARCHAR(20), "
                            + VARIANT_COLUMN_VARIANT_COLOR + " VARCHAR(20), "
                            + VARIANT_COLUMN_VARIANT_SIZE + " VARCHAR(20), "
                            + VARIANT_COLUMN_VARIANT_PRICE + " VARCHAR(20), "
                            + VARIANT_COLUMN_PRODUCT_ID + " VARCHAR(50) " + ")"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS "
                            + RANKING_TABLE_NAME + "("
                            + RANKING_COLUMN_RANKING + " VARCHAR(20), "
                            + RANKING_COLUMN_PRODUCT_ID + " VARCHAR(50) " + ")"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + VARIANT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RANKING_TABLE_NAME);
        onCreate(db);
    }

    protected void getProducts() throws Resources.NotFoundException, NullPointerException{
        Log.v("database","inside getProducts");
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery(
                    "SELECT * FROM "
                            + PRODUCT_TABLE_NAME,
                    null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                Log.v("database","id: " + cursor.getColumnIndex(PRODUCT_COLUMN_PRODUCT_ID)
                                        + "name: " + cursor.getColumnIndex(PRODUCT_COLUMN_PRODUCT_NAME));
//                User user = new User();
//                user.setId(cursor.getLong(cursor.getColumnIndex(USER_COLUMN_USER_ID)));
//                user.setName(cursor.getString(cursor.getColumnIndex(USER_COLUMN_USER_NAME)));
//                user.setAddress(cursor.getString(cursor.getColumnIndex(USER_COLUMN_USER_ADDRESS)));
//                user.setCreatedAt(cursor.getString(cursor.getColumnIndex(USER_COLUMN_USER_CREATED_AT)));
//                user.setUpdatedAt(cursor.getString(cursor.getColumnIndex(USER_COLUMN_USER_UPDATED_AT)));
//                return user;
            } else {
                //throw new Resources.NotFoundException("No data");
                Log.v("database","No data");
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
            throw e;
        }
        finally {
            if (cursor != null)
                cursor.close();
        }

    }

    protected Long insertProduct(Category.Product product) throws Exception {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PRODUCT_COLUMN_PRODUCT_ID, product.getId());
            contentValues.put(PRODUCT_COLUMN_PRODUCT_NAME, product.getName());
            contentValues.put(PRODUCT_COLUMN_PRODUCT_DATE_ADDED, product.getDate_added());
            contentValues.put(PRODUCT_COLUMN_CATEGORY_ID, product.getCat_id());
            return db.insert(PRODUCT_TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
