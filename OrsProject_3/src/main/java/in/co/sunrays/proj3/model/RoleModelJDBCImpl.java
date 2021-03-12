package in.co.sunrays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.JDBCDataSource;

/**
 * JDBC Implementation of Role Model
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class RoleModelJDBCImpl implements RoleModelInt  {

	  private static Logger log = Logger.getLogger(RoleModelJDBCImpl.class);

	    /**
	     * Find next PK of Role
	     * 
	     * @throws DatabaseException
	     */
	    public Integer nextPK() throws DatabaseException {
	        log.debug("Model nextPK Started");
	        Connection conn = null;
	        int pk = 0;
	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn
	                    .prepareStatement("SELECT MAX(ID) FROM ST_ROLE");
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
	     * Add a Role
	     * @param dto
	     * @return
	     * @throws ApplicationException
	     * @throws DuplicateRecordException
	     */
	    public long add(RoleDTO dto) throws ApplicationException,
	            DuplicateRecordException {
	        log.debug("Model add Started");
	        Connection conn = null;
	        int pk = 0;

	        RoleDTO duplicataRole = findByName(dto.getName());
	        // Check if create Role already exist
	        if (duplicataRole != null) {
	            throw new DuplicateRecordException("Role already exists");
	        }

	        try {
	            conn = JDBCDataSource.getConnection();
	            pk = nextPK();
	            // Get auto-generated next primary key
	            conn.setAutoCommit(false); // Begin transaction
	            PreparedStatement pstmt = conn
	                    .prepareStatement("INSERT INTO ST_ROLE VALUES(?,?,?,?,?,?,?)");
	            pstmt.setInt(1, pk);
	            pstmt.setString(2, dto.getName());
	            pstmt.setString(3, dto.getDescription());
	            pstmt.setString(4, dto.getCreatedBy());
	            pstmt.setString(5, dto.getModifiedBy());
	            pstmt.setTimestamp(6, dto.getCreatedDatetime());
	            pstmt.setTimestamp(7, dto.getModifiedDatetime());
	            pstmt.executeUpdate();
	            conn.commit(); // End transaction
	            pstmt.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	            log.error("Database Exception..", e);
	            try {
	                conn.rollback();
	            } catch (Exception ex) {
	                throw new ApplicationException(
	                        "Exception : add rollback exception " + ex.getMessage());
	            }
	            throw new ApplicationException("Exception : Exception in add Role");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model add End");
	        return pk;
	    }

	    /**
	     * Delete a Role
	     * 
	     * @param dto
	     * @throws ApplicationException
	     */
	    public void delete(RoleDTO dto) throws ApplicationException {
	        log.debug("Model delete Started");
	        Connection conn = null;
	        try {
	            conn = JDBCDataSource.getConnection();
	            conn.setAutoCommit(false); // Begin transaction
	            PreparedStatement pstmt = conn
	                    .prepareStatement("DELETE FROM ST_ROLE WHERE ID=?");
	            pstmt.setLong(1, dto.getId());
	           int a= pstmt.executeUpdate();
	            conn.commit(); // End transaction
	            pstmt.close();

	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            try {
	                conn.rollback();
	            } catch (Exception ex) {
	                throw new ApplicationException(
	                        "Exception : Delete rollback exception "
	                                + ex.getMessage());
	            }
	            throw new ApplicationException(
	                    "Exception : Exception in delete Role");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model delete Started");
	    }

	    /**
	     * Find User by Role
	     * 
	     * @param name
	     *            : get parameter
	     * @return dto
	     * @throws ApplicationException
	     */

	    public RoleDTO findByName(String name) throws ApplicationException {
	        log.debug("Model findBy EmailId Started");
	        StringBuffer sql = new StringBuffer(
	                "SELECT * FROM ST_ROLE WHERE NAME=?");
	        RoleDTO dto = null;
	        Connection conn = null;
	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            pstmt.setString(1, name);
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                dto = new RoleDTO();
	                dto.setId(rs.getLong(1));
	                dto.setName(rs.getString(2));
	                dto.setDescription(rs.getString(3));
	                dto.setCreatedBy(rs.getString(4));
	                dto.setModifiedBy(rs.getString(5));
	                dto.setCreatedDatetime(rs.getTimestamp(6));
	                dto.setModifiedDatetime(rs.getTimestamp(7));

	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in getting User by emailId");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model findBy EmailId End");
	        return dto;
	    }

	    /**
	     * Find Role by PK
	     * 
	     * @param pk
	     *            : get parameter
	     * @return dto
	     * @throws ApplicationException
	     */

	    public RoleDTO findByPK(long pk) throws ApplicationException {
	        log.debug("Model findByPK Started");
	        StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE ID=?");
	        RoleDTO dto = null;
	        Connection conn = null;
	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            pstmt.setLong(1, pk);
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                dto = new RoleDTO();
	                dto.setId(rs.getLong(1));
	                dto.setName(rs.getString(2));
	                dto.setDescription(rs.getString(3));
	                dto.setCreatedBy(rs.getString(4));
	                dto.setModifiedBy(rs.getString(5));
	                dto.setCreatedDatetime(rs.getTimestamp(6));
	                dto.setModifiedDatetime(rs.getTimestamp(7));
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in getting User by pk");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model findByPK End");
	        return dto;
	    }

	    /**
	     * Update a Role
	     * 
	     * @param dto
	     * @throws ApplicationException
	     * @throws DuplicateRecordException
	     */

	    public void update(RoleDTO dto) throws ApplicationException,
	            DuplicateRecordException {
	        log.debug("Model update Started");
	        Connection conn = null;

	        RoleDTO duplicataRole = findByName(dto.getName());
	        // Check if updated Role already exist
	        if (duplicataRole != null && duplicataRole.getId() != dto.getId()) {
	            throw new DuplicateRecordException("Role already exists");
	        }
	        try {
	            conn = JDBCDataSource.getConnection();

	            conn.setAutoCommit(false); // Begin transaction
	            PreparedStatement pstmt = conn
	                    .prepareStatement("UPDATE ST_ROLE SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
	            pstmt.setString(1, dto.getName());
	            pstmt.setString(2, dto.getDescription());
	            pstmt.setString(3, dto.getCreatedBy());
	            pstmt.setString(4, dto.getModifiedBy());
	            pstmt.setTimestamp(5, dto.getCreatedDatetime());
	            pstmt.setTimestamp(6, dto.getModifiedDatetime());
	            pstmt.setLong(7, dto.getId());
	            pstmt.executeUpdate();
	            conn.commit(); // End transaction
	            pstmt.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            try {
	                conn.rollback();
	            } catch (Exception ex) {
	                throw new ApplicationException(
	                        "Exception : Delete rollback exception "
	                                + ex.getMessage());
	            }
	            throw new ApplicationException("Exception in updating Role ");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model update End");
	    }

	    /**
	     * Search Role
	     * 
	     * @param dto
	     *            : Search Parameters
	     * @throws ApplicationException
	     */

	    public List search(RoleDTO dto) throws ApplicationException {
	        return search(dto, 0, 0);
	    }

	    /**
	     * Search Role with pagination
	     * 
	     * @return list : List of Roles
	     * @param dto
	     *            : Search Parameters
	     * @param pageNo
	     *            : Current Page No.
	     * @param pageSize
	     *            : Size of Page
	     * 
	     * @throws ApplicationException
	     */

	    public List search(RoleDTO dto, int pageNo, int pageSize)
	            throws ApplicationException {
	        log.debug("Model search Started");
	        StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE 1=1");

	        if (dto != null) {
	            if (dto.getId() > 0) {
	                sql.append(" AND id = " + dto.getId());
	            }
	            if (dto.getName() != null && dto.getName().length() > 0) {
	                sql.append(" AND NAME like '" + dto.getName() + "%'");
	            }
	            if (dto.getDescription() != null
	                    && dto.getDescription().length() > 0) {
	                sql.append(" AND DESCRIPTION like '" + dto.getDescription()
	                        + "%'");
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
	                dto = new RoleDTO();
	                dto.setId(rs.getLong(1));
	                dto.setName(rs.getString(2));
	                dto.setDescription(rs.getString(3));
	                dto.setCreatedBy(rs.getString(4));
	                dto.setModifiedBy(rs.getString(5));
	                dto.setCreatedDatetime(rs.getTimestamp(6));
	                dto.setModifiedDatetime(rs.getTimestamp(7));
	                list.add(dto);
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in search Role");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }

	        log.debug("Model search End");
	        return list;
	    }

	    /**
	     * Get List of Role
	     * 
	     * @return list : List of Role
	     * @throws ApplicationException
	     */

	    public List list() throws ApplicationException {
	        return list(0, 0);
	    }

	    /**
	     * Get List of Role with pagination
	     * 
	     * @return list : List of Role
	     * @param pageNo
	     *            : Current Page No.
	     * @param pageSize
	     *            : Size of Page
	     * @throws ApplicationException
	     */

	    public List list(int pageNo, int pageSize) throws ApplicationException {
	        log.debug("Model list Started");
	        ArrayList list = new ArrayList();
	        StringBuffer sql = new StringBuffer("select * from ST_ROLE");
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
	                RoleDTO dto = new RoleDTO();
	                dto.setId(rs.getLong(1));
	                dto.setName(rs.getString(2));
	                dto.setDescription(rs.getString(3));
	                dto.setCreatedBy(rs.getString(4));
	                dto.setModifiedBy(rs.getString(5));
	                dto.setCreatedDatetime(rs.getTimestamp(6));
	                dto.setModifiedDatetime(rs.getTimestamp(7));
	                list.add(dto);
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in getting list of Role");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }

	        log.debug("Model list End");
	        return list;

	    }

}
