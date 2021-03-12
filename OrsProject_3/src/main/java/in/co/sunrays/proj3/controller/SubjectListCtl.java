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
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.CourseModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.SubjectModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * College List functionality Controller. Performs operation for list, search
 * and delete operations of College
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "SubjectListCtl", urlPatterns = { "/ctl/SubjectListCtl" })
public class SubjectListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(SubjectListCtl.class);

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 */
	protected void preload(HttpServletRequest request) {

		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		try {
			List subjectList = subjectModel.list();
			List courseList = courseModel.list();
			request.setAttribute("subjectList", subjectList);
			request.setAttribute("courseList", courseList);

		} catch (ApplicationException e) {
			log.error(e);
		}
	}

	/**
	 * Populates the SubjectBean object from request parameters
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 * @return bean: SubjectBean object
	 */
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		SubjectDTO dto = new SubjectDTO();

		dto.setId(DataUtility.getLong(request.getParameter("subjectId")));
		// bean.setDescription(DataUtility.getString(request.getParameter("description")));
		dto.setName(DataUtility.getString(request.getParameter("subjectname")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		return dto;
	}

	/**
	 * Display Logics inside this Method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * HttpSession session = request.getSession(true); UserDTO userDto =
		 * (UserDTO) session.getAttribute("user"); if (userDto.getRoleId() !=
		 * RoleDTO.ADMIN) { ServletUtility.redirect(ORSView.ERROR_CTL, request,
		 * response); return; }
		 */
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		SubjectDTO dto = (SubjectDTO) populateDTO(request);
		SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();
		List list = null;
		try {
			list = model.search(dto, pageNo, pageSize);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
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
		log.debug("SubjectListCtl doPost Start");

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		String op = DataUtility.getString(request.getParameter("operation"));
		SubjectDTO dto = (SubjectDTO) populateDTO(request);
		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");
		SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();
		List list = null;
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
				ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					SubjectDTO deleteDto = new SubjectDTO();
					for (String id : ids) {
						deleteDto.setId(DataUtility.getLong(id));
						model.delete(deleteDto);
					}
					ServletUtility.setSuccessMessage("Record deleted Successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}

			list = model.search(dto, pageNo, pageSize);
			// ServletUtility.setList(list, request);
			if ((list == null || list.size() == 0) && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setDto(dto, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("SubjectListCtl doPost End");
	}

	/**
	 * Returns the view page of SubjectListCtl
	 * 
	 * @return SubjectListView.jsp: View page of SubjectListCtl
	 */
	@Override
	protected String getView() {
		return ORSView.SUBJECT_LIST_VIEW;
	}

}
