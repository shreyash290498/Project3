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
import in.co.sunrays.proj3.dto.FacultyDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;

/**
 * 
 * Hibernate Implementation of FacultyModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0 @Copyright(c) SUNRAYS Technologies
 */
public class FacultyModelHibImpl implements FacultyModelInt {

	Logger log = Logger.getLogger(FacultyModelHibImpl.class);

	/**
	 * Add a Faculty
	 *
	 * @param dto
	 * @throws DatabaseException
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */
	public long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model add started");
		long pk = 0;
		FacultyDTO FacultyDTO = findByEmail(dto.getEmailId());
		if (FacultyDTO !=null) {
			throw new DuplicateRecordException("Email  Already Exist");
		}
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegeDto = collegeModel.findByPK(dto.getCollegeId());
		dto.setCollegeName(collegeDto.getName());

		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO courseDto = courseModel.findByPK(dto.getCourseId());
		dto.setCourseName(courseDto.getName());

		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subjectDto = subjectModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectDto.getName());

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
			throw new ApplicationException("Exception a Add Faculty" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model add end");

		return pk;
	}

	/**
	 * Update Faculty
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		log.debug("Model update started");
		FacultyDTO dtoExist = findByEmail(dto.getEmailId());
		if (!dto.getEmailId().equalsIgnoreCase(dtoExist.getEmailId())) {
			System.out.println("fdfcgvhbmj");
			throw new DuplicateRecordException("Faculty Already Exist");
		}
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegeDto = collegeModel.findByPK(dto.getCollegeId());
		dto.setCollegeName(collegeDto.getName());

		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO courseDto = courseModel.findByPK(dto.getCourseId());
		dto.setCourseName(courseDto.getName());

		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subjectDto = subjectModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectDto.getName());

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
			throw new ApplicationException("Exception in update Faculty" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model update end");

	}

	/**
	 * Delete Faculty
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(FacultyDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception a delete Faculty" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model delete end");

	}

	/**
	 * Find Faculty by Login
	 *
	 * @param login
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public FacultyDTO findByEmail(String email) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByLogin start");
		Session session = null;
		FacultyDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			criteria.add(Restrictions.eq("emailId", email));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (FacultyDTO) list.get(0);
			}
		} catch (HibernateException e) {
			log.error("DataBase Exception", e);
			throw new ApplicationException("Exception in findByLogin faculty " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model FindByLogin end");

		return dto;
	}

	/**
	 * Find Faculty by Id
	 * 
	 * @param pk
	 * @throws ApplicationException
	 */
	public FacultyDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByPK Started");
		Session session = null;
		FacultyDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (FacultyDTO) session.get(FacultyDTO.class, pk);
		} catch (HibernateException e) {
			log.error("DataBase Exception" + e);
			throw new ApplicationException("Exception a FindByPK Faculty" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model FindByPK End");
		return dto;
	}

	/**
	 * SearchesFaculty with pagination
	 *
	 * @return list : List ofFaculty
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 */
	public List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model search started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			if (dto != null) {
				if (dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getCollegeId() > 0) {
					criteria.add(Restrictions.eq("collegeId", dto.getCollegeId()));
				}
				if (dto.getCourseId() > 0) {
					criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
				}
				if (dto.getSubjectId() > 0) {
					criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
				}
				if (dto.getCollegeName() != null) {
					criteria.add(Restrictions.like("collegeName", dto.getCollegeName() + "%"));
				}
				if (dto.getFirstName() != null) {
					criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
				}
				if (dto.getEmailId() != null) {
					criteria.add(Restrictions.like("emailId", dto.getEmailId() + "%"));
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
			throw new ApplicationException("Exception in search Faculty" + e.getMessage());
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
	public List search(FacultyDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	/**
	 * Gets List of Faculty
	 *
	 * @return list : List of Facultys
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	/**
	 * get List of Faculty with pagination
	 *
	 * @return list : List of Facultys
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
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			// if PageSize Greater than Zero then applymPagination
			/*
			 * if (pageSize > 0) { pageNo = ((pageNo - 1) * pageSize) ;
			 * criteria.setFirstResult(pageNo);
			 * criteria.setMaxResults(pageSize); }
			 */
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Databases Exception", e);
			throw new ApplicationException("Exception in list Faculty" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model list started");

		return list;
	}

}
