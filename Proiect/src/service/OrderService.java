package service;

import model.Order;
import model.audit.Auditable;
import repository.OrderProductRepository;
import repository.OrderRepository;

import java.io.FileNotFoundException;

public class OrderService {

    private OrderRepository orderRepository;
    private OrderProductRepository orderProductRepository;

    public OrderService() throws FileNotFoundException {
        this.orderRepository = OrderRepository.getInstance();
        this.orderProductRepository = OrderProductRepository.getInstance();
    }

    public void postOrder(Order order){
        orderRepository.post(order);
        orderProductRepository.postOrderProduct(order.getOrderProductList());
    }
}
