package com.nineleaps.authentication.jwt.service;

import com.nineleaps.authentication.jwt.dto.*;
import com.nineleaps.authentication.jwt.enums.UserRole;
import com.nineleaps.authentication.jwt.repository.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class LoggingAndReportingService {
    @Autowired
    private ConnectionRequestRepo connectionRequestRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
//   
    @Autowired
    private GoalTrackerRepository goalTrackerRepository;
    @Autowired
    private SlotRepository slotRepository;
    @Autowired
    private EngagementRepository engagementRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;

    //count no of mentors
    public long countMentors() {
        return userRepository.countUsersByRole(UserRole.MENTOR);
    }
    //count no of mentees
    public long countMentees() {
        return userRepository.countUsersByRole(UserRole.MENTEE);
    }


    //    Count of goals for a particular engagement
    public int countGoalsByEngagement(Long engagementId) {
        return goalTrackerRepository.countGoalsByEngagement(engagementId);
    }
    //        ________________________________________________________________________
    //Slot count
    //    Total slot a mentor have
    public int getTotalSlotsByMentorId(Long mentorId) {
        return slotRepository.getTotalSlotsByMentorId(mentorId);
    }
    //    Count of booked slots a mentor have
    public int getBookedSlotsByMentorId(Long mentorId) {
        return slotRepository.getBookedSlotsByMentorId(mentorId);
    }

    //    Count of pending  slots a mentor have
    public int getPendingSlotsByMentorId(Long mentorId) {
        return slotRepository.getPendingSlotsByMentorId(mentorId);
    }

    //connection requests counting
    public int getConnectionsRecivedByMentor(Long mentorId){
        return connectionRequestRepo.countConnectionRequestsReceivedByMentor(mentorId);
    }
    public int getConnectionsAcceptedByMentor(Long mentorId){
        return connectionRequestRepo.countConnectionRequestsAcceptedByMentor(mentorId);
    }
    public int getConnectionsRejectedByMentor(Long mentorId){
        return connectionRequestRepo.countConnectionRequestsRejectedByMentor(mentorId);
    }
    public int getConnectionsPendingMentor(Long mentorId){
        return connectionRequestRepo.countConnectionRequestsPendingByMentor(mentorId);
    }
    //Connection requests Total/accepted/pending/rejected
    public ConnectionRequestStatisticsReportDTO getConnectionRequestStatistics() {
        return connectionRequestRepo.getConnectionRequestStatistics();
    }
    //________________________________________________________________________
    //    Counting of users/mentees/mentors
    public UserStatsDTO getRegisteredUsersCount() {
        return userRepository.getRegisteredUsersCount();
    }
    //    __________________________________________________________________
//    Engagement Counting
    public EngagementStatisticsDTO getEngagementSummary() {
        return engagementRepository.getEngagementSummary();
    }
    //________________________________________________________________________
//    Feedback counting
    public FeedbackAnalyticsDTO getFeedbackAnalytics() {
        return feedbackRepository.getFeedbackAnalytics();
    }
//_____________________________________________________________________________-

//        Enagement Status
//    public EngagementMetrics getEngagementMetrics() {
//        String jpaQuery = "SELECT COUNT(eng), " +
//                "SUM(CASE WHEN es.menteeEngStatus = 'PENDING' OR es.mentorEngStatus = 'PENDING' THEN 1 ELSE 0 END) As pengingEngagements, " +
//                "SUM(CASE WHEN es.menteeEngStatus = 'DONE' AND es.mentorEngStatus = 'DONE' THEN 1 ELSE 0 END)AS completedEngagements " +
//                "FROM Engagement eng " +
//                "JOIN eng.engagementStatuses es";
//        Object[] result = (Object[]) entityManager.createQuery(jpaQuery).getSingleResult();
//        Long pendingEngagements = (Long) result[1];
//        Long doneEngagements = (Long) result[2];
//        return new EngagementMetrics( pendingEngagements, doneEngagements);
//    }
    
    public EngagementMetrics getEngagementMetrics() {
        String jpaQuery = "SELECT COUNT(eng), " +
                "SUM(CASE WHEN es.menteeEngStatus = 'PENDING' OR es.mentorEngStatus = 'PENDING' THEN 1 ELSE 0 END) As pendingEngagements, " +
                "SUM(CASE WHEN es.menteeEngStatus = 'DONE' AND es.mentorEngStatus = 'DONE' THEN 1 ELSE 0 END) AS completedEngagements " +
                "FROM Engagement eng " +
                "JOIN eng.engagementStatuses es";
        Object[] result = (Object[]) entityManager.createQuery(jpaQuery).getSingleResult();
        Long pendingEngagements = (Long) result[1];  // Index 1 for pendingEngagements
        Long doneEngagements = (Long) result[2];     // Index 2 for doneEngagements
        return new EngagementMetrics(pendingEngagements, doneEngagements);
    }

   
 //    Export to pdf
    public void exportDataToPDF(UserStatsDTO userStatsDTO,
                                ConnectionRequestStatisticsReportDTO connectionRequestStatisticsReportDTO
            ,EngagementStatisticsDTO engagementStatisticsDTO,EngagementMetrics engagementMetrics
            ,FeedbackAnalyticsDTO feedbackAnalyticsDTO   ) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Background color
                contentStream.setNonStrokingColor(new Color(237, 237, 237));
                contentStream.addRect(0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
                contentStream.fill();
                // Set the font style and color for the main heading
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
                contentStream.setNonStrokingColor(Color.BLUE);  // Set the color (blue in this example)
// Get the width and height of the page
                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();
// Calculate the center X coordinate for the main heading
                float centerX = pageWidth / 2;
// Set the Y coordinate for the main heading at the top of the page
                float headingY = pageHeight - 50;  // Adjust the value as needed for the desired distance from the top
// Get the width of the text
                float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth("REPORTING") / 1000f * 24;
// Calculate the X coordinate to center the text
                float textX = centerX - (textWidth / 2);
// Add the main heading "REPORTING" at the top center
                contentStream.beginText();
                contentStream.newLineAtOffset(textX, headingY);  // Position the text at the calculated X and Y coordinates
                contentStream.showText("REPORTING");
                contentStream.endText();
                // Heading 1
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 15);
                contentStream.beginText();
                contentStream.newLineAtOffset(20, page.getMediaBox().getHeight() - 180);
                contentStream.showText("USER METRICS");
                contentStream.endText();
// Styling content text
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, page.getMediaBox().getHeight() - 200);
                contentStream.showText("Total Users: " + userStatsDTO.getTotalUsers());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Mentees Count: " + userStatsDTO.getMenteesCount());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Mentors Count: " + userStatsDTO.getMentorsCount());
                contentStream.endText();
// Create a new dataset for the bar chart
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                dataset.addValue(userStatsDTO.getTotalUsers(), "Total Users", "User Metrics");
                dataset.addValue(userStatsDTO.getMenteesCount(), "Mentees Count", "User Metrics");
                dataset.addValue(userStatsDTO.getMentorsCount(), "Mentors Count", "User Metrics");
// Create a bar chart
                JFreeChart barChart = ChartFactory.createBarChart(
                        "User Metrics", "Metrics", "Value", dataset);
// Convert the chart to an image
                ByteArrayOutputStream chartOutputStream = new ByteArrayOutputStream();
                ChartUtils.writeChartAsPNG(chartOutputStream, barChart, 250, 200);
// Load the chart image into a PDImageXObject
                PDImageXObject chartImage = PDImageXObject.createFromByteArray(
                        document, chartOutputStream.toByteArray(), "chart.png");
// Calculate the coordinates for the chart image
                float chartX = 250; // X coordinate for the chart image
                float chartY = page.getMediaBox().getHeight() - 300; // Y coordinate for the chart image
// Draw the chart image onto the PDF
                contentStream.drawImage(chartImage, chartX, chartY);
//_________________________
// Heading 2
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 15);
                contentStream.beginText();
                contentStream.newLineAtOffset(20, page.getMediaBox().getHeight() - 340);
                contentStream.showText("Connection Metrics:");
                contentStream.endText();
                // Styling content text
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, page.getMediaBox().getHeight() - 360);
                contentStream.showText("Total Connections: " + connectionRequestStatisticsReportDTO.getTotalRequests());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Accepted Requests: " + connectionRequestStatisticsReportDTO.getAcceptedRequests());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Pending Requests: " + connectionRequestStatisticsReportDTO.getPendingRequests());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Rejected Requests: " + connectionRequestStatisticsReportDTO.getRejectedRequests());
                contentStream.endText();
// Create a new dataset for the bar chart
                // Create a new dataset for the bar chart
                DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
                dataset1.addValue(connectionRequestStatisticsReportDTO.getTotalRequests(), "Total Connections", "Connection Metrics");
                dataset1.addValue(connectionRequestStatisticsReportDTO.getAcceptedRequests(), "Accepted", "Connection Metrics");
                dataset1.addValue(connectionRequestStatisticsReportDTO.getPendingRequests(), "Pending", "Connection Metrics");
                dataset1.addValue(connectionRequestStatisticsReportDTO.getRejectedRequests(), "Rejected", "Connection Metrics");
// Create a bar chart
                JFreeChart barChart1 = ChartFactory.createBarChart(
                        "Connection Metrics", "Metrics", "Value", dataset1);
// Convert the chart to an image
                ByteArrayOutputStream chartOutputStream1 = new ByteArrayOutputStream();
                ChartUtils.writeChartAsPNG(chartOutputStream1, barChart1, 250, 200);
// Load the chart image into a PDImageXObject
                PDImageXObject chartImage1 = PDImageXObject.createFromByteArray(
                        document, chartOutputStream1.toByteArray(), "chart1.png");
// Calculate the coordinates for the chart image
                float chartX1 = 250; // X coordinate for the chart image
                float chartY1 = page.getMediaBox().getHeight() - 530; // Y coordinate for the chart image
// Draw the chart image onto the PDF
                contentStream.drawImage(chartImage1, chartX1, chartY1);
//                _______________________________________________________
                // Heading 3
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 15);
                contentStream.beginText();
                contentStream.newLineAtOffset(20, page.getMediaBox().getHeight() - 530);
                contentStream.showText("Engagements Metrics:");
                contentStream.endText();
                // Styling content text
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 550);
                contentStream.showText("Total Engagements: " + engagementStatisticsDTO.getTotalEngagements());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Total hours spent on engagements: " + engagementStatisticsDTO.getTotalHours());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Average of hours spent on engagements: " + engagementStatisticsDTO.getAverageHours());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Pending Engagements :"+engagementMetrics.getPendingEngagements());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Completed Engagements :"+engagementMetrics.getDoneEngagements());
                contentStream.endText();
// Create a dataset for the pie chart
                DefaultPieDataset dataset2 = new DefaultPieDataset();
                dataset2.setValue("Pending Engagements", engagementMetrics.getPendingEngagements());
                dataset2.setValue("Completed Engagements", engagementMetrics.getDoneEngagements());
// Create a pie chart
                JFreeChart pieChart = ChartFactory.createPieChart("Engagement Distribution", dataset2, true, true, false);
// Convert the chart to an image
                ByteArrayOutputStream chartOutputStream2 = new ByteArrayOutputStream();
                ChartUtils.writeChartAsPNG(chartOutputStream2, pieChart, 250, 200);
// Load the chart image into a PDImageXObject
                PDImageXObject chartImage2 = PDImageXObject.createFromByteArray(document, chartOutputStream2.toByteArray(), "chart.png");
// Calculate the coordinates for the chart image
                float chartxx = 300; // X coordinate for the chart image
                float chartyy = page.getMediaBox().getHeight() - 780; // Y coordinate for the chart image
// Draw the chart image onto the PDF
                contentStream.drawImage(chartImage2, chartxx, chartyy);
//                _________________________________________________________________
                // Heading 4
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 15);
                contentStream.beginText();
                contentStream.newLineAtOffset(20, page.getMediaBox().getHeight() - 680);
                contentStream.showText("Feedback Metrics:");
                contentStream.endText();
                // Styling content text
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(30, page.getMediaBox().getHeight() - 700);
                contentStream.showText("Average Of Engagement Ratings: " + feedbackAnalyticsDTO.getAverageEngagementRating());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Average Of Mentor Ratings: " + feedbackAnalyticsDTO.getAverageMentorRating());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Average Of Mentee Ratings: " + feedbackAnalyticsDTO.getAverageMenteeRating());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Positive Feedback Count For Engagements: " + feedbackAnalyticsDTO.getPositiveFeedbackCount());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Negative Feedback Count For Engagements: " + feedbackAnalyticsDTO.getNegativeFeedbackCount());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Neutral Feedback Count For Engagements: " + feedbackAnalyticsDTO.getNeutralFeedbackCount());
                contentStream.endText();
            }
            // Add border
            try (PDPageContentStream borderStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                borderStream.setLineWidth(2.0f);
                borderStream.setNonStrokingColor(Color.gray);
                borderStream.addRect(5, 10, 600, 760);
                borderStream.stroke();
            }
            document.save("report.pdf");
        }
    }
}