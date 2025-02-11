package serv;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import data.Product;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProductService {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "https://api.escuelajs.co/api/v1/products";
    private static final Gson gson = new Gson();

    public static List<Product> getAllProducts() throws IOException {
    	// crear el mensaje de solicitud
	    Request request = new Request.Builder()
	                .url(BASE_URL)
	                .get()
	                .build();
        // ejecutar la solicitud y obtener el mensaje de respuesta
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
            	String responseBody = response.body().string();
            	// Tipo genérico para deserializar
                Type productListType = new TypeToken<List<Product>>(){}.getType();
                // Deserializa el JSON en una lista de productos                
                return gson.fromJson(responseBody, productListType); 
              } else {
                throw new IOException("Error: " + response.code());
            }
        }
	}


    public static Product getProductById(int productId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + productId)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return gson.fromJson(response.body().string(), Product.class);  
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

	    // POST: Crear un nuevo producto
    public static Product createProduct(Product product) throws IOException {
        // Asegúrate de que category no sea null antes de llamar a category.id()
        Product.ProductApi productApi = product.getProduct();

        // Convierte el producto a JSON
        String json = gson.toJson(productApi);
        
        // Crea el cuerpo de la solicitud
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));

        // Construye la solicitud
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(requestBody)
                .build();

        // Envía la solicitud y maneja la respuesta
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Deserializa la respuesta JSON a un objeto Product
                return gson.fromJson(response.body().string(), Product.class);
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }


    // PUT: Actualizar un producto
    public static Product updateProduct(int productId, Product product) throws IOException {
        String json = gson.toJson(product);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + productId)
                .put(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return gson.fromJson(response.body().string(), Product.class);  
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    // DELETE: Eliminar un producto
    public static boolean deleteProduct(int productId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + productId)
                .delete()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return true;
            } else {
            	throw new IOException("Error: " + response.code());
            }
        }
    }
	
	

}
