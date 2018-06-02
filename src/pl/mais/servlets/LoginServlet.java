package pl.mais.servlets;

import java.io.IOException;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glassfish.jersey.internal.util.Base64;

import pl.mais.db.DBHelper;
import pl.mais.utils.MD5Utils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		DBHelper db = new DBHelper();
		db.open();
		String username = request.getParameter("username");
		try {
			String encryptedPassword = 
					MD5Utils.getMD5HashAsString(request.getParameter("password"));
			if (db.tryLogin(Integer.parseInt(username), encryptedPassword)) {
				//login succeeded
				getServletContext().setAttribute("dbhelper", db);
				char role = db.getUserById(Integer.parseInt(username)).getRole();
				request.getSession().setAttribute("userid", username);
				
				if (role == 'a') {
					response.sendRedirect(request.getContextPath() + "/adminPanel.jsp");
				} else if (role == 's') {
					response.sendRedirect(request.getContextPath() + "/studentPanel.jsp");
				} else if (role == 't') {
					response.sendRedirect(request.getContextPath() + "/teacherPanel.jsp");
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/login.jsp?f=creds");
			}
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/login.jsp?f=creds");
		} finally {
			db.close();
		}
		
	}

}
