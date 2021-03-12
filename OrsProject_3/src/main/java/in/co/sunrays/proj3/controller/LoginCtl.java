package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.RoleModelInt;
import in.co.sunrays.proj3.model.UserModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

@WebServlet(name = "LoginCtl", urlPatterns = "/LoginCtl")
public class LoginCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(LoginCtl.class);

	private static final long serialVersionUID = 1L;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";

	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("LoginCtl  Method validate  Started");
		boolean pass = true;
		String op = DataUtility.getString(request.getParameter("operation"));
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		if (OP_LOG_OUT.equalsIgnoreCase(op) || OP_REGISTER.equalsIgnoreCase(op)) {
			return pass;
		}
		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "LoginId"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login"));
			pass = false;
		}
		if (DataValidator.isNull(password)) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}
		log.debug("LoginCtl  Method validate  End");
		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("LoginCtl  Method populateDTO  Started");
		UserDTO dto = new UserDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		dto.setPassword(DataUtility.getString(request.getParameter("password")));
		log.debug("LoginCtl  Method populateDTO  End ");
		return dto;
	}

	/**
	 * Display Logic inside This Method
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Mehtod Do-Get Started");
		HttpSession session = request.getSession(true);
		String op = DataUtility.getString(request.getParameter("operation"));
		if (OP_LOG_OUT.equalsIgnoreCase(op)) {
			session.invalidate();
			ServletUtility.setSuccessMessage("You have been logged out", request);
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("Mehtod Do-Get End");
	}

	/**
	 * Submit Logic inside this Method
	 * 
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug(" LoginCtl Method Do-post Started ");
		HttpSession session = request.getSession(true);
		// Get UserModel
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		// Get RoleModel
		RoleModelInt role = ModelFactory.getInstance().getRoleModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String uri = request.getParameter("URI");
		if (OP_SIGN_IN.equalsIgnoreCase(op)) {
			UserDTO dto = (UserDTO) populateDTO(request);
			try {
				dto = userModel.authenticate(dto.getLogin(), dto.getPassword());
				if (dto != null) {
					session.setAttribute("user", dto);
					long pk = dto.getRoleId();
					RoleDTO roleDto = (RoleDTO) role.findByPK(pk);
					if (roleDto != null) {
						session.setAttribute("role", roleDto.getName());
					}
					if (uri == null || uri.equalsIgnoreCase("null")) {
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					} else {
						ServletUtility.redirect(uri, request, response);
						return;
					}
				} else {
					dto = (UserDTO) populateDTO(request);
					ServletUtility.setDto(dto, request);
					ServletUtility.setErrorMessage("Login ID And PassWord Is Invalid", request);
				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("LoginCtl  Method Do-post End ");
	}

	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}
