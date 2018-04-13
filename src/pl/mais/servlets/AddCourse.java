package pl.mais.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.mais.db.DBHelper;
import pl.mais.mapping.User;

/**
 * Servlet implementation class AddCourse
 */
@WebServlet("/AddCourse")
public class AddCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCourse() {
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
			if (db.addCourse(request.getParameter("courseid"), 
							 request.getParameter("coursename"), 
							 request.getParameter("mode"), 
							 Boolean.parseBoolean( request.getParameter("opened") ), 
							 Integer.parseInt( request.getParameter("maxcapacity") ), 
							 Integer.parseInt( request.getParameter("teacherid") ), 
							 Integer.parseInt( request.getParameter("nects") ), 
							 request.getParameter("faculty"))) {
				request.getSession().setAttribute("success", true);
				request.getSession().setAttribute("object", "Course");
				request.getSession().setAttribute("redirect", request.getContextPath() + "/adminPanel.jsp");
				response.sendRedirect(request.getContextPath() + "/resultPage.jsp");
			} else {
				request.getSession().setAttribute("success", false);
				request.getSession().setAttribute("object", "Course");
				request.getSession().setAttribute("redirect", request.getContextPath() + "/adminPanel.jsp");
				response.sendRedirect(request.getContextPath() + "/resultPage.jsp");
			}
			db.close();
		}
	}

}
