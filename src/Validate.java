import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.models.*;
import com.database.*;
@WebServlet("/Validate")
public class Validate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
		int userId = 0;
		try
		{
			userId = Integer.parseInt(req.getParameter("id"));			
		}
		catch(NumberFormatException e)
		{
			response.getWriter().println("Invalid Input");
		}
		int type=0;
		String userType = req.getParameter("ch");
		if(userType.equals("Admin"))
			type = 1;
		if(userType.equals("Supplier"))
			type = 2;
		if(userType.equals("Customer"))
			type = 3;
		User user = null;
		try {
			user = new DatabaseAccess().getUser(userId, type);
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			RequestDispatcher rd = req.getRequestDispatcher("Main");
			rd.forward(req, response);
		} catch (NoRecordsFoundException e) 
		{
			response.getWriter().println("User does not exist");		
		}
		
	}
}
