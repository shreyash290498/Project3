package in.co.sunrays.proj3.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.dto.StudentDTO;
import in.co.sunrays.proj3.dto.StudentDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;

public class StudentModelHibImpl implements StudentModelInt {

	Logger log = Logger.getLogger(StudentModelHibImpl.class);

	/**
	 * Add a Student
	 *
	 * @param dto
	 * @throws DatabaseException
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */

	public long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model add started");
		long pk = 0;
		StudentDTO studentDto = findByEmailId(dto.getEmail());
		if (studentDto !=null) {
			throw new DuplicateRecordException("Loginid  Already Exist");
		}
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegeDto = collegeModel.findByPK(dto.getCollegeId());
		dto.setCollegeName(collegeDto.getName());
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
			throw new ApplicationException("Exception a Add Student" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model add end");
		System.out.println("return pk==" + pk);
		return pk;
	}

	/**
	 * Update Student
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */

	public void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model update started");
		StudentDTO dtoExist = findByEmailId(dto.getEmail());
		if (!dtoExist.getEmail().equalsIgnoreCase(dto.getEmail())) {
			throw new DuplicateRecordException("LoginID Already Exist");
		}
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegeDto = collegeModel.findByPK(dto.getCollegeId());
		dto.setCollegeName(collegeDto.getName());
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
			throw new ApplicationException("Exception in update Student" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model update end");
	}

	/**
	 * Delete College
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */

	public void delete(StudentDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception a delete Student" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model delete end");

	}

	/**
	 * Find Student by Login
	 *
	 * @param login
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public StudentDTO findByEmailId(String emailId) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByLogin start");
		Session session = null;
		StudentDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(StudentDTO.class);
			criteria.add(Restrictions.eq("email", emailId));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (StudentDTO) list.get(0);
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
	 * Find Student by Id
	 * 
	 * @param pk
	 * @throws ApplicationException
	 */

	public StudentDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByPK Started");
		Session session = null;
		StudentDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (StudentDTO) session.get(StudentDTO.class, pk);
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
	 * SearchesStudent with pagination
	 *
	 * @return list : List ofCollege
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 */

	public List search(StudentDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model search started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(StudentDTO.class);
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
				if (dto.getEmail() != null) {
					criteria.add(Restrictions.like("email", dto.getEmail() + "%"));
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
			throw new ApplicationException("Exception in search Student" + e.getMessage());
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

	public List search(StudentDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	/**
	 * Gets List of Student
	 *
	 * @return list : List of Students
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	/**
	 * get List of Student with pagination
	 *
	 * @return list : List of Students
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
			Criteria criteria = session.createCriteria(StudentDTO.class);
			// if PageSize Greater than Zero then applymPagination
			/*
			 * if (pageSize > 0) { pageNo = ((pageNo - 1) * pageSize) ;
			 * criteria.setFirstResult(pageNo);
			 * criteria.setMaxResults(pageSize); }
			 */
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Databases Exception", e);
			throw new ApplicationException("Exception in list Student" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model list started");
		return list;
	}
}
