package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.RoleModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Role List functionality Controller. Performs operation for list, search
 * operations of Role
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "RoleListCtl", urlPatterns = { "/ctl/RoleListCtl" })
public class RoleListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(RoleListCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("RoleLIstCtl method preload started");
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		List list = null;
		try {
			list = model.list();
			request.setAttribute("roleList", list);
		} catch (ApplicationException e) {
			log.error(e);
		}
		log.debug("RoleLIstCtl method preload end");
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("RoleLIstCtl method populateDTO started");
		RoleDTO dto = new RoleDTO();
		dto.setId(DataUtility.getLong(request.getParameter("roleId")));
		dto.setName(DataUtility.getString(request.getParameter("Name")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		log.debug("RoleLIstCtl method populateDTO End");
		return dto;
	}

	/**
	 * Display logic inside this method
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("RoleLIstCtl metho Do-get started");
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		RoleDTO dto = (RoleDTO) populateDTO(request);
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		List list = null;
		try {
			list = model.search(dto, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("Record not Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		log.debug("RoleLIstCtl method Do-get end");
	}

	/**
	 * Submit logic inside this method
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("RoleLIstCtl metho Do-post started");
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		RoleDTO dto = (RoleDTO) populateDTO(request);
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		List list = null;
		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				System.out.println("search op====  "+op);
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					System.out.println("search op==== set PageNo...  ");
					pageNo  =1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op) || OP_CANCEL.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				if (ids != null) {
					RoleDTO dto1 = new RoleDTO();
					for (String id : ids) {
						dto1.setId(DataUtility.getLong(id));
						model.delete(dto1);
					}
					ServletUtility.setSuccessMessage("Record hasbeen successfully deleted", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
				list = model.search(dto, pageNo, pageSize);
				if ((list == null || list.size() == 0) && !OP_DELETE.equalsIgnoreCase(op)) {
					ServletUtility.setErrorMessage("Record not found ", request);
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
		log.debug("RoleLIstCtl metho Do-post started");
	}

	@Override
	protected String getView() {
		return ORSView.ROLE_LIST_VIEW;
	}

}
