package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.CourseModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Course List functionality Controller. Performs operation for list, search and
 * delete operations of Course
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(CourseListCtl.class);

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("preload =");
		log.debug("CourseListCtl method preload start");
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		try {
			List list = courseModel.list();
			request.setAttribute("courseList", list);
		} catch (ApplicationException e) {
			log.error(e);
		}
		log.debug("CourseListCtl method preload end");
	}

	/**
	 * Populates CourseDTO object from request parameters
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 * @return bean: CourseDTO object
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("CourseListCtl method populateDTO start");
		CourseDTO dto = new CourseDTO();
		dto.setId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setName(DataUtility.getString(request.getParameter("courseName")));
		dto.setDuration(DataUtility.getString(request.getParameter("duration")));
		// String s = request.getParameter("duration");
		// System.out.println( "check on"+s.length());
		log.debug("CourseListCtl method populateDTO end");
		return dto;
	}

	/**
	 * Display Logics inside This Method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CourseListCtl Method do-Get Start");
		/*
		 * HttpSession session = request.getSession(true); UserDTO dto =
		 * (UserDTO) session.getAttribute("user"); if (dto.getRoleId() !=
		 * RoleDTO.ADMIN) { ServletUtility.redirect(ORSView.ERROR_CTL, request,
		 * response); return; }
		 */
		List list = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		CourseDTO dto = (CourseDTO) populateDTO(request);
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		try {
			list = model.search(dto, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("CourseListCtl metho do-Get End");
	}

	/**
	 * Submit logics inside this Method
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CourseListCtl doPost Start");
		List list = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		CourseDTO bean = (CourseDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
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
				ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null) {
					CourseDTO dto1 = new CourseDTO();
					for (String id : ids) {
						dto1.setId(DataUtility.getLong(id));
						model.delete(dto1);
					}
					ServletUtility.setSuccessMessage("Record successfully deleted", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			list = model.search(bean, pageNo, pageSize);
			// request.setAttribute("courseList", list);
			// ServletUtility.setList(list, request);

			if ((list == null || list.size() == 0) && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No records Found", request);
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("CourseListCtl doPost End");
	}

	/**
	 * Returns the VIEW page of CourseListCtl
	 * 
	 * @return CourseListView.jsp: View page of CourseListCtl
	 */
	@Override
	protected String getView() {
		return ORSView.COURSE_LIST_VIEW;
	}

}
