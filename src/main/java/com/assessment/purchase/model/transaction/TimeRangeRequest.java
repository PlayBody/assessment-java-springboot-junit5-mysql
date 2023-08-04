package com.assessment.purchase.model.transaction;

import java.time.LocalDateTime;

public class TimeRangeRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Constructor
    public TimeRangeRequest(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter methods
    public LocalDateTime getStartDate() {
        return startDate;
    }


    public LocalDateTime getEndDate() {
        return endDate;
    }

}
