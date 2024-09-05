package src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Welcome to the Restaurant Reservation System!");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please confirm whether you are an owner (enter 0), a customer (enter 1), or a regulatory agency (enter 2): ");
        int type = scanner.nextInt();

        while (type >= -1 && type <= 2) {
            while (type == 0) {
                OwnerController simulator = new OwnerController();
                Scanner commandLineInput = new Scanner(System.in);
                System.out.print("Owner requests: ");
                simulator.commandHandle(commandLineInput.nextLine());
                if (simulator.exitLoop()) {
                    type = -1;
                }
            }
            while (type == 1) {
                CustomerController simulator = new CustomerController();
                Scanner commandLineInput = new Scanner(System.in);
                System.out.print("Customer requests: ");
                simulator.commandHandle(commandLineInput.nextLine());
                if (simulator.exitLoop()) {
                    type = -1;
                }

            }
            while (type == 2) {
                RegulatoryController simulator = new RegulatoryController();
                Scanner commandLineInput = new Scanner(System.in);
                System.out.print("Regulatory requests: ");
                simulator.commandHandle(commandLineInput.nextLine());
                if (simulator.exitLoop()) {
                    type = -1;
                }
            }

            if (type == -1) {
                Scanner scans = new Scanner(System.in);
                System.out.print("Please confirm whether you are an owner (enter 0), a customer (enter 1), or a regulatory agency (enter 2), or you want to quit (enter 3): ");
                type = scans.nextInt();
            }
        }
    }
}