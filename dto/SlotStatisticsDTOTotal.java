package com.nineleaps.authentication.jwt.dto;

public class SlotStatisticsDTOTotal {

    private int totalSlots;
    private int bookedSlots;
    private int pendingSlots;
	public int getTotalSlots() {
		return totalSlots;
	}
	public void setTotalSlots(int totalSlots) {
		this.totalSlots = totalSlots;
	}
	public int getBookedSlots() {
		return bookedSlots;
	}
	public void setBookedSlots(int bookedSlots) {
		this.bookedSlots = bookedSlots;
	}
	public int getPendingSlots() {
		return pendingSlots;
	}
	public void setPendingSlots(int pendingSlots) {
		this.pendingSlots = pendingSlots;
	}
	public SlotStatisticsDTOTotal(int totalSlots, int bookedSlots, int pendingSlots) {
		super();
		this.totalSlots = totalSlots;
		this.bookedSlots = bookedSlots;
		this.pendingSlots = pendingSlots;
	}


    
}

