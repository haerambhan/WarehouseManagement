import com.database.*;
import com.google.gson.Gson;
import com.models.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Products/*")
public class ProductServlet extends HttpServlet 
{
	public Gson gson = new Gson();
	
	private static final long serialVersionUID = 1L;
	
    public void sendAsJson(HttpServletResponse response, Object obj) throws IOException
    {
    	String res = gson.toJson(obj);
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter(); 
		out.print(res);
		out.flush();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		DatabaseAccess db = new DatabaseAccess();
		String pathInfo = request.getPathInfo();
		System.out.println(pathInfo);
		if(pathInfo == null || pathInfo.equals("/")){
			Set<Product> products = null;
			try {
				if(request.getParameter("catId")!=null)
				{
					int catId = Integer.parseInt(request.getParameter("catId"));
					products = db.getProducts(catId);
				}
				else
				products = db.getProducts();
			} catch (NoRecordsFoundException e) {
				response.sendError(404);
			}
			sendAsJson(response, products);
		}
		else
		{
			int prodId = Integer.parseInt(pathInfo.substring(1));
			Product product = null;
			try {
				product = db.getProductById(prodId);
			}
			catch (NoRecordsFoundException e) {
				response.sendError(404);
			}
			sendAsJson(response, product);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseAccess db = new DatabaseAccess();
		String name = request.getParameter("name");
		int price = Integer.parseInt(request.getParameter("price"));
		int catId = Integer.parseInt(request.getParameter("category"));
		int id = db.createProduct(name, catId, price);
		sendAsJson(response,id);
		
	}
}
