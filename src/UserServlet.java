import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.database.DatabaseAccess;
import com.models.NoRecordsFoundException;
import com.google.gson.*;
import com.models.User;

@WebServlet("/Users/*")
public class UserServlet extends HttpServlet 
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
		if(pathInfo == null || pathInfo.equals("/")){
			Set<User> users = null;
			try
			{
				if(request.getParameter("prodId")!=null)
				{
					int pId = Integer.parseInt(request.getParameter("prodId"));
					users = db.getSuppliers(pId);
				}
				else
				users = db.getUsers();
			} 
			catch (NoRecordsFoundException e) {
				response.sendError(404);
			}
			sendAsJson(response, users);
		}
		else
		{
			
			int id = Integer.parseInt(pathInfo.substring(1));
			User user = null;
			try {
				user = db.getUser(id);
			}
			catch (NoRecordsFoundException e) {
				response.sendError(404);
			}
			sendAsJson(response, user);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseAccess db = new DatabaseAccess();
		int type = Integer.parseInt(request.getParameter("type"));
		String name = request.getParameter("name");
		int userId = db.createUser(name, type);
		sendAsJson(response,userId);
	}

}
