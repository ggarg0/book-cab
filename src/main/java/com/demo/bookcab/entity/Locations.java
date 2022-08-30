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
@Table(name = "LOCATION_DETAILS")
@AllArgsConstructor
@NoArgsConstructor
public class Locations {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String location_name;
	private Integer location_sequence;
	private Integer pickup_count;
	private Integer drop_count;
	private Long tariff;

}
