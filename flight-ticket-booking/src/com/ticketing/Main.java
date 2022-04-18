package com.ticketing;

import java.util.*;

public class Main {
	public static void main(String[] args) {
		List<Flight> flights = new ArrayList<>();
		Flight flight1 = new Flight(101, 2000, 1000);
		Flight flight2 = new Flight(102, 2000, 1000);	
		flights.add(flight1);
		flights.add(flight2);
		
		Scanner in = new Scanner(System.in);
		
		while(true) {
			System.out.println("1.Book, 2.Cancel, 3.Check availability, 4.Flight Summary, 5.Bookings summary, 6.Exit");
			int choice = in.nextInt();
			
			if(choice == 1) {
				//book ticket
				System.out.println("Enter the flightID : ");
				int flightID = in.nextInt();
				Flight currentFlight = null;
				for(Flight flight : flights) {
					if(flightID == flight.flightID) {
						currentFlight = flight;
						break;
					}
				}
				if(currentFlight == null) {
					System.out.println("Invalid flightID");
				}
				else {
					System.out.println("Enter the seat Category(Economy or Business class as EC/BC) : ");
					String seatCategory = in.next();
					System.out.println("Enter number of tickets : ");
					int tickets = in.nextInt();
					if(!currentFlight.checkAvailabilty(seatCategory, tickets)) {
						System.out.println("Tickets not available");
					}
					else {
						System.out.println("Are you opting for meals(Y/N) : ");
						String mealOption = in.next();
//						List<Integer> bookedTickets = new ArrayList<>();
						
						int bookingID = currentFlight.bookTicket(seatCategory, tickets, mealOption);
						System.out.println("Tickets booked Successfully. Booking ID : " + bookingID);
					}
				}
			}
			else if(choice == 2){
				//cancel ticket
				System.out.println("Enter the flightID : ");
				int flightID = in.nextInt();
				Flight currentFlight = null;
				for(Flight flight : flights) {
					if(flightID == flight.flightID) {
						currentFlight = flight;
						break;
					}
				}
				if(currentFlight == null) {
					System.out.println("Invalid flightID");
				}
				System.out.println("Enter booking ID");
				int bookingID = in.nextInt();
				int refundAmount = currentFlight.cancelTicket(bookingID);
				System.out.println("Tickets cancelled successfully with refund : "+refundAmount);
			}
			else if(choice == 3) {
				//Check availability
				for(Flight flight : flights) {
					List<Integer> availableTickets = new ArrayList<>();
					availableTickets = flight.getAvailability();
					System.out.println("flight ID : "+ flight.flightID);
					System.out.println("AvailableSeats : "+ availableTickets);
				}
				
			}
			else if(choice == 4) {
				//flight summary
				for(Flight flight : flights) {
					List<Integer> availableTickets = new ArrayList<>();
					availableTickets = flight.getAvailability();
					System.out.println("flight ID : "+ flight.flightID);
					System.out.println("Booked seats : "+ flight.getBookedSeatsForFlight());
					System.out.println("Total cost : "+ flight.getTotalCost());
					System.out.println("Meals booked seats : "+ flight.getMealsBookedSeats());
					System.out.println("AvailableSeats : "+ availableTickets);
				}	
			}
			else if(choice == 5) {
				List<Integer> bookingIDs; 
				List<Integer> totalAmounts;
				List<String> seatCategories;
				List<String> mealOptions;
				List<List<Integer>> bookedSeats;
				List<Boolean> cancelled;
				
				//booking summary
				for(Flight flight : flights) {
					bookingIDs = flight.getBookingIDs();
					totalAmounts = flight.getTotalAmounts();
					seatCategories = flight.getSeatCategories();
					mealOptions = flight.getMealOptions();
					bookedSeats = flight.getBookedSeats();
					cancelled =  flight.getCancelled();
					for(int index=0; index<bookingIDs.size(); index++) {
						System.out.println("Booking ID : "+ bookingIDs.get(index));
						System.out.println("Flight ID : "+ flight.flightID);
						if(cancelled.get(index)) {
							System.out.println("Cancelled");
							System.out.println("Total cost : "+ totalAmounts.get(index));
						}else {
							System.out.println("Booked seats : "+ bookedSeats.get(index));
							System.out.println("Class : "+seatCategories.get(index));
							System.out.println("Total cost : "+ totalAmounts.get(index));
							System.out.println("Meals required : " + mealOptions.get(index));
						}
					}
				}	
			}
			else if(choice == 6) {
				break;
			}
			else {
				System.out.println("Invalid input");
			}
		}
//		in.close();
		
	}

}
