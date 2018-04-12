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
		try {
			
			String pass = request.getParameter("password");
			StringBuffer hexString = new StringBuffer();
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(pass.getBytes());
			for (int i = 0; i < bytes.length; i++) {
			    if ((0xff & bytes[i]) < 0x10) {
			        hexString.append("0"
			                + Integer.toHexString((0xFF & bytes[i])));
			    } else {
			        hexString.append(Integer.toHexString(0xFF & bytes[i]));
			    }
			}
			String encryptedPassword = hexString.toString();
			
			if (db.tryLogin(Integer.parseInt((String)request.getParameter("username")), encryptedPassword)) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} else {
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
