package src;
import java.util.*;

public class Owner {
     private String ownerID;
     private String fname;
     private String lname;
     private Address address;
     private String RestaurantGroup;
     private final String beginDate;
     private List<Restaurant> ownedRestaurants = new ArrayList<>();

     private static final List<Owner> owners = new ArrayList<>();

     public Owner(String ownerID, String fname, String lname, String city, String state, String zip, String beginDate, String RestaurantGroup) {
         this.ownerID = ownerID;
         this.fname = fname;
         if (lname.equals("null")){this.lname = null;}
         else{this.lname = lname;}
         this.beginDate = beginDate;
         this.RestaurantGroup = RestaurantGroup;
         address = new Address(city, state, zip);
         if (findOwner(ownerID) != null) {
             System.out.println("ERROR: duplicate unique identifier");
         } else {
            System.out.printf("Owner added: %s - %s\n", this.getOwnerID(), this.getOwnerName());
             owners.add(this);
         }
     }

    public static Owner findOwner(String ownerID) {
        for (Owner o : owners) {
            if (o.getOwnerID().equals(ownerID)) {
                return o;
            }
        }
        return null;
    }

    public void viewOwnedRestaurants() {
         //REST001 (Mellow Mushroom): Unified License - FS817
         for (Restaurant r : ownedRestaurants) {
             System.out.printf("%s (%s): Unified License - %s\n", r.getRestaurantID(), r.getName(), r.getUnifiedLicense());
         }
    }

    public static void viewOwners(String group) {
         if (owners.isEmpty()) {
             System.out.println("ERROR: No owner exists");
             return;
         }
         int count = 0;
         for (Owner o : owners) {
             if (o.getRestaurantGroup().equals(group)) {
                 count++;
                 if (o.lname == null) {
                     System.out.println(o.getOwnerFname());
                 } else {
                     System.out.println(o.getOwnerFname() + " " + o.getOwnerLname());
                 }
             }
         }
         if (count == 0) {
             System.out.println("ERROR: Group doesn't exist");
         }
    }

     public void registerRestaurant(Restaurant r) {
         ownedRestaurants.add(r);
     }

     public String getOwnerID(){
        return this.ownerID;
     }
     public String getOwnerFname(){
        return this.fname;
    }
    public String getOwnerLname(){
        return this.lname;
    }
    public String getOwnerName(){
        if (getOwnerLname() == null) {
            return getOwnerFname();
        }
        return getOwnerFname() + " " + getOwnerLname();
    }
    public String getRestaurantGroup() {
         return RestaurantGroup;
    }

    public static List<Owner> getOwners() {
        return owners;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "ownerID='" + ownerID + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", RestaurantGroup='" + RestaurantGroup + '\'' +
                '}';
    }
}
