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

package com.demo.bookcab.service.business.tripdetails;

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
import com.demo.bookcab.data.service.RiderDetailsDataService;
import com.demo.bookcab.data.service.TripDetailsDataService;
import com.demo.bookcab.dto.RiderInquiry;
import com.demo.bookcab.dto.TripDetailsResponse;
import com.demo.bookcab.entity.Trips;
import com.demo.bookcab.security.AuthenticationService;

@SpringBootTest
public class TripDetailsServiceTest {

	@Mock
	private TripDetailsDataService tripDetailsDataService;

	@Autowired
	private RiderDetailsDataService riderDetailsDataService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private CabDetailsDataService cabDetailsDataService;

	@InjectMocks
	private TripDetailsServiceImpl tripDetailsServiceImpl;

	@BeforeEach
	public void setUp() {
		when(tripDetailsDataService.getAllTripDetails()).then(new Answer<List<Trips>>() {
			@Override
			public List<Trips> answer(InvocationOnMock invocationOnMock) throws Throwable {
				List<Trips> trip = new ArrayList<>();
				trip.add(new Trips(1L, "A1", "A6", 100.0, 11.0, "1234567890", "CAR1", 112233445566l, "Completed"));
				trip.add(new Trips(1L, "A1", "A4", 75.0, 8.0, "1234567890", "CAR4", 112233499566l, "Completed"));
				trip.add(new Trips(2L, "A6", "A1", 100.0, 11.0, "2345678901", "CAR2", 112233775566l, "Completed"));
				return trip;
			}
		});

		when(tripDetailsDataService.getTripDetailsByUserName("1234567890")).then(new Answer<List<Trips>>() {
			@Override
			public List<Trips> answer(InvocationOnMock invocationOnMock) throws Throwable {
				List<Trips> trip = new ArrayList<>();
				trip.add(new Trips(1L, "A1", "A6", 100.0, 11.0, "1234567890", "CAR1", 112233445566l, "Completed"));
				trip.add(new Trips(1L, "A1", "A4", 75.0, 8.0, "1234567890", "CAR4", 112233499566l, "Completed"));
				return trip;
			}
		});

		when(tripDetailsDataService.getTripDetailsByUserName("2345678901")).then(new Answer<List<Trips>>() {
			@Override
			public List<Trips> answer(InvocationOnMock invocationOnMock) throws Throwable {
				List<Trips> trip = new ArrayList<>();
				return trip;
			}
		});

		this.tripDetailsServiceImpl.setAuthenticationService(authenticationService);
		this.tripDetailsServiceImpl.setCabDetailsDataService(cabDetailsDataService);
		this.tripDetailsServiceImpl.setRiderDetailsDataService(riderDetailsDataService);
	}

	@Test
	public void testSuccessGetAllTripDetails() {
		List<Trips> response = this.tripDetailsServiceImpl.getAllTripsDetails();
		Assertions.assertTrue(response.get(0).getCab_number().equalsIgnoreCase("CAR1"));
	}

	@Test
	public void testSuccessGetAllTripDetailsSize() {
		List<Trips> response = this.tripDetailsServiceImpl.getAllTripsDetails();
		Assertions.assertEquals(3, response.size());
	}

	@Test
	public void testSuccessGetTripDetailsByUserName() {
		RiderInquiry riderInquiry = new RiderInquiry("1234567890", "1234");
		List<TripDetailsResponse> response = this.tripDetailsServiceImpl.getTripDetailsByUserName(riderInquiry);
		Assertions.assertEquals(2, response.size());
	}

	@Test
	public void testInvalidPinGetTripDetailsByUserName() {
		RiderInquiry riderInquiry = new RiderInquiry("1234567890", "1236");
		List<TripDetailsResponse> response = this.tripDetailsServiceImpl.getTripDetailsByUserName(riderInquiry);
		Assertions.assertTrue(MessageConstants.InvalidPin.equals(response.get(0).getMessage()));
	}

	@Test
	public void testInvalidBookingRequestGetTripDetailsByUserName() {
		RiderInquiry riderInquiry = null;
		List<TripDetailsResponse> response = this.tripDetailsServiceImpl.getTripDetailsByUserName(riderInquiry);
		Assertions.assertTrue(MessageConstants.BookingRequestNotValid.equals(response.get(0).getMessage()));
	}

	@Test
	public void testRiderNotFoundGetTripDetailsByUserName() {
		RiderInquiry riderInquiry = new RiderInquiry("2345678908", "4321");
		List<TripDetailsResponse> response = this.tripDetailsServiceImpl.getTripDetailsByUserName(riderInquiry);
		Assertions.assertTrue(MessageConstants.RiderNotFound.equals(response.get(0).getMessage()));
	}

	@Test
	public void testTripsNotFoundGetTripDetailsByUserName() {
		RiderInquiry riderInquiry = new RiderInquiry("2345678901", "4321");
		List<TripDetailsResponse> response = this.tripDetailsServiceImpl.getTripDetailsByUserName(riderInquiry);
		Assertions.assertTrue(MessageConstants.TripDetailsNotFound.equals(response.get(0).getMessage()));
	}
	
	

}
