package hello.shop.repository.order;

import com.querydsl.core.annotations.QueryProjection;
import hello.shop.entity.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class OrderDtoV1ListVer {

    private Long orderId;
    private String memberName;
    private List<String> itemNames = new ArrayList<>();
    private List<Integer> orderItemCounts = new ArrayList<>();
    private OrderStatus orderStatus;

    public OrderDtoV1ListVer(Long orderId, String memberName, List<String> itemNames, List<Integer> orderItemCounts, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.memberName = memberName;
        this.itemNames = itemNames;
        this.orderItemCounts = orderItemCounts;
        this.orderStatus = orderStatus;
    }

    public OrderDtoV1ListVer(){};

    public void addItemName(String itemName){
        itemNames.add(itemName);
    }

    public void addOrderItemCounts(Integer orderItemCount){
        orderItemCounts.add(orderItemCount);
    }

    public static ArrayList<OrderDtoV1ListVer> osToOls(List<OrderDtoV1> os){
        ArrayList<OrderDtoV1ListVer> ols = new ArrayList<>();
        for (OrderDtoV1 o : os) {

            Optional<OrderDtoV1ListVer> optionalOl = ols.stream().filter(orderDto -> orderDto.getOrderId() == o.getOrderId()).findFirst();
            if (optionalOl.isPresent()) {
                optionalOl.get().addItemName(o.getItemName());
                optionalOl.get().addOrderItemCounts(o.getOrderItemCount());
            } else {
                OrderDtoV1ListVer ol = new OrderDtoV1ListVer();
                ol.setOrderId(o.getOrderId());
                ol.setMemberName(o.getMemberName());
                ol.addItemName(o.getItemName());
                ol.addOrderItemCounts(o.getOrderItemCount());
                ol.setOrderStatus(o.getOrderStatus());
                ols.add(ol);
            }
        }
        return ols;
    }
}
