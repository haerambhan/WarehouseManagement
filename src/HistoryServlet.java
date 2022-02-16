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


@WebServlet("/History/*")
public class HistoryServlet extends HttpServlet 
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
			Set<Record> records = null;
			try {
				records = db.getRecords();
			} catch (NoRecordsFoundException e) {
				response.sendError(404);
			}
			sendAsJson(response, records);
		}
	}
}
