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
@Table(name = "TRIP_DETAILS")
@AllArgsConstructor
@NoArgsConstructor
public class Trips {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String location_pickup;
	private String location_drop;
	private Double fare;
	private Double distance;
	private String user_name;
	private String cab_number;
	private long booking_id;
	private String status;
}
