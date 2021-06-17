package br.com.courier.services;

import br.com.courier.enuns.Status;
import br.com.courier.interfaces.OrderRepository;
import br.com.courier.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void saveNewOrder(Order order) {

        order.setStatus(Status.OPEN);

        orderRepository.save(order);

    }

    public List<Order> findOrder(Long id){

        List<Order> orderList = new ArrayList<>();
        orderList.add(orderRepository.findByIdOrder(id));

        return orderList;

    }

    public List<Order> findAll(){

        return orderRepository.findAll();

    }

    public void updateOrder(Order order){

        Order orderFind = orderRepository.findByIdOrder(order.getIdOrder());

        orderFind.setStatus(order.getStatus());
        orderFind.setDelivered(order.getDelivered());

        orderRepository.save(orderFind);

    }

}
