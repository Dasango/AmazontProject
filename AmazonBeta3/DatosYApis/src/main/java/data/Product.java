package data;

import java.util.Arrays;

public record Product(int id, String title, double price, String description, 
		Category category, String[] images ) {
	
	public Product(int id, String title, double price, String description){
		this(id, title, price, description, null, null);	
	}
	
	public record ProductApi(int id, String title, double price, String description, 
			int categoryId, String[] images ) {
		
	}
	
	//Obtener con la id de la  
	public ProductApi getProduct() {
	    // Si images es null, evita NullPointerException asignando un array vacío
	    String[] cleanedImages = images == null ? new String[0] : 
	        Arrays.stream(images)
	              .map(img -> img.replaceAll("[\\[\\]\"']", "").trim())
	              .toArray(String[]::new);
	    
	    return new ProductApi(id, title, price, description, category.id(), cleanedImages);
	}

}
