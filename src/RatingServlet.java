

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.DatabaseAccess;
import com.google.gson.Gson;
import com.models.NoRecordsFoundException;
import com.models.Rating;

@WebServlet("/Ratings/*")
public class RatingServlet extends HttpServlet {
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
			Set<Rating> ratings = null;
			try 
			{
				ratings = db.getRatings();
			} catch (NoRecordsFoundException e) {
				response.sendError(404);
			}
			sendAsJson(response, ratings);
		}
		else
		{
			int prodId = Integer.parseInt(pathInfo.substring(1,4));
			System.out.println(prodId);
			try 
			{
				if(request.getParameter("userId")!=null)
				{
					int userId = Integer.parseInt(request.getParameter("userId"));
					Rating rating = db.getRatings(prodId,userId);	
					sendAsJson(response, rating);
				}
				else
				{
					Set<Rating> ratings = db.getRatings(prodId);
					sendAsJson(response, ratings);
				}
			}
			catch (NoRecordsFoundException e) {
				response.sendError(404);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseAccess db = new DatabaseAccess();

		int rating = Integer.parseInt(request.getParameter("rating"));
		int prodId = Integer.parseInt(request.getParameter("prodId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		db.createRating(userId,prodId,rating);	
	}
}
