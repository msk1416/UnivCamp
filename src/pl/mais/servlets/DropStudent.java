package pl.mais.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.mais.db.DBHelper;
import pl.mais.mapping.User;
import pl.mais.utils.MD5Utils;

/**
 * Servlet implementation class DropStudent
 */
@WebServlet("/DropStudent")
public class DropStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DropStudent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userid = ((User)request.getSession().getAttribute("user")).getId();
		String regToDelete = request.getParameter("registersToDrop");
		DBHelper db = (DBHelper)getServletContext().getAttribute("dbhelper");
		db.open();
		if ( db.tryLogin(userid, MD5Utils.getMD5HashAsString( request.getParameter("password") )) ) {
			if (db.removeRegistration(regToDelete)) {
				request.getSession().setAttribute("success", true);
				request.getSession().setAttribute("action", "removed");
				request.getSession().setAttribute("object", "Registration");
				request.getSession().setAttribute("redirect", request.getContextPath() + "/adminPanel.jsp");
				response.sendRedirect(request.getContextPath() + "/resultPage.jsp");
			} else {
				request.getSession().setAttribute("success", false);
				request.getSession().setAttribute("action", "removed");
				request.getSession().setAttribute("object", "Registration");
				request.getSession().setAttribute("redirect", request.getContextPath() + "/adminPanel.jsp");
				response.sendRedirect(request.getContextPath() + "/resultPage.jsp");
			}
		} else {
			response.getWriter().println("<html><body>"
					+ "<h2 style='color: red'>Credentials verification failed</h2><br/>"
					+ "<a href=\""+ request.getContextPath() +"/adminPanel.jsp\" style=\"color:blue;\">Go back to your panel</a>"
					+ "</body></html>");
		}
	}

}
