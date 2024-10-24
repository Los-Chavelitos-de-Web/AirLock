package Controllers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Model.DBHelper;
import Model.Producto;

public class ProductoCtrl {

    private DBHelper dbHelper;

    public ProductoCtrl(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public ArrayList<Producto> obtenerProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Usa dbHelper para obtener la base de datos
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Cursor cursor = db.rawQuery("SELECT * FROM Productos", null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex("ProductoID");
                    int nombreIndex = cursor.getColumnIndex("Nombre");
                    int descIndex = cursor.getColumnIndex("Descripcion");
                    int precioCIndex = cursor.getColumnIndex("PrecioCompra");
                    int precioVIndex = cursor.getColumnIndex("PrecioVenta");
                    int stockIndex = cursor.getColumnIndex("Stock");
                    int proovIndex = cursor.getColumnIndex("ProveedorID");
                    int fechaIIndex = cursor.getColumnIndex("FechaIngreso");

                    if (idIndex != -1 && nombreIndex != -1) {
                        int id = cursor.getInt(idIndex);
                        String nombre = cursor.getString(nombreIndex);
                        String descripcion = cursor.getString(descIndex);
                        Float precioC = cursor.getFloat(precioCIndex);
                        Float precioV = cursor.getFloat(precioVIndex);
                        int stock = cursor.getInt(stockIndex);
                        int proovedor = cursor.getInt(proovIndex);
                        String fechaIngreso = cursor.getString(fechaIIndex);
                        productos.add(new Producto(
                                id, nombre, descripcion, precioC, precioV, stock,
                                proovedor, formatter.parse(fechaIngreso)
                        ));

                    } else {
                        Log.e("airlock_555", "Una o más columnas no encontradas.");
                    }
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("airlock_555", "Error al consultar la base de datos: " + e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return productos;
    }
}