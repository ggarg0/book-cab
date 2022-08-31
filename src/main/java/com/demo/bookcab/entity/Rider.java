package com.demo.bookcab.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "RIDER_ACCOUNT")
@AllArgsConstructor
@NoArgsConstructor
public class Rider {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String first_name;
	private String last_name;
	private String user_name;
	private String email;
	private String wallet_pin;
	private Double wallet_balance;
	private Double wallet_balance_onhold;

}
