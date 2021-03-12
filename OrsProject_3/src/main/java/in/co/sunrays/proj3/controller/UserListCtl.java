package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.RoleModelInt;
import in.co.sunrays.proj3.model.UserModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * User List functionality Controller. Performs operation for list, search and
 * delete operations of User
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "UserListCtl", urlPatterns = "/ctl/UserListCtl")
public class UserListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(UserListCtl.class);

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("UserListCtl Method  populateDTO Started ");
		UserDTO dto = new UserDTO();
		// dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setRoleId(DataUtility.getLong(request.getParameter("roleId")));
		dto.setLogin(DataUtility.getString(request.getParameter("email")));
		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		log.debug("UserListCtl Method  populateDTO Started ");
		return dto;
	}

	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("UserListCtl Method  preload  Started ");
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		RoleModelInt roleModel = ModelFactory.getInstance().getRoleModel();
		List userList = null;
		List roleList = null;
		try {
			userList = userModel.list();
			roleList = roleModel.list();
			request.setAttribute("userList", userList);
			request.setAttribute("roleList", roleList);
		} catch (ApplicationException e) {
			log.error(e);
		}
		log.debug("UserListCtl Method  preload  End ");
	}

	/**
	 * Display Logic inside this method
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserListCtl Method  Do-get Started ");
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		UserDTO dto = (UserDTO) populateDTO(request);
		List list = null;
		try {

			list = userModel.search(dto, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
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
		log.debug("UserListCtl Method  Do-get Started ");
	}

	/**
	 * Submit logic inside this method
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserListCtl Method  Do-post Started ");

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		UserDTO dto = (UserDTO) populateDTO(request);
		List list = null;
		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;

				if (ids != null) {
					UserDTO dto1 = new UserDTO();
					for (String id : ids) {
						if (id.equalsIgnoreCase("1")) {
							ServletUtility.setErrorMessage("Admin can not be deleted", request);
						} else {
							dto1.setId(DataUtility.getLong(id));
							userModel.delete(dto1);
							ServletUtility.setSuccessMessage("Record hasbeen successfully deleted", request);
						}
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			list = userModel.search(dto, pageNo, pageSize);
			if ((list == null || list.size() == 0) && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setDto(dto, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("UserListCtl Method  Do-post End");
	}

	@Override
	protected String getView() {
		return ORSView.USER_LIST_VIEW;
	}
}
