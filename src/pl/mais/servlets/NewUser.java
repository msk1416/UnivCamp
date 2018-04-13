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
					response.getWriter().println("<html><body><h2 style='color: green'>Student has been added successfully</h2></body></html>");
				} else {
					response.getWriter().println("<html><body><h2 style='color: red'>Student could not be added</h2></body></html>");
				}
			} else if (request.getParameter("role").equals("t")) {
				if (db.addTeacher(request.getParameter("firstname"), 
								request.getParameter("lastname"), 
								request.getParameter("birthday"), 
								request.getParameter("email"), 
								request.getParameter("officenumber"))) {
					response.getWriter().println("<html><body><h2 style='color: green'>Teacher has been added successfully</h2></body></html>");
				} else {
					response.getWriter().println("<html><body><h2 style='color: red'>Teacher could not be added</h2></body></html>");
				}
			}
			db.close();
		}
	}

}
