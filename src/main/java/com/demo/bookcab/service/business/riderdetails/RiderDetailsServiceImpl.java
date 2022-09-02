package com.demo.bookcab.service.business.riderdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookcab.constant.MessageConstants;
import com.demo.bookcab.data.service.CabDetailsDataService;
import com.demo.bookcab.data.service.LocationDetailsDataService;
import com.demo.bookcab.data.service.RiderDetailsDataService;
import com.demo.bookcab.data.service.TripDetailsDataService;
import com.demo.bookcab.dto.BookingIdRequest;
import com.demo.bookcab.dto.RiderInquiry;
import com.demo.bookcab.dto.RiderInquiryResponse;
import com.demo.bookcab.dto.TripDetailsResponse;
import com.demo.bookcab.dto.TripRequest;
import com.demo.bookcab.entity.Cabs;
import com.demo.bookcab.entity.Locations;
import com.demo.bookcab.entity.Rider;
import com.demo.bookcab.entity.Trips;
import com.demo.bookcab.exception.formatter.Formatters;
import com.demo.bookcab.exceptions.CabDetailsNotFoundException;
import com.demo.bookcab.exceptions.InsufficientFundsException;
import com.demo.bookcab.exceptions.LocationDetailsNotFoundException;
import com.demo.bookcab.exceptions.RiderMismatchFoundException;
import com.demo.bookcab.exceptions.RiderNotFoundException;
import com.demo.bookcab.exceptions.TripDetailsNotFoundException;
import com.demo.bookcab.security.AuthenticationService;

import lombok.Setter;

@Service
@Setter
public class RiderDetailsServiceImpl implements RiderDetailsService {

	@Autowired
	private RiderDetailsDataService riderDetailsDataService;

	@Autowired
	private CabDetailsDataService cabDetailsDataService;

	@Autowired
	private LocationDetailsDataService locationDetailsDataService;

	@Autowired
	private TripDetailsDataService tripDetailsDataService;

	@Autowired
	AuthenticationService authenticationService;

	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 *
	 * @param riderInquiry {@link RiderInquiry} details for getting account
	 *                     information.
	 * @return
	 */
	public RiderInquiryResponse getBalanceDetailsForRider(RiderInquiry riderInquiry) {
		if (Objects.isNull(riderInquiry)) {
			return new RiderInquiryResponse(null, null, null, null, 0.0, 0.0, MessageConstants.InvalidBalanceInquiry);
		}

		// Fetch details from DB.
		Rider rider = null;
		try {
			rider = riderDetailsDataService.getRiderAccountDetails(riderInquiry.getUserName());

		} catch (RiderNotFoundException exp) {
			return new RiderInquiryResponse(riderInquiry.getUserName(), null, null, null, 0.0, 0.0,
					MessageConstants.RiderNotFound);
		}

		// Authenticate
		if (this.authenticationService.authenticateRiderAccount(rider, riderInquiry.getWalletPin())) {
			// Respond Balance.
			return new RiderInquiryResponse(rider.getUser_name(), rider.getFirst_name(), rider.getLast_name(),
					rider.getEmail(), rider.getWallet_balance(), rider.getWallet_balance_onhold(), "");

		} else {
			return new RiderInquiryResponse(riderInquiry.getUserName(), null, null, null, 0.0, 0.0,
					MessageConstants.InvalidPin);
		}
	}

	@Override
	public List<Rider> getAllRiderAccountDetails() {
		return riderDetailsDataService.getAllRiderAccountDetails();
	}

	@Transactional
	@Override
	public synchronized TripDetailsResponse BookCabForRider(TripRequest bookingRequest) {
		if (Objects.isNull(bookingRequest)) {
			return new TripDetailsResponse(null, null, null, null, null, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null,
					MessageConstants.InvalidBookingRequest);
		}

		List<Locations> locationsDetails = new ArrayList<Locations>();
		List<Cabs> cabs = new ArrayList<Cabs>();
		Cabs cabSelected = null;

		double fare = 0;
		double distance = 0;

		Rider rider = null;
		try {
			logger.info("Fetching rider details from database");
			rider = riderDetailsDataService.getRiderAccountDetails(bookingRequest.getUserName());

			if (Objects.isNull(rider)) {
				throw new RiderNotFoundException(MessageConstants.RiderNotFound);
			}

			if (this.authenticationService.authenticateRiderAccount(rider, bookingRequest.getWalletPin())) {

				logger.info("Fetching location details from database");
				locationsDetails = locationDetailsDataService.getLocationDetails(bookingRequest.getPickUp(),
						bookingRequest.getDrop());

				logger.info("Fetching cabs details from database");
				cabs = cabDetailsDataService.getCabDetailsByCabName(bookingRequest.getCabName());
				cabSelected = cabs.stream().filter(cab -> cab.getStatus().equalsIgnoreCase(MessageConstants.Available))
						.collect(Collectors.toList()).get(0);

				distance = calculateDistanceBetweenLocations(locationsDetails, bookingRequest.getPickUp(),
						bookingRequest.getDrop());

				fare = Formatters.formatDecimalRoundToTwo(distance * cabSelected.getRate());

				double remainingBalance = Formatters.formatDecimalRoundToTwo(rider.getWallet_balance() - fare);

				if (remainingBalance < 0) {
					throw new InsufficientFundsException(MessageConstants.InsufficientBalance);
				}

				logger.info("Update and persist ride details");
				rider.setWallet_balance_onhold(
						Formatters.formatDecimalRoundToTwo(rider.getWallet_balance_onhold() + fare));
				rider.setWallet_balance(Formatters.formatDecimalRoundToTwo(rider.getWallet_balance() - fare));
				riderDetailsDataService.saveRiderAccountDetails(rider);

				logger.info("Update and persist location details");
				locationDetailsDataService.saveAllLocationDetails(locationsDetails);

				logger.info("Update and persist cab details");

				cabSelected.setStatus(MessageConstants.Booked);
				cabDetailsDataService.saveCabDetails(cabSelected);

				Long bookingId = System.currentTimeMillis();
				tripDetailsDataService.saveTripDetails(
						new Trips(0l, bookingRequest.getPickUp(), bookingRequest.getDrop(), fare, distance,
								rider.getUser_name(), cabSelected.getCab_number(), bookingId, cabSelected.getStatus()));

				return new TripDetailsResponse(bookingRequest.getUserName(), bookingRequest.getCabName(),
						cabSelected.getCab_number(), cabSelected.getDriver_name(), bookingRequest.getPickUp(),
						bookingRequest.getDrop(), distance, cabSelected.getRate(), fare, rider.getWallet_balance(),
						rider.getWallet_balance_onhold(), 0.0, MessageConstants.Booked, bookingId,
						"Cab booked successfully");

			} else {
				return new TripDetailsResponse(bookingRequest.getUserName(), bookingRequest.getCabName(), null, null,
						bookingRequest.getPickUp(), bookingRequest.getDrop(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null,
						MessageConstants.InvalidPin);
			}
		} catch (RiderNotFoundException riderException) {
			logger.error(MessageConstants.RiderNotFound);
			return new TripDetailsResponse(bookingRequest.getUserName(), null, null, null, null, null, 0.0, 0.0, 0.0,
					0.0, 0.0, 0.0, null, null, MessageConstants.RiderNotFound);
		} catch (LocationDetailsNotFoundException locationException) {
			logger.error(MessageConstants.LocationDetailsNotFound);
			return new TripDetailsResponse(bookingRequest.getUserName(), null, null, null, bookingRequest.getPickUp(),
					bookingRequest.getDrop(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null,
					MessageConstants.LocationDetailsNotFound);
		} catch (CabDetailsNotFoundException cabException) {
			logger.error(MessageConstants.CabDetailsNotFound);
			return new TripDetailsResponse(bookingRequest.getUserName(), bookingRequest.getCabName(), null, null,
					bookingRequest.getPickUp(), bookingRequest.getDrop(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null,
					MessageConstants.CabDetailsNotFound);
		} catch (InsufficientFundsException fundException) {
			logger.error(MessageConstants.InsufficientBalance);
			return new TripDetailsResponse(bookingRequest.getUserName(), bookingRequest.getCabName(),
					cabSelected.getCab_number(), cabSelected.getDriver_name(), bookingRequest.getPickUp(),
					bookingRequest.getDrop(), distance, cabSelected.getRate(), fare, rider.getWallet_balance(),
					rider.getWallet_balance_onhold(), 0.0, null, null, MessageConstants.InsufficientBalance);
		}
	}

	@Transactional
	@Override
	public synchronized TripDetailsResponse RiderTripTransaction(BookingIdRequest bookingRequest, String action) {
		if (Objects.isNull(bookingRequest)) {
			return new TripDetailsResponse(null, null, null, null, null, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null,
					MessageConstants.InvalidBookingRequest);
		}

		List<Locations> locationsDetails = new ArrayList<Locations>();
		Cabs cabSelected = null;
		Rider rider = null;
		Trips trip = null;
		double refund = 0;
		try {

			logger.info("Fetching trip details from database");
			trip = tripDetailsDataService.getTripDetailsByBookingId(bookingRequest.getBookingId());

			logger.info("Fetching rider details from database");
			rider = riderDetailsDataService.getRiderAccountDetails(bookingRequest.getUserName());

			if (Objects.isNull(rider)) {
				throw new RiderNotFoundException(MessageConstants.RiderNotFound);
			}

			if (!trip.getUser_name().equalsIgnoreCase(rider.getUser_name())) {
				throw new RiderMismatchFoundException(MessageConstants.RiderMismatchFound);
			}

			if (this.authenticationService.authenticateRiderAccount(rider, bookingRequest.getWalletPin())) {

				logger.info("Fetching location details from database");
				locationsDetails = locationDetailsDataService.getLocationDetails(trip.getLocation_pickup(),
						trip.getLocation_drop());

				logger.info("Fetching cabs details from database");
				cabSelected = cabDetailsDataService.getCabDetailsByCabNumber(trip.getCab_number()).get(0);

				logger.info("Update and persist ride details");
				rider.setWallet_balance_onhold(
						Formatters.formatDecimalRoundToTwo(rider.getWallet_balance_onhold() - trip.getFare()));
				if (action.equalsIgnoreCase(MessageConstants.Cancel)) {
					rider.setWallet_balance(
							Formatters.formatDecimalRoundToTwo(rider.getWallet_balance() + trip.getFare()));
				}
				riderDetailsDataService.saveRiderAccountDetails(rider);

				if (action.equalsIgnoreCase(MessageConstants.Complete)) {
					logger.info("Update and persist location details");
					updateLocationFootfallCount(locationsDetails, trip.getLocation_pickup(), trip.getLocation_drop());
					locationDetailsDataService.saveAllLocationDetails(locationsDetails);
				}

				logger.info("Update and persist cab details");
				cabSelected.setStatus(MessageConstants.Available);
				cabDetailsDataService.saveCabDetails(cabSelected);

				logger.info("Update and persist trip details");
				String successMessage = "";
				if (action.equalsIgnoreCase(MessageConstants.Cancel)) {
					trip.setStatus(MessageConstants.Cancelled);
					refund = trip.getFare();
					trip.setFare(0.0);
					successMessage = "Trip cancelled successfully";
				} else if (action.equalsIgnoreCase(MessageConstants.Complete)) {
					trip.setStatus(MessageConstants.Completed);
					successMessage = "Trip completed successfully";
				}
				tripDetailsDataService.saveTripDetails(trip);

				return new TripDetailsResponse(bookingRequest.getUserName(), cabSelected.getCab_name(),
						trip.getCab_number(), cabSelected.getDriver_name(), trip.getLocation_pickup(),
						trip.getLocation_drop(),
						calculateDistanceBetweenLocations(locationsDetails, trip.getLocation_pickup(),
								trip.getLocation_drop()),
						cabSelected.getRate(), trip.getFare(), rider.getWallet_balance(),
						rider.getWallet_balance_onhold(), refund, trip.getStatus(), bookingRequest.getBookingId(),
						successMessage);

			} else {
				return new TripDetailsResponse(rider.getUser_name(), null, null, null, null, null, 0.0, 0.0, 0.0, 0.0,
						0.0, 0.0, null, null, MessageConstants.InvalidPin);
			}
		} catch (RiderNotFoundException riderException) {
			logger.error(MessageConstants.RiderNotFound);
			return new TripDetailsResponse(trip.getUser_name(), null, null, null, null, null, 0.0, 0.0, 0.0, 0.0, 0.0,
					0.0, null, null, MessageConstants.RiderNotFound);
		} catch (TripDetailsNotFoundException tripException) {
			logger.error(MessageConstants.TripDetailsNotFound);
			return new TripDetailsResponse(null, null, null, null, null, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null,
					bookingRequest.getBookingId(), MessageConstants.TripDetailsNotFound);
		} catch (RiderMismatchFoundException riderMismatchException) {
			logger.error(MessageConstants.RiderMismatchFound);
			return new TripDetailsResponse(bookingRequest.getUserName(), null, null, null, null, null, 0.0, 0.0, 0.0,
					0.0, 0.0, 0.0, null, bookingRequest.getBookingId(), MessageConstants.RiderMismatchFound);
		}
	}

	public double calculateDistanceBetweenLocations(List<Locations> locationDetails, String pickup, String drop) {
		double pickupDistance = 0;
		double dropDistance = 0;
		for (Locations location : locationDetails) {
			if (pickup.equalsIgnoreCase(location.getLocation_name())) {
				pickupDistance = location.getDistance();
			}

			if (drop.equalsIgnoreCase(location.getLocation_name())) {
				dropDistance = location.getDistance();
			}
		}
		return Formatters.formatDecimalRoundToTwo(
				pickupDistance > dropDistance ? (pickupDistance - dropDistance) : (dropDistance - pickupDistance));
	}

	public void updateLocationFootfallCount(List<Locations> locationDetails, String pickup, String drop) {
		for (Locations location : locationDetails) {
			if (pickup.equalsIgnoreCase(location.getLocation_name())) {
				location.setPickup_count(location.getPickup_count() + 1);
			}

			if (drop.equalsIgnoreCase(location.getLocation_name())) {
				location.setDrop_count(location.getDrop_count() + 1);
			}
		}
	}
}
