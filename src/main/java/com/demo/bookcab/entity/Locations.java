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
@Table(name = "LOCATION_DETAILS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Locations {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String location_name;
	private Integer location_sequence;
	private Integer pickup_count;
	private Integer drop_count;
	private Double distance;

}
