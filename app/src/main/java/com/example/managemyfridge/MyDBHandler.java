package com.example.managemyfridge;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    //Σταθερές για τη ΒΔ (όνομα ΒΔ, έκδοση, πίνακες κλπ)
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MMFDB.db";
    public static final String TABLE_PRODUCTS = "PRODUCT";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "Name";
    public static final String COLUMN_EXDATE = "Expiration_Date";
    public static final String COLUMN_IS_IT_OPEN = "Is_it_open";
    public static final String COLUMN_TYPE = "PRODUCT_Type";
    public static final String COLUMN_DATE_OF_OPENING = "Date_of_opening";
    public static final String COLUMN_QUANTITY = "Quantity";
    public static final String COLUMN_UNIT = "Unit";

    public static final String TABLE_USERS = "USERS";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "User_Password";

    public static final String TABLE_RECIPES = "RECIPE";
    public static final String COLUMN_RECIPENAME = "Name";
    public static final String COLUMN_RECIPE_TYPE = "Type_of_Recipe";
    public static final String COLUMN_INSTRUCTIONS = "Instructions";
    public static final String COLUMN_IS_IT_FAV = "Is_it_fav";


    public static final String TABLE_TIPS = "TIP";
    public static final String COLUMN_TIPNAME = "Name";
    public static final String COLUMN_DESCRIPTION = "Description";
    public static final String COLUMN_RELATED_PRODUCT = "Related_Product";
    public static final String COLUMN_IMAGE = "Img";

    public static final String TABLE_INGREDIENTS = "INGREDIENT";
    public static final String COLUMN_INGREDIENTNAME = "Ingredient_Name";
    public static final String COLUMN_ID_OF_RECIPE = "_idofRECIPE";


    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");

    //Constructor
    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //Δημιουργία του σχήματος της ΒΔ (πίνακας products, Ingredients, Recipes)
    //this is called the first time a database is accessed
    //Added autoincrement in id key
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create products table
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PRODUCTNAME + " TEXT NOT NULL," +
                COLUMN_QUANTITY + " TEXT NOT NULL," + //QUANTINY no longer integer
                COLUMN_EXDATE + " TEXT," +
                COLUMN_IS_IT_OPEN + " TEXT," +
                COLUMN_TYPE + " TEXT  ," + //NOT NULL Here
                COLUMN_DATE_OF_OPENING + " TEXT," +
                COLUMN_IMAGE + " TEXT," +
                COLUMN_UNIT + " TEXT" +
                ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        //create ingredients table
        String CREATE_INGREDIENTS_TABLE = "CREATE TABLE " +
                TABLE_INGREDIENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_ID_OF_RECIPE + " INTEGER NOT NULL," +
                COLUMN_INGREDIENTNAME + " TEXT NOT NULL," +
                COLUMN_QUANTITY + " TEXT NOT NULL," +
                COLUMN_UNIT + " TEXT" +
                ")";
        db.execSQL(CREATE_INGREDIENTS_TABLE);

        //create recipes table
        String CREATE_RECIPES_TABLE = "CREATE TABLE " +
                TABLE_RECIPES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_RECIPENAME + " TEXT NOT NULL," +
                COLUMN_RECIPE_TYPE + " TEXT  ," +
                COLUMN_INSTRUCTIONS + " TEXT  ," +
                COLUMN_IS_IT_FAV + " TEXT  ," +
                COLUMN_IMAGE + " TEXT" +
                ")";
        db.execSQL(CREATE_RECIPES_TABLE);

        //create tips table
        String CREATE_TIPS_TABLE = "CREATE TABLE " +
                TABLE_TIPS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TIPNAME + " TEXT NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT  ," +
                COLUMN_RELATED_PRODUCT + " TEXT  ," +
                COLUMN_IS_IT_FAV + " TEXT  ," +
                COLUMN_IMAGE + " TEXT" +
                ")";
        db.execSQL(CREATE_TIPS_TABLE);

        //create users table
        String CREATE_USERS_TABLE = "CREATE TABLE " +
                TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " TEXT ," +
                COLUMN_EMAIL + " TEXT  ," +
                COLUMN_PASSWORD + " TEXT  ," +
                COLUMN_IMAGE + " TEXT" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);
    }




    //Αναβάθμιση ΒΔ: εδώ τη διαγραφώ και τη ξαναδημιουργώ ίδια
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }



    //PRODUCT METHODS

    //Μέθοδος για προσθήκη ενός προϊόντος στη ΒΔ
    public void addProduct(Product product) {
        //added: exdate, isopened, prodtype, dateofopening, img, unit
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getProductName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_EXDATE, product.get_exdate());
        //values.put(COLUMN_IS_IT_OPEN, product.isOpened());
        values.put(COLUMN_IS_IT_OPEN, product.get_opened());
        values.put(COLUMN_TYPE, product.get_prodtype());
        values.put(COLUMN_DATE_OF_OPENING, product.get_DateofOpening());
        values.put(COLUMN_IMAGE, product.get_img());
        values.put(COLUMN_UNIT, product.get_unit());
        SQLiteDatabase db = this.getWritableDatabase();
        //original
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();


        //change to make sure it works
        /*long insert = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        if(insert==-1){
            return false;
        }
        else {
            return true;
        }*/

    }

    //Μέθοδος για εύρεση προϊόντος βάσει ονομασίας του
    public Product findProduct(String productname) {
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " +
                COLUMN_PRODUCTNAME + " = '" + productname + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Product product = new Product();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            product.setID(Integer.parseInt(cursor.getString(0)));
            product.setProductName(cursor.getString(1));
            product.setQuantity(cursor.getString(2));
            cursor.close();
        } else {
            product = null;
        }
        db.close();
        return product;
    }

    //Μέθοδος για εύρεση προϊόντος βάσει ονομασίας του
    public Product newfindProduct(String productname) {
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " +
                COLUMN_PRODUCTNAME + " = '" + productname + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Product product = new Product();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            product.setID(Integer.parseInt(cursor.getString(0)));
            product.setProductName(cursor.getString(1));
            product.setQuantity(cursor.getString(2));
            product.set_exdate(cursor.getString(3));
            product.set_opened(cursor.getString(4));
            product.set_prodtype(cursor.getString(5));
            product.set_DateofOpening(cursor.getString(6));
            //product.set_img(cursor.getString(7)); //the img changes based on type, not user's choice.
            product.set_unit(cursor.getString(8));
            cursor.close();

        } else {
            product = null;
        }
        db.close();
        return product;
    }

    //Μέθοδος για διαγραφή προϊόντος βάσει ονομασίας του
    public boolean deleteProduct(String productname) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " +
                COLUMN_PRODUCTNAME + " = '" + productname + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Product product = new Product();
        if (cursor.moveToFirst()) {
            product.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(product.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    //add user
    public void addUser(){
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, LoginScreen.user.getEmail());
        values.put(COLUMN_USERNAME, LoginScreen.user.getUsername());
        values.put(COLUMN_PASSWORD, LoginScreen.user.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        //original
        db.insert(TABLE_USERS, null, values);
        db.close();

    }
    //user
    public boolean findUser(String username) {
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + " = '" + username + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag=false;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            LoginScreen.user.setID(Integer.parseInt(cursor.getString(0)));
            LoginScreen.user.setUsername(cursor.getString(1));
            LoginScreen.user.setPassword(cursor.getString(2));
            LoginScreen.user.setImg(cursor.getString(3));
            cursor.close();
            flag = true;
        } else {
            LoginScreen.user = null;
        }
        db.close();
        return flag;
    }


    //delete user
    public boolean deleteUser(String username) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + " = '" + username + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            LoginScreen.user.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(LoginScreen.user.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    //update user
    public void updateUser (){

        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, LoginScreen.user.getPassword());
        values.put(COLUMN_USERNAME, LoginScreen.user.getUsername());
        values.put(COLUMN_EMAIL, LoginScreen.user.getEmail());

        SQLiteDatabase db = this.getWritableDatabase();
        //original
        db.update(TABLE_USERS, values ,COLUMN_ID + " = ?", new String[] { String.valueOf(LoginScreen.user.getID()) });
        db.close();

    }

    // we have created a new method that returns all the products in the fridge.
    public ArrayList<Product> showallProducts() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);

        // on below line we are creating a new array list.
        ArrayList<Product> fridgeProducts = new ArrayList<>();

        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                fridgeProducts.add(new Product(cursor.getInt(0), //id
                        cursor.getString(1), //name
                        cursor.getString(2), //quantity
                        cursor.getString(3), //expiration date
                        cursor.getString(4), //is_it_open
                        cursor.getString(5), //type
                        cursor.getString(6), //date of opening
                        //image      cursor.getString(7),
                        cursor.getString(8) //unit

                ));
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor and db
        // and returning our array list.
        cursor.close();
        db.close();
        return fridgeProducts;
    }

    //method to change/update the values of  the is_it_open and dateofopening variables
    public boolean makeProductOpen(int id, String todaydate){
        boolean result;
        //String query = "SELECT _id, Is_it_open, Date_of_opening FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID + " = '" + id + "'";
        String query = "SELECT _id FROM " + TABLE_PRODUCTS + " WHERE " +
                COLUMN_ID + " = '" + id + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ContentValues values = new ContentValues();
        Product product = new Product();
        if (cursor.moveToFirst()) {
            product.setID(Integer.parseInt(cursor.getString(0)));
            product.set_opened("yes");
            product.set_DateofOpening(todaydate);

            values.put(COLUMN_IS_IT_OPEN, product.get_opened());
            values.put(COLUMN_DATE_OF_OPENING, product.get_DateofOpening());

            db.update(TABLE_PRODUCTS, values ,COLUMN_ID + " = ?", new String[] { String.valueOf(product.getID()) });
            cursor.close();
            result = true;
        }
        else {
            result = false;
        }

        db.close();
        return result;

    }


    //method to update product properties
    //FIRST IN MAINACTIVITY A findProduct METHOD NEEDS TO BE CALLED TO SHOW ALL PRODUCT PROPERTIES. THEN CALL THIS METHOD  WITH THAT FOUND PRODUCT AS THE VARIABLE
    public void updateProduct(Product product){

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getProductName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_EXDATE, product.get_exdate());
        //values.put(COLUMN_IS_IT_OPEN, product.isOpened());
        values.put(COLUMN_IS_IT_OPEN, product.get_opened());
        values.put(COLUMN_TYPE, product.get_prodtype());
        values.put(COLUMN_DATE_OF_OPENING, product.get_DateofOpening());
        values.put(COLUMN_IMAGE, product.get_img());
        values.put(COLUMN_UNIT, product.get_unit());
        SQLiteDatabase db = this.getWritableDatabase();
        //original
        db.update(TABLE_PRODUCTS, values ,COLUMN_ID + " = ?", new String[] { String.valueOf(product.getID()) });
        db.close();


    }


    //INGREDIENT METHODS

    // we have created a new method that returns all the ingredients of a recipe.
    public ArrayList<Ingredient> getallIngredients() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INGREDIENTS, null);

        // on below line we are creating a new array list.
        ArrayList<Ingredient> listofIngredients = new ArrayList<>();

        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                listofIngredients.add(new Ingredient(cursor.getInt(0), //id
                        cursor.getInt(1), //idofRECIPE
                        cursor.getString(2), //ingredientname
                        cursor.getString(3), //quantity
                        cursor.getString(4) //unit

                ));
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor and db
        // and returning our array list.
        cursor.close();
        db.close();
        return listofIngredients;
    }



}