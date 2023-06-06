package com.alphanove.AN_journey.dto;

import lombok.Data;

@Data
public class JourneyDto {

    private Long id;
    private String journeyFrom;
    private String journeyTo;
    private String journeyDate;
    private String journeyTime;
    private String journeyAirline;
    private String username;


}
