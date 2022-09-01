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

package com.demo.bookcab.service.business.cabdetails;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.bookcab.constant.MessageConstants;
import com.demo.bookcab.data.service.CabDetailsDataService;
import com.demo.bookcab.entity.Cabs;
import com.demo.bookcab.exceptions.CabDetailsNotFoundException;

@SpringBootTest
public class CabDetailsServiceTest {

	@Mock
	private CabDetailsDataService cabDetailsDataService;
	
	@InjectMocks
	private CabDetailsServiceImpl cabDetailsServiceImpl;

	@BeforeEach
	public void setUp() {
		when(cabDetailsDataService.getAllCabDetails()).then(new Answer<List<Cabs>>() {
			@Override
			public List<Cabs> answer(InvocationOnMock invocationOnMock) throws Throwable {
				List<Cabs> cabs = new ArrayList<>();
				cabs.add(new Cabs(1L, "Swift", "CAR1", "ARUN", 11.0, "Available"));
				cabs.add(new Cabs(2L, "Swift", "CAR2", "AJAY", 10.5, "Booked"));
				cabs.add(new Cabs(3L, "Alto", "CAR3", "AMAR", 10.0, "Booked"));
				cabs.add(new Cabs(4L, "Alto", "CAR4", "ABHI", 10.0, "Available"));
				return cabs;
			}
		});

		when(cabDetailsDataService.getAvailableCabDetailsByCabName("Alto")).then(new Answer<List<Cabs>>() {
			@Override
			public List<Cabs> answer(InvocationOnMock invocationOnMock) throws Throwable {
				List<Cabs> cabs = new ArrayList<>();
				cabs.add(new Cabs(4L, "Alto", "CAR4", "ABHI", 10.0, "Available"));
				return cabs;
			}
		});
		
		when(cabDetailsDataService.getAvailableCabDetailsByCabName("unknown")).then(new Answer<List<Cabs>>() {
			@Override
			public List<Cabs> answer(InvocationOnMock invocationOnMock) throws Throwable {
				throw new CabDetailsNotFoundException(MessageConstants.CabDetailsNotFound);
			}
		});
		
		when(cabDetailsDataService.getCabDetailsByCabName("Swift")).then(new Answer<List<Cabs>>() {
			@Override
			public List<Cabs> answer(InvocationOnMock invocationOnMock) throws Throwable {
				List<Cabs> cabs = new ArrayList<>();
				cabs.add(new Cabs(1L, "Swift", "CAR1", "ARUN", 11.0, "Available"));
				cabs.add(new Cabs(2L, "Swift", "CAR2", "AJAY", 10.5, "Booked"));
				return cabs;
			}
		});
		
		when(cabDetailsDataService.getCabDetailsByCabName("unknown")).then(new Answer<List<Cabs>>() {
			@Override
			public List<Cabs> answer(InvocationOnMock invocationOnMock) throws Throwable {
				throw new CabDetailsNotFoundException(MessageConstants.CabDetailsNotFound);
			}
		});

		when(cabDetailsDataService.getCabDetailsByCabNumber("CAR2")).then(new Answer<List<Cabs>>() {
			@Override
			public List<Cabs> answer(InvocationOnMock invocationOnMock) throws Throwable {
				List<Cabs> cabs = new ArrayList<>();
				cabs.add(new Cabs(2L, "Swift", "CAR2", "AJAY", 10.5, "Booked"));
				return cabs;
			}
		});

		when(cabDetailsDataService.getCabDetailsByCabNumber("unknown")).then(new Answer<List<Cabs>>() {
			@Override
			public List<Cabs> answer(InvocationOnMock invocationOnMock) throws Throwable {
				throw new CabDetailsNotFoundException(MessageConstants.CabDetailsNotFound);
			}
		});
		

	}
/*
	@Test
	public void testMockData() {
		List<MetroCard> listOfAccounts = this.cardDetailsDataService.getAllCardDetails();
		Assertions.assertEquals(4, listOfAccounts.size());
	}

	@Test
	public void testSuccessBalanceinquiry() {
		BalanceInquiry be = new BalanceInquiry("11111", "1234");
		BalanceInquiryResponse ber = this.cardDetailsServiceImpl.getCardBalanceForUser(be);
		Assertions.assertEquals(800, ber.getBalance());
	}

	@Test
	public void testSuccessBalanceinquiryUser() {
		BalanceInquiry be = new BalanceInquiry("22222", "4321");
		BalanceInquiryResponse ber = this.cardDetailsServiceImpl.getCardBalanceForUser(be);
		Assertions.assertEquals(500, ber.getBalance());
	}

	@Test
	public void testUnSuccessBalanceinquiryUser() {
		BalanceInquiry be = new BalanceInquiry("22222", "4321");
		BalanceInquiryResponse ber = this.cardDetailsServiceImpl.getCardBalanceForUser(be);
		Assertions.assertNotEquals(800, ber.getBalance());
	}
	
	@Test
	public void testIncorrectPinBalanceinquiry() {
		BalanceInquiry be = new BalanceInquiry("11111", "1232");
		BalanceInquiryResponse ber = this.cardDetailsServiceImpl.getCardBalanceForUser(be);
		Assertions.assertTrue(MessageConstants.InvalidPin.equals(ber.getMessage()));
	}

	@Test
	public void testInvalidCardHolderinquiry() {
		BalanceInquiry be = new BalanceInquiry("unknown", "1234");
		BalanceInquiryResponse ber = this.cardDetailsServiceImpl.getCardBalanceForUser(be);
		Assertions.assertTrue(MessageConstants.CardNumberNotFound.equals(ber.getMessage()));
	}

	@Test
	public void testNullBalanceinquiry() {
		BalanceInquiryResponse ber = this.cardDetailsServiceImpl.getCardBalanceForUser(null);
		System.out.println(ber);
		Assertions.assertTrue(MessageConstants.InvalidBalanceInquiry.equalsIgnoreCase(ber.getMessage()));
	}
*/
}
