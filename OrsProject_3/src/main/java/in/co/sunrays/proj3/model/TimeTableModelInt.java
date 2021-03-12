package in.co.sunrays.proj3.model;

import java.util.Date;
import java.util.List;

import in.co.sunrays.proj3.dto.TimeTableDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.exception.RecordNotFoundException;

/**
 * Data Access Object of TimeTable
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */

public interface TimeTableModelInt {


	 /**
   * Add a TimeTable
   *
   * @param dto
   * @throws ApplicationException
   * @throws DuplicateRecordException
   *             : throws when TimeTable already exists
	 * @throws RecordNotFoundException 
   */
  public long add(TimeTableDTO dto) throws ApplicationException,
          DuplicateRecordException, RecordNotFoundException;

  /**
   * Update a TimeTable
   *
   * @param dto
   * @throws ApplicationException
   * @throws DuplicateRecordException
   *             : if updated TimeTable record is already exist
 * @throws RecordNotFoundException 
   */
  public void update(TimeTableDTO dto) throws ApplicationException,
          DuplicateRecordException, RecordNotFoundException;

  /**
   * Delete a TimeTable
   *
   * @param dto
   * @throws ApplicationException
   */
  public void delete(TimeTableDTO dto) throws ApplicationException;

  /**
   * Find TimeTable by Name
   *
   * @param name
   *            : get parameter
   * @return dto
   * @throws ApplicationException
   */
  public TimeTableDTO findByName(String name) throws ApplicationException;

  /**
   * Find TimeTable by PK
   *
   * @param pk
   *            : get parameter
   * @return dto
   * @throws ApplicationException
   */
  public TimeTableDTO findByPK(long pk) throws ApplicationException;
  /**
	 * FindTimeTableDuplicacy
	 * @param courseId
	 * @param Semester
	 * @param subjectId
	 * @return
	 * @throws ApplicationException
	 */
	public TimeTableDTO findTimeTableDuplicacy(Long courseId, 
			Date examDate, Long subjectId) throws ApplicationException, DuplicateRecordException, RecordNotFoundException;
  /**
	 * FindTimeTableDuplicacy
	 * @param courseId
	 * @param Semester
	 * @param subjectId
	 * @return
	 * @throws ApplicationException
	 */
	public TimeTableDTO findTimeTableDuplicacy(Long courseId, 
			String Semester, Long subjectId) throws ApplicationException, DuplicateRecordException, RecordNotFoundException;
  /**
	 * FindTimeTableDuplicacy
	 * @param courseId
	 * @param Semester
	 * @param examdate
	 * @return
	 * @throws ApplicationException
	 */
	public TimeTableDTO findTimeTableDuplicacy(Long courseId, 
			String Semester, Date examdate) throws ApplicationException, DuplicateRecordException, RecordNotFoundException ;
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
  public List search(TimeTableDTO dto, int pageNo, int pageSize)
          throws ApplicationException;

  /**
   * Search TimeTable
   *
   * @return list : List of TimeTable
   * @param dto
   *            : Search Parameters
   * @throws ApplicationException
 * @throws RecordNotFoundException 
   */
  public List search(TimeTableDTO dto) throws ApplicationException, RecordNotFoundException;

  /**
   * Gets List of TimeTable
   *
   * @return list : List of TimeTable
   * @throws DatabaseException
   */
  public List list() throws ApplicationException;

  /**
   * get List of TimeTable with pagination
   *
   * @return list : List of TimeTable
   * @param pageNo
   *            : Current Page No.
   * @param pageSize
   *            : Size of Page
   * @throws ApplicationException
   */
  public List list(int pageNo, int pageSize) throws ApplicationException;


}
