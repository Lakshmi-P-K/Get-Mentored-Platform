package com.nineleaps.authentication.jwt.dto;

public class SlotStatisticsDTO {
	    private long totalSlots;
	    private long bookedSlots;
	    private long pendingSlots;
	    
	    public SlotStatisticsDTO() {
		
		}

		public long getTotalSlots() {
			return totalSlots;
		}

		public void setTotalSlots(long totalSlots) {
			this.totalSlots = totalSlots;
		}

		public long getBookedSlots() {
			return bookedSlots;
		}

		public void setBookedSlots(long bookedSlots) {
			this.bookedSlots = bookedSlots;
		}

		public long getPendingSlots() {
			return pendingSlots;
		}

		public void setPendingSlots(long pendingSlots) {
			this.pendingSlots = pendingSlots;
		}

		public SlotStatisticsDTO(long totalSlots, long bookedSlots, long pendingSlots) {
			super();
			this.totalSlots = totalSlots;
			this.bookedSlots = bookedSlots;
			this.pendingSlots = pendingSlots;
		}
	    
}
	    
	    

