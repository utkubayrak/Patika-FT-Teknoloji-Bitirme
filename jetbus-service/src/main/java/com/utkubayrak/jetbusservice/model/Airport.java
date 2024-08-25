package com.utkubayrak.jetbusservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "airport_name", nullable = false, unique = true)
    private String airportName;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;

}
