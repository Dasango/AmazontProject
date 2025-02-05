package serv;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import data.Category;
import data.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UserService {
	
	private static final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "https://api.escuelajs.co/api/v1/users";
    private static final Gson gson = new Gson();
    
    public static List<User> getAllUsers() throws IOException {
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
                Type UserListType = new TypeToken<List<User>>(){}.getType();
                return gson.fromJson(responseBody, UserListType); 
              } else {
                throw new IOException("Error: " + response.code());
            }
        }
	}
    
    

    

    public static User getUserById(int userId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + userId)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return gson.fromJson(response.body().string(), User.class);  
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    public User createUser(User user) throws IOException {
        String json = gson.toJson(user);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
            	return gson.fromJson(response.body().string(), User.class);  
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    public User updateUser(int userId, User user) throws IOException {
        String json = gson.toJson(user);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/" + userId)
                .put(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return gson.fromJson(response.body().string(), User.class);  
            } else {
                throw new IOException("Error: " + response.code());
            }
        }
    }

    public boolean deleteUser(int userId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + userId)
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
	
    public static boolean verifyUserEmail(String email) throws IOException {
    	
    	 MediaType mediaType = MediaType.parse("application/json");
         String jsonBody = "{\r\n  \"email\": \"" + email + "\"\r\n}";
         RequestBody body = RequestBody.create(mediaType, jsonBody);
         Request request = new Request.Builder()
                 .url("https://api.escuelajs.co/api/v1/users/is-available")
                 .method("POST", body)
                 .addHeader("Content-Type", "application/json")
                 .build();
         Response response = client.newCall(request).execute();
         if (response.isSuccessful()) {
             String responseBody = response.body().string();

             JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

             boolean isAvailable = jsonResponse.get("isAvailable").getAsBoolean();

             return isAvailable;
         } else {
        	 return false;
         }
    }


}
