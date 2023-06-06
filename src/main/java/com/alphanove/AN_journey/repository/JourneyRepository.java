package com.alphanove.AN_journey.repository;

import com.alphanove.AN_journey.entity.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyRepository extends JpaRepository<Journey,Long> {
    List<Journey> findByJourneyFrom(String journeyFrom);
    List<Journey> findByJourneyTo(String journeyTo);
    List<Journey> findByJourneyDate(String journeyDate);
    List<Journey> findByUsername(String username);
    List<Journey> findByJourneyFromAndJourneyTo(String journeyFrom, String journeyTo);
}
