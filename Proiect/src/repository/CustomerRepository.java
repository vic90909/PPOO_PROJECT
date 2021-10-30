package repository;

import model.Customer;
import utils.AppendableObjectOutputStream;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.TreeMap;

import static utils.Constants.CUSTOMER_FILENAME;

public class CustomerRepository {

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private static CustomerRepository instance = null;

    private CustomerRepository() {
    }

    public static CustomerRepository getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    public void postCustomer(Customer customer) {
        try {
            openOutputStream();
            objectOutputStream.writeObject(customer);
        } catch (IOException e) {
            System.out.println("The customer could not be written to the file");
        }
        closeOutputStream();
    }

    public Customer findCustomerByUuid(String uuid) {
        Customer customer = null;
        openInputStream();
        try {
            do {
                customer = (Customer) objectInputStream.readObject();
                if (customer.getUuid().equals(uuid) && !customer.isLogicallyDeleted()) {
                    closeInputStream();
                    return customer;
                }
            } while (customer != null);

        }catch (EOFException eofException){
            System.out.println("The customer could not be found in the file with the specified uuid");
            return null;
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        closeInputStream();
        return customer;
    }

    public TreeMap<String, Customer> findAllCustomersOrderByName() {
        TreeMap<String, Customer> map = new TreeMap<>();
        Customer customer = null;
        openInputStream();
        try {
            do {
                customer = (Customer) objectInputStream.readObject();
                if (!customer.isLogicallyDeleted()) {
                    map.put(customer.getUuid(), customer);
                }
            } while (customer != null);

        }catch (EOFException eofException){
            closeInputStream();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("The customers could not be retrieved from the file ");
        }
        return map;
    }

    private void closeOutputStream() {
        try {
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("The ouput stream could not be closed");
        }
    }

    private void closeInputStream() {
        try {
            objectInputStream.close();
        } catch (IOException e) {
            System.out.println("The input stream could not be closed");
        }
    }

    private void openOutputStream() {
        File customerFile = new File(CUSTOMER_FILENAME.getName());
        try {
            if (customerFile.exists()) {
                objectOutputStream = new AppendableObjectOutputStream(new FileOutputStream(CUSTOMER_FILENAME.getName(), true));
            } else {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(CUSTOMER_FILENAME.getName()));
            }
        } catch (IOException e) {
            System.out.println("The stream could not be closed");
        }
    }

    private void openInputStream() {
        File customerFile = new File(CUSTOMER_FILENAME.getName());
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(CUSTOMER_FILENAME.getName()));
        } catch (IOException e) {
            System.out.println("The stream could not be closed");
        }
    }

}
