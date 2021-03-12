package in.co.sunrays.proj3.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.exception.RecordNotFoundException;
import in.co.sunrays.proj3.util.EmailBuilder;
import in.co.sunrays.proj3.util.EmailMessage;
import in.co.sunrays.proj3.util.EmailUtility;
import in.co.sunrays.proj3.util.HibDataSource;

/**
 * Hibernate Implementation of UserModel
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class UserModelHibImpl implements UserModelInt {
	Logger log = Logger.getLogger(UserModelHibImpl.class);

	/**
	 * Add a User
	 *
	 * @param dto
	 * @throws DatabaseException
	 *
	 */
	public long add(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Add method Started");
		long pk = 0;
		UserDTO dtoExist = findByLogin(dto.getLogin());
		RoleModelInt roleModel = ModelFactory.getInstance().getRoleModel();
		RoleDTO roleDto = roleModel.findByPK(dto.getRoleId());
		dto.setRoleName(roleDto.getName());
		if (dtoExist != null) {
			throw new DuplicateRecordException("LoginId Already Exist");
		}
		Session session = HibDataSource.getSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			transaction.commit();
		} catch (HibernateException e) {
			log.error("DataBase Eception", e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(" Exceptionin add user " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Add method ended");
		return pk;
	}

	/**
	 * Delete a User
	 *
	 * @param dto
	 * @throws DatabaseException
	 */
	public void delete(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model delete start");

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibDataSource.getSession();
			transaction = session.beginTransaction();
			session.delete(dto);
			transaction.commit();
		} catch (HibernateException e) {
			log.error("DataBase Exception", e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception is delete user" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model delete end");

	}

	/**
	 * Find User by Login
	 *
	 * @param login
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public UserDTO findByLogin(String login) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByLogin start");
		Session session = null;
		UserDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (UserDTO) list.get(0);
			}
		} catch (HibernateException e) {
			log.error("DataBase Exception", e);
			throw new ApplicationException("Exception in findByLogin " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model FindByLogin end");
		return dto;
	}

	/**
	 * Find User by PK
	 *
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public UserDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model findByPK start");
		Session session = null;
		UserDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (UserDTO) session.get(UserDTO.class, pk);
		} catch (HibernateException e) {
			log.error("DataBaseException", e);
			throw new ApplicationException("Exception in FindByPK" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model findByPK start");
		return dto;
	}

	/**
	 * Update a User
	 *
	 * @param dto
	 * @throws DatabaseException
	 */
	public void update(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model update start");
		UserDTO dtoExist = findByLogin(dto.getLogin());
		RoleModelInt rmodel = ModelFactory.getInstance().getRoleModel();
		RoleDTO rdto = rmodel.findByPK(dto.getRoleId());
		dto.setRoleName(rdto.getName());
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("LoginId Already Exist");
		}
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibDataSource.getSession();
			transaction = session.beginTransaction();
			session.update(dto);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in update user" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model update end");
	}

	/**
	 * Searches User
	 *
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model search started");
		log.debug("Model search end");
		return search(dto, 0, 0);
	}

	/**
	 * Searches Users with pagination
	 *
	 * @return list : List of Users
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 *
	 * @throws DatabaseException
	 */

	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model search started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (dto != null) {
				if (dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getFirstName() != null) {
					criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
				}
				if (dto.getLastName() != null) {
					criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
				}
				if (dto.getLogin() != null) {
					criteria.add(Restrictions.like("login", dto.getLogin() + "%"));
				}
				if (dto.getGender() != null) {
					criteria.add(Restrictions.like("gender", dto.getGender() + "%"));
				}
				if (dto.getDob() != null) {
					criteria.add(Restrictions.like("dob", dto.getDob() + "%"));
				}
				if (dto.getRoleId() > 0) {
					criteria.add(Restrictions.eq("roleId", dto.getRoleId()));
				}
			}
			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				criteria.setFirstResult((pageNo));
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("DataBase Exception", e);
			throw new ApplicationException("Exception in search user" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model search end");
		return list;
	}

	/**
	 * Gets List of user
	 *
	 * @return list : List of Users
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	/**
	 * get List of User with pagination
	 *
	 * @return list : List of Users
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model list started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			// if PageSize Greater than Zero then applymPagination
			/*
			 * if (pageSize > 0) { pageNo = ((pageNo - 1) * pageSize) + 1;
			 * criteria.setFirstResult(pageNo);
			 * criteria.setMaxResults(pageSize); }
			 */
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Databases Exception", e);
			throw new ApplicationException("Exception in list user" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model list started");
		return list;
	}

	/**
	 * Change Password By pk
	 *
	 * @param pk
	 *            ,oldPassword,newPassword : get parameter
	 * @return dto
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 */
	public boolean changePassword(Long id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model changePassWord started");
		boolean flag = false;
		Session session = null;
		UserDTO dto = null;
		dto = (UserDTO) findByPK(id);
		if (dto != null) {
			dto.setPassword(newPassword);
			try {
				update(dto);
				flag = true;
			} catch (ApplicationException e) {
				log.error("DataBase Exception", e);
			}
		} else {
			throw new RecordNotFoundException("Loginid not Exist");
		}
		/*HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());
		map.put("firstName", dto.getFirstName());
		map.put("lastName", dto.getLastName());
		String message = EmailBuilder.getChangePasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(dto.getLogin());
		msg.setSubject("SUNARYS ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);*/
		log.debug("Model  changePassWord end");
		return flag;
	}

	/**
	 * @param login
	 *            : String login
	 * @param password
	 *            : password
	 * @throws DatabaseException
	 */

	public UserDTO authenticate(String login, String password) throws ApplicationException {
		log.debug("Model authenticate Started");
		Session session = null;
		UserDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Query q = session.createQuery("from UserDTO where login=? and password=? ");
			q.setString(0, login);
			q.setString(1, password);

			List list = q.list();
			if (list.size() > 0) {
				dto = (UserDTO) list.get(0);
			} else {
				dto = null;
			}
		} catch (HibernateException e) {
			throw new ApplicationException(" Authenticate mathod exception" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model authenticate End");
		return dto;
	}

	public boolean lock(String login) throws RecordNotFoundException, ApplicationException {
		// TODO Auto-generated method stub
		return false;
	}

	public List getRoles(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public UserDTO updateAccess(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Register a user
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when user already exists
	 */
	public long registerUser(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub

		log.debug("Model registerUser Started");

		long pk = add(dto);

	/*	HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Registration is successful for ORS Project SUNRAYS Technologies");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);*/
		log.debug("Model  registerUser end");
		return pk;
	}

	public boolean resetPassword(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Send the password of User to his Email
	 *
	 * @return boolean : true if success otherwise false
	 * @param login
	 *            : User Login
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 *             : if user not found
	 */

	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		// TODO Auto-generated method stub
		log.debug("Model  forgetPassword started");
		UserDTO userData = findByLogin(login);
		if (userData == null) {
			throw new RecordNotFoundException("LoginId Does not Exist");
		}
		boolean flag = false;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", userData.getLogin());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstName());
		map.put("lastName", userData.getLastName());
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNARYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		/*EmailUtility.sendMail(msg);*/
		flag = true;
		log.debug("Model  forgetPassword end");
		
		return flag;
	}

	public static void main(String[] args) {
		UserModelHibImpl sm = new UserModelHibImpl();
		int pageNo = 1;
		int pageSize = 10;
		UserDTO dto = new UserDTO();
		dto.setFirstName("Rajkumar");
		try {
			// List list=sm.list();
			// List list=sm.search(dto, pageNo, pageSize);
			List list = sm.list(pageNo, pageSize);
			Iterator<UserDTO> it = list.iterator();
			while (it.hasNext()) {
				UserDTO dto1 = new UserDTO();
				dto1 = it.next();
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
