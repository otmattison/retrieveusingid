package web;

import product.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/product")
public class ProductServl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GenericDAO<Product> product;

	public ProductServl() {
		product = new ProductDAOImpl();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<form action='' method='POST'>");
		out.println("<label>Enter Product id: <input type='text' name='id'></input></label>");
		out.println("<input type='submit'>Find Product</input>");
		out.println("</form>");

		out.println("<h2>PRODUCT</h2>");
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String productId = request.getParameter("id");
		
		PrintWriter out = response.getWriter();
		//out.println(productId);
		ProductDAO p = new ProductDAOImpl();
		Product p1 = (p.productInfo((Long.parseLong(productId))));
		if (p1!=null) {
			System.out.println("Product found : " + p1.toString());
			out.println("Product Id : " + p1.getId() + "<br>");
			out.println("Product Name : " + p1.getName() + "<br>");
			out.println("Product is " + (p1.getPrice() + "<br>"));
		} else
			out.println("Product not found");
		}catch(NumberFormatException e) {
			PrintWriter out = response.getWriter();
			out.println("something went wrong");
		}
		

	}
	
}
