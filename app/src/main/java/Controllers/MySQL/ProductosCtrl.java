package Controllers.MySQL;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lta.airlock.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import Model.Producto;

public class ProductosCtrl {

    Producto p;
    Properties props = new Properties();
    InputStream inputStream;
    public Context context;

    public ProductosCtrl(Context c) {
        this.context = c;

        AssetManager assetManager = context.getAssets();

        try {
            inputStream = assetManager.open("local.properties");
            props.load(inputStream);
        } catch (IOException e) {
            Log.e("airlock_555", e.getMessage());
        }
    }

    public interface ProductFetchListener {
        void onProductsFetched(List<Producto> productos);
    }

    public void getProducts(final ProductFetchListener listener) {
        Log.i("airlock_555", "Enviando solicitud a la URL: http://192.168.100.138:3000/api/v1/products");

        StringRequest request = new StringRequest(Request.Method.GET, String.format("%s/products", props.getProperty("BACKEND_HOST")),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Producto> productos = new ArrayList<>();
                        // Log.i("airlock_555", "Respuesta recibida: " + response);

                        productos.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            if (jsonArray.length() == 0) {
                                Log.i("airlock_555", "La respuesta JSON está vacía");
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                int id = object.getInt("ProductoID");
                                String nombre = object.getString("Nombre");
                                String descripcion = object.getString("Descripcion");
                                Double p_compra = object.getDouble("PrecioCompra");
                                Double p_venta = object.getDouble("PrecioVenta");
                                int stock = object.getInt("Stock");
                                int prov_id = object.getInt("ProveedorID");
                                int fecha_i = object.getInt("FechaIngreso");

                                p = new Producto(
                                        id, nombre, descripcion, p_compra,
                                        p_venta, stock, prov_id, fecha_i
                                );
                                productos.add(p);
                            }

                        } catch (JSONException e) {
                            Log.e("airlock_555", "Error al procesar el JSON: " + e.getMessage());
                        }
                        listener.onProductsFetched(productos);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("airlock_555", "Error en la solicitud: " + error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }

    public void getProductsForId(final ProductFetchListener listener, int id) {
        Log.i("airlock_555", "Enviando solicitud a la URL: http://192.168.100.138:3000/api/v1/products");

        // Create JSON body to send the product ID in the request body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ProductoID", id);
        } catch (JSONException e) {
            Log.e("airlock_555", "Error al crear el JSON: " + e.getMessage());
        }

        // Use JsonArrayRequest since the response is a JSON array
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,
                String.format("%s/products", props.getProperty("BACKEND_HOST")),
                jsonBody.names(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Producto> productos = new ArrayList<>();
                        try {
                            // Process each product in the JSON array
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject productObject = response.getJSONObject(i);

                                int id = productObject.getInt("ProductoID");
                                String nombre = productObject.getString("Nombre");
                                String descripcion = productObject.getString("Descripcion");
                                Double p_compra = productObject.getDouble("PrecioCompra");
                                Double p_venta = productObject.getDouble("PrecioVenta");
                                int stock = productObject.getInt("Stock");
                                int prov_id = productObject.getInt("ProveedorID");
                                int fecha_i = productObject.getInt("FechaIngreso");

                                Producto p = new Producto(id, nombre, descripcion, p_compra, p_venta, stock, prov_id, fecha_i);
                                productos.add(p);
                            }
                        } catch (JSONException e) {
                            Log.e("airlock_555", "Error al procesar el JSON: " + e.getMessage());
                        }

                        // Pass the list of products to the listener
                        listener.onProductsFetched(productos);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("airlock_555", "Error en la solicitud: " + error.getMessage());
                    }
                }) {
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes(StandardCharsets.UTF_8);  // Convert JSON body to byte array
            }
        };

        // Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }

    public void searchProduct(String text_search, final ProductFetchListener listener, Context c) {
        Log.i("airlock_555", "Enviando solicitud a la URL: http://192.168.100.138:3000/api/v1/searchProducts");

        // Create JSON body to send the product ID in the request body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("text_search", text_search);
        } catch (JSONException e) {
            Log.e("airlock_555", "Error al crear el JSON: " + e.getMessage());
        }

        // Use JsonArrayRequest since the response is a JSON array
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,
                String.format("%s/searchProducts", props.getProperty("BACKEND_HOST")),
                jsonBody.names(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Producto> productos = new ArrayList<>();
                        try {
                            // Process each product in the JSON array
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject productObject = response.getJSONObject(i);

                                int id = productObject.getInt("ProductoID");
                                String nombre = productObject.getString("Nombre");
                                String descripcion = productObject.getString("Descripcion");
                                Double p_compra = productObject.getDouble("PrecioCompra");
                                Double p_venta = productObject.getDouble("PrecioVenta");
                                int stock = productObject.getInt("Stock");
                                int prov_id = productObject.getInt("ProveedorID");
                                int fecha_i = productObject.getInt("FechaIngreso");

                                Producto p = new Producto(id, nombre, descripcion, p_compra, p_venta, stock, prov_id, fecha_i);
                                productos.add(p);
                            }
                        } catch (JSONException e) {
                            Log.e("airlock_555", "Error al procesar el JSON: " + e.getMessage());
                        }

                        // Pass the list of products to the listener
                        listener.onProductsFetched(productos);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(c, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("airlock_555", "Error en la solicitud: " + error.getMessage());
                    }
                }) {
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes(StandardCharsets.UTF_8);  // Convert JSON body to byte array
            }
        };

        // Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }

}
