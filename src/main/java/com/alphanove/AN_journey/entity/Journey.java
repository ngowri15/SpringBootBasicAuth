package com.alphanove.AN_journey.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "journey_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"})
})
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "journeyFrom")
    private String journeyFrom;

    @Column(name = "journeyTo")
    private String journeyTo;

    @Column(name = "journeyDate")
    private String journeyDate;

    @Column(name = "journeyTime")
    private String journeyTime;

    @Column(name = "journeyAirline")
    private String journeyAirline;

    @Column(name = "username")
    private String username;


}
