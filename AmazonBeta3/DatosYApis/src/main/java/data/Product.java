package data;

public record Product(int id, String title, double price, String description, 
		Category category, String[] images ) {
	
	public Product(int id, String title, double price, String description){
		this(id, title, price, description, null, null);	
	}
}
