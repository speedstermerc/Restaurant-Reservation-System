package src;
import java.time.*;
import java.lang.*;
public class Reservation {
    private String customerID;
    private String restaurantID;
    private int partySize;
    private int status;
    private String reservationDate;
    private String reservationTime;
    private LocalDateTime reservationDateTime;
    private LocalDateTime reservationEndTime;
    private int credits;

    private Bill bill;

    public Reservation(String customerID, String restaurantID, int partySize, String reservationDate, String reservationTime, int credits){
        this.customerID = customerID;
        this.restaurantID = restaurantID;
        this.partySize = partySize;
        this.credits = credits;
        this.status = 0;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.bill = new Bill(Restaurant.findRestaurant(restaurantID), Customer.findCustomer(customerID), reservationDate, reservationTime);
        try {
            reservationDateTime = stringToDatetime(reservationDate, reservationTime);
            reservationEndTime = reservationDateTime.plusMinutes(119);
        
        } catch (DateTimeException e) {
            System.out.println("Reservation failed, please specify valid date and time");
        }
    }

    public static LocalDateTime stringToDatetime(String reservationDate, String reservationTime) {
        String[] ymd = reservationDate.split("-");
        int year = Integer.parseInt(ymd[0]);
        int month = Integer.parseInt(ymd[1]);
        int day = Integer.parseInt(ymd[2]);

        String[] hm = reservationTime.split(":"); 
        int hour = Integer.parseInt(hm[0]);
        int minute = Integer.parseInt(hm[1]); 
        int second = 0;

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    public static Reservation getReservation(Restaurant res, String customerID, String ReservationDate, String ReservationTime) {
        for (Reservation r: res.getDiningEvents()) {
            if (r.getCustomerID().equals(customerID)
                && r.getReservationDate().equals(ReservationDate)
                && r.getReservationTime().equals(ReservationTime)
            ) {
                return r;   
            }
        }
        return null;
    }

    public String toString(){
        return this.reservationDateTime.toString() + " " + this.status;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setBill(Bill b){
        this.bill = b;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public int getStatus() {
        return status;
    }
    protected void setStatus(int completed) {
        // this.status = 0 -> reservation not yet passed
        // this.status = 1 -> reservation successfully completed
        // this.status = 2 -> walk-in
        // this.status = -1 -> reservation missed

        if (this.status != 0) {
            System.out.println("Customer has already arrived");
           
        }
        if (-1 <= completed && completed <= 2) {
            this.status = completed;
          
        }
      
    }
    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public LocalDateTime getReservationEndTime() {
        return reservationEndTime;
    }

    public void setReservationEndTime(LocalDateTime reservationEndTime) {
        this.reservationEndTime = reservationEndTime;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = Math.max(credits, 0);
    }
}

