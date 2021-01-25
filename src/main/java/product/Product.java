package product;

public class Product {

	private Long id;
	private String name;
	private float price;

	public Product() {

	}

	public Product(Long id, String name, float price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long generatedKeys) {
		this.id = generatedKeys;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
