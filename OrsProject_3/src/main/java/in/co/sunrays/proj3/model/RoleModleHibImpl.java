package in.co.sunrays.proj3.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;

public class RoleModleHibImpl implements RoleModelInt {

	Logger log = Logger.getLogger(UserModelHibImpl.class);

	/**
	 * Add a Role
	 *
	 * @param dto
	 * @throws DatabaseException
	 *
	 */
	public long add(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Add method Started");
		long pk = 0;
		RoleDTO dtoExist = findByName(dto.getName());

		if (dtoExist != null) {
			throw new DuplicateRecordException("RoleName Already Exist");
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
			throw new ApplicationException(" Exceptionin add Role " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Add method ended");
		return pk;

	}

	public void update(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model update start");
		RoleDTO dtoExist = findByName(dto.getName());
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Role Already Exist");
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
			throw new ApplicationException("Exception in update Role" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model update end");

	}

	/**
	 * Delete a Role
	 *
	 * @param dto
	 * @throws DatabaseException
	 */
	public void delete(RoleDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception is delete role" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model delete end");
	}

	/**
	 * Find User by Name
	 *
	 * @param login
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public RoleDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByName start");
		Session session = null;
		RoleDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (RoleDTO) list.get(0);
			}
		} catch (HibernateException e) {
			log.error("DataBase Exception", e);
			throw new ApplicationException("Exception in findByName " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model FindByName end");
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
	public RoleDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model findByPK start");
		Session session = null;
		RoleDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (RoleDTO) session.get(RoleDTO.class, pk);
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
	 * Searches Roles with pagination
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
	public List search(RoleDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model search started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			if(dto !=null){
				if (dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
			}
			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				criteria.setFirstResult(((pageNo - 1) * pageSize));
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("DataBase Exception", e);
			throw new ApplicationException("Exception in search role" + e.getMessage());
		} finally {
			session.close();
		}

		log.debug("Model search end");
		return list;

	}

	/**
	 * Searches Role
	 *
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(RoleDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model search started");
		log.debug("Model search end");
		return search(dto, 0, 0);
	}

	/**
	 * Gets List of Role
	 *
	 * @return list : List of Users
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	/**
	 * get List of Role with pagination
	 *
	 * @return list : List of Roles
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
			Criteria criteria = session.createCriteria(RoleDTO.class);
			// if PageSize Greater than Zero then applymPagination
			/*if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) ;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}*/
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Databases Exception", e);
			throw new ApplicationException("Exception in list role" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model list started");
		return list;
	}

}
