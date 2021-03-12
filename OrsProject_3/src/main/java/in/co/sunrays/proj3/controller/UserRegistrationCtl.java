package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.UserModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * User registration functionality Controller. Performs operation for User
 * Registration
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "UserRegistrationCtl", urlPatterns = "/UserRegistrationCtl")
public class UserRegistrationCtl extends BaseCtl {
	public static final String OP_SIGN_UP = "SignUp";
	private static Logger log = Logger.getLogger(UserRegistrationCtl.class);

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("UserRegistrationCtl populateDTO  method Start");
		UserDTO dto = new UserDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setRoleId(RoleDTO.STUDENT);
		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
		dto.setLogin(DataUtility.getString(request.getParameter("loginId")));
		dto.setPassword(DataUtility.getString(request.getParameter("password")));
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		dto.setConfirmPassword(DataUtility.getString(request.getParameter("ConfirmPassword")));
		dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		dto.setGender(DataUtility.getString(request.getParameter("gender")));
		log.debug("UserRegistrationCtl populateDTO  method Start");
		return dto;
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("UserRegistrationCtl validate  method Start");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "FirstName"));
			pass = false;
		} else if (!DataValidator.isFname(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.fname", "FirstName"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "LastName"));
			pass = false;
		} else if (!DataValidator.isLname(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.lname", "LastName"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("loginId"))) {
			request.setAttribute("loginId", PropertyReader.getValue("error.require", "LoginId"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("loginId"))) {
			request.setAttribute("loginId", PropertyReader.getValue("error.error.email", "LoginId"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.pass", "Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "ConfirmPassword"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.pass", "ConfirmPassword"));
			pass = false;
		} else if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Confirm  Password  should not be matched.");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile"));
			pass = false;
		} else if (!DataValidator.isMobile(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.mono", "Mobile"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		}
		log.debug("UserRegistrationCtl validate  method End");
		return pass;
	}

	/**
	 * Display Logic inside This Method
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserRegistrationCtl Do-Get  method Start");

		ServletUtility.forward(getView(), request, response);

		log.debug("UserRegistrationCtl Do-Get  method end");
	}

	/**
	 * Submit Logic inside This Method
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserRegistrationCtl Do-Post  method Start");
		String op = DataUtility.getString(request.getParameter("operation"));
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();

		if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			UserDTO dto = (UserDTO) populateDTO(request);

			try {
				userModel.registerUser(dto);
				ServletUtility.setDto(dto, request);
				ServletUtility.setSuccessMessage("User register successfully", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error("Database Exception " + e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				log.error("DuplicateRecordException " + e);
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("LoginId Already Exists", request);
				ServletUtility.forward(getView(), request, response);
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.forward(getView(), request, response);
		}
		log.debug("UserRegistrationCtl Do-Post  method end");
	}

	@Override
	protected String getView() {
		return ORSView.USER_REGISTRATION_VIEW;
	}

}
