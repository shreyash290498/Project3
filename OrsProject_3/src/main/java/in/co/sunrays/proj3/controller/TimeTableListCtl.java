package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.TimeTableDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.RecordNotFoundException;
import in.co.sunrays.proj3.model.CourseModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.SubjectModelInt;
import in.co.sunrays.proj3.model.TimeTableModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Timetable List functionality Controller. Performs operation for list, search
 * and delete operations of Timetables
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "TimeTableListCtl", urlPatterns = { "/ctl/TimeTableListCtl" })
public class TimeTableListCtl extends BaseCtl {

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 */
	protected void preload(HttpServletRequest request) {
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
		try {
			List cList = courseModel.list();
			List sList = subjectModel.list();
			request.setAttribute("courseList", cList);
			request.setAttribute("subjectList", sList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Populates TimeTableBean object from request parameters
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 * @return bean: TimeTableBean object
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {
		TimeTableDTO dto = new TimeTableDTO();
		dto.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		dto.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
		dto.setSemester(DataUtility.getString(request.getParameter("semester")));
		dto.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		return dto;
	}

	/**
	 * Display Logics inside this Method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * HttpSession session=request.getSession(true); UserDTO
		 * userDto=(UserDTO)session.getAttribute("user");
		 * if(userDto.getRoleId()!=RoleDTO.ADMIN){
		 * ServletUtility.redirect(ORSView.ERROR_CTL, request, response);
		 * return; }
		 */

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		TimeTableDTO dto = (TimeTableDTO) populateDTO(request);
		TimeTableModelInt model = ModelFactory.getInstance().getTimeTableModel();
		List list = null;

		try {
			list = model.search(dto, pageNo, pageSize);
			if (list.size() == 0 || list == null) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Submit Logics inside this Method
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		int count = 0;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		TimeTableDTO dto = (TimeTableDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");
		TimeTableModelInt model = ModelFactory.getInstance().getTimeTableModel();
		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TIME_TABLE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					TimeTableDTO deleteDto = new TimeTableDTO();
					for (String id : ids) {
						deleteDto.setId(DataUtility.getLong(id));
						model.delete(deleteDto);
					}
					ServletUtility.setSuccessMessage("Record deleted successfully", request);
				} else {
					ServletUtility.setErrorMessage("Please select at least one record ", request);
				}
			}
			list = model.search(dto, pageNo, pageSize);
			if ((list.size() == 0 || list == null) && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			ServletUtility.setDto(dto, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	/**
	 * Returns the view page of TimeTableListCtl
	 * 
	 * @return TimeTableListView.jsp: View page of TimeTableListCtl
	 */
	protected String getView() {
		return ORSView.TIME_TABLE_LIST_VIEW;
	}

}
