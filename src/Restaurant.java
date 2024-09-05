package src;
import java.time.*;
import java.util.*;
public class Restaurant {
    private String restaurantID;
    private String restaurantGroupID;
    private String name;
    private Address address;
    private int avgRating;
    private List<Review> reviews = new ArrayList<>();
    private List<Reservation> diningEvents = new ArrayList<>();
    private boolean top10 = false;
    private int maxCapacity;
    private int currentCapacity = 0;
    private int totalRevenue = 0;
    private String unifiedLicense; // Concatinated letters followed by a unique identifier for each restaurant.
    private Owner owner;
    private Menu menu;

    private static final List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant(String restaurantID,String name,
        String city, String state, String zip, int maxCapacity, Owner owner, String unifiedLicense){

        this.restaurantID = restaurantID;
        this.name = name;
        this.address = new Address(city, state, zip);

        this.maxCapacity = maxCapacity;
        this.owner = owner;
        this.owner.registerRestaurant(this);
        
        this.unifiedLicense = unifiedLicense;
        this.menu = new Menu();
        if (findRestaurant(restaurantID) == null) {
            restaurants.add(this);
            System.out.printf("Restaurant created: %s (%s) - %s\n", restaurantID, name,
                    this.address.getAddress());
            System.out.printf("%s (%s) owns %s (%s)\n",
                    this.getOwnerID(), this.getOwnerName(), this.getRestaurantID(), this.getName());
        } else {
            System.out.println("ERROR: duplicate unique identifier");
        }
    }

    public static Restaurant findRestaurant(String restaurantID) {
        for (Restaurant r : restaurants) {
            if (r.getRestaurantID().equals(restaurantID)) {
                return r;
            }
        }
        return null;
    }

    public static void viewAllRestaurants() {
        //REST001 (Mellow Mushroom)
        for (Restaurant r : restaurants) {
            System.out.printf("%s (%s)\n", r.getRestaurantID(), r.getName());
        }
    }

    private static boolean isBetween(LocalDateTime lowerBound, LocalDateTime upperBound, LocalDateTime compare){
        if (lowerBound.isAfter(upperBound)) {
            throw new IllegalArgumentException("Start time is after end time");
        }

        // Check if the compare time is between (inclusive of both bounds)
        return (compare.isEqual(lowerBound) || compare.isAfter(lowerBound)) &&
               (compare.isEqual(upperBound) || compare.isBefore(upperBound));
    }
    
    // Calculate current capacity at time T
    public void calculateCapacity(LocalDateTime T) {
        this.currentCapacity = 0;
        diningEvents.sort(new Comparator<Reservation>() {
            @Override
            public int compare(Reservation r1, Reservation r2) {
                Duration duration = Duration.between(r1.getReservationDateTime(), r2.getReservationDateTime());
                return -(int) duration.toMinutes();
            }
        });
        
        for (Reservation r : diningEvents) {
            if (r.getStatus() != -1 && (r.getReservationEndTime().isBefore(T))  || r.getReservationEndTime().isEqual(T)) {
                continue;
            } else if (r.getStatus() != -1 && (r.getReservationDateTime().isBefore(T) || r.getReservationDateTime().isEqual(T))) {
                this.currentCapacity += r.getPartySize();
            } else {return;}
        } 

    }


    public boolean availableSeating(LocalDateTime arrivalTime, int partySize) {
        
        //for walk in parties and new reservations, check if there exists enough space for seating
            // one idea:
                /*  calculate restaurant capacity at time arrivalTime
                    find all "interesting events" in interval [arrivalTime, arrivalTime + 2]
                    interesting events = diners leaving / reservations starting
                    Then, you can calculate capacity of the restaurant at all intervals
                    Ensure the restaurant always has [partySize] seats */

        //Add to dining events if seating available
        LocalDateTime endTime = arrivalTime.plusMinutes(119);
        // step (1), sort all diningEvents by reservation time.
        diningEvents.sort(new Comparator<Reservation>() {
            @Override
            public int compare(Reservation r1, Reservation r2) {
                Duration duration = Duration.between(r1.getReservationDateTime(), r2.getReservationDateTime());

                return -(int) duration.toMinutes();
            }
        });

        // step (2), calculate current capacity in restaurant
        calculateCapacity(arrivalTime);
        

        // step (3), calculate max capacity restaurant will be at in next 2 hrs.
        int maxRollingCapacity = this.currentCapacity;
        int simulatedCapacity = maxRollingCapacity;
        for (Reservation r : diningEvents) {
            
            if (r.getStatus() != -1 && isBetween(arrivalTime, endTime, r.getReservationEndTime())) {
                simulatedCapacity -= r.getPartySize();
            }
            if (r.getStatus() != -1 && isBetween(arrivalTime, endTime, r.getReservationDateTime())) {
                simulatedCapacity += r.getPartySize();
            }
            maxRollingCapacity = Math.max(maxRollingCapacity, simulatedCapacity);
        } 
        
        return partySize <= this.maxCapacity - maxRollingCapacity;
    }


    public void addRevenue(int bill) {
        this.totalRevenue += bill;
    }

    public void addReview(Review newReview) {
        if (reviews != null) {
            this.reviews.add(newReview);
        }
    }
   
    public void addDiningEvent(Reservation r) {
        this.diningEvents.add(r);
    }
    public boolean makeRestaurantReservation(Reservation r){
        if (availableSeating(r.getReservationDateTime(), r.getPartySize())) {

            addDiningEvent(r);
            return true;
        }
        return false;
    }

    public String toString(){
        return this.restaurantID;
    }
    
    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getRestaurantGroupID() {
        return restaurantGroupID;
    }

    public void setRestaurantGroupID(String restaurantGroupID) {
        this.restaurantGroupID = restaurantGroupID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void updateAvgRating() {
        int sum = 0;
        for (Review rev: reviews) {
            sum += rev.getScore();
        }
        avgRating = sum / reviews.size();
    }
    public int getAvgRating() {
        return this.avgRating;
    }
    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Reservation> getDiningEvents() {
        return diningEvents;
    }

    public void setDiningEvents(List<Reservation> diningEvents) {
        this.diningEvents = diningEvents;
    }


    public boolean isTop10() {
        return top10;
    }

    public void setTop10(boolean top10) {
        this.top10 = top10;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getUnifiedLicense() {
        return unifiedLicense;
    }

    public void setUnifiedLicense(String unifiedLicense) {
        this.unifiedLicense = unifiedLicense;
    }
    public Owner getOwner(){
        return this.owner;
    }
    public void setOwner(Owner owner){
        this.owner = owner;
    }
    public String getOwnerName(){
        return this.owner.getOwnerName();
    }
    public String getOwnerID(){
        return this.owner.getOwnerID();
    }
    public Menu getMenu() {
        return menu;
    }

    public static List<Restaurant> getRestaurants() {
        return restaurants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Double.compare(avgRating, that.avgRating) == 0 && top10 == that.top10 && maxCapacity == that.maxCapacity && currentCapacity == that.currentCapacity && Double.compare(totalRevenue, that.totalRevenue) == 0 && Objects.equals(restaurantID, that.restaurantID) && Objects.equals(restaurantGroupID, that.restaurantGroupID) && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(reviews, that.reviews) && Objects.equals(diningEvents, that.diningEvents) && Objects.equals(unifiedLicense, that.unifiedLicense) && Objects.equals(owner, that.owner) && Objects.equals(menu, that.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantID, restaurantGroupID, name, address, avgRating, reviews, diningEvents, top10, maxCapacity, currentCapacity, totalRevenue, unifiedLicense, owner, menu);
    }
}
