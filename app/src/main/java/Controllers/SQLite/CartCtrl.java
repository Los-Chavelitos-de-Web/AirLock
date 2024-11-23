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

    public void addNewProduct(String uri, String prod_name, double price) {
        dbHelper.addProduct(uri, prod_name, price);
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

                    prods.add(new ProdCart(id, Uri.EMPTY, name, price, quantity));
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

    public void updateProduct(int id, String name, double price, int quantity) {
        dbHelper.updateCant(id, name, price, quantity);
    }

    public void removeProduct(int id) {
        dbHelper.deleteProduct(id);
    }

}