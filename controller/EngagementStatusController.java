package com.nineleaps.authentication.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nineleaps.authentication.jwt.dto.EngagementStatusDTO;
import com.nineleaps.authentication.jwt.service.EngagementStatusService;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/v1/engagementStatus")
public class EngagementStatusController {
	
	
	
	private final EngagementStatusService engagementStatusService;
    public EngagementStatusController(EngagementStatusService engagementStatusService) {
        this.engagementStatusService = engagementStatusService;
    }

    @PutMapping("/createOrUpdateEngagementStatus")
    public ResponseEntity<EngagementStatusDTO> createOrUpdateEngagementStatus(
            @RequestParam Long engagementId,
            @RequestBody EngagementStatusDTO engagementStatusDTO
    ) {
        engagementStatusDTO.setEngagementId(engagementId);
        EngagementStatusDTO result = engagementStatusService.createOrUpdateEngagementStatus(engagementStatusDTO);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/getById")
    @ApiOperation("Get Engagement Status by Engagement Id")
    public ResponseEntity<EngagementStatusDTO> getEngagementStatusByEngagementId(@RequestParam("id") Long engagementId) {
        EngagementStatusDTO engagementStatusDTO = engagementStatusService.getEngagementStatusByEngagementId(engagementId);
        if (engagementStatusDTO != null) {
            return ResponseEntity.ok(engagementStatusDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
