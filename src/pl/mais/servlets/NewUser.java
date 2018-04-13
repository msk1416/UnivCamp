package pl.mais.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.glass.ui.Application;

import pl.mais.db.DBHelper;
import pl.mais.mapping.User;

/**
 * Servlet implementation class NewUser
 */
@WebServlet("/NewUser")
public class NewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUser() {
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
		if (((User)request.getSession().getAttribute("user")).getRole() == 'a') {
			DBHelper db = (DBHelper)getServletContext().getAttribute("dbhelper");
			db.open();
			if (request.getParameter("role").equals("s")) {
				if (db.addStudent(request.getParameter("firstname"), 
								request.getParameter("lastname"), 
								request.getParameter("birthday"), 
								request.getParameter("email"), 
								request.getParameter("currentstudies"), 
								Integer.parseInt(request.getParameter("currentects"))) )  {
					request.getSession().setAttribute("success", true);
					request.getSession().setAttribute("object", "Student");
					request.getSession().setAttribute("redirect", request.getContextPath() + "/adminPanel.jsp");
					response.sendRedirect(request.getContextPath() + "/resultPage.jsp");
				} else {
					request.getSession().setAttribute("success", false);
					request.getSession().setAttribute("object", "Student");
					request.getSession().setAttribute("redirect", request.getContextPath() + "/adminPanel.jsp");
					response.sendRedirect(request.getContextPath() + "/resultPage.jsp");
				}
			} else if (request.getParameter("role").equals("t")) {
				if (db.addTeacher(request.getParameter("firstname"), 
								request.getParameter("lastname"), 
								request.getParameter("birthday"), 
								request.getParameter("email"), 
								request.getParameter("officenumber"))) {
					request.getSession().setAttribute("success", true);
					request.getSession().setAttribute("object", "Teacher");
					request.getSession().setAttribute("redirect", request.getContextPath() + "/adminPanel.jsp");
					response.sendRedirect(request.getContextPath() + "/resultPage.jsp");
				} else {
					request.getSession().setAttribute("success", false);
					request.getSession().setAttribute("object", "Teacher");
					request.getSession().setAttribute("redirect", request.getContextPath() + "/adminPanel.jsp");
					response.sendRedirect(request.getContextPath() + "/resultPage.jsp");
				}
			}
			db.close();
		}
	}

}
