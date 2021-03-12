package in.co.sunrays.proj3.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;

/**
 * 
 * Hibernate Implementation of CourseModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0 @Copyright(c) SUNRAYS Technologies
 */
public class CourseModelHibImpl implements CourseModelInt {

	Logger log = Logger.getLogger(CourseModelHibImpl.class);

	/**
	 * Add a Course
	 *
	 * @param dto
	 * @throws DatabaseException
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */

	public long add(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model add started");
		long pk = 0;
		CourseDTO courseDto = findByName(dto.getName());
		if (courseDto !=null) {
			throw new DuplicateRecordException("CourseName  Already Exist");
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
			throw new ApplicationException("Exception a Add Course" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model add end");

		return pk;
	}

	/**
	 * Update Course
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */

	public void update(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model update started");
		CourseDTO dtoExist = findByName(dto.getName());
		if (!dtoExist.getName().equalsIgnoreCase(dto.getName())) {
			throw new DuplicateRecordException("Course Already Exist");
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
			throw new ApplicationException("Exception in update Course" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model update end");

	}

	/**
	 * Delete Course
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */

	public void delete(CourseDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model delete started");
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
			throw new ApplicationException("Exception a delete Course" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model delete end");
	}

	/**
	 * Find Course by Name
	 * 
	 * @param Name
	 *            : getparameter
	 * @return dto
	 * @throws ApplicationException
	 */

	public CourseDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByName start");
		Session session = null;
		CourseDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CourseDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (CourseDTO) list.get(0);
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
	 * Find Course by Id
	 * 
	 * @param pk
	 * @throws ApplicationException
	 */

	public CourseDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByPK Started");
		Session session = null;
		CourseDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (CourseDTO) session.get(CourseDTO.class, pk);
		} catch (HibernateException e) {
			log.error("DataBase Exception" + e);
			throw new ApplicationException("Exception a FindByPK Course" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model FindByPK End");
		return dto;

	}

	/**
	 * SearchesCourse with pagination
	 *
	 * @return list : List ofCourse
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 */

	public List search(CourseDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model search started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CourseDTO.class);
			if (dto != null) {
				if (dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				if (dto.getDuration() != null) {
					criteria.add(Restrictions.like("duration", dto.getDuration() + "%"));
				}
				if (dto.getDescription() != null) {
					criteria.add(Restrictions.like("description", dto.getDescription() + "%"));
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
			throw new ApplicationException("Exception in search Course" + e.getMessage());
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

	public List search(CourseDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	/**
	 * Gets List of Course
	 *
	 * @return list : List of Courses
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	/**
	 * get List of Course with pagination
	 *
	 * @return list : List of Courses
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
			Criteria criteria = session.createCriteria(CourseDTO.class);
			// if PageSize Greater than Zero then applymPagination
			/*
			 * if (pageSize > 0) { pageNo = ((pageNo - 1) * pageSize) + 1;
			 * criteria.setFirstResult(pageNo);
			 * criteria.setMaxResults(pageSize); }
			 */
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Databases Exception", e);
			throw new ApplicationException("Exception in list Course" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model list started");

		return list;
	}

}
