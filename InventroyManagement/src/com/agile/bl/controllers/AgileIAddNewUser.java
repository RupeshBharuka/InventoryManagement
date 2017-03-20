package com.agile.bl.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.agile.bl.dao.AgileUserDaoImplementation;
import com.agile.bl.model.AgileUser;

/**
 * Servlet implementation class AgileInsertNewUser
 * 
 * @author Vishal Arora
 * 
 *         this servlet does the task of isnerting a fresh user inside database.
 */

@WebServlet("/insertuser")
public class AgileIAddNewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	AgileUserDaoImplementation agileUserImpl = new AgileUserDaoImplementation();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession httpSession = request.getSession(false);

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		if (httpSession == null) {
			response.sendRedirect("login.jsp");
		} else {

			try {
				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				String emailId = request.getParameter("emailid");
				String password = request.getParameter("password");
				String adminCheckBoxValue = request.getParameter("admincheck");

				System.out.println("-> " + adminCheckBoxValue);

				boolean isAdmin;

				if ("chk".equalsIgnoreCase(adminCheckBoxValue)) {
					isAdmin = true;
				} else {
					isAdmin = false;
				}

				System.out.println(" -> -> " + isAdmin);

				AgileUser agileUser = new AgileUser();
				agileUser.setFirstName(firstName);
				agileUser.setLastName(lastName);
				agileUser.setEmailId(emailId);
				agileUser.setPassword(password);
				agileUser.setIsadmin(isAdmin);

				/*String data =*/agileUserImpl.addUserDetails(agileUser);
				/*if(data!=null)*/
				response.sendRedirect("agilelogin");
				

			} catch (Exception e) {
				Logger.getGlobal().log(Level.SEVERE, "Unable to log user", e.getClass().getName());
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
