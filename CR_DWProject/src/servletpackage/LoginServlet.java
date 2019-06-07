package servletpackage;

import databasepackage.Database;
import auxpackage.*;
import userspackage.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Pair<Boolean, String> ValidationResult = Database.ValidateCredentials(username, password);
		if(ValidationResult == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Credentials -1");
		}
		else {
			if(ValidationResult.getVar1()) {
				switch(ValidationResult.getVar2()) {
					case "admin":
						// TODO Show admin html
						break;
					case "contentadmin":
						request.setAttribute("contentAdmin", new ContentAdmin(Database.getNameOfUser(username, "contentAdmin"), username, password));
						response.sendRedirect(request.getContextPath() + "/contentAdminServlet");
						break;
					case "customer":
						// TODO Show customer html
						break;
					default:
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
			else {
				String error = ValidationResult.getVar2();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, error + " @!#@@#$#");
			}
		}
	}
}
