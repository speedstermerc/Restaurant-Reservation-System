package src;
import java.util.*;

public class Bill {

    class Pair<L,R> { //ask neal
        private  L left; //Item
        private  R right; //Quantity
        public Pair(L left, R right){
            this.left = left;
            this.right = right;
        }
        public L getLeft() {
            return this.left;
        }
        public R getRight() {
            return this.right;
        }
    }

    private List<Pair<MenuItem, Integer>> billedItems = new ArrayList<>();
    private Restaurant restaurant;
    private Customer customer;
     
    private String reservationDateTime;
    protected final static List<Bill> bills = new ArrayList<>();

    public Bill(Restaurant restaurant, Customer customer, String reservationDate, String reservationTime){
        this.customer = customer;
        this.restaurant = restaurant;
        this.reservationDateTime = reservationDate + reservationTime;
        bills.add(this);
    }

    public void addItem(MenuItem item, int timesOrdered) {
        billedItems.add(new Pair<MenuItem, Integer>(item, timesOrdered));
    }

    public int getTimesOrdered(String menuItemName){
        int count = 0;
        for (Pair<MenuItem,Integer> order : billedItems) {
            if (order.getLeft().getName().equalsIgnoreCase(menuItemName)) {
                count += order.getRight();
            }
        }
        return count;
    }


    public static List<Bill> restaurantBills(Restaurant r) {
        List<Bill> resBill = new ArrayList<>();
        for (Bill bill : bills) {
            if (bill.restaurant.equals(r)) {
                resBill.add(bill);
            }
        }

        return resBill;
    }


    public Customer getCustomer() {
        return customer;
    }
    public String getCustomerName(){
        return customer.getCustomerName();
    }
    public Restaurant getRestaurant() {
        return restaurant;
    }
    public String getRestaurantName(){
        return restaurant.getName();
    }
   
    public List<Pair<MenuItem,Integer>> getBilledItems() {return billedItems; }

    public static Bill findBill(String restaurant, String customer, String reservationDateString) {
        for (Bill bill : bills) {
            if (customer.equals(bill.getCustomer().getCustomerID()) && restaurant.equals(bill.getRestaurant().getRestaurantID())
                && reservationDateString.equals(bill.reservationDateTime)) {
                return bill;
            }
        }
        System.out.println("ERROR: Bill not found");
        return null;
    }
    public void addToBill(Restaurant r, Customer c, String itemName, int timesOrdered) {
        MenuItem m = r.getMenu().find_item(itemName);
        if (m == null) {
            System.out.printf("%s not found in MenuItems\n", itemName);
        } else {
            int costAdded = restaurant.getMenu().getPrice(m) * timesOrdered;
            if (costAdded > c.getCustomerFunds()) {
                System.out.println("ERROR: Customer does not have enough funds to order this.");
            } else {
                addItem(m, timesOrdered);
            /*
            Menu item successfully ordered
            Total Price for ordered amount: 15
            CUST003 updated bill: 35
            CUST003 updated funds: 65
            REST004 total revenue from all reservations: 35
             */
                System.out.print("Menu item successfully ordered\n");
                System.out.printf("Total Price for ordered amount: %d\n", costAdded);
                int total = calculateTotalPrice();
                System.out.printf("%s bill for current reservation: %d\n", customer.getCustomerID(), total);
                customer.decreaseFunds(costAdded);
                System.out.printf("%s remaining funds: %d\n", customer.getCustomerID(), customer.getCustomerFunds());
                restaurant.setTotalRevenue(restaurant.getTotalRevenue() + costAdded);
                System.out.printf("%s total revenue from all reservations: %d\n", restaurant.getRestaurantID(), restaurant.getTotalRevenue());
            }
        }
    }

    public int calculateTotalPrice() {
        int total = 0;
        for (Pair<MenuItem, Integer> p : billedItems) {
            total += restaurant.getMenu().getPrice(p.getLeft()) * p.getRight();
        }
        return total;
    }
}