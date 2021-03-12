package in.co.sunrays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.dto.TimeTableDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.exception.RecordNotFoundException;
import in.co.sunrays.proj3.util.JDBCDataSource;

/**
 * JDBC Implementation of Time Table Model
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class TimeTableModelJDBCImpl implements TimeTableModelInt {
	private static Logger log = Logger.getLogger(TimeTableModelJDBCImpl.class);

	/**
	 * Find next PK of TimeTable
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_TIMETABLE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * Add a TimeTable semester validation according to course duration
	 * 
	 * @param cb
	 * @param tb
	 * @throws RecordNotFoundException
	 */
	public void semChecker(CourseDTO cb, TimeTableDTO tb) throws RecordNotFoundException {
		String duration = cb.getDuration();

		if (duration.equalsIgnoreCase("3 years") && tb.getSemester().equalsIgnoreCase("VII")
				|| tb.getSemester().equalsIgnoreCase("VIII")) {

			throw new RecordNotFoundException("This Semester is not available for this course");

		}
		if (duration.equalsIgnoreCase("2 years") && tb.getSemester().equalsIgnoreCase("V")
				|| tb.getSemester().equalsIgnoreCase("VI") || tb.getSemester().equalsIgnoreCase("VII")
				|| tb.getSemester().equalsIgnoreCase("VIII")) {
			throw new RecordNotFoundException("This Semester is not available for this course");

		}
	}

	/**
	 * Add a TimeTable
	 * 
	 * @param dto
	 * @return
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 * @throws RecordNotFoundException
	 */
	public long add(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException, RecordNotFoundException {

		log.debug("Model add started");
		Connection conn = null;

		CourseModelJDBCImpl cModel = new CourseModelJDBCImpl();
		CourseDTO CourseDTO = cModel.findByPK(dto.getCourseId());
		dto.setCourseName(CourseDTO.getName());

		SubjectModelJDBCImpl sModel = new SubjectModelJDBCImpl();
		SubjectDTO subjectdto = sModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectdto.getName());

		if (subjectdto != null) {
			if (subjectdto.getCourseId() != dto.getCourseId()) {

				throw new RecordNotFoundException("This Subject is not Available For This Course");
			}
		}

		semChecker(CourseDTO, dto);

		TimeTableDTO duplicatename = findTimeTableDuplicacy(dto.getCourseId(), dto.getSemester(), dto.getExamDate());
		TimeTableDTO duplicatename1 = findTimeTableDuplicacy(dto.getCourseId(), dto.getSemester(), dto.getSubjectId());
		TimeTableDTO duplicatename2 = findTimeTableDuplicacy(dto.getCourseId(), dto.getExamDate(), dto.getSubjectId());

		int pk = 0;
		if (duplicatename != null) {
			throw new DuplicateRecordException("Time Table already exist");
		}

		if (duplicatename1 != null) {
			throw new DuplicateRecordException("Time Table already exist");
		}

		if (duplicatename2 != null) {
			throw new DuplicateRecordException("Time Table already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement psmt = conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			psmt.setInt(1, pk);
			psmt.setLong(2, dto.getCourseId());
			psmt.setString(3, dto.getCourseName());
			psmt.setLong(4, dto.getSubjectId());
			psmt.setString(5, dto.getSubjectName());
			psmt.setString(6, dto.getSemester());
			psmt.setDate(7, new java.sql.Date(dto.getExamDate().getTime()));
			psmt.setString(8, dto.getTime());
			psmt.setString(9, dto.getCreatedBy());
			psmt.setString(10, dto.getModifiedBy());
			psmt.setTimestamp(11, dto.getCreatedDatetime());
			psmt.setTimestamp(12, dto.getModifiedDatetime());

			psmt.executeUpdate();
			conn.commit();// End Transaction
			psmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Delete a TimeTable
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(TimeTableDTO dto) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement psmt = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
			psmt.setLong(1, dto.getId());
			psmt.executeUpdate();
			conn.commit();
			psmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Ended");
	}

	/**
	 * Update a TimeTable
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 * @throws RecordNotFoundException
	 * @throws @throws
	 *             DatabaseException
	 */

	public void update(TimeTableDTO dto)
			throws ApplicationException, DuplicateRecordException, RecordNotFoundException {
		log.debug("Model update Started");
		Connection conn = null;

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

		CourseModelJDBCImpl cModel = new CourseModelJDBCImpl();
		CourseDTO CourseDTO = cModel.findByPK(dto.getCourseId());
		dto.setCourseName(CourseDTO.getName());

		SubjectModelJDBCImpl sModel = new SubjectModelJDBCImpl();
		SubjectDTO subjectdto = sModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectdto.getName());

		if (subjectdto != null) {
			if (subjectdto.getCourseId() != dto.getCourseId()) {

				throw new RecordNotFoundException("This Subject is not Available For This Course");
			}
		}
		semChecker(CourseDTO, dto);

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement psmt = conn.prepareStatement(
					"UPDATE ST_TIMETABLE SET COURSE_ID=?,COURSE_NAME=?,SUBJECT_ID=?,SUBJECT_NAME=?,SEMESTER=?,EXAM_DATE=?,EXAM_TIME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			psmt.setLong(1, dto.getCourseId());
			psmt.setString(2, dto.getCourseName());
			psmt.setLong(3, dto.getSubjectId());
			psmt.setString(4, dto.getSubjectName());
			psmt.setString(5, dto.getSemester());
			psmt.setDate(6, new java.sql.Date(dto.getExamDate().getTime()));
			psmt.setString(7, dto.getTime());
			psmt.setString(8, dto.getCreatedBy());
			psmt.setString(9, dto.getModifiedBy());
			psmt.setTimestamp(10, dto.getCreatedDatetime());
			psmt.setTimestamp(11, dto.getModifiedDatetime());
			psmt.setLong(12, dto.getId());
			psmt.executeUpdate();
			conn.commit();
			psmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating TimeTable ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");

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
		log.debug("Method FindTimeTable of Model TimeTable started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=?  AND SEMESTER=? AND EXAM_DATE = ?");
		TimeTableDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setString(2, Semester);
			pstmt.setDate(3, new java.sql.Date(examdate.getTime()));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubjectId(rs.getLong(2));
				dto.setSubjectName(rs.getString(3));
				dto.setCourseId(rs.getLong(4));
				dto.setCourseName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setTime(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method FindTimeTable of Model TimeTable End");
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
		log.debug("Method FindTimeTable of Model TimeTable started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=?  AND SEMESTER=? AND SUBJECT_ID = ?");
		TimeTableDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setString(2, Semester);
			pstmt.setLong(3, subjectId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubjectId(rs.getLong(2));
				dto.setSubjectName(rs.getString(3));
				dto.setCourseId(rs.getLong(4));
				dto.setCourseName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setTime(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
			}

			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method FindTimeTable of Model TimeTable End");
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
	public TimeTableDTO findTimeTableDuplicacy(Long courseId, Date examDate, Long subjectId)
			throws ApplicationException {
		log.debug("Method FindTimeTable of Model TimeTable started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=?  AND EXAM_DATE=? AND SUBJECT_ID = ?");
		TimeTableDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setDate(2, new java.sql.Date(examDate.getTime()));
			pstmt.setLong(3, subjectId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubjectId(rs.getLong(2));
				dto.setSubjectName(rs.getString(3));
				dto.setCourseId(rs.getLong(4));
				dto.setCourseName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setTime(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
			}

			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method FindTimeTable of Model TimeTable End");
		return dto;

	}

	/**
	 * Find TimeTable by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 * 
	 */

	public TimeTableDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE ID=?");
		TimeTableDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseId(rs.getLong(2));
				dto.setCourseName(rs.getString(3));
				dto.setSubjectId(rs.getLong(4));
				dto.setSubjectName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setTime(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
			}

			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting TimeTable by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");

		return dto;
	}

	/**
	 * Search TimeTable
	 * 
	 * @param dto
	 *            : Search Parameters
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 */

	public List search(TimeTableDTO dto) throws ApplicationException, RecordNotFoundException {
		return search(dto, 0, 0);
	}

	/**
	 * Search TimeTable with pagination
	 * 
	 * @return list : List of TimeTable
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 */
	public List search(TimeTableDTO dto, int pageNo, int PageSize)
			throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("Select * From ST_TIMETABLE Where 1=1");
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND id = " + dto.getId());
			}
			if (dto.getCourseId() > 0) {

				sql.append(" AND COURSE_ID = " + dto.getCourseId());
			}
			if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
				sql.append(" AND COURSE_NAME like '" + dto.getCourseName() + "%'");
			}
			if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
				sql.append(" AND SUBJECT_NAME like '" + dto.getSubjectName() + "%'");
			}
			if (dto.getSemester() != null && dto.getSemester().trim().length() > 0) {
				sql.append(" AND SEMESTER like '" + dto.getSemester() + "'");
			}
			if (dto.getExamDate() != null) {

				// new java.sql.Date(dto.getExameDate().getTime())
				sql.append(
						" AND EXAM_DATE like '" + new SimpleDateFormat("yyyy-MM-dd").format(dto.getExamDate()) + "'");
			}

		}

		// if pageSize1 is greater than zero then apply pagination
		if (PageSize > 0) {
			// calculate start record index
			pageNo = (pageNo - 1) * PageSize;
			sql.append(" limit " + pageNo + "," + PageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql.toString());
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubjectId(rs.getLong(2));
				dto.setSubjectName(rs.getString(3));
				dto.setCourseId(rs.getLong(4));
				dto.setCourseName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setTime(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("Model search End");
		return list;
	}

	/**
	 * Get List of TimeTable
	 * 
	 * @return list : List of TimeTable
	 * @throws ApplicationException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of TimeTable with pagination
	 * 
	 * @return list : List of TimeTable
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_timetable");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				TimeTableDTO dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubjectId(rs.getLong(2));
				dto.setSubjectName(rs.getString(3));
				dto.setCourseId(rs.getLong(4));
				dto.setCourseName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setTime(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));

				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;
	}

	public TimeTableDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
