package com.alphanove.AN_journey.service;

import com.alphanove.AN_journey.dto.JourneyDto;
import java.util.List;

public interface JourneyService {

    List<JourneyDto> getAllJourneys();

    String createJourney(JourneyDto journeyDto);

    /*List<JourneyDto> filterJourneys(String journeyFrom,
                                    String journeyTo,
                                    String journeyDate,
                                    String journeyTime,
                                    String journeyAirline);*/

    List<JourneyDto> filterJourneyByDestination(String journeyFrom, String journeyTo);

    List<JourneyDto> fillterJourneyByDate(String journeyDate);

    String updateJourney(Long id,JourneyDto journeyDto);

    String deleteJourney(Long id);

    //List<JourneyDto> testingRepoCap(String journeyFrom, String journeyTo);
}
