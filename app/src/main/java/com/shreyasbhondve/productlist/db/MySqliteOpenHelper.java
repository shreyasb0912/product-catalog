package com.shreyasbhondve.productlist.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.shreyasbhondve.productlist.di.qualifier.ApplicationContext;
import com.shreyasbhondve.productlist.di.qualifier.DatabaseInfo;
import com.shreyasbhondve.productlist.pojo.Category;
import com.shreyasbhondve.productlist.pojo.ProductCatalog;
import com.shreyasbhondve.productlist.pojo.Ranking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

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
    public static final String PRODUCT_COLUMN_VIEW_COUNT= "view_count";
    public static final String PRODUCT_COLUMN_ORDER_COUNT = "order_count";
    public static final String PRODUCT_COLUMN_SHARE_COUNT= "share_count";

    //Variant table
    public static final String VARIANT_TABLE_NAME = "variant";
    public static final String VARIANT_COLUMN_VARIANT_ID = "id";
    public static final String VARIANT_COLUMN_VARIANT_COLOR = "color";
    public static final String VARIANT_COLUMN_VARIANT_SIZE = "size";
    public static final String VARIANT_COLUMN_VARIANT_PRICE = "price";
    public static final String VARIANT_COLUMN_PRODUCT_ID = "prod_id";

    //Rankings table
//    public static final String RANKING_TABLE_NAME = "rankings";
//    public static final String RANKING_COLUMN_RANKING = "ranking";
//    public static final String RANKING_COLUMN_PRODUCT_ID = "prod_id";
//    public static final String RANKING_COLUMN_VIEW_COUNT = "view_count";
//    public static final String RANKING_COLUMN_ORDER_COUNT = "order_count";
//    public static final String RANKING_COLUMN_SHARE_COUNT= "share_count";

    private Context mContext;

    @Inject
    public MySqliteOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name, @DatabaseInfo int version) {
        super(context, name, null, version);
        this.mContext = context;
    }


//    public MySqliteOpenHelper(Context context) {
//        super(context, MY_LOCAL_DATABASE_NAME, null, MY_LOCAL_DATABASE_VERSION);
//    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        tableCreateStatements(db);
    }

    private void tableCreateStatements(SQLiteDatabase db) {

        //Log.v("database","inside tableCreateStatements");
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
                            + PRODUCT_COLUMN_PRODUCT_NAME + " VARCHAR(20), "
                            + PRODUCT_COLUMN_PRODUCT_DATE_ADDED + " VARCHAR(20), "
                            + PRODUCT_COLUMN_VIEW_COUNT + " VARCHAR(50), "
                            + PRODUCT_COLUMN_ORDER_COUNT + " VARCHAR(50), "
                            + PRODUCT_COLUMN_SHARE_COUNT + " VARCHAR(50), "
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

//        try {
//            db.execSQL(
//                    "CREATE TABLE IF NOT EXISTS "
//                            + RANKING_TABLE_NAME + "("
//                            + RANKING_COLUMN_RANKING + " VARCHAR(20), "
//                            + RANKING_COLUMN_VIEW_COUNT + " VARCHAR(20), "
//                            + RANKING_COLUMN_ORDER_COUNT + " VARCHAR(20), "
//                            + RANKING_COLUMN_SHARE_COUNT + " VARCHAR(20), "
//                            + RANKING_COLUMN_PRODUCT_ID + " VARCHAR(50) " + ")"
//            );
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + VARIANT_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + RANKING_TABLE_NAME);
        onCreate(db);
    }

    protected boolean isProductsPresent() throws Resources.NotFoundException, NullPointerException {
        Log.v("database", "inside isProductsPresent");
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery(
                    "SELECT * FROM "
                            + PRODUCT_TABLE_NAME,
                    null);


            int i = 1;
            if (cursor.moveToFirst()) {
                do {
                    Log.v("database", "-------" + i + "--------" + "\n\nprod_id: " + cursor.getColumnIndex(PRODUCT_COLUMN_PRODUCT_ID)
                            + "\nprod_name: " + cursor.getColumnIndex(PRODUCT_COLUMN_PRODUCT_NAME) + "\n\n\n");
                    i++;
                } while (cursor.moveToNext());
                db.close();
                return true;
            } else {
                db.close();
                return false;
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (cursor != null)
                cursor.close();
        }

    }

    protected void isCategoriesPresent() throws Resources.NotFoundException, NullPointerException {
        List<ProductCatalog.Category> categoryList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + CATEGORY_TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_CATEGORY_NAME));
                String id = cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_CATEGORY_ID));
                ProductCatalog.Category category = new ProductCatalog.Category();
                category.setId(id);
                category.setName(name);
                categoryList.add(category);
                cursor.moveToNext();
            }
        }

        // close db connection
        db.close();



    }

    protected boolean isVariantsPresent() throws Resources.NotFoundException, NullPointerException {
        Log.v("database", "inside isVariantsPresent");
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery(
                    "SELECT * FROM "
                            + VARIANT_TABLE_NAME,
                    null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
//                Log.v("database","var_id: " + cursor.getColumnIndex(VARIANT_COLUMN_VARIANT_ID)
//                        + "\nprice: " + cursor.getColumnIndex(VARIANT_COLUMN_VARIANT_PRICE)
//                        + "\nsize: " + cursor.getColumnIndex(VARIANT_COLUMN_VARIANT_SIZE));
                Log.v("database", "------\nvar_id: " + cursor.getColumnIndex(VARIANT_COLUMN_VARIANT_ID) + "\nprice: " + cursor.getColumnIndex(VARIANT_COLUMN_VARIANT_PRICE)
                        + "\nsize: " + cursor.getColumnIndex(VARIANT_COLUMN_VARIANT_SIZE) + "------\n");
                db.close();
                return true;
            } else {
                //throw new Resources.NotFoundException("No data");
                //Log.v("database","No data");
                db.close();
                return false;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (cursor != null)
                cursor.close();
        }

    }

    protected void insertCategory(ProductCatalog.Category category){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.v("database","\ncat_id: " + category.getId() + "\ncat_name: " + category.getName() + "\n\n\n");
        contentValues.put(CATEGORY_COLUMN_CATEGORY_ID, category.getId());
        contentValues.put(CATEGORY_COLUMN_CATEGORY_NAME, category.getName());
        db.insert(CATEGORY_TABLE_NAME, null, contentValues);
        db.close();


    }

    protected void insertProduct(ProductCatalog.Category.Product product) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Log.v("database","\nprod_id: " + product.getId() + "\nprod_name: " + product.getName() + "\n\n\n");
        contentValues.put(PRODUCT_COLUMN_PRODUCT_ID, product.getId());
        contentValues.put(PRODUCT_COLUMN_PRODUCT_NAME, product.getName());
        contentValues.put(PRODUCT_COLUMN_PRODUCT_DATE_ADDED, product.getDate_added());
        contentValues.put(PRODUCT_COLUMN_CATEGORY_ID, product.getCat_id());
//        contentValues.put(PRODUCT_COLUMN_VIEW_COUNT, product.getView_count());
//        contentValues.put(PRODUCT_COLUMN_ORDER_COUNT, product.getOrder_count());
//        contentValues.put(PRODUCT_COLUMN_SHARE_COUNT, product.getShare_count());
        db.insert(PRODUCT_TABLE_NAME, null, contentValues);
        db.close();


    }

    protected void insertVariant(ProductCatalog.Category.Product.Variants variants) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Log.v("database","\nvar_id: " + variants.getId() + "\nprice: " + variants.getPrice() + "\n\n\n");
        contentValues.put(VARIANT_COLUMN_PRODUCT_ID, variants.getProd_id());
        contentValues.put(VARIANT_COLUMN_VARIANT_COLOR, variants.getColor());
        contentValues.put(VARIANT_COLUMN_VARIANT_ID, variants.getId());
        contentValues.put(VARIANT_COLUMN_VARIANT_PRICE, variants.getPrice());
        contentValues.put(VARIANT_COLUMN_VARIANT_SIZE, variants.getSize());
        db.insert(VARIANT_TABLE_NAME, null, contentValues);
        db.close();


    }

    protected void insertViewRanking(Ranking.ProductRanking.Products ranking) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Log.v("database","\nvar_id: " + variants.getId() + "\nprice: " + variants.getPrice() + "\n\n\n");
        contentValues.put(PRODUCT_COLUMN_VIEW_COUNT, ranking.getView_count());
//        contentValues.put(PRODUCT_COLUMN_ORDER_COUNT, ranking.getOrder_count());
//        contentValues.put(PRODUCT_COLUMN_SHARE_COUNT, ranking.getShare_count());

        //db.insert(RANKING_TABLE_NAME, null, contentValues);
        db.update(PRODUCT_TABLE_NAME,contentValues,"id=?",new String[]{
                ranking.getId()
        });
        db.close();
    }

    protected void insertShareRanking(Ranking.ProductRanking.Products ranking) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Log.v("database","\nvar_id: " + variants.getId() + "\nprice: " + variants.getPrice() + "\n\n\n");
//        contentValues.put(PRODUCT_COLUMN_VIEW_COUNT, ranking.getView_count());
//        contentValues.put(PRODUCT_COLUMN_ORDER_COUNT, ranking.getOrder_count());
        contentValues.put(PRODUCT_COLUMN_SHARE_COUNT, ranking.getShare_count());

        //db.insert(RANKING_TABLE_NAME, null, contentValues);
        db.update(PRODUCT_TABLE_NAME,contentValues,"id=?",new String[]{
                ranking.getId()
        });
        db.close();
    }

    protected void insertOrderRanking(Ranking.ProductRanking.Products ranking) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Log.v("database","\nvar_id: " + variants.getId() + "\nprice: " + variants.getPrice() + "\n\n\n");
//        contentValues.put(PRODUCT_COLUMN_VIEW_COUNT, ranking.getView_count());
        contentValues.put(PRODUCT_COLUMN_ORDER_COUNT, ranking.getOrder_count());
//        contentValues.put(PRODUCT_COLUMN_SHARE_COUNT, ranking.getShare_count());

        //db.insert(RANKING_TABLE_NAME, null, contentValues);
        db.update(PRODUCT_TABLE_NAME,contentValues,"id=?",new String[]{
                ranking.getId()
        });
        db.close();
    }

//    protected void exportDB(){
//        try {
//            File sd = Environment.getExternalStorageDirectory();
//            File data = Environment.getDataDirectory();
//
//            if (sd.canWrite()) {
//                String currentDBPath = "/data/data/com.shreyasbhondve.productlist" + "/databases/" + MY_LOCAL_DATABASE_NAME + ".db";
//                String backupDBPath = "backupname.db";
//                File currentDB = new File(data, currentDBPath);
//                File backupDB = new File(sd, backupDBPath);
//
//                if (currentDB.exists()) {
//                    FileChannel src = new FileInputStream(currentDB).getChannel();
//                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
//                    dst.transferFrom(src, 0, src.size());
//                    src.close();
//                    dst.close();
//                    Toast.makeText(mContext, "Exported", Toast.LENGTH_LONG).show();
//                } else
//                    Toast.makeText(mContext, "File not found", Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(mContext, "Error!", Toast.LENGTH_LONG).show();
//        }
//    }
}
