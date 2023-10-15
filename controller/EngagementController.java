package com.nineleaps.authentication.jwt.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nineleaps.authentication.jwt.dto.CreateEngagementRequestDTO;
import com.nineleaps.authentication.jwt.entity.Engagement;
import com.nineleaps.authentication.jwt.repository.EngagementRepository;
import com.nineleaps.authentication.jwt.service.IEngagementService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/engagements")
public class EngagementController {

	@Autowired
    private EngagementRepository engagementRepository;

    
    
        private final IEngagementService engagementService;

        public EngagementController(IEngagementService engagementService) {
            this.engagementService = engagementService;
        }
   
        @PostMapping("/createEngagement")
        @ApiOperation("Start an Engagement between Mentor and Mentee")
        public ResponseEntity<CreateEngagementRequestDTO> createEngagement(
                @Valid @RequestBody CreateEngagementRequestDTO createEngagementRequest) {
            Engagement engagement = createEngagementRequest.toEngagement();
            Engagement createdEngagement = engagementService.createEngagement(
                createEngagementRequest.getConnectionRequest().getId(),
                
                createEngagementRequest.getStartTime(),
                createEngagementRequest.getDurationHours()
            );
            createEngagementRequest.setId(createdEngagement.getId());
            CreateEngagementRequestDTO engagementDTO = CreateEngagementRequestDTO.fromEngagement(createdEngagement);
            return ResponseEntity.ok(engagementDTO);
        }
   
        @GetMapping("/getEngagementsByUserId")
        @ApiOperation("Get Engagement Details By User id")
        public List<Map<String, Object>> getEngagementDetailsByUserId(@RequestParam Long userId) {
            return engagementRepository.findEngagementDetailsByUserId(userId);
        }


}


