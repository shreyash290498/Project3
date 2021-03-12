package in.co.sunrays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.FacultyDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.JDBCDataSource;

/**
 * JDBC implementation of faculty model
 * 
 * @author Strategy
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */

public class FacultyModelJDBCImpl implements FacultyModelInt {

	private static Logger log = Logger.getLogger(FacultyModelJDBCImpl.class);

	/**
	 * Find next PK of Faculty
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_FACULTY");
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
	 * Add a Faculty
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;
		CollegeModelJDBCImpl collegeModel = new CollegeModelJDBCImpl();
		CollegeDTO collegedto = collegeModel.findByPK(dto.getCollegeId());
		dto.setCollegeName(collegedto.getName());

		CourseModelJDBCImpl courseModel = new CourseModelJDBCImpl();
		CourseDTO coursedto = courseModel.findByPK(dto.getCourseId());
		dto.setCourseName(coursedto.getName());

		SubjectModelJDBCImpl subjectModel = new SubjectModelJDBCImpl();
		SubjectDTO subjectdto = subjectModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectdto.getName());

		FacultyDTO dtoExist = findByEmail(dto.getEmailId());
		if (dtoExist != null) {
			throw new DuplicateRecordException("Email already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			// System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, dto.getFirstName());
			pstmt.setString(3, dto.getLastName());
			pstmt.setString(4, dto.getGender());
			pstmt.setDate(5, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(6, dto.getQualification());
			pstmt.setString(7, dto.getEmailId());
			pstmt.setString(8, dto.getMobileNo());
			pstmt.setLong(9, dto.getCollegeId());
			pstmt.setString(10, dto.getCollegeName());
			pstmt.setLong(11, dto.getCourseId());
			pstmt.setString(12, dto.getCourseName());
			pstmt.setLong(13, dto.getSubjectId());
			pstmt.setString(14, dto.getSubjectName());
			pstmt.setString(15, dto.getCreatedBy());
			pstmt.setString(16, dto.getModifiedBy());
			pstmt.setTimestamp(17, dto.getCreatedDatetime());
			pstmt.setTimestamp(18, dto.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Delete a Faculty
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(FacultyDTO dto) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
			pstmt.setLong(1, dto.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	/**
	 * Find Faculty by Email
	 * 
	 * @param emailId
	 * @throws ApplicationException
	 */
	public FacultyDTO findByEmail(String emailId) throws ApplicationException {
		log.debug("Model findByEmail Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE EMAIL_ID=?");
		FacultyDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, emailId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setDob(rs.getDate(5));
				dto.setQualification(rs.getString(6));
				dto.setEmailId(rs.getString(7));
				dto.setMobileNo(rs.getString(8));
				dto.setCollegeId(rs.getLong(9));
				dto.setCollegeName(rs.getString(10));
				dto.setCourseId(rs.getLong(11));
				dto.setCourseName(rs.getString(12));
				dto.setSubjectId(rs.getLong(13));
				dto.setSubjectName(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Faculty by Email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByEmail End");
		return dto;
	}

	/**
	 * Find Faculty by pk
	 * 
	 * @param pk
	 *            :get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public FacultyDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
		FacultyDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			dto = new FacultyDTO();
			while (rs.next()) {

				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setDob(rs.getDate(5));
				dto.setQualification(rs.getString(6));
				dto.setEmailId(rs.getString(7));
				dto.setMobileNo(rs.getString(8));
				dto.setCollegeId(rs.getLong(9));
				dto.setCollegeName(rs.getString(10));
				dto.setCourseId(rs.getLong(11));
				dto.setCourseName(rs.getString(12));
				dto.setSubjectId(rs.getLong(13));
				dto.setSubjectName(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Faculty by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return dto;
	}

	/**
	 * Update a Faculty
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		CollegeModelJDBCImpl collegeModel = new CollegeModelJDBCImpl();
		CollegeDTO collegedto = collegeModel.findByPK(dto.getCollegeId());
		dto.setCollegeName(collegedto.getName());

		CourseModelJDBCImpl courseModel = new CourseModelJDBCImpl();
		CourseDTO coursedto = courseModel.findByPK(dto.getCourseId());
		dto.setCourseName(coursedto.getName());

		SubjectModelJDBCImpl subjectModel = new SubjectModelJDBCImpl();
		SubjectDTO subjectdto = subjectModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectdto.getName());

		FacultyDTO dtoExist = findByEmail(dto.getEmailId());
		// Check if updated EmailId already exist
		if (dtoExist != null && !(dtoExist.getId() == dto.getId())) {
			throw new DuplicateRecordException("EmailId is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_FACULTY SET COLLEGE_ID=?, COLLEGE_NAME=?, COURSE_ID=?, COURSE_NAME=?, SUBJECT_ID=?, SUBJECT_NAME=?, FIRST_NAME=?, LAST_NAME=?, QUALIFICATION=?, EMAIL_ID=?, DOB=?, MOBILE_NO=?, CREATED_BY=?, MODIFIED_BY=?, CREATED_DATETIME=?, MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setLong(1, dto.getCollegeId());
			pstmt.setString(2, dto.getCollegeName());
			pstmt.setLong(3, dto.getCourseId());
			pstmt.setString(4, dto.getCourseName());
			pstmt.setLong(5, dto.getSubjectId());
			pstmt.setString(6, dto.getSubjectName());
			pstmt.setString(7, dto.getFirstName());
			pstmt.setString(8, dto.getLastName());
			pstmt.setString(9, dto.getQualification());
			pstmt.setString(10, dto.getEmailId());
			pstmt.setDate(11, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(12, dto.getMobileNo());
			pstmt.setString(13, dto.getCreatedBy());
			pstmt.setString(14, dto.getModifiedBy());
			pstmt.setTimestamp(15, dto.getCreatedDatetime());
			pstmt.setTimestamp(16, dto.getModifiedDatetime());
			pstmt.setLong(17, dto.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Faculty ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Search Faculty
	 * 
	 * @param dto
	 *            : Search Parameters
	 * @throws ApplicationException
	 */

	public List search(FacultyDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * Search Faculty with pagination
	 * 
	 * @return list : List of Faculties
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 * @throws ApplicationException
	 */

	public List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND id = " + dto.getId());
			}
			if (dto.getFirstName() != null && dto.getFirstName().trim().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + dto.getFirstName() + "%'");
			}
			if (dto.getLastName() != null && dto.getLastName().trim().length() > 0) {
				sql.append(" AND LAST_NAME like '" + dto.getLastName() + "%'");
			}
			if (dto.getEmailId() != null && dto.getEmailId().length() > 0) {
				sql.append(" AND EMAIL like '" + dto.getEmailId() + "%'");
			}

			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + dto.getDob());
			}
			if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO = " + dto.getMobileNo());
			}
			if (dto.getCollegeId() > 0) {
				sql.append(" AND COLLEGE_ID = " + dto.getCollegeId());
			}
			if (dto.getCourseId() > 0) {
				sql.append(" AND COURSE_ID = " + dto.getCourseId());
			}
			if (dto.getSubjectId() > 0) {
				sql.append(" AND SUBJECT_ID = " + dto.getSubjectId());
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setDob(rs.getDate(5));
				dto.setQualification(rs.getString(6));
				dto.setEmailId(rs.getString(7));
				dto.setMobileNo(rs.getString(8));
				dto.setCollegeId(rs.getLong(9));
				dto.setCollegeName(rs.getString(10));
				dto.setCourseId(rs.getLong(11));
				dto.setCourseName(rs.getString(12));
				dto.setSubjectId(rs.getLong(13));
				dto.setSubjectName(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	/**
	 * Get List of Faculty
	 * 
	 * @return list : List of Faculty
	 * @throws ApplicationException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Faculty with pagination
	 * 
	 * @return list : List of Faculty
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_FACULTY");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		FacultyDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setDob(rs.getDate(5));
				dto.setQualification(rs.getString(6));
				dto.setEmailId(rs.getString(7));
				dto.setMobileNo(rs.getString(8));
				dto.setCollegeId(rs.getLong(9));
				dto.setCollegeName(rs.getString(10));
				dto.setCourseId(rs.getLong(11));
				dto.setCourseName(rs.getString(12));
				dto.setSubjectId(rs.getLong(13));
				dto.setSubjectName(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}
}
