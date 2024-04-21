package com.kp.hotel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotel {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME", length = 20)
	private String name;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "ABOUT")
	private String about;

}
