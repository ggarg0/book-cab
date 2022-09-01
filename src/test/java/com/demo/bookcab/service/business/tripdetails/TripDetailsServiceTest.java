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

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.bookcab.security.AuthenticationService;

@SpringBootTest
public class TripDetailsServiceTest {



	@Autowired
	private AuthenticationService authenticationService;

	@BeforeEach
	public void setUp() {

		
	}
/*
	@Test
	public void testJourneyDetailsForUserSuccess() {
		JourneyDetailsRequest req = new JourneyDetailsRequest("11111", "1234", "A1", "A8", "");
		JourneyDetailsResponse resp = this.journeyDetailsServiceImpl.getJourneyDetailsForUser(req);
		Assertions.assertTrue(resp.getMessage().contains(MessageConstants.Success));
	}

	@Test
	public void testNullJourneyInquiry() {
		JourneyDetailsResponse resp = this.journeyDetailsServiceImpl.getJourneyDetailsForUser(null);
		Assertions.assertTrue(MessageConstants.InvalidJourneyInquiry.equalsIgnoreCase(resp.getMessage()));
	}

	@Test
	public void testInvalidCardHolderInquiry() {
		JourneyDetailsRequest req = new JourneyDetailsRequest("unknown", "1234", "A1", "A8", "");
		JourneyDetailsResponse resp = this.journeyDetailsServiceImpl.getJourneyDetailsForUser(req);
		Assertions.assertTrue(MessageConstants.CardNumberNotFound.equals(resp.getMessage()));
	}

	@Test
	public void testIncorrectPinBalanceInquiry() {
		JourneyDetailsRequest req = new JourneyDetailsRequest("11111", "1232", "A1", "A8", "");
		JourneyDetailsResponse resp = this.journeyDetailsServiceImpl.getJourneyDetailsForUser(req);
		Assertions.assertTrue(MessageConstants.InvalidPin.equals(resp.getMessage()));
	}

	@Test
	public void testInsufficientAmountInAccountMessage() {
		JourneyDetailsRequest req = new JourneyDetailsRequest("11111", "1234", "A1", "A9", "");
		JourneyDetailsResponse resp = this.journeyDetailsServiceImpl.getJourneyDetailsForUser(req);
		Assertions.assertTrue(resp.getMessage().equals(MessageConstants.InsufficientAmountInAccountMessage));
	}

	@Test
	public void testInvalidStationInquiry() {
		JourneyDetailsRequest req = new JourneyDetailsRequest("11111", "1234", "B1", "A8", "");
		JourneyDetailsResponse resp = this.journeyDetailsServiceImpl.getJourneyDetailsForUser(req);
		Assertions.assertTrue(MessageConstants.StationDetailsNotFound.equals(resp.getMessage()));
	}
	*/
}
