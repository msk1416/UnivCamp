package pl.mais.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.mais.db.DBHelper;

/**
 * Servlet implementation class UpdateGrade
 */
@WebServlet("/UpdateGrade")
public class UpdateGrade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateGrade() {
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
		System.out.println("regId = " + request.getParameter("regId") + ", new grade = " + request.getParameter("grade"));

		DBHelper db = (DBHelper)getServletContext().getAttribute("dbhelper");
		float grade = Float.parseFloat(request.getParameter("grade"));
		String regId = request.getParameter("regId");
		String course = regId.substring(regId.indexOf('_')+1);
		db.open();
		if (regId != null && db.updateGrade(grade, regId)) {
			request.getSession().setAttribute("success", true);
			request.getSession().setAttribute("object", "Grade");
			request.getSession().setAttribute("action", "updated");
			request.getSession().setAttribute("redirect", request.getContextPath() + "/teacherPanel.jsp?courseid=" + course);
			response.sendRedirect(request.getContextPath() + "/resultPage.jsp");
		} else {
			request.getSession().setAttribute("success", false);
			request.getSession().setAttribute("object", "Grade");
			request.getSession().setAttribute("action", "updated");
			request.getSession().setAttribute("redirect", request.getContextPath() + "/teacherPanel.jsp?courseid=" + course);
			response.sendRedirect(request.getContextPath() + "/resultPage.jsp");
		}
		db.close();
	}
	

}
