package com.demo.bookcab.service.business.locationdetails;

import java.util.List;

import com.demo.bookcab.dto.LocationFootfallResponse;
import com.demo.bookcab.entity.Locations;

public interface LocationDetailsService {

	/**
	 * <p>
	 * This method should return the location details for entered locations
	 * </p>
	 * <br>
	 * 
	 * @param pickup, drop : Input locations
	 * @return {@link Locations} List of locations {Locations}
	 */

	List<Locations> getLocationDetails(String pickup, String drop);

	/**
	 * <p>
	 * This method should return the footfall details of all locations as list of
	 * LocationFootfallResponse.
	 * </p>
	 * <br>
	 * 
	 * @return {@link LocationFootfallResponse} A footfall inquiry response for all
	 *         locations {@LocationFootfallResponse}
	 */

	List<LocationFootfallResponse> getAllLocationFootfall();

	/**
	 * <p>
	 * This method should save the footfall details of all stations .
	 * </p>
	 * <br>
	 * 
	 * @param {List of @link Stations}
	 *
	 */
	void saveAllLocationDetails(List<Locations> locationDetails);

}
