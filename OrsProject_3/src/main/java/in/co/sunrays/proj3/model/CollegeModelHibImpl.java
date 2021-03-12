package in.co.sunrays.proj3.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;

/**
 * 
 * Hibernate Implementation of CollegeModelHibImpl
 * 
 * @author SUNRAYS Technologies
 * @version 1.0 @Copyright(c) SUNRAYS Technologies
 */
public class CollegeModelHibImpl implements CollegeModelInt {

	Logger log = Logger.getLogger(CollegeModelHibImpl.class);

	/**
	 * Add a College
	 *
	 * @param dto
	 * @throws DatabaseException
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */
	public long add(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model add college started");
		long pk = 0;
		CollegeDTO collegeDto = findByName(dto.getName());
		if (collegeDto!=null) {
			throw new DuplicateRecordException("Name  Already Exist");
		}
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibDataSource.getSession();
			transaction = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			transaction.commit();
		} catch (HibernateException e) {
			log.error("DataBase Exception", e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception a AddCollege" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model add end");

		return pk;
	}

	/**
	 * Update College
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model update college started");
		CollegeDTO dtoExist = findByName(dto.getName());
		if (!dtoExist.getName().equalsIgnoreCase(dto.getName())) {
			throw new DuplicateRecordException("College Already Exist");
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
			throw new ApplicationException("Exception in update College" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model update college end");

	}

	/**
	 * Delete College
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(CollegeDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model delete collegestarted");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibDataSource.getSession();
			transaction = session.beginTransaction();
			session.delete(dto);
			transaction.commit();
		} catch (HibernateException e) {
			log.error("DataBase Exception" + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception a delete College" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model delete college end");

	}

	/**
	 * Find College by Name
	 * 
	 * @param Name
	 *            : getparameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public CollegeDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByName Started");
		Session session = null;
		CollegeDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (CollegeDTO) list.get(0);
			}
		} catch (HibernateException e) {
			log.error("DataBase Exception" + e);
			throw new ApplicationException("Exception a FindByName college" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model FindByName End");
		return dto;
	}

	/**
	 * Find College by Id
	 * 
	 * @param pk
	 * @throws ApplicationException
	 */
	public CollegeDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByPK Started");
		Session session = null;
		CollegeDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (CollegeDTO) session.get(CollegeDTO.class, pk);
		} catch (HibernateException e) {
			log.error("DataBase Exception" + e);
			throw new ApplicationException("Exception a FindByPK college" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model FindByPK End");
		return dto;
	}

	/**
	 * SearchesCollege with pagination
	 *
	 * @return list : List ofCollege
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 */
	public List search(CollegeDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model search started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			if (dto != null) {
				if (dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				if (dto.getCity() != null) {
					criteria.add(Restrictions.like("city", dto.getCity() + "%"));
				}
			}
			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("DataBase Exception", e);
			throw new ApplicationException("Exception in search College" + e.getMessage());
		} finally {
			session.close();
		}

		log.debug("Model search end");

		return list;
	}

	/**
	 * Searches
	 *
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(CollegeDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	/**
	 * Gets List of College
	 *
	 * @return list : List of Colleges
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	/**
	 * get List of College with pagination
	 *
	 * @return list : List of Colleges
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
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			// if PageSize Greater than Zero then applymPagination
			/*
			 * if (pageSize > 0) { pageNo = ((pageNo - 1) * pageSize) ;
			 * criteria.setFirstResult(pageNo);
			 * criteria.setMaxResults(pageSize); }
			 */
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Databases Exception", e);
			throw new ApplicationException("Exception in list College" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model list started");

		return list;
	}

}
