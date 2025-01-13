package Controllers.MySQL;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

    public interface CreateUserFetchListener {
        void onResponse(int status, String message);
        void onError(String error);
    }


    public void getAllProducts(final ProductFetchListener listener) {
        Log.i("airlock_555", "Enviando solicitud a la URL: http://HOST/api/v1/products");

        StringRequest request = new StringRequest(Request.Method.GET, String.format("%s/api/v1/products", props.getProperty("BACKEND_HOST")),
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
                                String fecha_i = object.getString("FechaIngreso");
                                String gen = "";
                                String marca = "";
                                String img = "";

                                p = new Producto(
                                        id, nombre, descripcion, p_compra,
                                        p_venta, stock, prov_id, fecha_i, gen,
                                        marca, img
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

    public void getProductsSimilary(final ProductFetchListener listener, String gen, String marca) {
        Log.i("airlock_555", "Enviando solicitud a la URL: http://HOST/api/v1/productsSimilary");

        // Create JSON body to send the product ID in the request body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("genero", gen);
            jsonBody.put("marca", marca);
        } catch (JSONException e) {
            Log.e("airlock_555", "Error al crear el JSON: " + e.getMessage());
        }

        // Use JsonArrayRequest since the response is a JSON array
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,
                String.format("%s/api/v1/productsSimilary", props.getProperty("BACKEND_HOST")),
                jsonBody.names(),
                response -> {
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
                            String fecha_i = productObject.getString("FechaIngreso");
                            String gem = "";
                            String marca1 = "";
                            String img = "";

                            Producto p = new Producto(id, nombre, descripcion, p_compra, p_venta, stock, prov_id, fecha_i, gem, marca1, img);
                            productos.add(p);
                        }
                    } catch (JSONException e) {
                        Log.e("airlock_555", "Error al procesar el JSON: " + e.getMessage());
                    }

                    // Pass the list of products to the listener
                    listener.onProductsFetched(productos);
                },
                error -> Log.e("airlock_555", "Error en la solicitud: " + error.getMessage())) {
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes(StandardCharsets.UTF_8);  // Convert JSON body to byte array
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }

    public void getProductsForId(final ProductFetchListener listener, int id) {
        Log.i("airlock_555", "Enviando solicitud a la URL: http://HOST/api/v1/products");

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ProductoID", id);
        } catch (JSONException e) {
            Log.e("airlock_555", "Error al crear el JSON: " + e.getMessage());
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,
                String.format("%s/api/v1/products", props.getProperty("BACKEND_HOST")),
                jsonBody.names(),
                response -> {
                    List<Producto> productos = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject productObject = response.getJSONObject(i);

                            int id1 = productObject.getInt("ProductoID");
                            String nombre = productObject.getString("Nombre");
                            String descripcion = productObject.getString("Descripcion");
                            Double p_compra = productObject.getDouble("PrecioCompra");
                            Double p_venta = productObject.getDouble("PrecioVenta");
                            int stock = productObject.getInt("Stock");
                            int prov_id = productObject.getInt("ProveedorID");
                            String fecha_i = productObject.getString("FechaIngreso");
                            String gem = "";
                            String marca = "";
                            String img = "";

                            Producto p = new Producto(id1, nombre, descripcion, p_compra, p_venta, stock, prov_id, fecha_i, gem, marca, img);
                            productos.add(p);
                        }
                    } catch (JSONException e) {
                        Log.e("airlock_555", "Error al procesar el JSON: " + e.getMessage());
                    }

                    // Pass the list of products to the listener
                    listener.onProductsFetched(productos);
                },
                error -> Log.e("airlock_555", "Error en la solicitud: " + error.getMessage())) {
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes(StandardCharsets.UTF_8);  // Convert JSON body to byte array
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }

    public void searchProduct(String text_search, final ProductFetchListener listener, Context c) {
        Log.i("airlock_555", "Enviando solicitud a la URL: http://HOST/api/v1/searchProducts");

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("text_search", text_search);
        } catch (JSONException e) {
            Log.e("airlock_555", "Error al crear el JSON: " + e.getMessage());
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,
                String.format("%s/api/v1/searchProducts", props.getProperty("BACKEND_HOST")),
                jsonBody.names(),
                response -> {
                    List<Producto> productos = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject productObject = response.getJSONObject(i);

                            int id = productObject.getInt("ProductoID");
                            String nombre = productObject.getString("Nombre");
                            String descripcion = productObject.getString("Descripcion");
                            Double p_compra = productObject.getDouble("PrecioCompra");
                            Double p_venta = productObject.getDouble("PrecioVenta");
                            int stock = productObject.getInt("Stock");
                            int prov_id = productObject.getInt("ProveedorID");
                            String fecha_i = productObject.getString("FechaIngreso");
                            String gem = "";
                            String marca = "";
                            String img = "";

                            Producto p = new Producto(id, nombre, descripcion, p_compra, p_venta, stock, prov_id, fecha_i, gem, marca, img);
                            productos.add(p);
                        }
                    } catch (JSONException e) {
                        Log.e("airlock_555", "Error al procesar el JSON: " + e.getMessage());
                    }

                    // Pass the list of products to the listener
                    listener.onProductsFetched(productos);
                },
                error -> {
                    Toast.makeText(c, error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("airlock_555", "Error en la solicitud: " + error.getMessage());
                }) {
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes(StandardCharsets.UTF_8);  // Convert JSON body to byte array
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }

    public void createUser(
            final CreateUserFetchListener listener,
            String nombre,
            String apellidos,
            String tlf,
            String email,
            String pssw
    ) {
        Log.i("airlock_555", "Enviando solicitud a la URL: http://HOST/api/v1/auth/create-user");

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nombre", nombre);
            jsonBody.put("apellidos", apellidos);
            jsonBody.put("tlf", tlf);
            jsonBody.put("email", email);
            jsonBody.put("pssw", pssw);
        } catch (JSONException e) {
            Log.e("airlock_555", "Error al crear el JSON: " + e.getMessage());
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                String.format("%s/api/v1/auth/create-user", props.getProperty("BACKEND_HOST")),
                jsonBody,
                response -> {
                    try {
                        int status = response.getInt("status");
                        String message = response.getString("message");

                        // Llamar al listener para manejar la respuesta
                        listener.onResponse(status, message);

                    } catch (JSONException e) {
                        Log.e("airlock_555", "Error al procesar el JSON: " + e.getMessage());
                    }
                },
                error -> {
                    Log.e("airlock_555", "Error en la solicitud: " + error.getMessage());
                    listener.onError(error.getMessage());  // Llamar al listener en caso de error
                }) {
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }

    public void loginUser(
            final CreateUserFetchListener listener,
            String email,
            String pssw
    ) {
        Log.i("airlock_555", "Enviando solicitud a la URL: http://HOST/api/v1/auth/login-user");

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("pssw", pssw);
        } catch (JSONException e) {
            Log.e("airlock_555", "Error al crear el JSON: " + e.getMessage());
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                String.format("%s/api/v1/auth/login-user", props.getProperty("BACKEND_HOST")),
                jsonBody,  // Cuerpo JSON a enviar
                response -> {
                    try {
                        int status = response.getInt("status");
                        String message = response.getString("message");

                        listener.onResponse(status, message);

                    } catch (JSONException e) {
                        Log.e("airlock_555", "Error al procesar el JSON: " + e.getMessage());
                    }
                },
                error -> {
                    Log.e("airlock_555", "Error en la solicitud: " + error.getMessage());
                    listener.onError(error.getMessage());
                }) {
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }

    public void isValidateEmail(
            final CreateUserFetchListener listener,
            String email
    ) {
        Log.i("airlock_555", "Enviando solicitud a la URL: http://HOST/api/v1/auth/validate-email");

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
        } catch (JSONException e) {
            Log.e("airlock_555", "Error al crear el JSON: " + e.getMessage());
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                String.format("%s/api/v1/auth/validate-email", props.getProperty("BACKEND_HOST")),
                jsonBody,
                response -> {
                    try {
                        int status = response.getInt("status");
                        String message = response.getString("message");

                        listener.onResponse(status, message);

                    } catch (JSONException e) {
                        Log.e("airlock_555", "Error al procesar el JSON: " + e.getMessage());
                    }
                },
                error -> {
                    Log.e("airlock_555", "Error en la solicitud: " + error.getMessage());
                    listener.onError(error.getMessage());
                }) {
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }

    public void validateCode(
            final CreateUserFetchListener listener,
            String email,
            String code
    ) {
        Log.i("airlock_555", "Enviando solicitud a la URL: http://HOST/api/v1/auth/validate-code");

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("code", code.toUpperCase());
        } catch (JSONException e) {
            Log.e("airlock_555", "Error al crear el JSON: " + e.getMessage());
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                String.format("%s/api/v1/auth/validate-code", props.getProperty("BACKEND_HOST")),
                jsonBody,
                response -> {
                    try {
                        int status = response.getInt("status");
                        String message = response.getString("message");

                        listener.onResponse(status, message);

                    } catch (JSONException e) {
                        Log.e("airlock_555", "Error al procesar el JSON: " + e.getMessage());
                    }
                },
                error -> {
                    Log.e("airlock_555", "Error en la solicitud: " + error.getMessage());
                    listener.onError(error.getMessage());
                }) {
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }

    public void deleteCode(
            final CreateUserFetchListener listener,
            String email,
            String code
    ) {
        Log.i("airlock_555", "Enviando solicitud a la URL: http://HOST/api/v1/auth/delete-code");

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("code", code.toUpperCase());
        } catch (JSONException e) {
            Log.e("airlock_555", "Error al crear el JSON: " + e.getMessage());
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                String.format("%s/api/v1/auth/delete-code", props.getProperty("BACKEND_HOST")),
                jsonBody,
                response -> {
                    try {
                        int status = response.getInt("status");
                        String message = response.getString("message");

                        listener.onResponse(status, message);

                    } catch (JSONException e) {
                        Log.e("airlock_555", "Error al procesar el JSON: " + e.getMessage());
                    }
                },
                error -> {
                    Log.e("airlock_555", "Error en la solicitud: " + error.getMessage());
                    listener.onError(error.getMessage());
                }) {
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
    }

}
