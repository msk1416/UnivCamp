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
 * Servlet implementation class RemoveUser
 */
@WebServlet("/RemoveUser")
public class RemoveUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveUser() {
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
		int userToDelete = Integer.parseInt(request.getParameter("userToDelete"));
		DBHelper db = (DBHelper)getServletContext().getAttribute("dbhelper");
		db.open();
		if ( db.tryLogin(userid, MD5Utils.getMD5HashAsString( request.getParameter("password") )) ) {
			if (db.removeUser(userToDelete)) {
				response.getWriter().println("<html><body><h2 style='color: green'>User has been removed successfully</h2></body></html>");
			} else {
				response.getWriter().println("<html><body><h2 style='color: red'>User deletion failed</h2></body></html>");
			}
		} else {
			response.getWriter().println("<html><body><h2 style='color: red'>Credentials verification failed</h2></body></html>");
		}
	}

}
