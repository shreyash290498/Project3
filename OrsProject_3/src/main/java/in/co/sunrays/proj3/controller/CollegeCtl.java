package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CollegeModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * College functionality Controller. Performs operation for add, update, delete
 * and get College
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CollegeCtl.class);

	/**
	 * Validates Input data entered by user
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 * @return pass: a boolean variable
	 */
	protected boolean validate(HttpServletRequest request) {
		log.debug("CollegeCtl Method validate Started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "Phone No"));
			pass = false;
		} else if (!DataValidator.isMobile(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("error.mono", "Phone No"));
			pass = false;
		}

		log.debug("CollegeCtl Method validate Ended");

		return pass;
	}

	/**
	 * Populates the CollegeDTO object from request parameters
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 * @return dto: CollegeDTO object
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("CollegeCtl Method populatebean Started");

		CollegeDTO dto = new CollegeDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setAddress(DataUtility.getString(request.getParameter("address")));
		dto.setState(DataUtility.getString(request.getParameter("state")));
		dto.setCity(DataUtility.getString(request.getParameter("city")));
		dto.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));
		populateDTO(dto, request);

		log.debug("CollegeCtl Method populatebean Ended");

		return dto;
	}

	/**
	 * Display Logics inside this Method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CollegeCtl Method Do-get Started");
		/*
		 * HttpSession session = request.getSession(true); UserDTO userDto =
		 * (UserDTO) session.getAttribute("user"); if (userDto.getRoleId() !=
		 * RoleDTO.ADMIN) { ServletUtility.redirect(ORSView.ERROR_CTL, request,
		 * response); return; }
		 */
		// get model
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			CollegeDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("CollegeCtl Method Do-get End");
	}

	/**
	 * Submit Logics inside this method
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("CollegeCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {

			CollegeDTO dto = (CollegeDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully update", request);
				} else {
					long pk = model.add(dto);
					dto.setId(pk);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}

			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("College Name already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			CollegeDTO dto = (CollegeDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("CollegeCtl Method doGet Ended");
	}

	/**
	 * Returns the view page of CollegeCtl
	 * 
	 * @return CollegeView.jsp: View page CollegeCtl
	 */
	@Override
	protected String getView() {
		return ORSView.COLLEGE_VIEW;
	}

}
