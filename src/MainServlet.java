import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.models.*;

@WebServlet("/Main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("Reached servlet");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user.userType.equals("Admin"))
		{
			response.sendRedirect("adminMenu.html");
		}
		else if(user.userType.equals("Supplier"))
		{
			response.sendRedirect("supplierMenu.html");
		}
		else if(user.userType.equals("Customer"))
		{
			response.sendRedirect("customerMenu.html");
		}		
	}
}

