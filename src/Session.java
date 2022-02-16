import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.*;
import com.models.User;


@WebServlet("/Session")
public class Session extends HttpServlet 
{
	public Gson gson = new Gson();
	private static final long serialVersionUID = 1L;
    public void sendAsJson(HttpServletResponse response, Object obj) throws IOException
    {
    	String res = gson.toJson(obj);
    	PrintWriter out = response.getWriter(); 
		out.print(res);
		out.flush();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		String pathInfo = request.getPathInfo();
		System.out.println(pathInfo);
		sendAsJson(response, u);
	}
}
