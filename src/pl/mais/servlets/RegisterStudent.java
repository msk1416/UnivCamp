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
 * Servlet implementation class RegisterStudent
 */
@WebServlet("/RegisterStudent")
public class RegisterStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterStudent() {
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
		int userToRegister;
		String courseToRegister, status;
		Double grade;
		boolean requestedByUser = false;
		if (isRequestedByRightStudent(request.getParameter("requestedBy"))) {
			userToRegister = Integer.parseInt(request.getParameter("requestedBy"));
			grade = 0.0;
			status = "cur";
			requestedByUser = true;
		} else {
			userToRegister = Integer.parseInt( request.getParameter("userToRegister") );
			grade = (request.getParameter("grade") == null) ? -1 : Double.parseDouble(request.getParameter("grade"));
			status = request.getParameter("courseStatus");
		}
		courseToRegister = request.getParameter("coursesToRegister");
		DBHelper db = (DBHelper)getServletContext().getAttribute("dbhelper");
		db.open();
		if ( requestedByUser || db.tryLogin(userid, MD5Utils.getMD5HashAsString( request.getParameter("password") )) ) {
			if (db.addRegistration(userToRegister, 
									courseToRegister, 
									grade, 
									status)) {
				if (request.getParameter("test") != null) {
					response.getOutputStream().write("true".getBytes());
				} else {
					request.getSession().setAttribute("success", true);
					request.getSession().setAttribute("object", "Registration");
					request.getSession().setAttribute("action", "added");
					if (((User)request.getSession().getAttribute("user")).getRole() == 'a')
						request.getSession().setAttribute("redirect", request.getContextPath() + "/adminPanel.jsp");
					else
						request.getSession().setAttribute("redirect", request.getContextPath() + "/studentPanel.jsp");
					response.sendRedirect(request.getContextPath() + "/resultPage.jsp");
				}
			} else {
				if (request.getParameter("test") != null) {
					response.getOutputStream().write("false".getBytes());
				} else {
					request.getSession().setAttribute("success", false);
					request.getSession().setAttribute("object", "Registration");
					request.getSession().setAttribute("action", "added");
					if (((User)request.getSession().getAttribute("user")).getRole() == 'a')
						request.getSession().setAttribute("redirect", request.getContextPath() + "/adminPanel.jsp");
					else
						request.getSession().setAttribute("redirect", request.getContextPath() + "/studentPanel.jsp");
					response.sendRedirect(request.getContextPath() + "/resultPage.jsp");
				}
			}
		} else {
			response.getWriter().println("<html><body>"
					+ "<h2 style='color: red'>Credentials verification failed</h2><br/>"
					+ "<a href=\""+ request.getContextPath() +"/adminPanel.jsp\" style=\"color:blue;\">Go back to your panel</a>"
					+ "</body></html>");
		}
	}
	
	private boolean isRequestedByRightStudent(String studentId) {
		return (studentId != null);
	}

}
