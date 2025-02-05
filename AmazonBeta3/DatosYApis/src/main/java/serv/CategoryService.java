package serv;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import data.Category;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CategoryService {
	
	private static final String BASE_URL_CATEGORY = "https://api.escuelajs.co/api/v1/categories";
	private static final OkHttpClient client = new OkHttpClient();
	private static final Gson gson = new Gson();

	public static List<Category> getAllCategories() throws IOException {
	    Request request = new Request.Builder()
	                .url(BASE_URL_CATEGORY)
	                .get()
	                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
            	String responseBody = response.body().string();
                Type categoryListType = new TypeToken<List<Category>>(){}.getType();
                return gson.fromJson(responseBody, categoryListType); 
              } else {
                throw new IOException("Error: " + response.code());
            }
        }
	}
	
	public static Category getCategoryById(int categoryId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL_CATEGORY + "/" + categoryId)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return gson.fromJson(response.body().string(), Category.class);  
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }
	
	public static Category createCategory(Category category) throws IOException {
        String json = gson.toJson(category);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL_CATEGORY)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
            	return gson.fromJson(response.body().string(), Category.class);  
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }
	
	public Category updateProduct(int categoryId, Category category) throws IOException {
        String json = gson.toJson(category);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL_CATEGORY + "/" + categoryId)
                .put(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return gson.fromJson(response.body().string(), Category.class);  
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }
	
	public static boolean deleteCategory(int categoryId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL_CATEGORY + "/" + categoryId)
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
