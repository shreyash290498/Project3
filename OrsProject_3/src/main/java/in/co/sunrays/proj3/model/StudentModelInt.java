package in.co.sunrays.proj3.model;

import java.util.List;

import in.co.sunrays.proj3.dto.StudentDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;

/**
 * Data Access Object of Student
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */

public interface StudentModelInt {

    /**
     * Add a Student
     *
     * @param dto
     * @throws ApplicationException
     * @throws DuplicateRecordException
     *             : throws when Student already exists
     */
    public long add(StudentDTO dto) throws ApplicationException,
            DuplicateRecordException;

    /**
     * Update a Student
     *
     * @param dto
     * @throws ApplicationException
     * @throws DuplicateRecordException
     *             : if updated user record is already exist
     */
    public void update(StudentDTO dto) throws ApplicationException,
            DuplicateRecordException;

    /**
     * Delete a Student
     *
     * @param dto
     * @throws ApplicationException
     */
    public void delete(StudentDTO dto) throws ApplicationException;

    /**
     * Find Student by LoginID
     *
     * @param name
     *            : get parameter
     * @return dto
     * @throws ApplicationException
     */
    public StudentDTO findByEmailId(String emailId) throws ApplicationException;

    /**
     * Find Student by PK
     *
     * @param pk
     *            : get parameter
     * @return dto
     * @throws ApplicationException
     */
    public StudentDTO findByPK(long pk) throws ApplicationException;

    /**
     * Search Student with pagination
     *
     * @return list : List of Student
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws ApplicationException
     */
    public List search(StudentDTO dto, int pageNo, int pageSize)
            throws ApplicationException;

    /**
     * Search Student
     *
     * @return list : List of Student
     * @param dto
     *            : Search Parameters
     * @throws ApplicationException
     */
    public List search(StudentDTO dto) throws ApplicationException;

    /**
     * Gets List of College
     *
     * @return list : List of College
     * @throws DatabaseException
     */
    public List list() throws ApplicationException;

    /**
     * get List of College with pagination
     *
     * @return list : List of College
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws ApplicationException
     */
    public List list(int pageNo, int pageSize) throws ApplicationException;

}