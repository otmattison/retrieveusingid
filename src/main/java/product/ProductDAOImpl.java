package product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

	

	

	public class ProductDAOImpl implements ProductDAO {

	    final static String SELECT_ALL_SQL = "SELECT id, name, good FROM products;";
	    final static String INSERT_SQL = "INSERT INTO products (name, price) VALUES (?, ?);";
	    final static String UPDATE_SQL = "UPDATE products SET name = ?, good = ? WHERE id = ?;";
	    final static String DELETE_SQL = "DELETE FROM products WHERE id = ?;";
	    final static String FIND_SQL = "SELECT * FROM products WHERE id = ?;";

	    Connection conn = null;

	    public ProductDAOImpl() {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/products?createDatabaseIfNotExist=true", "root", "root");
	        } catch (ClassNotFoundException e) {
	            System.out.println("Driver not found");
	        } catch (SQLException e) {
	            System.out.println("SQL exception");
	            e.printStackTrace();
	        }
	    }
	    
	    public Product productInfo(Long id) {
	    	
	    	 try (PreparedStatement preparedStatement = conn.prepareStatement(FIND_SQL)) {
	             preparedStatement.setLong(1, id);
	             ResultSet rs = preparedStatement.executeQuery();
	             if (rs.next()) {
	            	Product product = new Product( rs.getLong("id"), rs.getString("name"), rs.getFloat("price"));
	            	return product;
	                
	             }
	         } catch (SQLException e) {
	             System.out.println("error");
	             e.printStackTrace();
	         }
	         
	    	return null;
			
	    }

	    @Override
	    public List<Product> getAll() {
	        List<Product> products = new ArrayList<>();
	        try (Statement stmt = conn.createStatement()) {
	            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);
	            while (rs.next()) {
	            	Long id = rs.getLong("id");
	                String name = rs.getString("name");
	                float price = rs.getFloat("price");
	                products.add(new Product(id, name, price));
	            }
	        } catch (SQLException e) {
	            System.out.println("unable to run query");
	            e.printStackTrace();
	        }
	        return products;
	    }

	    @Override
	    public Product create(Product product) {
	        try (PreparedStatement preparedStatement = conn.prepareStatement(
	                INSERT_SQL,
	                Statement.RETURN_GENERATED_KEYS
	        )) {
	            preparedStatement.setString(1, product.getName());
	            preparedStatement.setFloat(2, (product.getPrice()));
	            int affectedRows = preparedStatement.executeUpdate();
	            if (affectedRows == 0) {
	                throw new SQLException("Unable to create record");
	            }
	            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    product.setId(generatedKeys.getLong(1));
	                    return product;
	                } else {
	                    throw new SQLException("Creating product failed, no ID obtained.");
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("unable to run query");
	            e.printStackTrace();
	        }
	        return null;
	    }
	    

		

	    @Override
	    public void remove(Product product) {
	        try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_SQL)) {
	            preparedStatement.setLong(1, product.getId());
	            preparedStatement.execute();
	        } catch (SQLException e) {
	            System.out.println("Unable to run query");
	            System.out.println("SQL State: " + e.getSQLState());
	            System.out.println("Error Code: " + e.getErrorCode());
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public Product update(Product product) {
	        if (product.getId() > Integer.MAX_VALUE) {
	            throw new RuntimeException("ID too large");
	        }
	        try (PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_SQL)) {
	            preparedStatement.setString(1, product.getName());
	            preparedStatement.setLong(2, product.getId());
	            preparedStatement.setFloat(3, product.getPrice());
	            int changes = preparedStatement.executeUpdate();
	            if (changes > 0) {
	                return product;
	            }
	        } catch (SQLException e) {
	            System.out.println("Unable to run query");
	            System.out.println("SQL State: " + e.getSQLState());
	            System.out.println("Error Code: " + e.getErrorCode());
	            e.printStackTrace();
	        }
	        return null;
	    }

	    @Override
	    public int count() {
	        return 0;
	    }

		@Override
		public void remove(Long id) {
			// TODO Auto-generated method stub
			
		}

	}

