package Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Relojeria.db";
    private static final int DATABASE_VERSION = 1;
    private final Context context;
    private String databasePath;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.databasePath = context.getDatabasePath(DATABASE_NAME).getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* String CREATE_CART_PRODS_TABLE = "CREATE TABLE cart_prods (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "prod_id INTEGER," +
                "uri TEXT," +
                "prod_name TEXT," +
                "price REAL," +
                "cant INTEGER DEFAULT 1);";
        db.execSQL(CREATE_CART_PRODS_TABLE); */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* if (oldVersion < 2) { // If version 2 is greater than the current version
            // Example: Create the table if it doesn't exist already
            String CREATE_CART_PRODS_TABLE = "CREATE TABLE IF NOT EXISTS cart_prods (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "prod_id INTEGER," +
                    "uri TEXT," +
                    "prod_name TEXT," +
                    "price REAL," +
                    "cant INTEGER DEFAULT 1);";
            db.execSQL(CREATE_CART_PRODS_TABLE);
        } */
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();

        if (!dbExist) {
            Log.i("airlock_555", "La base de datos no existe, creando una nueva...");
            this.getReadableDatabase(); // Crea la base de datos vacía
            try {
                copyDatabase();
                Log.i("airlock_555", "Base de datos copiada exitosamente.");
            } catch (Exception e) {
                Log.e("airlock_555", "Error al copiar la base de datos: " + e.getMessage());
            }
        } else {
            Log.i("airlock_555", "La base de datos ya existe.");
        }
    }

    public void deleteDatabase() {
        File dbFile = new File(databasePath);
        if (dbFile.exists()) {
            if (dbFile.delete()) {
                Log.i("airlock_555", "Base de datos eliminada: " + databasePath);
            } else {
                Log.e("airlock_555", "Error al eliminar la base de datos");
            }
        }
    }

    private boolean checkDatabase() {
        File dbFile = new File(databasePath);
        return dbFile.exists();
    }

    private void copyDatabase() throws IOException {
        InputStream input = context.getAssets().open(DATABASE_NAME);
        OutputStream output = new FileOutputStream(databasePath);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        input.close();
    }

    public SQLiteDatabase openDatabase() {
        return SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    // Intern operations
    public void addProduct(int prod_id, String uri, String prod_name, double price) {
        if (prod_name == null || prod_name.isEmpty()) {
            Log.e("airlock_555", "El nombre del producto no puede estar vacío");
            return;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("prod_id", prod_id);
        values.put("uri", uri);
        values.put("prod_name", prod_name);
        values.put("price", price);
        values.put("cant", 1);  // Inicializamos la cantidad en 1

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM cart_prods WHERE prod_id = ?", new String[]{String.valueOf(prod_id)});

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();  // Nos aseguramos de que el cursor apunte al primer (y único) resultado

                int currentQuantity = cursor.getInt(cursor.getColumnIndex("cant"));
                cursor.close();

                ContentValues updateValues = new ContentValues();
                updateValues.put("cant", currentQuantity + 1);

                int rowsAffected = db.update("cart_prods", updateValues, "prod_id = ?", new String[]{String.valueOf(prod_id)});
                if (rowsAffected > 0) {
                    Log.i("airlock_555", "Cantidad del producto con ID " + prod_id + " actualizada.");
                } else {
                    Log.e("airlock_555", "Error al actualizar la cantidad del producto con ID " + prod_id);
                }
            } else {
                long rowId = db.insert("cart_prods", null, values);
                if (rowId != -1) {
                    Log.i("airlock_555", "Producto agregado correctamente, ID: " + rowId);
                } else {
                    Log.e("airlock_555", "Error al insertar el producto en la base de datos");
                }
            }

        } catch (Exception e) {
            Log.e("airlock_555", "Error al agregar o actualizar producto: " + e.getMessage());
        } finally {
            db.close();  // Asegurarse de cerrar la base de datos
        }
    }

    public Cursor getProductForId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM cart_prods WHERE prod_id = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM cart_prods", null);
    }

    public void updateCant(int productId, int cant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cant", cant);

        // Actualiza el producto por su ID
        db.update("cart_prods", values, "id = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

    public void updateProduct(int productId, String name, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("price", price);
        values.put("quantity", quantity);

        // Actualiza el producto por su ID
        db.update("cart_prods", values, "id = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

    public void deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart_prods", "id = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

    public void deleteAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete("cart_prods", null, null);  // Eliminar todos los registros
            Log.i("airlock_555", "Todos los productos fueron eliminados correctamente.");
        } catch (Exception e) {
            Log.e("airlock_555", "Error al eliminar todos los productos: " + e.getMessage());
        } finally {
            db.close();
        }
    }


}