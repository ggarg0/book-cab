/*
 * *
 *  * The MIT License (MIT)
 *  * <p>
 *  * Copyright (c) 2022
 *  * <p>
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  * <p>
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  * <p>
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package com.demo.bookcab.service.business.riderdetails;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
import com.demo.bookcab.entity.Rider;
import com.demo.bookcab.exceptions.RiderNotFoundException;
import com.demo.bookcab.security.AuthenticationService;

@SpringBootTest
public class RiderDetailsServiceTest {

	@Mock
	private RiderDetailsDataService riderDetailsDataService;

	@InjectMocks
	private RiderDetailsServiceImpl riderDetailsServiceImpl;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private CabDetailsDataService cabDetailsDataService;

	@Autowired
	private TripDetailsDataService tripDetailsDataService;

	@Autowired
	private LocationDetailsDataService locationDetailsDataService;

	@BeforeEach
	public void setUp() {
		when(riderDetailsDataService.getAllRiderAccountDetails()).then(new Answer<List<Rider>>() {
			@Override
			public List<Rider> answer(InvocationOnMock invocationOnMock) throws Throwable {
				List<Rider> rider = new ArrayList<>();
				rider.add(new Rider(1l, "Adam", "Sandler", "1234567890", "adam@hollywood.com",
						"$2a$10$AVI6e8rkyn.9Y93Yrbu9/.YM8plIBGZ33PUQy57U1fXw34uXT9mNC", 800.0, 0.0));
				rider.add(new Rider(2l, "Brad", "Pitt", "2345678901", "brad@hollywood.com",
						"$2a$10$BkDnsFV/DA1.fIZ0l6BkEOcWxYMc3313nN2uD0YYNLKkwx6cgY/y.", 200.0, 0.0));
				rider.add(new Rider(3l, "Mark", "Waugh", "3456789012", "mark@hollywood.com",
						"$2a$10$AVI6e8rkyn.9Y93Yrbu9/.YM8plIBGZ33PUQy57U1fXw34uXT9mNC", 800.0, 0.0));
				return rider;
			}
		});

		when(riderDetailsDataService.getRiderAccountDetails("1234567890")).then(new Answer<Rider>() {
			@Override
			public Rider answer(InvocationOnMock invocationOnMock) throws Throwable {
				Rider rider = new Rider(1l, "Adam", "Sandler", "1234567890", "adam@hollywood.com",
						"$2a$10$AVI6e8rkyn.9Y93Yrbu9/.YM8plIBGZ33PUQy57U1fXw34uXT9mNC", 800.0, 0.0);
				return rider;
			}
		});

		when(riderDetailsDataService.getRiderAccountDetails("2345678901")).then(new Answer<Rider>() {
			@Override
			public Rider answer(InvocationOnMock invocationOnMock) throws Throwable {
				Rider rider = new Rider(2l, "Brad", "Pitt", "2345678901", "brad@hollywood.com",
						"$2a$10$BkDnsFV/DA1.fIZ0l6BkEOcWxYMc3313nN2uD0YYNLKkwx6cgY/y.", 200.0, 0.0);
				return rider;
			}
		});

		when(riderDetailsDataService.getRiderAccountDetails("3456789012")).then(new Answer<Rider>() {
			@Override
			public Rider answer(InvocationOnMock invocationOnMock) throws Throwable {
				Rider rider = new Rider(3l, "Mark", "Waugh", "3456789012", "mark@hollywood.com",
						"$2a$10$AVI6e8rkyn.9Y93Yrbu9/.YM8plIBGZ33PUQy57U1fXw34uXT9mNC", 800.0, 0.0);
				return rider;
			}
		});

		when(riderDetailsDataService.getRiderAccountDetails("unknown")).then(new Answer<Rider>() {
			@Override
			public Rider answer(InvocationOnMock invocationOnMock) throws Throwable {
				throw new RiderNotFoundException(MessageConstants.RiderNotFound);
			}
		});

		this.riderDetailsServiceImpl.setAuthenticationService(authenticationService);
		this.riderDetailsServiceImpl.setCabDetailsDataService(cabDetailsDataService);
		this.riderDetailsServiceImpl.setLocationDetailsDataService(locationDetailsDataService);
		this.riderDetailsServiceImpl.setTripDetailsDataService(tripDetailsDataService);

	}

	@Test
	public void testGetAllRiderAccountDetailsSize() {
		List<Rider> rider = this.riderDetailsServiceImpl.getAllRiderAccountDetails();
		Assertions.assertEquals(3, rider.size());
	}

	@Test
	public void testGetAllRiderAccountDetails() {
		List<Rider> rider = this.riderDetailsServiceImpl.getAllRiderAccountDetails();
		Assertions.assertTrue(rider.get(0).getUser_name().equalsIgnoreCase("1234567890"));
	}

	@Test
	public void testGetBalanceDetailsForRider() {
		List<Rider> rider = this.riderDetailsServiceImpl.getAllRiderAccountDetails();
		Assertions.assertTrue(rider.get(0).getUser_name().equalsIgnoreCase("1234567890"));
	}

	@Test
	public void testSuccessBalanceinquiryForRider() {
		RiderInquiry ri = new RiderInquiry("1234567890", "1234");
		RiderInquiryResponse rir = this.riderDetailsServiceImpl.getBalanceDetailsForRider(ri);
		Assertions.assertEquals(800, rir.getWalletBalance());
	}

	@Test
	public void testBalanceinquiryForNullRider() {
		RiderInquiry ri = null;
		RiderInquiryResponse rir = this.riderDetailsServiceImpl.getBalanceDetailsForRider(ri);
		Assertions.assertTrue(MessageConstants.InvalidBalanceInquiry.equals(rir.getMessage()));
	}

	@Test
	public void testBalanceinquiryForInvalidRider() {
		RiderInquiry ri = new RiderInquiry("unknown", "1234");
		RiderInquiryResponse rir = this.riderDetailsServiceImpl.getBalanceDetailsForRider(ri);
		Assertions.assertTrue(MessageConstants.RiderNotFound.equals(rir.getMessage()));
	}

	@Test
	public void testBalanceinquiryForRiderWithInvalidPin() {
		RiderInquiry ri = new RiderInquiry("1234567890", "1236");
		RiderInquiryResponse rir = this.riderDetailsServiceImpl.getBalanceDetailsForRider(ri);
		Assertions.assertTrue(MessageConstants.InvalidPin.equals(rir.getMessage()));
	}

	@Test
	public void testSuccessBookCabForRider() {
		TripRequest req = new TripRequest("1234567890", "1234", "Alto", "A1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.Booked.equals(resp.getStatus()));

		BookingIdRequest booking = new BookingIdRequest("1234567890", "1234", resp.getBookingId());
		TripDetailsResponse complete = this.riderDetailsServiceImpl.RiderTripTransaction(booking,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.Completed.equals(complete.getStatus()));
	}

	@Test
	public void testSuccessBookCabForRiderForDifferentLocations() {
		TripRequest req = new TripRequest("1234567890", "1234", "Alto", "A6", "A1");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.Booked.equals(resp.getStatus()));

		BookingIdRequest booking = new BookingIdRequest("1234567890", "1234", resp.getBookingId());
		TripDetailsResponse complete = this.riderDetailsServiceImpl.RiderTripTransaction(booking,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.Completed.equals(complete.getStatus()));
	}

	@Test
	public void testInvalidPinBookCabForRider() {
		TripRequest req = new TripRequest("1234567890", "1236", "Alto", "A1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.InvalidPin.equals(resp.getMessage()));
	}

	@Test
	public void testInsufficientBalanceBookCabForRider() {
		TripRequest req = new TripRequest("2345678901", "4321", "Swift", "A1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.InsufficientBalance.equals(resp.getMessage()));
	}

	@Test
	public void testBookCabForNullRider() {
		TripRequest req = null;
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.InvalidBookingRequest.equals(resp.getMessage()));
	}

	@Test
	public void testBookCabForInvalidRider() {
		TripRequest req = new TripRequest("2345678908", "4321", "Swift", "A1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.RiderNotFound.equals(resp.getMessage()));
	}

	@Test
	public void testBookCabForInvalidCab() {
		TripRequest req = new TripRequest("2345678901", "4321", "Ertiga", "A1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.CabDetailsNotFound.equals(resp.getMessage()));
	}

	@Test
	public void testBookCabForInvalidLocations() {
		TripRequest req = new TripRequest("2345678901", "4321", "Swift", "B1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.LocationDetailsNotFound.equals(resp.getMessage()));
	}

	@Test
	public void testSuccessCompleteCabForRider() {
		TripRequest req = new TripRequest("1234567890", "1234", "Swift", "A1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.Booked.equals(resp.getStatus()));

		BookingIdRequest booking = new BookingIdRequest("1234567890", "1234", resp.getBookingId());
		TripDetailsResponse complete = this.riderDetailsServiceImpl.RiderTripTransaction(booking,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.Completed.equals(complete.getStatus()));
	}

	@Test
	public void testCompleteCabForNullRider() {
		TripRequest req = new TripRequest("3456789012", "1234", "Dzire", "A1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.Booked.equals(resp.getStatus()));

		BookingIdRequest booking = null;
		TripDetailsResponse complete = this.riderDetailsServiceImpl.RiderTripTransaction(booking,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.InvalidBookingRequest.equals(complete.getMessage()));

		BookingIdRequest bookingComplete = new BookingIdRequest("3456789012", "1234", resp.getBookingId());
		TripDetailsResponse completeResp = this.riderDetailsServiceImpl.RiderTripTransaction(bookingComplete,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.Completed.equals(completeResp.getStatus()));
	}

	@Test
	public void testCompleteCabForRiderInvalidPin() {
		TripRequest req = new TripRequest("3456789012", "1234", "Swift", "A1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.Booked.equals(resp.getStatus()));

		BookingIdRequest booking = new BookingIdRequest("3456789012", "1236", resp.getBookingId());
		TripDetailsResponse complete = this.riderDetailsServiceImpl.RiderTripTransaction(booking,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.InvalidPin.equals(complete.getMessage()));

		BookingIdRequest bookingComplete = new BookingIdRequest("3456789012", "1234", resp.getBookingId());
		TripDetailsResponse completeResp = this.riderDetailsServiceImpl.RiderTripTransaction(bookingComplete,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.Completed.equals(completeResp.getStatus()));
	}

	@Test
	public void testSuccessCancelCabForRider() {
		TripRequest req = new TripRequest("1234567890", "1234", "Alto", "A1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.Booked.equals(resp.getStatus()));

		BookingIdRequest booking = new BookingIdRequest("1234567890", "1234", resp.getBookingId());
		TripDetailsResponse cancel = this.riderDetailsServiceImpl.RiderTripTransaction(booking,
				MessageConstants.Cancel);
		Assertions.assertTrue(MessageConstants.Cancelled.equals(cancel.getStatus()));
	}

	@Test
	public void testCompleteCabForRiderMismatchRider() {
		TripRequest req = new TripRequest("3456789012", "1234", "Alto", "A1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.Booked.equals(resp.getStatus()));

		BookingIdRequest booking = new BookingIdRequest("2345678901", "4321", resp.getBookingId());
		TripDetailsResponse complete = this.riderDetailsServiceImpl.RiderTripTransaction(booking,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.RiderMismatchFound.equals(complete.getMessage()));
		
		BookingIdRequest bookingComplete = new BookingIdRequest("3456789012", "1234", resp.getBookingId());
		TripDetailsResponse completeResp = this.riderDetailsServiceImpl.RiderTripTransaction(bookingComplete,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.Completed.equals(completeResp.getStatus()));
	}
	
	@Test
	public void testCompleteCabForRiderInvalidRider() {
		TripRequest req = new TripRequest("3456789012", "1234", "Alto", "A1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.Booked.equals(resp.getStatus()));

		BookingIdRequest booking = new BookingIdRequest("2345678923", "4321", resp.getBookingId());
		TripDetailsResponse complete = this.riderDetailsServiceImpl.RiderTripTransaction(booking,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.RiderNotFound.equals(complete.getMessage()));
		
		BookingIdRequest bookingComplete = new BookingIdRequest("3456789012", "1234", resp.getBookingId());
		TripDetailsResponse completeResp = this.riderDetailsServiceImpl.RiderTripTransaction(bookingComplete,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.Completed.equals(completeResp.getStatus()));
	}
	
	@Test
	public void testCompleteCabForRiderInvalidTrip() {
		TripRequest req = new TripRequest("3456789012", "1234", "Alto", "A1", "A6");
		TripDetailsResponse resp = this.riderDetailsServiceImpl.BookCabForRider(req);
		Assertions.assertTrue(MessageConstants.Booked.equals(resp.getStatus()));

		BookingIdRequest booking = new BookingIdRequest("2345678923", "4321",  1235698774l);
		TripDetailsResponse complete = this.riderDetailsServiceImpl.RiderTripTransaction(booking,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.TripDetailsNotFound.equals(complete.getMessage()));
		
		BookingIdRequest bookingComplete = new BookingIdRequest("3456789012", "1234", resp.getBookingId());
		TripDetailsResponse completeResp = this.riderDetailsServiceImpl.RiderTripTransaction(bookingComplete,
				MessageConstants.Complete);
		Assertions.assertTrue(MessageConstants.Completed.equals(completeResp.getStatus()));
	}
}
