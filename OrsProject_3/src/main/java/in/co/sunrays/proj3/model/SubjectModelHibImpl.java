package in.co.sunrays.proj3.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;

/**
 * 
 * Hibernate Implementation of SubjectModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0 @Copyright(c) SUNRAYS Technologies
 */

public class SubjectModelHibImpl implements SubjectModelInt {
	Logger log = Logger.getLogger(SubjectModelHibImpl.class);

	/**
	 * Add a Subject
	 *
	 * @param dto
	 * @throws DatabaseException
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */

	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model add started");
		long pk = 0;
		SubjectDTO SubjectDto = findByName(dto.getName());
		if (SubjectDto !=null) {
			throw new DuplicateRecordException("Subject  Already Exist");
		}
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO courseDto = courseModel.findByPK(dto.getCourseId());
		dto.setCourseName(courseDto.getName());
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
			throw new ApplicationException("Exception a Add Subject" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model add end");

		return pk;
	}

	/**
	 * Update Subject
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */

	public void update(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model update started");
		SubjectDTO dtoExist = findByName(dto.getName());
		if (!dtoExist.getName().equalsIgnoreCase(dto.getName())) {
			throw new DuplicateRecordException("Subject Already Exist");
		}
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO courseDto = courseModel.findByPK(dto.getCourseId());
		dto.setCourseName(courseDto.getName());
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
			throw new ApplicationException("Exception in update Subject" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model update end");

	}

	/**
	 * Delete Subject
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */

	public void delete(SubjectDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception a delete Subject" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model delete end");
	}

	/**
	 * Find Subject by Name
	 * 
	 * @param Name
	 *            : getparameter
	 * @return dto
	 * @throws ApplicationException
	 */

	public SubjectDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByName Started");
		Session session = null;
		SubjectDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SubjectDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (SubjectDTO) list.get(0);
			}
		} catch (HibernateException e) {
			log.error("DataBase Exception" + e);
			throw new ApplicationException("Exception a FindByName Subject" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model FindByName End");
		return dto;
	}

	/**
	 * Find Subject by Id
	 * 
	 * @param pk
	 * @throws ApplicationException
	 */

	public SubjectDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByPK Started");
		Session session = null;
		SubjectDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (SubjectDTO) session.get(SubjectDTO.class, pk);
		} catch (HibernateException e) {
			log.error("DataBase Exception" + e);
			throw new ApplicationException("Exception a FindByPK Subject" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model FindByPK End");
		return dto;

	}

	/**
	 * SearchesSubject with pagination
	 *
	 * @return list : List ofSubject
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 */

	public List search(SubjectDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model search started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SubjectDTO.class);
			if (dto != null) {
				if (dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getCourseId() > 0) {
					criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
				}
				if (dto.getName() != null) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
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
			throw new ApplicationException("Exception in search Subject" + e.getMessage());
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

	public List search(SubjectDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	/**
	 * Gets List of Subject
	 *
	 * @return list : List of Subjects
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	/**
	 * get List of Subject with pagination
	 *
	 * @return list : List of Subjects
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
			Criteria criteria = session.createCriteria(SubjectDTO.class);
			// if PageSize Greater than Zero then applymPagination
			/*
			 * if (pageSize > 0) { pageNo = ((pageNo - 1) * pageSize) ;
			 * criteria.setFirstResult(pageNo);
			 * criteria.setMaxResults(pageSize); }
			 */
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Databases Exception", e);
			throw new ApplicationException("Exception in list Subject" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model list started");

		return list;
	}

}
