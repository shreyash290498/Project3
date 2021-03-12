package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.dto.FacultyDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CollegeModelInt;
import in.co.sunrays.proj3.model.CourseModelInt;
import in.co.sunrays.proj3.model.FacultyModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.SubjectModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Faculty functionality Controller. Performs operation for add,update and get
 * Faculty
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "FacultyCtl", urlPatterns = "/ctl/FacultyCtl")
public class FacultyCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(FacultyCtl.class);

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		// TODO Auto-generated method stub
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		try {
			List collegeList = collegeModel.list();
			List subjectList = subjectModel.list();
			List courseList = courseModel.list();
			request.setAttribute("CollegeList", collegeList);
			request.setAttribute("SubjectList", subjectList);
			request.setAttribute("CourseList", courseList);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Populates the FacultyDTO object from request parameters
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 * @return dto: FacultyDTO object
	 */
	protected boolean validate(HttpServletRequest request) {
		log.debug("FacultyCtl Method Validate Started");
		boolean pass = true;
		String dob = request.getParameter("dob");
		String email = request.getParameter("loginId");
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
		}
		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "SubjectName"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("qualification"))) {
			request.setAttribute("qualification", PropertyReader.getValue("error.require", "Qualification"));
			pass = false;
		}
		if (DataValidator.isNull(email)) {
			request.setAttribute("loginId", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(email)) {
			request.setAttribute("loginId", PropertyReader.getValue("error.email", "Email Id"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		} else if (!DataValidator.isMobile(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.mono", "Mobile No"));
			pass = false;
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}
		log.debug("FacultyCtl Method Validate End");
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
		FacultyDTO dto = new FacultyDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
		dto.setGender(DataUtility.getString(request.getParameter("gender")));
		dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		dto.setQualification(DataUtility.getString(request.getParameter("qualification")));
		dto.setEmailId(DataUtility.getString(request.getParameter("loginId")));
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		populateDTO(dto, request);

		return dto;
	}

	/**
	 * Display Logics inside this Method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * HttpSession session = request.getSession(true); UserDTO uesrDto =
		 * (UserDTO) session.getAttribute("user");
		 * 
		 * if (uesrDto.getRoleId() != RoleDTO.ADMIN) {
		 * ServletUtility.redirect(ORSView.ERROR_CTL, request, response);
		 * return; }
		 */
		long id = DataUtility.getInt(request.getParameter("id"));
		FacultyModelInt facultymodel = ModelFactory.getInstance().getFacultyModel();
		if (id > 0) {
			try {
				FacultyDTO dto = (FacultyDTO) facultymodel.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Summit Logics inside this Method
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String op = DataUtility.getString(request.getParameter("operation"));
		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FacultyDTO dto = (FacultyDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Data successfully updated", request);
				} else {
					long pk = model.add(dto);
					dto.setId(pk);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Data successfully saved", request);
				}
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login is Already Exist", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			FacultyDTO dto = (FacultyDTO) populateDTO(request);
			try {
				model.delete(dto);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FACULTY_VIEW;
	}

}
