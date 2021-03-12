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
import in.co.sunrays.proj3.exception.RecordNotFoundException;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.UserModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Change Password functionality Controller. Performs operation for Change
 * Password
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "ChangePasswordCtl", urlPatterns = { "/ctl/ChangePasswordCtl" })
public class ChangePasswordCtl extends BaseCtl {
	public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

	private static Logger log = Logger.getLogger(ChangePasswordCtl.class);

	/**
	 * Validates Input data entered by user
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 * @return pass: a boolean variable
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("ChangepasswordCtl method Validate started");
		boolean pass = true;
		String op = DataUtility.getString(request.getParameter("operation"));
		if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			return pass;
		}
		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.pass", "New Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.pass", "Confirm Password"));
			pass = false;
		} else if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "New and Confirm Password Not Matched");
			pass = false;
		}
		log.debug("ChangepasswordCtl method Validate End");
		return pass;
	}

	/**
	 * Populates the ChangePasswordDTO object from request parameters
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 * @return dto: ChangePasswordDTO object
	 */
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("ChangePasswordCtl Method PopulateDTO Started");
		UserDTO dto = new UserDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setPassword(request.getParameter("oldPassword"));
		dto.setConfirmPassword(request.getParameter("confirmPassword"));
		populateDTO(dto, request);
		log.debug("ChangePasswordCtl Method PopulateDTO End");
		return dto;
	}

	/**
	 * Display Logics Inside this method
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("ChangePasswordCtl Method Do-Get Started");

		ServletUtility.forward(getView(), request, response);

		log.debug("ChangePasswordCtl Method Do-Get End");
	}

	/**
	 * Submit Logics Inside this method
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("ChangePasswordCtl Method Do-post Started");
		HttpSession session = request.getSession(true);
		UserDTO userDto = (UserDTO) session.getAttribute("user");
		long id = userDto.getId();

		String op = DataUtility.getString(request.getParameter("operation"));
		String newPassword = DataUtility.getString(request.getParameter("newPassword"));
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		if (OP_UPDATE.equalsIgnoreCase(op)) {
			UserDTO dto = (UserDTO) populateDTO(request);
			try {
				boolean flag = model.changePassword(id, dto.getPassword(), newPassword);
				if (flag == true) {
					System.out.println("Change pass dopost flage=============");
					dto = model.findByLogin(userDto.getLogin());
					session.setAttribute("user", dto);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Password has been changed successfully.", request);
				}
			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage("Old Password invalid", request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
			}
		} else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("ChangePasswordCtl Method Do-post End");
	}

	@Override
	protected String getView() {
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}
