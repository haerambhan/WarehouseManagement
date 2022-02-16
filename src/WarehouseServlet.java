import com.database.*;
import com.google.gson.Gson;
import com.models.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Warehouse/*")
public class WarehouseServlet extends HttpServlet 
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
			Set<Warehouse> warehouseContent = null;
			try {
				warehouseContent = db.getWarehouseContent();
			} catch (NoRecordsFoundException e) {
				response.sendError(404,"Cannot find warehouse content");
			}
			sendAsJson(response, warehouseContent);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseAccess db = new DatabaseAccess();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int prodId = Integer.parseInt(request.getParameter("product"));
		int qty = Integer.parseInt(request.getParameter("qty"));
		String actionPerformed = request.getParameter("actionPerformed");
		int uid=user.userId;
		int sid=0;
		try {
			if(actionPerformed.equals("Removed"))
			{
				sid=user.userId;
				db.removeFromWarehouse(prodId, sid, qty);
			}
			
			if(actionPerformed.equals("Stocked"))
			{
				sid=user.userId;
				db.addToWarehouse(prodId, user.userId, qty);
			}
			
			if(actionPerformed.equals("Bought"))
			{
				sid = Integer.parseInt(request.getParameter("supId"));
				db.removeFromWarehouse(prodId, sid, qty);
			}
			db.addtoHistory(uid, prodId, actionPerformed, qty);
		} catch (NumberFormatException e) {
			response.sendError(400);
		} catch (SQLException e) {
			response.sendError(400);
		}
		sendAsJson(response,prodId);	
	}
}
