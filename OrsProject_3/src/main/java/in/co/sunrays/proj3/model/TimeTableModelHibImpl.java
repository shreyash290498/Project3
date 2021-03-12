package in.co.sunrays.proj3.model;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.dto.TimeTableDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.exception.RecordNotFoundException;
import in.co.sunrays.proj3.util.HibDataSource;

/**
 * 
 * Hibernate Implementation of TimeTableModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0 @Copyright(c) SUNRAYS Technologies
 */
public class TimeTableModelHibImpl implements TimeTableModelInt {

	Logger log = Logger.getLogger(TimeTableModelHibImpl.class);

	/**
	 * Add a TimeTable
	 *
	 * @param dto
	 * @throws DatabaseException
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 */

	public long add(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException, RecordNotFoundException {
		// TODO Auto-generated method stub
		log.debug("Model add started");
		CourseModelHibImpl cModel = new CourseModelHibImpl();
		CourseDTO CourseDTO = cModel.findByPK(dto.getCourseId());
		dto.setCourseName(CourseDTO.getName());
		SubjectModelHibImpl sModel = new SubjectModelHibImpl();
		SubjectDTO subjectdto = sModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectdto.getName());
		if (subjectdto != null) {
			if (subjectdto.getCourseId() != dto.getCourseId()) {

				throw new RecordNotFoundException("This Subject is not Available For This Course");
			}
		}

		TimeTableDTO duplicatename = findTimeTableDuplicacy(dto.getCourseId(), dto.getSemester(), dto.getExamDate());
		TimeTableDTO duplicatename1 = findTimeTableDuplicacy(dto.getCourseId(), dto.getSemester(), dto.getSubjectId());
		TimeTableDTO duplicatename2 = findTimeTableDuplicacy(dto.getCourseId(), dto.getExamDate(), dto.getSubjectId());

		if (duplicatename != null) {
			throw new DuplicateRecordException("Time Table already exist");
		}

		if (duplicatename1 != null) {
			throw new DuplicateRecordException("Time Table already exist");
		}

		if (duplicatename2 != null) {
			throw new DuplicateRecordException("Time Table already exist");
		}

		long pk = 0;
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
			throw new ApplicationException("Exception a AddTimeTable" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model add end");

		return pk;
	}

	/**
	 * Update TimeTable
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 * @throws RecordNotFoundException
	 */

	public void update(TimeTableDTO dto)
			throws ApplicationException, DuplicateRecordException, RecordNotFoundException {
		// TODO Auto-generated method stub
		log.debug("Model update started");
		// check duplicacy

		TimeTableDTO duplicatename = findTimeTableDuplicacy(dto.getCourseId(), dto.getSemester(), dto.getExamDate());
		TimeTableDTO duplicatename1 = findTimeTableDuplicacy(dto.getCourseId(), dto.getSemester(), dto.getSubjectId());
		TimeTableDTO duplicatename2 = findTimeTableDuplicacy(dto.getCourseId(), dto.getExamDate(), dto.getSubjectId());

		if (duplicatename != null && duplicatename.getId() != dto.getId()) {
			throw new DuplicateRecordException("Time Table already exist");
		}

		if (duplicatename1 != null && duplicatename1.getId() != dto.getId()) {
			throw new DuplicateRecordException("Time Table already exist");
		}

		if (duplicatename2 != null && duplicatename2.getId() != dto.getId()) {
			throw new DuplicateRecordException("Time Table already exist");
		}

		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO course = cModel.findByPK(dto.getCourseId());
		dto.setCourseName(course.getName());
		SubjectModelInt sModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subjectdto = sModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectdto.getName());
		if (subjectdto != null) {
			if (subjectdto.getCourseId() != dto.getCourseId()) {

				throw new RecordNotFoundException("This Subject is not Available For This Course");
			}
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
			throw new ApplicationException("Exception in update TimeTable" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model update end");

	}

	/**
	 * Delete TimeTable
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */

	public void delete(TimeTableDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception a delete TimeTable" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model delete end");
	}

	/**
	 * Find TimeTable by Name
	 * 
	 * @param Name
	 *            : getparameter
	 * @return dto
	 * @throws ApplicationException
	 */

	public TimeTableDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByName Started");
		Session session = null;
		TimeTableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (TimeTableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			log.error("DataBase Exception" + e);
			throw new ApplicationException("Exception a FindByName TimeTable" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model FindByName End");
		return dto;
	}

	/**
	 * Find TimeTable by Id
	 * 
	 * @param pk
	 * @throws ApplicationException
	 */

	public TimeTableDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model FindByPK Started");
		Session session = null;
		TimeTableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (TimeTableDTO) session.get(TimeTableDTO.class, pk);
		} catch (HibernateException e) {
			log.error("DataBase Exception" + e);
			throw new ApplicationException("Exception a FindByPK TimeTable" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model FindByPK End");
		return dto;

	}

	/**
	 * SearchesTimeTable with pagination
	 *
	 * @return list : List ofTimeTable
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 */

	public List search(TimeTableDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model search started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
		if(dto !=null){
			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getSubjectId() > 0) {
				criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
			}
			if (dto.getCourseId() > 0) {
				criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
			}
			if (dto.getCourseName() != null) {
				criteria.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
			}
			if (dto.getExamDate() != null) {
				criteria.add(Restrictions.eq("examDate", dto.getExamDate()));
			}
			if (dto.getSubjectName() != null) {
				criteria.add(Restrictions.like("subjectName", dto.getSubjectName() + "%"));
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
			throw new ApplicationException("Exception in search TimeTableModel" + e.getMessage());
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

	public List search(TimeTableDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	/**
	 * Gets List of TimeTable
	 *
	 * @return list : List of TimeTables
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	/**
	 * get List of TimeTable with pagination
	 *
	 * @return list : List of TimeTables
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
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			// if PageSize Greater than Zero then applymPagination
			/*
			 * if (pageSize > 0) { pageNo = ((pageNo - 1) * pageSize) ;
			 * criteria.setFirstResult(pageNo);
			 * criteria.setMaxResults(pageSize); }
			 */
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Databases Exception", e);
			throw new ApplicationException("Exception in list TimeTable" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model list started");

		return list;
	}

	/**
	 * FindTimeTableDuplicacy
	 * 
	 * @param courseId
	 * @param ExamDate
	 * @param subjectId
	 * @return
	 * @throws ApplicationException
	 */

	public TimeTableDTO findTimeTableDuplicacy(Long courseId, Date examDate, Long subjectId)
			throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model  start findTimeTableDuplicacy");
		Session session = null;
		TimeTableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			criteria.add(Restrictions.eq("courseId", courseId));
			criteria.add(Restrictions.eq("examDate", examDate));
			criteria.add(Restrictions.eq("subjectId", subjectId));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (TimeTableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			log.error("DataBase Exception", e);
			throw new ApplicationException("Exception in findTImeTableDuplicacy " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model findTimeTableDuplicacy end");
		return dto;
	}

	/**
	 * FindTimeTableDuplicacy
	 * 
	 * @param courseId
	 * @param Semester
	 * @param subjectId
	 * @return
	 * @throws ApplicationException
	 */

	public TimeTableDTO findTimeTableDuplicacy(Long courseId, String Semester, Long subjectId)
			throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model  start findTimeTableDuplicacy");
		Session session = null;
		TimeTableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			criteria.add(Restrictions.eq("courseId", courseId));
			criteria.add(Restrictions.eq("semester", Semester));
			criteria.add(Restrictions.eq("subjectId", subjectId));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (TimeTableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			log.error("DataBase Exception", e);
			throw new ApplicationException("Exception in findTImeTableDuplicacy " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model findTimeTableDuplicacy end");
		return dto;
	}

	/**
	 * FindTimeTableDuplicacy
	 * 
	 * @param courseId
	 * @param Semester
	 * @param examdate
	 * @return
	 * @throws ApplicationException
	 */

	public TimeTableDTO findTimeTableDuplicacy(Long courseId, String Semester, Date examdate)
			throws ApplicationException {
		// TODO Auto-generated method stub
		log.debug("Model  start findTimeTableDuplicacy");
		Session session = null;
		TimeTableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			criteria.add(Restrictions.eq("courseId", courseId));
			criteria.add(Restrictions.eq("semester", Semester));
			criteria.add(Restrictions.eq("examDate", examdate));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (TimeTableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			log.error("DataBase Exception", e);
			throw new ApplicationException("Exception in findTImeTableDuplicacy " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model findTimeTableDuplicacy end");
		return dto;
	}

}
