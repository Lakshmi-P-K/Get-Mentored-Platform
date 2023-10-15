package com.nineleaps.authentication.jwt.controller;


import com.nineleaps.authentication.jwt.dto.*;

import com.nineleaps.authentication.jwt.service.LoggingAndReportingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/")
public class LoggingAndReportingController {


    @Autowired
    private LoggingAndReportingService loggingAndReportingService;
    
    @GetMapping("/connections/recieved/accepted/pending/rejectedByMentor/mentorId")
    @ApiOperation("Get Connection Request Statistics including the Accepted ,Rejected and Pending conections of a Mentor by Mentor Id")
    
    public ConnectionRequestStatisticsMentorDTO getConnectionsStatisticsByMentorId(@RequestParam Long mentorId) {
        int total = loggingAndReportingService.getConnectionsRecivedByMentor(mentorId);
        int accepted= loggingAndReportingService.getConnectionsAcceptedByMentor(mentorId);
        int rejected = loggingAndReportingService.getConnectionsRejectedByMentor(mentorId);
        int pending =loggingAndReportingService.getConnectionsPendingMentor(mentorId);

        return new ConnectionRequestStatisticsMentorDTO(total,accepted, rejected, pending);
    }

//_______________________________________________________________________________

    //Counting of Total Slots, booked,pending
    @GetMapping("/mentorId/countOfSlots/pending/booked")
    @ApiOperation("Get the count of Total number of Slots with their Status as Pending and Booked ")

    public SlotStatisticsDTO getSlotStatisticsByMentorId(@RequestParam Long mentorId) {
        int totalSlots = loggingAndReportingService.getTotalSlotsByMentorId(mentorId);
        int bookedSlots = loggingAndReportingService.getBookedSlotsByMentorId(mentorId);
        int pendingSlots = loggingAndReportingService.getPendingSlotsByMentorId(mentorId);

        return new SlotStatisticsDTO(totalSlots, bookedSlots, pendingSlots);
    }
  
  //pdf generation
  @GetMapping("/export/pdf")
  public ResponseEntity<Resource> exportDataToPDF() throws IOException {
      UserStatsDTO userStatsDTO = loggingAndReportingService.getRegisteredUsersCount();
      ConnectionRequestStatisticsReportDTO connectionRequestStatisticsReportDTO = loggingAndReportingService.getConnectionRequestStatistics();
      EngagementStatisticsDTO engagementStatisticsDTO=loggingAndReportingService.getEngagementSummary();
      EngagementMetrics engagementMetrics=loggingAndReportingService.getEngagementMetrics();
      FeedbackAnalyticsDTO feedbackAnalyticsDTO=loggingAndReportingService.getFeedbackAnalytics();
      loggingAndReportingService.exportDataToPDF(userStatsDTO, connectionRequestStatisticsReportDTO,engagementStatisticsDTO,engagementMetrics,feedbackAnalyticsDTO);
      // Load the generated PDF file
      Resource resource = new FileSystemResource("report.pdf");
      return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
              .contentType(MediaType.APPLICATION_PDF)
              .body(resource);
  }
//________________________________________________________

}
