package minh.project.multishop.network.dtos.DTOResponse;

import java.util.Date;
import java.util.List;

import minh.project.multishop.network.dtos.DTOmodels.OrderItemDTO;
import minh.project.multishop.network.dtos.DTOmodels.Payment;

public class CreateOrderResponse {
    public int id;
    public String status;
    public int user;
    public boolean is_paid;
    public Payment payment;
    public String name;
    public String address;
    public String phone_number;
    public int sum_price;
    public int shipping_fee;
    public int total_cost;
    public Date created_at;
    public Date updated_at;
    public List<OrderItemDTO> items;
}
