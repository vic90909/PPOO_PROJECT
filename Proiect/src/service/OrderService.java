package service;

import model.Customer;
import model.Order;
import model.OrderProduct;
import model.Product;
import model.audit.Auditable;
import repository.CustomerRepository;
import repository.OrderProductRepository;
import repository.OrderRepository;
import repository.ProductRepository;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class OrderService {

    private OrderRepository orderRepository;
    private OrderProductRepository orderProductRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;

    public OrderService() throws FileNotFoundException {
        this.orderRepository = OrderRepository.getInstance();
        this.orderProductRepository = OrderProductRepository.getInstance();
        this.customerRepository = CustomerRepository.getInstance();
        this.productRepository = ProductRepository.getInstance();
    }

    public void postOrder(Order order) throws FileNotFoundException {
        if (!Objects.isNull(order)) {
            orderRepository.postOrder(order);
            if (!Objects.isNull(order.getOrderProductList()) && order.getOrderProductList().size()!=0) {
                orderProductRepository.postOrderProduct(order.getOrderProductList());
            } else {
                System.out.println("The list with the products from the order is either null or empty!");
            }
        } else {
            System.out.println("The order is null!");
        }
    }

    public void showOrders() {
        int i = 1;
        for (Order each : getAllOrders()) {
            System.out.println(i + ". :" + each.toString());
            i++;
        }
    }

    public List<Order> getAllOrders(){
        List<Order> orderList = orderRepository.readAllOrders();
        for(Order each: orderList){
            Customer customer = customerRepository.findCustomerByUuid(each.getCustomer().getUuid());
            List<OrderProduct> orderProductList = orderProductRepository.findOrderProductsByUuid(each.getUuid());
            for (OrderProduct orderProduct : orderProductList){
                Product product = productRepository.findProductByUuid(orderProduct.getProduct().getUuid());
                orderProduct.setProduct(product);
            }
            each.setOrderProductList(orderProductList);
            each.setCustomer(customer);
        }

        return orderList;
    }
}
