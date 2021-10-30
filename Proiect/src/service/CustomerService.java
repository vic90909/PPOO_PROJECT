package service;

import model.Customer;
import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import repository.CustomerRepository;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CustomerService {

    CustomerRepository customerRepository;

    public CustomerService() throws FileNotFoundException {
        this.customerRepository = CustomerRepository.getInstance();
    }

    public TreeMap<String, Customer> getAllCustomers() {
        return new TreeMap<>(customerRepository.findAllCustomersOrderByName());
    }

    public void showCustomers() {
        int i = 0;
        for (Customer each : getAllCustomers().values()) {
            System.out.println(i + ". :" + each.toString());
            i++;
        }
    }

    public Customer findByUuid(String uuid) {
        Customer found = customerRepository.findCustomerByUuid(uuid);
        if (!Objects.isNull(found)) {
            System.out.println("The customer with the given uuid: " + uuid + " is: " + found.toStringNice());
        } else {
            System.out.println("No customer found with the given uuid: " + uuid + ".");
        }
        return found;
    }

    public Customer postCustomer(Scanner scanner) throws FileNotFoundException {
        Customer newCustomer = inputCustomer(scanner);

        if (newCustomer != null) {
            customerRepository.postCustomer(newCustomer);
        } else {
            System.out.println("You did not create any customer (null)");
        }
        return newCustomer;
    }

    private Customer inputCustomer(Scanner scanner) {
        Customer newCustomer = null;
        do {
            System.out.println("Name: ");
            String name = scanner.nextLine();
            System.out.println("Surname: ");
            String surname = scanner.nextLine();
            System.out.println("Phone Number: ");
            String phoneNumber = scanner.nextLine();
            System.out.println("Address: ");
            String address = scanner.nextLine();

            System.out.println("Date of Birth: ");

            boolean ok = false;
            Integer year = 0;
            while (ok == false) {
                ok = true;
                try {
                    System.out.println("Year: ");
                    year = Integer.valueOf(scanner.nextLine());
                } catch (IllegalArgumentException exception) {
                    System.out.println("Wrong argument for Year. Try again");
                    ok = false;
                }
            }

            ok = false;
            Integer month = 0;
            while (ok == false) {
                ok = true;
                try {
                    System.out.println("Month: ");
                    month = Integer.valueOf(scanner.nextLine());
                    if (month > 12 || month < 1) {
                        System.out.println("The month is not in the right bounds(1-12)");
                        ok = false;
                    }
                } catch (IllegalArgumentException exception) {
                    System.out.println("Wrong argument for Month. Try again");
                    ok = false;
                }
            }

            Integer day = 0;
            ok = false;
            while (ok == false) {
                ok = true;
                try {
                    System.out.println("Day: ");
                    day = Integer.valueOf(scanner.nextLine());
                    if (day > 31 || day < 1) {
                        System.out.println("The day is not in the right bounds(1-31)");
                        ok = false;
                    }
                    Integer finalMonth = month;
                    if ((Arrays.asList(4, 6, 9, 11).stream().filter(a -> a == finalMonth).collect(Collectors.toList()).size() > 0
                            && day == 31) || (month == 2 && day > 29 && year % 4 == 0) || (month == 2 && day > 28 && year % 4 != 0)) {
                        System.out.println("The day is not in the right bounds, please consider the number of days for month nr."+month);
                        ok = false;
                    }
                }catch (IllegalFieldValueException e){
                    System.out.println("Wrong argument for Day. Try again");
                    ok = false;
                }
                catch (IllegalArgumentException e) {
                    System.out.println("Wrong argument for Day. Try again");
                    ok = false;
                }
            }

            newCustomer = new Customer(name, surname, address, phoneNumber, new DateTime(year, month, day, 0, 0));
            System.out.println(newCustomer.toStringNice());
            System.out.println("This is your choice or do you want to start over? \n Yes to post this customer or No to Start over");

        } while (scanner.nextLine().equalsIgnoreCase("No"));
        return newCustomer;
    }

}
