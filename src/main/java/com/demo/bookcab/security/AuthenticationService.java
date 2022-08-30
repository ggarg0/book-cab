package com.demo.bookcab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.bookcab.entity.UserAccount;

@Service
public class AuthenticationService {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	public Boolean authenticateUserAccount(UserAccount account, String pinPlainText) {
		return this.passwordEncoder().matches(pinPlainText, account.getWallet_pin());
	}

}
