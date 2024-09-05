package src;
import javax.naming.ldap.Control;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Gui extends JFrame {
    RestaurantControllerGUI backend = new RestaurantControllerGUI();
    JComboBox<String> comboBox;
    JPanel mainFrame;
    JPanel ComboBoxPanel;
    JTextArea OUTPUTTEXTAREA = new JTextArea();
    JComboBox<String> ControllerSelector = new JComboBox<>(new String[]{"","Customer","Owner","Regulatory Agency"});
    ArrayList<JTextField> allTextFields = new ArrayList<>();


    // People Specific
    public JTextField PersonFName = new JTextField(10);
    public JTextField PersonLName = new JTextField(10);
    public JTextField PersonCity = new JTextField(10);
    public JTextField PersonState = new JTextField(10);
    public JTextField PersonZip = new JTextField(10);

    // Customer Specific
    public JTextField CustID = new JTextField(10);
    public JTextField CustFName = new JTextField(10);
    public JTextField CustLName = new JTextField(10);
    public JTextField CustCity = new JTextField(10);
    public JTextField CustState = new JTextField(10);
    public JTextField CustZip = new JTextField(10);
    public JTextField CustFunds = new JTextField(10);
    public JButton SUBMITCUSTOMER = new JButton("Create Customer");
    JPanel CustomerInputs;

    //Owner Specific
    public JTextField OwnerID = new JTextField(10);
    public JTextField OwnerFName = new JTextField(10);
    public JTextField OwnerLName = new JTextField(10);
    public JTextField OwnerCity = new JTextField(10);
    public JTextField OwnerState = new JTextField(10);
    public JTextField OwnerZip = new JTextField(10);
    public JTextField ManageStartDate = new JTextField(10);
    public JTextField RestaurantGroup = new JTextField(10);
    public JButton SUBMITOWNER = new JButton("Create Owner");
    JPanel OwnerInputs;

    // Restaurant Specific
    public JTextField RestID = new JTextField(10);
    public JTextField RestName = new JTextField(10);
    public JTextField RestCity = new JTextField(10);
    public JTextField RestState = new JTextField(10);
    public JTextField RestZip = new JTextField(10);
    public JTextField RestCapacity = new JTextField(10);
    public JTextField RestManager = new JTextField(10);
    public JTextField RestLicense = new JTextField(10);
    public JButton SUBMITREST = new JButton("Create Restaurant");
    JPanel RestInputs;

    // menu items
    public JTextField MenuItemName = new JTextField(10);
    public JTextField MenuIngredients = new JTextField(10);
    public JButton SUBMITMENUITEM = new JButton("CREATE Menu Item");
    JPanel MenuItemInputs;

    // add menu items
    public JTextField menuAddName = new JTextField(10);
    public JTextField restaurantAddName = new JTextField(10);
    public JTextField menuPrice = new JTextField(10);
    public JButton SUBMITADDMENU = new JButton("ADD Menu Item" );
    JPanel AddMenuInputs;

    // make reservation

    public JTextField ResCustID = new JTextField(10);
    public JTextField ResResID = new JTextField(10);
    public JTextField ResParty = new JTextField(10);
    public JTextField ResDate = new JTextField(10);
    public JTextField ResTime = new JTextField(10);
    public JTextField ResCredits = new JTextField(10);
    public JButton SUBMITRES = new JButton("Reserve");
    JPanel AddReservationInputs;

    //customer_arrival
    //CUST001, REST005, 2024-05-31, 19:30, 19:00
    public JTextField ArrivalCust = new JTextField(10);
    public JTextField ArrivalRest = new JTextField(10);
    public JTextField ArrivalDate = new JTextField(10);
    public JTextField ArrivalTime = new JTextField(10);
    public JTextField AnticipatedArrival = new JTextField(10);
    public JButton SUBMITARRIVAL = new JButton("Log Arrival");
    JPanel AddCustomerArrival;

    //order menu items
    //order_menu_item, CUST001, REST001, 2024-05-24, 19:00, cheese pizza, 2
    public JTextField OrderCust = new JTextField(10);
    public JTextField OrderRest = new JTextField(10);
    public JTextField OrderDate = new JTextField(10);
    public JTextField OrderTime = new JTextField(10);
    public JTextField OrderItem = new JTextField(10);
    public JTextField OrderQuantity = new JTextField(10);
    public JButton SUBMITORDER = new JButton("Order");
    JPanel AddOrderFood;

    //review
    //customer_review, CUST004, REST006, 2024-05-22, 19:00, 87, lively
    public JTextField ReviewCust = new JTextField(10);
    public JTextField ReviewRest = new JTextField(10);
    public JTextField ReviewDate = new JTextField(10);
    public JTextField ReviewTime = new JTextField(10);
    public JTextField ReviewScore = new JTextField(10);
    public JTextField ReviewTags = new JTextField(10);
    public JButton SUBMITREVIEW = new JButton("Review");
    JPanel AddReview;

    // calculate avg price
    // calculate_average_price, pineapple pizza
    public JTextField AvgPriceItem = new JTextField(10);
    public JButton SUBMITAVGPRICE = new JButton("Find Average");
    JPanel AddAveragePrice;

    // calculate item popularity
    public JTextField PopItem = new JTextField(10);
    public JButton SUBMITPOP = new JButton("Find Popularity");
    JPanel AddPopularity;

    // view restaurant owns
    public JTextField OwnerGroup = new JTextField(10);
    public JButton SUBMITGROUPRES = new JButton("Find Owners in Group");
    JPanel AddViewGroup;

    public JTextField ItemIngredients = new JTextField(10);
    public JButton SUBMITITEMINGREDIENTS = new JButton("Find Ingredients");
    JPanel AddViewIngredients;

    public JTextField RestMenu = new JTextField(10);
    public JButton SUBMITMENUITEMS = new JButton("Find All Menu Items");
    JPanel AddViewMenuItems;


    public JTextField OwnerIDRest = new JTextField(10);
    public JButton SUBMITOWNERID = new JButton("Find Restaurants Owned");
    JPanel AddViewOwns;

    public JButton VIEWALLRESTAURANTS = new JButton("View All Restaurants");
    JPanel AddViewRest;

    public JButton VIEWALLCUSTOMERS = new JButton("View All Customers");
    JPanel AddViewCust;

    public JButton VIEWALLMENUITEMS = new JButton("View All Menu Items");
    JPanel AddViewAllMenuItems;


    ArrayList<JPanel> InputPanels = new ArrayList<>();
    private final int MARGIN = 20;

    String[] items = {"Create Customer", "Create Restaurant", "Create Owner", "Create Menu Item", "Add Item to Restaurant", "Make Reservation",
            "Customer Arrival", "Order Menu Item", "Customer Review", "Find Average Price", "Find Item Popularity",
            "View Owners in Group", "View Ingredients", "View Restaurant Menu Items", "View Restaurants Owned",
            "View All Customers", "View All Restaurants", "View All Menu Items"};

    String[] REGCMDS = new String[]{
            "View Restaurants Owned",
            "View Owners in Group",
            "View All Restaurants",
            "View All Customers",
            "View All Menu Items",
            "View Ingredients",
            "View Restaurant Menu Items"
    };
    String[] CUSTCMDS = new String []{
            "Create Customer",
            "Make Reservation",
            "Order Menu Item",
            "Customer Review",
            "Find Average Price",
            "Find Item Popularity",
            "View All Restaurants",
            "View Restaurant Menu Items",
            "View All Menu Items",
            "View Ingredients"};

    String[] OWNCMDS = new String[]{
            "Create Owner",
            "Create Restaurant",
            "Customer Arrival",
            "Create Menu Item",
            "Find Average Price",
            "Find Item Popularity",
            "Add Item to Restaurant",
            "View Owners in Group",
            "View All Restaurants",
            "View Restaurants Owned",
            "View All Customers",
            "View Ingredients",
            "View Restaurant Menu Items",
            "View All Menu Items"
    };

    public JPanel makeSelector(){
        JPanel SelectorChoice = new JPanel();
        SelectorChoice.setBorder(new EmptyBorder(MARGIN,MARGIN,MARGIN,MARGIN));

        SelectorChoice.add(LabeledToggle("What User are You?      ", ControllerSelector));
        return SelectorChoice;
    }
    public JPanel makeComboBox(){
        JPanel ComboBoxChoice = new JPanel();
        ComboBoxChoice.setBorder(new EmptyBorder(MARGIN,MARGIN,MARGIN,MARGIN));

        comboBox = new JComboBox<>();
        comboBox.addItem("");
        restoreComboBox();


        ComboBoxChoice.add(LabeledToggle("Choose a Command!      ", comboBox));
        return ComboBoxChoice;
    }
    public void clearComboBox(){
        for (String command:items) {
            comboBox.removeItem(command);
        }
    }
    public void restoreComboBox(){
        for (String command:items) {
            comboBox.addItem(command);
        }
    }


    public JPanel makeCustomerInputs() {
        ArrayList<JPanel> inputs = new ArrayList<>();

        // really ugly but java swing only takes one input binding :/
        inputs.add(LabeledToggle("Customer ID:", CustID));
        inputs.add(LabeledToggle("Customer First Name:", CustFName));
        inputs.add(LabeledToggle("Customer Last Name:", CustLName));
        inputs.add(LabeledToggle("Customer City:", CustCity));
        inputs.add(LabeledToggle("Customer State:", CustState));
        inputs.add(LabeledToggle("Customer Zip:", CustZip));
        inputs.add(LabeledToggle("Customer Funds:", CustFunds));

        return makeInputJPanel(inputs, SUBMITCUSTOMER, MARGIN);

    }
    public JPanel makeOwnerInputs() {
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Owner ID:", OwnerID));
        inputs.add(LabeledToggle("Owner First Name:", OwnerFName));
        inputs.add(LabeledToggle("Owner Last Name:", OwnerLName));
        inputs.add(LabeledToggle("Owner City:", OwnerCity));
        inputs.add(LabeledToggle("Owner State:", OwnerState));
        inputs.add(LabeledToggle("Owner Zip:", OwnerZip));
        inputs.add(LabeledToggle("Start Date (YYYY-MM-DD):", ManageStartDate));
        inputs.add(LabeledToggle("Restaurant Group:", RestaurantGroup));
        return makeInputJPanel(inputs, SUBMITOWNER, MARGIN);
    }

    public JPanel makeRestaurantInputs() {
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Restaurant ID:", RestID));
        inputs.add(LabeledToggle("Restaurant Name:", RestName));
        inputs.add(LabeledToggle("Restaurant City:", RestCity));
        inputs.add(LabeledToggle("Restaurant State:", RestState));
        inputs.add(LabeledToggle("Restaurant Zip:", RestZip));
        inputs.add(LabeledToggle("Restaurant Capacity:", RestCapacity));
        inputs.add(LabeledToggle("Manager ID:", RestManager));
        inputs.add(LabeledToggle("License:", RestLicense));
        return makeInputJPanel(inputs, SUBMITREST, MARGIN);
    }

    public JPanel makeMenuItemInputs(){
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Menu Item Name:", MenuItemName));
        inputs.add(LabeledToggle("Menu Item Ingredients:", MenuIngredients));
        return makeInputJPanel(inputs, SUBMITMENUITEM, MARGIN);
    }

    public JPanel makeAddMenuInputs() {
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Menu Item Name:", menuAddName));
        inputs.add(LabeledToggle("Restaurant ID:", restaurantAddName));
        inputs.add(LabeledToggle("Price:", menuPrice));
        return makeInputJPanel(inputs, SUBMITADDMENU, MARGIN);
    }

    public JPanel makeReservationInputs() {
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Customer ID:",ResCustID));
        inputs.add(LabeledToggle("Restaurant ID:", ResResID));
        inputs.add(LabeledToggle("Party Size:", ResParty));
        inputs.add(LabeledToggle("Reservation Date (YYYY-MM-DD):",ResDate));
        inputs.add(LabeledToggle("Reservation Time (HH:MM):", ResTime));
        inputs.add(LabeledToggle("Credits:",ResCredits));
        return makeInputJPanel(inputs, SUBMITRES, MARGIN);
    }

    public JPanel makeArrivalInputs() {
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Customer ID:", ArrivalCust));
        inputs.add(LabeledToggle("Restaurant ID:", ArrivalRest));
        inputs.add(LabeledToggle("Arrival Date (YYYY-MM-DD):", ArrivalDate));
        inputs.add(LabeledToggle("Arrival Time (HH:MM):", ArrivalTime));
        inputs.add(LabeledToggle("Reservation Time (HH:MM):", AnticipatedArrival));
        return makeInputJPanel(inputs,SUBMITARRIVAL, MARGIN);
    }

    public JPanel makeOrderFoodInputs() {
        ArrayList inputs = new ArrayList();
        inputs.add(LabeledToggle("Customer ID:", OrderCust));
        inputs.add(LabeledToggle("Restaurant ID:", OrderRest));
        inputs.add(LabeledToggle("Date (YYYY-MM-DD):", OrderDate));
        inputs.add(LabeledToggle("Time (HH:MM):", OrderTime));
        inputs.add(LabeledToggle("Item:", OrderItem));
        inputs.add(LabeledToggle("Quantity:", OrderQuantity));
        return makeInputJPanel(inputs, SUBMITORDER, MARGIN);
    }
    public JPanel makeCustomerReview(){
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Customer ID:", ReviewCust));
        inputs.add(LabeledToggle("Restaurant ID:", ReviewRest));
        inputs.add(LabeledToggle("Date (YYYY-MM-DD):", ReviewDate));
        inputs.add(LabeledToggle("Time (HH:MM):", ReviewTime));
        inputs.add(LabeledToggle("Score:", ReviewScore));
        inputs.add(LabeledToggle("Tags:", ReviewTags));

        return makeInputJPanel(inputs, SUBMITREVIEW, MARGIN);
    }
    public JPanel makeAveragePrice() {
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Item:", AvgPriceItem));

        return makeInputJPanel(inputs, SUBMITAVGPRICE, MARGIN);
    }
    public JPanel makePopularity() {
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Item:", PopItem));

        return makeInputJPanel(inputs, SUBMITPOP, MARGIN);
    }

    public JPanel makeViewGroup() {
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Restaurant Group:", OwnerGroup));

        return makeInputJPanel(inputs, SUBMITGROUPRES,MARGIN);
    }

    public JPanel makeViewIngredients() {
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Item Name:", ItemIngredients));

        return makeInputJPanel(inputs, SUBMITITEMINGREDIENTS,MARGIN);
    }

    public JPanel makeViewMenuItems() {
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Restaurant ID:", RestMenu));

        return makeInputJPanel(inputs, SUBMITMENUITEMS,MARGIN);
    }
    public JPanel makeViewOwns() {
        ArrayList<JPanel> inputs = new ArrayList<>();
        inputs.add(LabeledToggle("Owner ID:", OwnerIDRest));

        return makeInputJPanel(inputs, SUBMITOWNERID,MARGIN);
    }


    public Gui() {

        setTitle("Restaurant Reservation");
        setSize(700, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        PrintStream printStream = new PrintStream(new GuiPrinting(OUTPUTTEXTAREA));
        System.setOut(printStream);
        System.setErr(printStream);

        mainFrame = new JPanel();
        mainFrame.setLayout( new BoxLayout(mainFrame, BoxLayout.Y_AXIS) );

        JPanel selectorPanel = makeSelector();
        mainFrame.add(selectorPanel);


        ComboBoxPanel = makeComboBox();
        mainFrame.add(ComboBoxPanel);

        comboBox.setEnabled(false);

        CustomerInputs = makeCustomerInputs();
        mainFrame.add(CustomerInputs);

        OwnerInputs = makeOwnerInputs();
        mainFrame.add(OwnerInputs);

        RestInputs = makeRestaurantInputs();
        mainFrame.add(RestInputs);

        MenuItemInputs = makeMenuItemInputs();
        mainFrame.add(MenuItemInputs);

        AddMenuInputs = makeAddMenuInputs();
        mainFrame.add(AddMenuInputs);

        AddReservationInputs = makeReservationInputs();
        mainFrame.add(AddReservationInputs);

        AddCustomerArrival = makeArrivalInputs();
        mainFrame.add(AddCustomerArrival);

        AddOrderFood = makeOrderFoodInputs();
        mainFrame.add(AddOrderFood);

        AddReview = makeCustomerReview();
        mainFrame.add(AddReview);

        AddAveragePrice = makeAveragePrice();
        mainFrame.add(AddAveragePrice);

        AddPopularity = makePopularity();
        mainFrame.add(AddPopularity);

        AddViewGroup = makeViewGroup();
        mainFrame.add(AddViewGroup);

        AddViewIngredients = makeViewIngredients();
        mainFrame.add(AddViewIngredients);

        AddViewMenuItems = makeViewMenuItems();
        mainFrame.add(AddViewMenuItems);

        AddViewOwns = makeViewOwns();
        mainFrame.add(AddViewOwns);

        AddViewCust = makeInputJPanel(new ArrayList<>(), VIEWALLCUSTOMERS, MARGIN);
        mainFrame.add(AddViewCust);

        AddViewRest = makeInputJPanel(new ArrayList<>(), VIEWALLRESTAURANTS, MARGIN);
        mainFrame.add(AddViewRest);

        AddViewAllMenuItems = makeInputJPanel(new ArrayList<>(), VIEWALLMENUITEMS, MARGIN);
        mainFrame.add(AddViewAllMenuItems);


        bindComboBox();
        bindButtons();


     //   JPanel TextOutput = new JPanel();

        OUTPUTTEXTAREA.setEditable(false);
        OUTPUTTEXTAREA.setFont(new Font("Arial", Font.PLAIN, 16));

    //    TextOutput.setBorder(new EmptyBorder(MARGIN,MARGIN,MARGIN,MARGIN));
    //    TextOutput.add(OUTPUTTEXTAREA);

       // mainFrame.setPreferredSize(new Dimension(500,300));
        //TextOutput.setPreferredSize(new Dimension(500,200));
        JPanel Master = new JPanel(new BorderLayout());
        Master.add(mainFrame, BorderLayout.NORTH);
        Master.add(OUTPUTTEXTAREA, BorderLayout.CENTER);
        add(Master);


    }
    public void addToComboBox(String[] args) {
        for (String c: args) {
            comboBox.addItem(c);
        }
    }
    public void bindComboBox() {
        hideAllInputPanels();
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideAllInputPanels();
                if (comboBox.getSelectedItem().equals("Create Customer")) {
                    CustomerInputs.setVisible(true);
                }
                else if (comboBox.getSelectedItem().equals("Create Owner")) {
                    OwnerInputs.setVisible(true);
                }
                else if (comboBox.getSelectedItem().equals("Create Restaurant")) {
                    RestInputs.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("Create Menu Item")) {
                    MenuItemInputs.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("Add Item to Restaurant")) {
                    AddMenuInputs.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("Make Reservation")) {
                    AddReservationInputs.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("Customer Arrival")) {
                    AddCustomerArrival.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("Order Menu Item")) {
                    AddOrderFood.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("Customer Review")) {
                    AddReview.setVisible(true);
                } else if(comboBox.getSelectedItem().equals("Find Average Price")) {
                    AddAveragePrice.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("Find Item Popularity")) {
                    AddPopularity.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("View Owners in Group")) {
                    AddViewGroup.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("View Ingredients")) {
                    AddViewIngredients.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("View Restaurant Menu Items")) {
                    AddViewMenuItems.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("View Restaurants Owned")) {
                    AddViewOwns.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("View All Customers")) {
                    AddViewCust.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("View All Restaurants")) {
                    AddViewRest.setVisible(true);
                } else if (comboBox.getSelectedItem().equals("View All Menu Items")) {
                    AddViewAllMenuItems.setVisible(true);
                }
                else {
                    hideAllInputPanels();
                }
            }
        });
        ControllerSelector.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ControllerSelector.getSelectedItem().equals("Customer")) {

                    clearComboBox();
                    addToComboBox(CUSTCMDS);
                    comboBox.setEnabled(true);
                } else if (ControllerSelector.getSelectedItem().equals("Owner"))  {
                    clearComboBox();
                    addToComboBox(OWNCMDS);

                    comboBox.setEnabled(true);
                } else if (ControllerSelector.getSelectedItem().equals("Regulatory Agency")) {
                    clearComboBox();
                    addToComboBox(REGCMDS);

                    comboBox.setEnabled(true);
                } else {
                    comboBox.setEnabled(false);
                }
            }
        });
    }

    public void bindButtons() {
        SUBMITCUSTOMER.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("create_customer");
                s.add(CustID.getText());
                s.add(CustFName.getText());
                s.add(CustLName.getText());
                s.add(CustCity.getText());
                s.add(CustState.getText());
                s.add(CustZip.getText());
                s.add(CustFunds.getText());

                submit(s);
            }
        });

        SUBMITOWNER.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("create_owner");
                s.add(OwnerID.getText());
                s.add(OwnerFName.getText());
                s.add(OwnerLName.getText());
                s.add(OwnerCity.getText());
                s.add(OwnerState.getText());
                s.add(OwnerZip.getText());
                s.add(ManageStartDate.getText());
                s.add(RestaurantGroup.getText());

                submit(s);
            }
        });

        SUBMITREST.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("create_restaurant");
                s.add(RestID.getText());
                s.add(RestName.getText());
                s.add(RestCity.getText());
                s.add(RestState.getText());
                s.add(RestZip.getText());
                s.add(RestCapacity.getText());
                s.add(RestManager.getText());
                s.add(RestLicense.getText());

                submit(s);
            }
        });
        SUBMITMENUITEM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("create_menu_item");
                s.add(MenuItemName.getText());
                s.add(MenuIngredients.getText());

                submit(s);
            }
        });

        SUBMITADDMENU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("add_menu_item");
                s.add(menuAddName.getText());
                s.add(restaurantAddName.getText());
                s.add(menuPrice.getText());

                submit(s);
            }
        });
        SUBMITRES.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("make_reservation");
                s.add(ResCustID.getText());
                s.add(ResResID.getText());
                s.add(ResParty.getText());
                s.add(ResDate.getText());
                s.add(ResTime.getText());
                s.add(ResCredits.getText());

                submit(s);
            }
        });

        SUBMITARRIVAL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("customer_arrival");
                s.add(ArrivalCust.getText());
                s.add(ArrivalRest.getText());
                s.add(ArrivalDate.getText());
                s.add(ArrivalTime.getText());
                s.add(AnticipatedArrival.getText());

                submit(s);
            }
        });

        SUBMITORDER.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("order_menu_item");
                s.add(OrderCust.getText());
                s.add(OrderRest.getText());
                s.add(OrderDate.getText());
                s.add(OrderTime.getText());
                s.add(OrderItem.getText());
                s.add(OrderQuantity.getText());

                submit(s);
            }
        });

        SUBMITREVIEW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("customer_review");
                s.add(ReviewCust.getText());
                s.add(ReviewRest.getText());
                s.add(ReviewDate.getText());
                s.add(ReviewTime.getText());
                s.add(ReviewScore.getText());
                s.add(ReviewTags.getText());

                submit(s);
            }
        });
        SUBMITAVGPRICE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("calculate_average_price");
                s.add(AvgPriceItem.getText());

                submit(s);
            }
        });

        SUBMITPOP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("calculate_item_popularity");
                s.add(PopItem.getText());

                submit(s);
            }
        });

        SUBMITGROUPRES.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("view_owners");
                s.add(OwnerGroup.getText());

                submit(s);
            }
        });
        SUBMITITEMINGREDIENTS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("view_ingredients");
                s.add(ItemIngredients.getText());

                submit(s);
            }
        });
        SUBMITMENUITEMS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("view_menu_items");
                s.add(RestMenu.getText());

                submit(s);
            }
        });
        SUBMITOWNERID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("view_restaurants_owned");
                s.add(OwnerIDRest.getText());

                submit(s);
            }
        });
        VIEWALLCUSTOMERS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("view_all_customers");


                submit(s);
            }
        });
        VIEWALLRESTAURANTS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("view_all_restaurants");


                submit(s);
            }
        });
        VIEWALLMENUITEMS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> s = new ArrayList<>();
                s.add("view_all_menu_items");


                submit(s);
            }
        });
    }

    public void submit(ArrayList<String> inputs) {
        OUTPUTTEXTAREA.setText("");
        String INPUT = String.join(",", inputs);
        backend.commandHandle(INPUT);
        clearAllText();
        pack();
    }

    public void clearAllText(){
        for (JTextField text : allTextFields ) {
            text.setText("");
        }

    }

    public JPanel addArguments(JPanel P, ArrayList<JPanel>args) {
        for (JPanel p : args) {
            P.add(p);
        }
        return P;
    }
    public void hideAllInputPanels(){
        for (JPanel p : InputPanels) {
            p.setVisible(false);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(700, 800);
    }
    public JPanel makeInputJPanel(ArrayList<JPanel> args, JButton SUBMIT, int padding) {
        JPanel P = new JPanel();
        P.setLayout(new BoxLayout(P, BoxLayout.Y_AXIS));

        P.setBorder(new EmptyBorder(padding, padding, padding, padding));
        addArguments(P, args);
        JPanel submitWrapper = new JPanel();
        submitWrapper.setLayout(new BoxLayout(submitWrapper, BoxLayout.X_AXIS));
        submitWrapper.add(Box.createHorizontalGlue());
        submitWrapper.add(SUBMIT);
        submitWrapper.add(Box.createHorizontalGlue());

        P.add(submitWrapper);
        InputPanels.add(P);
        return P;
    }
    public <T extends Component> JPanel LabeledToggle(String caption, T argument){
        if (argument instanceof JTextField){
            allTextFields.add((JTextField)argument);
        }
        JPanel captionedToggle = new JPanel(new BorderLayout());
        caption += "       ";

        captionedToggle.add(new JLabel(caption), BorderLayout.WEST);
        captionedToggle.add(argument, BorderLayout.EAST);
        return captionedToggle;
    }
    public static void main(String[] args) {
        Gui ex = new Gui();

        ex.setVisible(true);
    }
}