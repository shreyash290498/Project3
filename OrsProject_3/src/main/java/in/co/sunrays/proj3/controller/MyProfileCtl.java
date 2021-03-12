package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
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
 * My Profile functionality Controller. Performs operation for update your
 * Profile
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name="MyProfileCtl",urlPatterns={"/ctl/MyProfileCtl"})
public class MyProfileCtl extends BaseCtl {
	public static final String OP_CHANGE_MY_PASSWORD = "ChangePassword";
	private static Logger log = Logger.getLogger(MyProfileCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("MyProfileCtl Method Validate Started");
		boolean pass = true;
		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {

			return pass;
		}
		if (DataValidator.isNull(request.getParameter("loginId"))) {
			request.setAttribute("loginId", PropertyReader.getValue("error.require", "LoginId"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isFname(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.fname", "First Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isLname(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.lname", "Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		} else if (!DataValidator.isMobile(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.mono", "MobileNo"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		}
		log.debug("MyProfileCtl Method Validate End");
		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("MyProfileCtl Method  populateDTO Started");
		UserDTO dto = new UserDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setLogin(DataUtility.getString(request.getParameter("loginId")));

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateDTO(dto, request);

		log.debug("MyProfileCtl Method  populateDTO End");
		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("MyProfileCtl Method  Do-Get Started");
		HttpSession session = request.getSession(true);
		UserDTO userDto = (UserDTO) session.getAttribute("user");
		long id = userDto.getId();
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		if (id > 0) {
			UserDTO dto = null;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(userDto, request);
			} catch (ApplicationException e) {
				log.error("DatabaseException   " + e.getMessage());
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("MyProfileCtl Method  Do-Get End");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("MyProfileCtl Method  Do-Post Started");
		HttpSession session = request.getSession(true);
		UserDTO userDto = (UserDTO) session.getAttribute("user");
		long id = userDto.getId();
		String op = DataUtility.getString(request.getParameter("operation"));
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		if (OP_UPDATE.equalsIgnoreCase(op)) {
			UserDTO dto = (UserDTO) populateDTO(request);
			try {
				if (id > 0) {
					userDto.setFirstName(dto.getFirstName());
					userDto.setLastName(dto.getLastName());
					userDto.setGender(dto.getGender());
					userDto.setMobileNo(dto.getMobileNo());
					userDto.setDob(dto.getDob());
					model.update(userDto);
				}
				session.setAttribute("user", userDto);
				ServletUtility.setDto(dto, request);
				ServletUtility.setSuccessMessage("Data is successfully update", request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("LoginId Alrady Exists", request);
			}

		} else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("MyProfileCtl Method  Do-Post End");
	}

	@Override
	protected String getView() {
		return ORSView.MY_PROFILE_VIEW;
	}

}
