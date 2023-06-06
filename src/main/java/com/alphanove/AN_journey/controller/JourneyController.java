package com.alphanove.AN_journey.controller;


import com.alphanove.AN_journey.dto.JourneyDto;
import com.alphanove.AN_journey.repository.JourneyRepository;
import com.alphanove.AN_journey.service.JourneyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journeyApi")
@CrossOrigin
public class JourneyController {

    private JourneyService journeyService;
    private final JourneyRepository journeyRepository;

    public JourneyController(JourneyService journeyService, JourneyRepository journeyRepository) {
        this.journeyService = journeyService;
        this.journeyRepository = journeyRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createJourney")
    public String createJourney(@RequestBody JourneyDto journeyDto) {
        return journeyService.createJourney(journeyDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/journey")
    public List<JourneyDto> filterJourneys(@RequestParam(value = "journeyFrom", required = false) String journeyFrom ,
                                           @RequestParam(value ="journeyTo", required = false) String journeyTo,
                                           @RequestParam(value ="journeyDate", required = false) String journeyDate) {

        if (journeyFrom != null && journeyTo != null) {
            return journeyService.filterJourneyByDestination(journeyFrom, journeyTo);
        } else if (journeyDate != null) {
            return journeyService.fillterJourneyByDate(journeyDate);
        } else {
            return journeyService.getAllJourneys();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateJourney")
    public String updateJourney( @RequestParam(value="id") Long id, @RequestBody JourneyDto journeyDto){
        return journeyService.updateJourney(id,journeyDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteJourney")
    public String deleteJourney(@RequestParam(value = "id") Long id){
        return journeyService.deleteJourney(id);
    }
}
