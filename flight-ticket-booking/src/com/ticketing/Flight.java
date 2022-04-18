package com.ticketing;

import java.util.*;

public class Flight {
	int flightID;
	static int bookingID = 1;
	int priceEconomy;
	int priceBusiness;
	String seatCategory;
	List<Integer> bookingIDs;
	List<Integer> totalAmounts;
	List<String> seatCategories;
	List<String> mealOptions;
	List<List<Integer>> bookedSeats;
	List<Integer> availableBusinessSeats;
	List<Integer> availableEconomySeats;
	List<Boolean> cancelled;
	
	
	public Flight(int flightID, int priceBusiness, int priceEconomy) {
		this.flightID = flightID;
		this.priceEconomy = priceEconomy;
		this.priceBusiness = priceBusiness;
		this.bookingIDs = new ArrayList<>();
		this.totalAmounts = new ArrayList<>();
		this.seatCategories = new ArrayList<>();
		this.mealOptions = new ArrayList<>();
		this.bookedSeats = new ArrayList<>();
		this.cancelled = new ArrayList<>();
		this.availableBusinessSeats = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
		this.availableEconomySeats = new ArrayList<>(Arrays.asList(7,8,9,10,11,12,13,14,15,16,17,18));
	}
	
	public List<String> getSeatCategories() {
		return seatCategories;
	}
	
	public List<Boolean> getCancelled() {
		return cancelled;
	}

	public List<Integer> getBookingIDs() {
		return bookingIDs;
	}
	
	public List<List<Integer>> getBookedSeats() {
		return bookedSeats;
	}

	public List<Integer> getTotalAmounts() {
		return totalAmounts;
	}

	public List<String> getMealOptions() {
		return mealOptions;
	}
	
	public boolean checkAvailabilty(String seatCategory, int tickets) {
		if(seatCategory.equals("EC")) {
			if(availableEconomySeats.size() >= tickets) {
				return true;
			}
		}
		else {
			if(availableBusinessSeats.size() >= tickets) {
				return true;
			}
		}
		return false;
	}
	
	public int bookTicket(String seatCategory, int tickets, String mealOption) {
		bookingIDs.add(bookingID++);
		seatCategories.add(seatCategory);
		mealOptions.add(mealOption);
		cancelled.add(false);
		int totalAmount;
		List<Integer> bookedTickets = new ArrayList<>();
		if(seatCategory.equals("EC")) {
			//book tickets in economy			
			for(int i=0; i<tickets; i++) {
				bookedTickets.add(availableEconomySeats.get(i));	
			}
			for(int i=0; i<tickets; i++) {
				availableEconomySeats.remove(0);		
			}
			bookedSeats.add(bookedTickets);
			totalAmount = tickets * priceEconomy;
			this.priceEconomy += 100;
		}
		else {
			//book tickets in business
			for(int i=0; i<tickets; i++) {
				bookedTickets.add(availableBusinessSeats.get(i));
			}
			for(int i=0; i<tickets; i++) {
				availableBusinessSeats.remove(0);
			}
			bookedSeats.add(bookedTickets);
			totalAmount = tickets * priceBusiness;
			this.priceBusiness += 200;
		}
		//calculate amount
		if(mealOption.equals("Y")) {
			totalAmount += tickets * 200;
		}
		totalAmounts.add(totalAmount);	
		return bookingID-1;
	}
	
	public int cancelTicket(int bookingID){
		int index = 0;
		for(int i : bookingIDs) {
			if(bookingID == i) {
				index = bookingIDs.indexOf(i);
			}
		}
		mealOptions.set(index, "N");
		cancelled.set(index, true);
		String seatCategory = seatCategories.get(index);
		List<Integer> bookedTickets = new ArrayList<>();
		bookedTickets = bookedSeats.get(index);
		if(seatCategory.equals("EC")) {
			for(int i : bookedTickets) {
				availableEconomySeats.add(i);
			}
			availableEconomySeats.sort(null);
		}else {
			for(int i : bookedTickets) {
				availableBusinessSeats.add(i);
			}
			availableBusinessSeats.sort(null);
		}
		int paidAmount = totalAmounts.get(index);
		int refundAmount = paidAmount - ( 200 * bookedTickets.size());
		totalAmounts.set(index, paidAmount - refundAmount);
		return refundAmount;
	}
	
	public List<Integer> getAvailability(){
		List<Integer> availableTickets = new ArrayList<>();
		availableTickets.addAll(availableBusinessSeats);
		availableTickets.addAll(availableEconomySeats);
		return availableTickets;
	}
	
	public int getTotalCost() {
		int sum = 0;
		for(int i : totalAmounts) {
			sum += i;
		}
		return sum;	
	}
	
	public List<Integer> getMealsBookedSeats(){
		List<Integer> mealsBookedSeats = new ArrayList<>();
		for(int i=0; i<mealOptions.size(); i++) {
			if(mealOptions.get(i).equals("Y")) {
				if(cancelled.get(i) == false) {
					mealsBookedSeats.addAll(bookedSeats.get(i));
				}
			}
		}
		mealsBookedSeats.sort(null);
		return mealsBookedSeats;
	}
		
	public List<Integer> getBookedSeatsForFlight(){
		List<Integer> bookedSeatsForFlight = new ArrayList<>();
		for(int i=0; i<bookedSeats.size(); i++) {
			if(cancelled.get(i) == false) {
				bookedSeatsForFlight.addAll(bookedSeats.get(i));
			}
		}
		bookedSeatsForFlight.sort(null);
		return bookedSeatsForFlight;
	}
}
