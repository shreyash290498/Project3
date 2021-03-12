package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.ServletUtility;

@WebServlet(name = "WelcomeCtl", urlPatterns = "/WelcomeCtl")
public class WelcomeCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(WelcomeCtl.class);

	/**
	 * Display Logic inside This Method
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("WelcomCtl Method Do-Get Strsted");
		ServletUtility.forward(getView(), request, response);

		log.debug("WelcomCtl Method Do-Get End");
	}

	/**
	 * Submit Logic inside this Method
	 * 
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("WelcomCtl Method Do-Post Strsted");
		String op = DataUtility.getString(request.getParameter("operation"));
		ServletUtility.forward(getView(), request, response);

		log.debug("WelcomCtl Method Do-Post End");
	}

	/**
	 * Returns the view page of WelcomeCtl
	 * 
	 * @return WelcomeView.jsp: View page of WelcomeCtl
	 */
	@Override
	protected String getView() {
		return ORSView.WELCOME_VIEW;
	}
}
