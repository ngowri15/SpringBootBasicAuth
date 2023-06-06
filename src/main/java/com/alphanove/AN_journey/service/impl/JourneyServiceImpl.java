package com.alphanove.AN_journey.service.impl;


import com.alphanove.AN_journey.dto.JourneyDto;
import com.alphanove.AN_journey.entity.Journey;
import com.alphanove.AN_journey.exception.ResourceNotFoundException;
import com.alphanove.AN_journey.repository.JourneyRepository;
import com.alphanove.AN_journey.service.JourneyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JourneyServiceImpl implements JourneyService {

    private JourneyRepository journeyRepository;

    public JourneyServiceImpl(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    @Override
    public List<JourneyDto> getAllJourneys() {
        List<Journey> journeys = journeyRepository.findAll();
        return journeys.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<JourneyDto> filterJourneyByDestination(String journeyFrom, String journeyTo) {


        List<Journey> fromJourneys = new ArrayList<>();
        List<Journey> ToJourneys = new ArrayList<>();
        fromJourneys = journeyRepository.findByJourneyFrom(journeyFrom);
        ToJourneys=journeyRepository.findByJourneyTo(journeyTo);

        //fromJourneyswhereTojourneysischecked

        List<Journey> fromJourneyswhereTojourneysischecked = new ArrayList<>();
        List<Journey> toJourneyswhereFromjourneysischecked = new ArrayList<>();


        for(Journey j: fromJourneys){
            if(j.getJourneyTo().equals(journeyTo)){
                fromJourneyswhereTojourneysischecked.add(j);
            }
        }
        for(Journey j: ToJourneys){
            if(j.getJourneyFrom().equals(journeyFrom)){
                toJourneyswhereFromjourneysischecked.add(j);
            }
        }


        List<Journey> mergedList = new ArrayList<>(fromJourneyswhereTojourneysischecked);
        mergedList.addAll(toJourneyswhereFromjourneysischecked);

        Set<Journey> set = new LinkedHashSet<>(mergedList);
        List<Journey> resultList = new ArrayList<>(set);


        return resultList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<JourneyDto> fillterJourneyByDate(String journeyDate) {
        List<Journey> journeys = new ArrayList<>();
        journeys=journeyRepository.findByJourneyDate(journeyDate);
        return journeys.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

   @Override
    public String updateJourney(Long id, JourneyDto journeyDto) {

        Journey existingJourney= journeyRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Journey","id",id));

        Journey updatedJourney = new Journey();

        updatedJourney.setId(existingJourney.getId());
        updatedJourney.setJourneyFrom(journeyDto.getJourneyFrom());
        updatedJourney.setJourneyTo(journeyDto.getJourneyTo());
        updatedJourney.setJourneyDate(journeyDto.getJourneyDate());
        updatedJourney.setJourneyTime(journeyDto.getJourneyTime());
        updatedJourney.setJourneyAirline(journeyDto.getJourneyAirline());

        if(existingJourney.equals(updatedJourney)) {
            return "Same as existing journey: please give different details to update the journey";
        }
            else
                journeyRepository.save(updatedJourney);

        return "Journey Updated";
    }

    @Override
    public String deleteJourney(Long id) {
        Journey existingJourney = journeyRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Journey","id",id));
        journeyRepository.deleteById(id);
        return "Journey Deleted";
    }

   /* @Override
    public List<JourneyDto> testingRepoCap(String journeyFrom, String journeyTo) {
        return null;
    }
*/
    @Override
    public String createJourney(JourneyDto journeyDto) {

            List<Journey> journeys = new ArrayList<>();
            Journey journey = mapToEntity(journeyDto);
            journeys = journeyRepository.findByUsername(journey.getUsername());

            if (containsList(journeys,journey)) {
                return "Journey cannot be created as it already exists";
                } else {
                try {
                    journeyRepository.save(mapToEntity(journeyDto));
                } catch (DataIntegrityViolationException ex) {
                }
                return "Journey is created";
            }
    }

    private JourneyDto mapToDTO(Journey journey) {
        JourneyDto journeyDto = new JourneyDto();
        journeyDto.setId(journey.getId());
        journeyDto.setJourneyFrom(journey.getJourneyFrom());
        journeyDto.setJourneyTo(journey.getJourneyTo());
        journeyDto.setJourneyDate(journey.getJourneyDate());
        journeyDto.setJourneyTime(journey.getJourneyTime());
        journeyDto.setJourneyAirline(journey.getJourneyAirline());
        journeyDto.setUsername(journey.getUsername());
        return journeyDto;

    }

    //covert DTO into Entity
    private Journey mapToEntity(JourneyDto journeyDto) {
        Journey journey = new Journey();
        journey.setId(journeyDto.getId());
        journey.setJourneyFrom(journeyDto.getJourneyFrom());
        journey.setJourneyTo(journeyDto.getJourneyTo());
        journey.setJourneyDate(journeyDto.getJourneyDate());
        journey.setJourneyTime(journeyDto.getJourneyTime());
        journey.setJourneyAirline(journeyDto.getJourneyAirline());
        journey.setUsername(journeyDto.getUsername());
        return journey;
    }

    public boolean containsList(List<Journey> journeys,Journey journey){
        for(Journey j: journeys){
            if((j.getJourneyFrom().equals(journey.getJourneyFrom())&&
                    (j.getJourneyTo().equals(journey.getJourneyTo()))&&
                    (j.getJourneyDate().equals(journey.getJourneyDate())))&&
                    (j.getJourneyTime().equals(journey.getJourneyTime()))){
                return true;
            }
        }
        return false;
    }
}