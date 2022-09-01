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

package com.demo.bookcab.service.business.locationdetails;

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

import com.demo.bookcab.data.service.LocationDetailsDataService;
import com.demo.bookcab.dto.LocationFootfallResponse;
import com.demo.bookcab.entity.Locations;

@SpringBootTest
public class LocationDetailsServiceTest {

	@Mock
	private LocationDetailsDataService locationDetailsDataService;

	@InjectMocks
	private LocationDetailsServiceImpl locationDetailsServiceImpl;

	@BeforeEach
	public void setUp() {

		when(locationDetailsDataService.getAllLocationFootfall()).then(new Answer<List<LocationFootfallResponse>>() {
			@Override
			public List<LocationFootfallResponse> answer(InvocationOnMock invocationOnMock) throws Throwable {
				List<LocationFootfallResponse> response = new ArrayList<>();
				response.add(new LocationFootfallResponse("A1", 2, 3, ""));
				response.add(new LocationFootfallResponse("A2", 1, 1, ""));
				response.add(new LocationFootfallResponse("A3", 4, 2, ""));
				response.add(new LocationFootfallResponse("A4", 3, 6, ""));
				response.add(new LocationFootfallResponse("A5", 5, 4, ""));
				response.add(new LocationFootfallResponse("A6", 3, 1, ""));
				return response;
			}
		});

		when(locationDetailsDataService.getLocationDetails("A1", "A6")).then(new Answer<List<Locations>>() {
			@Override
			public List<Locations> answer(InvocationOnMock invocationOnMock) throws Throwable {
				List<Locations> locations = new ArrayList<>();
				locations.add(new Locations(1L, "A1", 1, 0, 0, 0.0));
				locations.add(new Locations(2L, "A6", 8, 0, 0, 37.5));

				return locations;
			}
		});
	}

	@Test
	public void testValidLocationsInquiry() {
		List<Locations> locations = this.locationDetailsServiceImpl.getLocationDetails("A1", "A6");
		Assertions.assertEquals(2, locations.size());
	}

	@Test
	public void testgetAllLocationsFootfall() {
		List<LocationFootfallResponse> response = this.locationDetailsServiceImpl.getAllLocationFootfall();
		Assertions.assertEquals(2, response.get(0).getPickupCount());
		Assertions.assertEquals(3, response.get(0).getDropCount());
		Assertions.assertEquals(6, response.size());
	}

}
