package in.co.sunrays.proj3.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.MarksheetDTO;
import in.co.sunrays.proj3.dto.StudentDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;

/**
 * 
 * Hibernate Implementation of MarksheetModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0 @Copyright(c) SUNRAYS Technologies
 */
public class MarksheetModelHibImpl implements MarksheetModelInt {

	Logger log = Logger.getLogger(MarksheetModelHibImpl.class);

	/**
	 * Add a MarkSheet
	 *
	 * @param dto
	 * @throws DatabaseException
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */
	public long add(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model add started");
		long pk = 0;
		MarksheetDTO userDto = findByRollNo(dto.getRollNo());
		if (userDto != null) {
			throw new DuplicateRecordException("RollNo. Already Exist");
		}
		StudentModelInt smodel = ModelFactory.getInstance().getStudentModel();
		StudentDTO sDto = smodel.findByPK(dto.getStudentId());
		dto.setName(sDto.getFirstName() + " " + sDto.getLastName());
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
			throw new ApplicationException("Exception a Add Marksheet" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model add end");
		return pk;
	}

	/**
	 * Delete Marksheet
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(MarksheetDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception a delete Marksheet" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model delete end");
	}

	/**
	 * Find marksheet by Rollno
	 * 
	 * @param rollno
	 *            : getparameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public MarksheetDTO findByRollNo(String rollNo) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByRollno. Started");
		Session session = null;
		MarksheetDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			criteria.add(Restrictions.eq("rollNo", rollNo));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (MarksheetDTO) list.get(0);
			}
		} catch (HibernateException e) {
			log.error("DataBase Exception" + e);
			throw new ApplicationException("Exception a FindByRollno" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model FindByRollno. End");
		return dto;
	}

	/**
	 * Find marksheet by Id
	 * 
	 * @param pk
	 * @return dto
	 * @throws ApplicationException
	 */
	public MarksheetDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByPK Started");
		Session session = null;
		MarksheetDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (MarksheetDTO) session.get(MarksheetDTO.class, pk);
		} catch (HibernateException e) {
			log.error("DataBase Exception" + e);
			throw new ApplicationException("Exception a FindByPK" + e.getMessage());
		} finally {
			session.close();
		}

		log.debug("Model FindByPK End");
		return dto;
	}

	/**
	 * Update marksheet
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stublog.debug("Model update start");
		log.debug("Model update started");
		MarksheetDTO dtoExist = findByRollNo(dto.getRollNo());
		if (!dtoExist.getRollNo().equalsIgnoreCase(dto.getRollNo())) {
			throw new DuplicateRecordException("RollNo Already Exist");
		}
		StudentModelInt smodel = ModelFactory.getInstance().getStudentModel();
		StudentDTO sDto = smodel.findByPK(dto.getStudentId());
		dto.setName(sDto.getFirstName() + " " + sDto.getLastName());
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
			throw new ApplicationException("Exception in update Marksheet" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model update end");

	}

	/**
	 * Searches
	 *
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(MarksheetDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	/**
	 * Searches Marksheet with pagination
	 *
	 * @return list : List of Marksheet
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 */
	public List search(MarksheetDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model search started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			if (dto != null) {
				if (dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getStudentId() > 0) {
					criteria.add(Restrictions.eq("studentId", dto.getStudentId()));
				}
				if (dto.getRollNo() != null) {
					criteria.add(Restrictions.like("rollNo", dto.getRollNo() + "%"));
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
			throw new ApplicationException("Exception in search Marksheet" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model search end");

		return list;
	}

	/**
	 * Gets List of Marksheet
	 *
	 * @return list : List of Marksheets
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	/**
	 * get List of Marksheet with pagination
	 *
	 * @return list : List of Marksheets
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
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			// if PageSize Greater than Zero then applymPagination
			/*
			 * if (pageSize > 0) { pageNo = ((pageNo - 1) * pageSize) ;
			 * criteria.setFirstResult(pageNo);
			 * criteria.setMaxResults(pageSize); }
			 */
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Databases Exception", e);
			throw new ApplicationException("Exception in list Marksheet" + e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * get Merit List of Marksheet with pagination
	 *
	 * @return list : List of Marksheets
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */
	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model getMeritList Started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();

			StringBuffer hql = new StringBuffer(
					"from MarksheetDTO where  physics >32 and chemistry >32 and maths >32 order by (physics + chemistry + maths) desc");
			Query query = session.createQuery(hql.toString());
			// if page size is greater than zero then apply pagination
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				query.setFirstResult(pageNo);
				query.setMaxResults(pageSize);
			}
			list = query.list();

		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception in  marksheet list" + e.getMessage());
		} finally {
			session.close();
		}

		log.debug("Model getMeritList End");
		return list;
	}

}
