import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.database.DatabaseAccess;
import com.google.gson.Gson;
import com.models.NoRecordsFoundException;
import com.models.*;

@WebServlet("/Stocks/*")
public class StockServlet extends HttpServlet 
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
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");		
		System.out.println(pathInfo);
		if(pathInfo == null || pathInfo.equals("/"))
		{
			Set<Stock> stocks = null;
			try {
				stocks = db.getStock(user.userId);
			} catch (NoRecordsFoundException e) {
				response.sendError(404);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				sendAsJson(response, stocks);				
			}
		}
	}
}
