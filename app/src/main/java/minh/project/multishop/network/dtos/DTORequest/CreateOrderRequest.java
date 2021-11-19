package minh.project.multishop.network.dtos.DTORequest;

import java.util.List;

import minh.project.multishop.network.dtos.DTOmodels.OrderItemDTO;

public class CreateOrderRequest {
    public String name;
    public String address;
    public String phone_number;
    public int payment;
    public List<OrderItemDTO> items;
}
