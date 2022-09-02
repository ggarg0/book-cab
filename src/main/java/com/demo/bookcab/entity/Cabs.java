package com.demo.bookcab.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "CAB_DETAILS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cabs {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String cab_name;
	private String cab_number;
	private String driver_name;
	private Double rate;
	private String status;

}
