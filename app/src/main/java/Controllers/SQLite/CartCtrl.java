package Controllers.SQLite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Model.DBHelper;
import Model.ProdCart;

public class CartCtrl {

    private DBHelper dbHelper;

    public CartCtrl(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void addNewProduct(int prod_id, String uri, String prod_name, double price) {
        dbHelper.addProduct(prod_id, uri, prod_name, price);
    }

    public List<ProdCart> getAllProducts() {
        List<ProdCart> prods = new ArrayList<>();

        try {
            Cursor cursor = dbHelper.getAllProducts();
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("prod_name"));
                    double price = cursor.getDouble(cursor.getColumnIndex("price"));
                    int quantity = cursor.getInt(cursor.getColumnIndex("cant"));
                    int prod_id = cursor.getInt(cursor.getColumnIndex("prod_id"));

                    prods.add(new ProdCart(id, Uri.EMPTY, name, price, quantity, prod_id));
                }
                cursor.close();
            } else {
                Log.i("airlock_555", "No hay productos en el carrito.");
            }
        } catch (Exception e) {
            Log.e("airlock_555", "Error al obtener productos del carrito: " + e.getMessage());
        }

        return prods;
    }

    public void updateCant(int id, int cant) {
        dbHelper.updateCant(id, cant);
    }

    public void updateProduct(int id, String name, double price, int quantity) {
        dbHelper.updateProduct(id, name, price, quantity);
    }

    public void removeProduct(int id) {
        dbHelper.deleteProduct(id);
    }

    public void deleteAllProducts() {
        dbHelper.deleteAllProducts();
    }

}