package in.co.sunrays.proj3.model;

import java.util.List;

import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;

/**
 * Data Access Object of Role
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */

public interface RoleModelInt {

    /**
     * Add a Role
     *
     * @param dto
     * @throws ApplicationException
     * @throws DuplicateRecordException
     *             : throws when Role already exists
     */
    public long add(RoleDTO dto) throws ApplicationException,
            DuplicateRecordException;

    /**
     * Update a Role
     *
     * @param dto
     * @throws ApplicationException
     * @throws DuplicateRecordException
     *             : if updated user record is already exist
     */
    public void update(RoleDTO dto) throws ApplicationException,
            DuplicateRecordException;

    /**
     * Delete a Role
     *
     * @param dto
     * @throws ApplicationException
     */
    public void delete(RoleDTO dto) throws ApplicationException;

    /**
     * Find Role by Name
     *
     * @param name
     *            : get parameter
     * @return dto
     * @throws ApplicationException
     */
    public RoleDTO findByName(String name) throws ApplicationException;

    /**
     * Find Role by PK
     *
     * @param pk
     *            : get parameter
     * @return dto
     * @throws ApplicationException
     */
    public RoleDTO findByPK(long pk) throws ApplicationException;

    /**
     * Search Role with pagination
     *
     * @return list : List of Role
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws ApplicationException
     */
    public List search(RoleDTO dto, int pageNo, int pageSize)
            throws ApplicationException;

    /**
     * Search Role
     *
     * @return list : List of Role
     * @param dto
     *            : Search Parameters
     * @throws ApplicationException
     */
    public List search(RoleDTO dto) throws ApplicationException;

    /**
     * Gets List of Role
     *
     * @return list : List of Role
     * @throws DatabaseException
     */
    public List list() throws ApplicationException;

    /**
     * get List of Role with pagination
     *
     * @return list : List of Role
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws ApplicationException
     */
    public List list(int pageNo, int pageSize) throws ApplicationException;

}
