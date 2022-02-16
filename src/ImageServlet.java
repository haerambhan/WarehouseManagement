

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
import com.models.*;;

@WebServlet("/Images/*")
public class ImageServlet extends HttpServlet 
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
			Set<Image> images = null;
			try
			{
				if(request.getParameter("prodId")!=null)
				{
					int pId = Integer.parseInt(request.getParameter("prodId"));
					images = db.getImages(pId);
				}
				else
				images = db.getImages();
			} 
			catch (NoRecordsFoundException e) {
				response.sendError(404);
			}
			sendAsJson(response, images);
		}
		else
		{	
			int id = Integer.parseInt(pathInfo.substring(1));
			Image image = null;
			try {
				image = db.getImage(id);
			}
			catch (NoRecordsFoundException e) {
				response.sendError(404);
			}
			sendAsJson(response, image);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseAccess db = new DatabaseAccess();
		String imageUrl = request.getParameter("imageUrl");
		int prodId = Integer.parseInt(request.getParameter("prodId"));
		int imageId = db.createImage(imageUrl);
		db.mapImageToProduct(imageId, prodId);
		doGet(request, response);
	}
}
