package src;

import java.util.*;
import java.time.*;

public class Customer {
    private String customerID;
    private String fname;
    private String lname;
    private Address address;
    private int funds;
    private int misses = 0;
    private int credits = 0;
    private List<Reservation> reservations = new ArrayList<>();

    private static final List<Customer> customers = new ArrayList<>();

    public Customer(String customerID, String fname, String lname, 
        String city, String state, String zip, int funds) {
            this.customerID = customerID;
            this.fname = fname;
            if (lname.equals("null")) {this.lname = null;}
            else {this.lname = lname;}
            this.address = new Address(city, state, zip);
            this.funds = funds;

            if (findCustomer(customerID) == null){
                customers.add(this);
                System.out.printf("Customer added: %s - %s\n", customerID, this.getCustomerName());
            } else {
                System.out.println("ERROR: duplicate unique identifier");
            }
        }
    public static Customer findCustomer(String customerID) {
        for (Customer c : customers) {
            if (c.getCustomerID().equals(customerID)) {
                return c;
            }
        }
        return null;
    }

    public static void viewCustomers() {
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    public boolean withinTwo(LocalDateTime arrivalTime) {
        for (Reservation r : reservations) {
            if (r.getStatus() != -1){
                Duration d = Duration.between(r.getReservationDateTime(), arrivalTime);
               // System.out.println(Math.abs(d.toMinutes()));
                if (Math.abs(d.toMinutes()) < 120) {
                    return false;
                }
               
            }
        }
        return true;
    }
    
    /**
     * For creating reservations
     * @param partySize
     * @param reservationDate
     * @param reservationTime
     * @param credits
     */
    public boolean createReservation(
    int partySize, String reservationDate, String reservationTime, int credits, Restaurant requestedRestaurant, boolean isWalkIn){
        Reservation r = new Reservation(customerID, requestedRestaurant.getRestaurantID(), partySize, reservationDate, reservationTime, credits);
        if (r.getReservationDateTime() == null) {
            // check if datetime valid
            return false;
        }
        if (isWalkIn){
            r.setStatus(2);
        }
        LocalDateTime reservationDateTime = Reservation.stringToDatetime(reservationDate, reservationTime);
        // check if customer has any other reservations in 2 hr block
        
        if (withinTwo(reservationDateTime)) {
            // check if enough seating capcity at this time.
            // ADD reservation into customer reservation list if valid
            Reservation thisRes = new Reservation(customerID, requestedRestaurant.getRestaurantID(), partySize,reservationDate, reservationTime, credits);
            if (requestedRestaurant.makeRestaurantReservation(thisRes)) {
                
                this.reservations.add(thisRes);
                if (r.getStatus() != 2) {
                    System.out.println("Reservation confirmed");
                    System.out.printf("Reservation made for %s (%s) at %s\n",getCustomerID(), getCustomerName(), requestedRestaurant.getName());
                }
                return true;
            } else {
                if (r.getStatus() != 2){
                    System.out.println("Reservation request denied, table has another active reservation within 2 hours of the requested time");
                }
                return false;
            }
          
        } else {
            if (r.getStatus() == 2) {return true;}
            System.out.println("Reservation request denied, customer already has reservation with another restaurant within 2 hours of the requested time");       
            return false;
        }
        
    }



    /**
     * For the customer_arrival command
     * @param reservationDate
     * @param arrivalTime
     * @param reservationTime
     * @return
     */
    public int inWindow(String reservationDate, String arrivalTime, String reservationTime){
        LocalDateTime reservationDateTime = Reservation.stringToDatetime(reservationDate, reservationTime);
        LocalDateTime arrivalDateTime = Reservation.stringToDatetime(reservationDate, arrivalTime);
        long durationInMinutes = Duration.between(reservationDateTime, arrivalDateTime).toMinutes();
        if (durationInMinutes < -30) {
            // -1 return means customer arrived too early
            return -1; 
        } else if (durationInMinutes > 15) {
            // 1 return means customer arrived too late
            return 1;
        } else {
            // 0 return means customer arrived in windows
            return 0;
        }
    }

  
    public Reservation findReservation(String reservationDate, String reservationTime){
        for (Reservation r : this.reservations) {
            if (r.getReservationDate().equals(reservationDate) &&
                r.getReservationTime().equals(reservationTime)) {
                    return r;
                }
        }
        return null;
    }
    
    public void handleCustomerArrival(String reservationDate, String arrivalTime, String reservationTime, Restaurant requestedRestaurant) {
        Reservation r = findReservation(reservationDate, reservationTime);
        if (r == null) {
            System.out.printf("%s - Walk-in party\n",getCustomerName());
            
            System.out.println("No credits rewarded and no misses added");
            
            if (createReservation(4, reservationDate, arrivalTime,0, requestedRestaurant, true)) {
                System.out.printf("Seats were available, %s seated\n",this.getCustomerName());
                

            } else {
                System.out.println("Seats not available - Request denied");
            }
            printCreditsMisses();
            return;
        }

        int status = inWindow(reservationDate , arrivalTime, reservationTime);
        if (status == 0) {
            if (r.getStatus() != 0) {
                System.out.printf("ERROR: %s has already arrived\n", this.getCustomerName());
                return;
            }
            System.out.printf("Customer %s (%s) has arrived at %s\n",
                        this.getCustomerID(), this.getCustomerName(), requestedRestaurant.getName());
            System.out.printf("%s - Successfully completed reservation\n", getCustomerName());
            //set reservation status to complete
          

            r.setStatus(1);
            this.credits += r.getCredits();
            System.out.println("Full credits rewarded");
            
        } else if (status == 1) {
            // late arrivals
            System.out.printf("Customer %s (%s) has arrived late at %s\n",getCustomerID(),getCustomerName(),requestedRestaurant.getName());
            System.out.printf("%s - Missed reservation\n",getCustomerName());
            System.out.println("No credits rewarded and 1 miss added");
            r.setStatus(-1);
            this.misses ++;
            if (this.misses == 3) {
                this.misses = 0;
                this.credits=  0;
                System.out.println("Misses: 3");
                System.out.printf(
                    "%s - 3 Misses reached, both misses and credits will reset back to 0\n"
                    ,getCustomerName());
            }
            // treat as walk in
            if (createReservation(r.getPartySize(),reservationDate, arrivalTime, 0, requestedRestaurant, true)){
                System.out.printf("Seats were available, %s seated\n",getCustomerName());

            }
        } else if (status == -1) {
            // early arrivals
            System.out.printf("Customer %s (%s) has arrived early at %s\n",
            getCustomerID(), getCustomerName(), requestedRestaurant.getName());
           
            System.out.println("Please come back during the reservation window");
            System.out.println("No credits rewarded and no misses added");
            
        }
        printCreditsMisses();
      
    }
    private void printCreditsMisses(){
        System.out.println("Credits: " + getCustomerCredits());
        System.out.println("Misses: " + getCustomerMisses());
    }

    public void decreaseFunds(int cost) {
        funds -= cost;
    }

    public String getCustomerID(){
        return this.customerID;
    }
    public String getCustomerFname(){
        return this.fname;
    }
    public String getCustomerLname(){
        return this.lname;
    }
    public String getCustomerName(){
        if (getCustomerLname() == null) {
            return getCustomerFname();
        }
        return getCustomerFname() + " " + getCustomerLname();
    }
    public int getCustomerFunds(){
        return this.funds;
    }
    public int getCustomerCredits(){
        return this.credits;
    }
    public int getCustomerMisses(){
        return this.misses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Double.compare(funds, customer.funds) == 0 && misses == customer.misses && credits == customer.credits && Objects.equals(customerID, customer.customerID) && Objects.equals(fname, customer.fname) && Objects.equals(lname, customer.lname) && Objects.equals(address, customer.address) && Objects.equals(reservations, customer.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerID, fname, lname, address, funds, misses, credits, reservations);
    }

    @Override
    public String toString() {
        String result = customerID + " (" + fname;
        if (lname == null) {
            result += ")";
            return result;
        } else {
            result += " " + lname + ")";
        }
        return result;
    }
}
