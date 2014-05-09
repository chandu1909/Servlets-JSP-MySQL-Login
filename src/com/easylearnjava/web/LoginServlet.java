package com.easylearnjava.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easylearnjava.service.LoginService;

/**
 * 
 * @author rnr
 *
 *LoginServlet inherits all the features of a servlet by extending HttpServlet.
 *The doPost method will be called by the webContainer.
 *If valid credentials are given the user is redirected to success page.
 *If invalid credentials are entered then the login page is displayed back to the user.  
 */
public class LoginServlet extends HttpServlet{
		
	/**
	 * This is autogenerated
	 */
	private static final long serialVersionUID = 850900806199818086L;
	

	/**
	 * The request comes to this method when the login button is clicked
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
	
		try {
			//get the user entered input values from the "HttpServletRequest" object, i.e request 
			String userNameStr =  request.getParameter("usernameTB");
			String passwordStr =  request.getParameter("passwordTB");			
			
			//inputdata validation
			boolean isDataValid = isValidData(userNameStr, passwordStr);
			if(!isDataValid){
				response.sendRedirect("login.jsp");
				return;
			}
			
			LoginService loginService = new LoginService();
			boolean isValid = loginService.isValidPassword(userNameStr, passwordStr);
			
			if(isValid){
				request.setAttribute("reqName", userNameStr);
				request.getRequestDispatcher("/loginSuccess.jsp").forward(request, response);
			}else{
				request.setAttribute("errMsg", "invalid login cidentials");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				
			}
		}catch(Exception e){
			request.setAttribute("errMsg", e.getMessage());
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
	
	/**
	 * Method for validating the input values
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean isValidData(String userName, String password){
		
		if((null != userName) && (userName != "") && (userName.length() >= 5)){
			if((null != password) && (password != "") && (password.length() >= 5)){
				return true;
			}
		}		
		return false;
	}	
}
