package com.demo.bookcab.service.business.riderdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import com.demo.bookcab.dto.BalanceInquiry;
import com.demo.bookcab.dto.BalanceInquiryResponse;
import com.demo.bookcab.dto.BookingDetailsResponse;
import com.demo.bookcab.dto.BookingRequest;
import com.demo.bookcab.entity.Cabs;
import com.demo.bookcab.entity.Locations;
import com.demo.bookcab.entity.Rider;
import com.demo.bookcab.entity.Trips;
import com.demo.bookcab.exception.formatter.Formatters;
import com.demo.bookcab.exceptions.CabDetailsNotFoundException;
import com.demo.bookcab.exceptions.InsufficientFundsException;
import com.demo.bookcab.exceptions.LocationDetailsNotFoundException;
import com.demo.bookcab.exceptions.RiderNotFoundException;
import com.demo.bookcab.security.AuthenticationService;

import lombok.Data;

@Service
@Data
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
	 * @param balanceInquiry {@link BalanceInquiry} details for getting account
	 *                       information.
	 * @return
	 */
	public BalanceInquiryResponse getBalanceDetailsForRider(BalanceInquiry balanceInquiry) {
		if (Objects.isNull(balanceInquiry)) {
			return new BalanceInquiryResponse(null, null, null, null, 0.0, 0.0, MessageConstants.InvalidBalanceInquiry);
		}

		// Fetch details from DB.
		Rider rider = null;
		try {
			rider = riderDetailsDataService.getRiderAccountDetails(balanceInquiry.getUserName());

		} catch (RiderNotFoundException exp) {
			return new BalanceInquiryResponse(balanceInquiry.getUserName(), null, null, null, 0.0, 0.0,
					MessageConstants.RiderNotFound);
		}

		// Authenticate
		if (this.authenticationService.authenticateRiderAccount(rider, balanceInquiry.getWalletPin())) {
			// Respond Balance.
			return new BalanceInquiryResponse(rider.getUser_name(), rider.getFirst_name(), rider.getLast_name(),
					rider.getEmail(), rider.getWallet_balance(), rider.getWallet_balance_onhold(), "");

		} else {
			return new BalanceInquiryResponse(balanceInquiry.getUserName(), null, null, null, 0.0, 0.0,
					MessageConstants.InvalidPin);
		}

	}

	@Override
	public List<Rider> getAllRiderAccountDetails() {
		return riderDetailsDataService.getAllRiderAccountDetails();
	}

	@Override
	public void saveAllRiderAccountDetails(List<Rider> riderDetails) {
		riderDetailsDataService.saveAllRiderAccountDetails(riderDetails);
	}

	@Transactional
	@Override
	public synchronized BookingDetailsResponse BookCabForRider(BookingRequest bookingRequest) {
		if (Objects.isNull(bookingRequest)) {
			return new BookingDetailsResponse(null, null, null, null, null, null, 0.0, 0.0, 0.0, 0.0, 0.0, null, null,
					MessageConstants.InvalidBookingRequest);
		}

		List<Cabs> cabDetails = new ArrayList<Cabs>();
		List<Locations> locationsDetails = new ArrayList<Locations>();
		Cabs cabSelected = null;
		double fare = 0;
		double distance = 0;

		// Fetch details from DB.
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
				cabDetails = cabDetailsDataService.getCabDetailsByCabName(bookingRequest.getCabName());
				cabSelected = cabDetails.get(0);

				distance = calculateFareAndUpdateLocationFootfallCount(locationsDetails, bookingRequest.getPickUp(),
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
				tripDetailsDataService.saveRiderAccountDetails(new Trips(0l, bookingRequest.getPickUp(),
						bookingRequest.getDrop(), Formatters.formatDecimalRoundToTwo(fare), rider.getUser_name(),
						cabSelected.getCab_number(), bookingId, cabSelected.getStatus()));

				return new BookingDetailsResponse(bookingRequest.getUserName(), bookingRequest.getCabName(),
						cabSelected.getCab_number(), cabSelected.getDriver_name(), bookingRequest.getPickUp(),
						bookingRequest.getDrop(), distance, Formatters.formatDecimalRoundToTwo(cabSelected.getRate()),
						Formatters.formatDecimalRoundToTwo(fare),
						Formatters.formatDecimalRoundToTwo(rider.getWallet_balance()),
						Formatters.formatDecimalRoundToTwo(rider.getWallet_balance_onhold()), cabSelected.getStatus(),
						bookingId, "Cab booked successfully");

			} else {
				return new BookingDetailsResponse(bookingRequest.getUserName(), bookingRequest.getCabName(), null, null,
						bookingRequest.getPickUp(), bookingRequest.getDrop(), 0.0, 0.0, 0.0, 0.0, 0.0, null, null,
						MessageConstants.InvalidPin);
			}
		} catch (RiderNotFoundException riderException) {
			logger.error(MessageConstants.RiderNotFound);
			return new BookingDetailsResponse(bookingRequest.getUserName(), null, null, null, null, null, 0.0, 0.0, 0.0,
					0.0, 0.0, null, null, MessageConstants.RiderNotFound);
		} catch (LocationDetailsNotFoundException locationException) {
			logger.error(MessageConstants.LocationDetailsNotFound);
			return new BookingDetailsResponse(bookingRequest.getUserName(), null, null, null,
					bookingRequest.getPickUp(), bookingRequest.getDrop(), 0.0, 0.0, 0.0, 0.0, 0.0, null, null,
					MessageConstants.LocationDetailsNotFound);
		} catch (CabDetailsNotFoundException cabException) {
			logger.error(MessageConstants.CabDetailsNotFound);
			return new BookingDetailsResponse(bookingRequest.getUserName(), bookingRequest.getCabName(), null, null,
					bookingRequest.getPickUp(), bookingRequest.getDrop(), 0.0, 0.0, 0.0, 0.0, 0.0, null, null,
					MessageConstants.CabDetailsNotFound);
		} catch (InsufficientFundsException fundException) {
			logger.error(MessageConstants.InsufficientBalance);
			return new BookingDetailsResponse(bookingRequest.getUserName(), bookingRequest.getCabName(),
					cabSelected.getCab_number(), cabSelected.getDriver_name(), bookingRequest.getPickUp(),
					bookingRequest.getDrop(), distance, cabSelected.getRate(), fare,
					Formatters.formatDecimalRoundToTwo(rider.getWallet_balance()),
					Formatters.formatDecimalRoundToTwo(rider.getWallet_balance_onhold()), null, null,
					MessageConstants.InsufficientBalance);

		}

	}

	public double calculateFareAndUpdateLocationFootfallCount(List<Locations> locationDetails, String pickup,
			String drop) {
		double pickupFare = 0;
		double dropFare = 0;
		for (Locations location : locationDetails) {
			if (pickup.equalsIgnoreCase(location.getLocation_name())) {
				pickupFare = location.getDistance();
				location.setPickup_count(location.getPickup_count() + 1);
			}

			if (drop.equalsIgnoreCase(location.getLocation_name())) {
				dropFare = location.getDistance();
				location.setDrop_count(location.getDrop_count() + 1);
			}
		}
		return Formatters
				.formatDecimalRoundToTwo(pickupFare > dropFare ? (pickupFare - dropFare) : (dropFare - pickupFare));
	}
}
