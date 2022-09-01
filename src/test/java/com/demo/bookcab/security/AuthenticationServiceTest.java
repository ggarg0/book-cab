package com.demo.bookcab.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.bookcab.entity.Rider;

@SpringBootTest
public class AuthenticationServiceTest {

	@Autowired
	private AuthenticationService authenticationService;

	@Test
	public void validateSuccessAuthenticationTest() {
		Rider rider = new Rider();
		rider.setWallet_pin("$2a$10$HWOWQw3hTu.UkxPeZltlQu2YDVMmZF2dUrYvueLvwHLoOd8jgYfi2");
		Boolean isAccountAuthentic = this.authenticationService.authenticateRiderAccount(rider, "1234");
		Assertions.assertTrue(isAccountAuthentic);
	}

	@Test
	public void validateFailedAuthenticationTest() {
		Rider rider = new Rider();
		rider.setWallet_pin("$2a$10$HWOWQw3hTu.UkxPeZltlQu2YDVMmZF2dUrYvueLvwHLoOd8jgYfi2");
		Boolean isAccountAuthentic = this.authenticationService.authenticateRiderAccount(rider, "4321");
		Assertions.assertFalse(isAccountAuthentic);
	}
}
