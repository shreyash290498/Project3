package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.MarksheetDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.MarksheetModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Get Marksheet functionality Controller. Performs operation for Get Marksheet
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "GetMarksheetCtl", urlPatterns = { "/ctl/GetMarksheetCtl" })
public class GetMarksheetCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(GetMarksheetCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("GetMarksheetCTL Method validate Started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("RollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		}
		/*
		 * else if(!DataValidator.isRollNo(request.getParameter("rollNo"))){
		 * request.setAttribute("rollNo",
		 * PropertyReader.getValue("error.roll","Roll Number")); pass=false; }
		 */

		log.debug("GetMarksheetCTL Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("GetMarksheetCtl Method populatebean Started");

		MarksheetDTO dto = new MarksheetDTO();
		dto.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		/*
		 * bean.setId(DataUtility.getLong(request.getParameter("id")));
		 * bean.setName(DataUtility.getString(request.getParameter("name")));
		 * bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		 * bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry"
		 * )));
		 * bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
		 */
		log.debug("GetMarksheetCtl Method populatebean Ended");

		return dto;
	}

	/**
	 * Display Logics inside this Method
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("GetMarksheetCtl Method doGet Started");
		/*HttpSession session = request.getSession(true);
		UserDTO userDto = (UserDTO) session.getAttribute("user");
		if (userDto.getRoleId() == RoleDTO.KIOSK) {
			ServletUtility.redirect(ORSView.ERROR_CTL, request, response);
			return;
		}*/
		ServletUtility.forward(getView(), request, response);

		log.debug("GetMarksheetCtl Method doGet Ended");
	}

	/**
	 * Submit Logics inside This Method
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("GetMarksheetCtl Method doGet Started");
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		if (OP_GO.equalsIgnoreCase(op)) {
			MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
			try {
				dto = model.findByRollNo(dto.getRollNo());
				if (dto != null) {
					ServletUtility.setBean(dto, request);
				} else {
					ServletUtility.setErrorMessage("Rollno does not exists", request);
				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		}else if(OP_RESET.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.GET_MARKSHEET_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("MarksheetCtl Method doGet Ended");
	}

	@Override
	protected String getView() {
		return ORSView.GET_MARKSHEET_VIEW;
	}

}
