package data;

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
		return new ProductApi(id, title, price, description,category.id(), images);
	}
}
