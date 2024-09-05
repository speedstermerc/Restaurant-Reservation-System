package src;

import java.util.Scanner;

public class RestaurantController {
    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                if (!commandLineInput.hasNextLine()) {
                    break;
                }

                wholeInputLine = commandLineInput.nextLine().trim();

                if (wholeInputLine.isEmpty() || wholeInputLine.startsWith("//")) {
                    continue;
                }

                tokens = wholeInputLine.split(DELIMITER);
                for (int i = 0; i < tokens.length; i++) {
                    // get rid of whitespace
                    tokens[i] = tokens[i].trim();
                }
                

                if (wholeInputLine.isEmpty() || tokens[0].indexOf("//") == 0) {
                    // System.out.println(wholeInputLine);
                    continue;
                    // instructions to create simulation resources
                }
                System.out.println(">> " + wholeInputLine.trim());
                if (tokens[0].equals("create_restaurant")) {

                    int seating = Integer.parseInt(tokens[6]);
                    if (seating <= 0 ){
                        System.out.println("ERROR: Restaurant capacity must be positive");
                        System.out.println();
                        continue;
                    }
                    String ownerID = tokens[7];

                    Owner o = Owner.findOwner(ownerID);
                    String unifiedLicense = tokens[8];
                    if (o != null) {
                        Restaurant r = new Restaurant(
                            tokens[1],tokens[2],tokens[3],tokens[4],tokens[5],seating,
                            o, unifiedLicense);
                    } else {
                        System.out.println("ERROR: owner doesn't exist");
                    }
                    
                } else if (tokens[0].equals("create_customer")) {
                    /* System.out.print("unique_identifier: " + tokens[1] + ", first_name: " + tokens[2] + ", last_name: " + tokens[3]);
                    System.out.print(", city: " + tokens[4] + ", state: " + tokens[5]  + ", zip_code: " + tokens[6]);
                    System.out.println(", funds: " + tokens[7]); */
                    
                    int funds = Integer.parseInt(tokens[7]);
                    if (funds <=0) {
                        System.out.println("Customer's funds must be positive");
                        System.out.println();
                        continue;
                    }

                    Customer c = new Customer(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5],
                    tokens[6], funds);
                } else if (tokens[0].equals("make_reservation")) {
                    /* System.out.print("customer_identifier: " + tokens[1] + ", restaurant_identifier: " + tokens[2] + ", party_size: " + tokens[3]);
                    System.out.println(", reservation_date: " + tokens[4] + ", reservation_time: " + tokens[5] + ", credits: " + tokens[6]); */
                    
                    int party_size = Integer.parseInt(tokens[3]);
                    if (party_size < 1) {System.out.println("Party size must be positive");System.out.println();continue;}
                    int credits = Integer.parseInt(tokens[6]);
                    if (credits <0) {System.out.println("Credits must be nonnegative");System.out.println();continue;}
                    Customer reservist = Customer.findCustomer(tokens[1]);
                    Restaurant restaurant = Restaurant.findRestaurant(tokens[2]);

                    if (reservist == null || restaurant == null) {
                        System.out.println("Either customer identifier or restaurant identifier is invalid");
                        System.out.println();
                        continue;
                    }
                    System.out.printf("Reservation requested for %s\n",reservist.getCustomerID());

                    reservist.createReservation(party_size, tokens[4], tokens[5], credits, restaurant, false);

                } else if (tokens[0].equals("customer_arrival")) {
                    /* System.out.print("customer_identifier: " + tokens[1] + ", restaurant_identifier: " + tokens[2]);
                    System.out.println(", reservation_date: " + tokens[3] + ", arrival_time: " + tokens[4] + ", reservation_time: " + tokens[5]); */

                    Customer reservist = Customer.findCustomer(tokens[1]);
                    Restaurant restaurant = Restaurant.findRestaurant(tokens[2]);

                    if (reservist == null || restaurant == null) {
                        System.out.println("Either customer identifier or restaurant identifier is invalid");
                        System.out.println();
                        continue;
                    }
                    reservist.handleCustomerArrival(tokens[3], tokens[4], tokens[5], restaurant);

                } else if (tokens[0].equals("create_owner")) {
                    // create_owner, OWN001, Ricardo, Cardenas, Orlando, FL, 32801, 2022-05-30, Restaurant Brands
                    Owner o = new Owner(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5],
                            tokens[6], tokens[7], tokens[8]);
                    
                } else if (tokens[0].equals("add_menu_item")) {
                    // add_menu_item, cheese pizza, REST001, 8
                    if (Integer.parseInt(tokens[3]) < 0) {
                        System.out.println("Cost of item must be nonnegative");
                        System.out.println();
                        continue;
                    }
                    Restaurant r = Restaurant.findRestaurant(tokens[2]);
                    if (r == null) {
                        System.out.println("ERROR: Restaurant does not exist");
                    } else{
                        r.getMenu().addMenuItem(r.getRestaurantID(),tokens[1], Integer.parseInt(tokens[3]));
                    }
                } else if (tokens[0].equals("create_menu_item")) {
                    // create_menu_item, cheese pizza, dough:mozzarella:tomato sauce
                    new MenuItem(tokens[1], tokens[2]);

                } else if (tokens[0].equals("order_menu_item")) {
                    // order_menu_item, CUST003, REST004, 2024-05-25, 19:00, hawaiian pizza, 1
                    Customer c = Customer.findCustomer(tokens[1]);
                    Restaurant r = Restaurant.findRestaurant(tokens[2]);

                    if (r == null) {
                        System.out.println("ERROR: Restaurant does not exist.");
                    } else if (c == null) {
                        System.out.println("ERROR: Customer does not exist.");
                    } else {
                        Bill b = Bill.findBill(r.getRestaurantID(), c.getCustomerID(), tokens[3] + tokens[4]);
                        if (b == null) {
                            System.out.println("Ensure that the customer has a reservation or has arrived at the restaurant at " + tokens[4]);
                            System.out.println();
                            continue;
                        }
                        if (MenuItem.find_item(tokens[5]) == null){System.out.printf("Item %s has not been created\n",tokens[5]); System.out.println();continue;};
                        MenuItem m = r.getMenu().find_item(tokens[5]);
                        if (m != null) {
                            if (Integer.parseInt(tokens[6])<=0) {
                                System.out.printf("Must order 1 or greater of %s\n",tokens[5]);
                                System.out.println();
                                continue;
                            }
                            b.addToBill(r, c, m.getName(), Integer.parseInt(tokens[6]));
                        } else {
                           System.out.printf("Item %s is not offered at %s\n", tokens[5],tokens[2]);
                        }
                    }

                } else if (tokens[0].equals("customer_review")) {
                    //customer_review, customerID, restaurantID, Date, time, score, tags
                    //ERROR: reservation doesn't exist
                    //ERROR: reservation not successfully completed
                    //REST004 (Domino's) rating for this reservation: 55
                    //REST004 (Domino's) average rating: 71
                    //Tags: romantic, live music, lively, vegetarian friendly, sports bar

                    Restaurant r = Restaurant.findRestaurant(tokens[2]);
                    if (r == null) {System.out.println("ERROR: restaurant does not exist");}
                        else {
                            Reservation reservation = Reservation.getReservation(r, tokens[1], tokens[3], tokens[4]);
                            if (reservation == null) {System.out.println("ERROR: reservation doesn't exist");
                            } else {
                                if (reservation.getStatus() == -1 || reservation.getStatus() == 0) {
                                    System.out.println("ERROR: reservation wasn't successfully completed");
                                } else {
                                    Review.addReview(r,Integer.parseInt(tokens[5]), tokens[6]);
                                }
                            }
                        }
                } else if (tokens[0].equals("calculate_average_price")) {
                    // calculate_average_price, menuItem
                    MenuItem.calculateAveragePrice(tokens[1]);

                } else if (tokens[0].equals("calculate_item_popularity")) {
                    // calculate_item_popularity, menuItem
                    MenuItem.calculateItemPopularity(tokens[1]);

                } else if (tokens[0].equals("view_owners")) {
                    String group = tokens[1];
                    Owner.viewOwners(group);
                } else if (tokens[0].equals("view_all_restaurants")) {
                    Restaurant.viewAllRestaurants();
                } else if (tokens[0].equals("view_restaurants_owned")) {
                    //>> view_restaurants_owned, OWN001
                    Owner o = Owner.findOwner(tokens[1]);
                    if (o == null) {
                        System.out.println("ERROR: Owner does not exist.");
                    } else {
                        o.viewOwnedRestaurants();
                    }
                } else if (tokens[0].equals("view_all_customers")) {
                    Customer.viewCustomers();
                } else if (tokens[0].equals("view_all_menu_items")) {
                    MenuItem.viewItems();
                } else if (tokens[0].equals("view_ingredients")) {
                    //>> view_ingredients, cheese pizza
                    String itemName = tokens[1];
                    MenuItem item = MenuItem.find_item(itemName);
                    if (item == null) {
                        System.out.println("ERROR: Item does not exist.");
                    } else {
                        item.viewIngredients();
                    }
                } else if (tokens[0].equals("view_menu_items")) {
                    //view_menu_items, REST001
                    Restaurant r = Restaurant.findRestaurant(tokens[1]);
                    if (r == null) {
                        System.out.println("ERROR: restaurant does not exist.");
                    } else {
                        System.out.println(r.getMenu().toString());
                    }
                }else if (tokens[0].equals("exit")) {
                    break;
                } else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                } 
                //System.out.println("Enter command: ");
            } catch (Exception e) {
                displayMessage("error", "during command loop >> execution");
                e.printStackTrace();
                System.out.println();
            }
            System.out.println();
        }
        commandLineInput.close();
    }

    void displayMessage(String status, String text_output) {
        System.out.println(status.toUpperCase() + ": " + text_output.toLowerCase());
    }
}