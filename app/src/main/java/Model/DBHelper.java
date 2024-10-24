package Model;

import android.content.Context;
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
        // Aquí no necesitas crear tablas, ya que estás usando un archivo existente
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar la actualización de la base de datos si es necesario
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
}