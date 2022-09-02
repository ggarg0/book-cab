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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.bookcab.constant.MessageConstants;
import com.demo.bookcab.data.service.CabDetailsDataService;
import com.demo.bookcab.dto.CabDetailsResponse;
import com.demo.bookcab.dto.CabNameRequest;
import com.demo.bookcab.dto.CabNumberRequest;
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
				cabs.add(new Cabs(2L, "Swift", "CAR2", "AJAY", 10.5, "Available"));
				cabs.add(new Cabs(3L, "Alto", "CAR3", "AMAR", 10.0, "Available"));
				cabs.add(new Cabs(4L, "Alto", "CAR4", "ABHI", 10.0, "Available"));
				return cabs;
			}
		});
		
		when(cabDetailsDataService.getCabDetailsByCabName("Swift")).then(new Answer<List<Cabs>>() {
			@Override
			public List<Cabs> answer(InvocationOnMock invocationOnMock) throws Throwable {
				List<Cabs> cabs = new ArrayList<>();
				cabs.add(new Cabs(1L, "Swift", "CAR1", "ARUN", 11.0, "Available"));
				cabs.add(new Cabs(2L, "Swift", "CAR2", "AJAY", 10.5, "Available"));
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
				cabs.add(new Cabs(2L, "Swift", "CAR2", "AJAY", 10.5, "Available"));
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

	@Test
	public void testSuccessGetAllCabDetails() {
		List<Cabs> response = this.cabDetailsServiceImpl.getAllCabDetails();
		Assertions.assertTrue(response.get(0).getCab_name().equalsIgnoreCase("Swift"));
	}

	@Test
	public void testFailureGetAllCabDetails() {
		List<Cabs> response = this.cabDetailsServiceImpl.getAllCabDetails();
		Assertions.assertFalse(response.get(0).getCab_name().equalsIgnoreCase("Alto"));
	}

	@Test
	public void testSizeGetAllCabDetails() {
		List<Cabs> response = this.cabDetailsServiceImpl.getAllCabDetails();
		Assertions.assertEquals(4, response.size());
	}

	@Test
	public void testSuccessGetAvailableCabDetailsByCabName() {

		CabNameRequest cab = new CabNameRequest("Swift");
		List<CabDetailsResponse> response = this.cabDetailsServiceImpl.getCabDetailsByCabName(cab);
		Assertions.assertTrue(response.get(0).getCabName().equalsIgnoreCase("Swift"));
		Assertions.assertEquals(2, response.size());
	}

	@Test
	public void testInvalidCabGetAvailableCabDetailsByCabName() {
		CabNameRequest cab = new CabNameRequest("unknown");
		List<CabDetailsResponse> response = this.cabDetailsServiceImpl.getCabDetailsByCabName(cab);
		Assertions.assertTrue(MessageConstants.CabDetailsNotFound.equals(response.get(0).getMessage()));
	}

	@Test
	public void testNullGetAvailableCabDetailsByCabName() {
		CabNameRequest cab = null;
		List<CabDetailsResponse> response = this.cabDetailsServiceImpl.getCabDetailsByCabName(cab);
		Assertions.assertTrue(MessageConstants.InvalidCarName.equals(response.get(0).getMessage()));
	}

	@Test
	public void testSuccessGetAvailableCabDetailsByCabNumber() {
		CabNumberRequest cab = new CabNumberRequest("CAR2");
		CabDetailsResponse response = this.cabDetailsServiceImpl.getCabDetailsByCabNumber(cab);
		Assertions.assertTrue(response.getCabNumber().equalsIgnoreCase("CAR2"));
	}

	@Test
	public void testInvalidCabGetAvailableCabDetailsByCabNumber() {
		CabNumberRequest cab = new CabNumberRequest("unknown");
		CabDetailsResponse response = this.cabDetailsServiceImpl.getCabDetailsByCabNumber(cab);
		Assertions.assertTrue(MessageConstants.CabDetailsNotFound.equals(response.getMessage()));
	}

	@Test
	public void testNullGetAvailableCabDetailsByCabNumber() {
		CabNumberRequest cab = null;
		CabDetailsResponse response = this.cabDetailsServiceImpl.getCabDetailsByCabNumber(cab);
		Assertions.assertTrue(MessageConstants.InvalidCarNumber.equals(response.getMessage()));
	}
}
