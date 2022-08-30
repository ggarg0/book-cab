package com.demo.bookcab.service.business.cabdetails;

import java.util.List;

import com.demo.bookcab.dto.CabDetailsResponse;
import com.demo.bookcab.entity.Cabs;

public interface CabDetailsService {

	/**
	 * <p>
	 * This method should return the cab details for entered cab number
	 * </p>
	 * <br>
	 * 
	 * @param cabNumber : Cab number
	 * @return {@link CabDetailsResponse} A cab inquiry response for the requested
	 *         cabNumber
	 */
	CabDetailsResponse getCabDetailsByCabNumber(String cabNumber);

	/**
	 * <p>
	 * This method should return the cab details for entered cab name
	 * </p>
	 * <br>
	 * 
	 * @param cabNumber : Cab name
	 * @return {List of @link Cabs}
	 * 
	 */
	List<Cabs> getCabDetailsByCabName(String cabName);
	
	/**
	 * <p>
	 * This method should return the details of all cabs as list of Cabs.
	 * </p>
	 * <br>
	 * 
	 * @return {List of @link Cabs}
	 */
	List<Cabs> getAllCabDetails();

	/**
	 * <p>
	 * This method should save the cab details.
	 * </p>
	 * <br>
	 * 
	 * @param {List of @link Cabs}
	 *
	 */
	void saveAllCabDetails(List<Cabs> cabDetails);

}
