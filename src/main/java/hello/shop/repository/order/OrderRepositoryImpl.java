package hello.shop.repository.order;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.shop.entity.Item;
import hello.shop.entity.Order;
import hello.shop.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static hello.shop.entity.QBasketItem.basketItem;
import static hello.shop.entity.QDelivery.delivery;
import static hello.shop.entity.QItem.item;
import static hello.shop.entity.QMember.member;
import static hello.shop.entity.QOrder.order;

public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private JPAQueryFactory query;

    public OrderRepositoryImpl(EntityManager em){
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderDtoV1> searchV1(OrderSearchCond cond, Pageable pageable) {
        String memberName = cond.getMemberName();
        OrderStatus orderStatus = cond.getOrderStatus();

        QueryResults<OrderDtoV1> results = query
                .select(new QOrderDtoV1(
                        order.id,
                        order.member.name,
                        order.status))
                .from(order)
                .leftJoin(order.member, member)
                .where(likeOrderName(memberName), equalOrderStatus(orderStatus))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(order.lastModifiedDate.desc())
                .fetchResults();

        List<OrderDtoV1> content = results.getResults();
        long total = results.getTotal();
        PageImpl<OrderDtoV1> page = new PageImpl<>(content, pageable, total);
        return page;
    }

    @Override
    public List<OrderDtoV2> searchV2(OrderSearchCond cond) {
        String memberName = cond.getMemberName();
        OrderStatus orderStatus = cond.getOrderStatus();
        // 결론: querydsl은 컬렉션속성조회를 지원하지 않으므로 단일속성조회를 해야 함
        return query
                .select(new QOrderDtoV2(
                        member.name,
                        item.name,
                        item.quantity))
                .from(order)
                .leftJoin(order.member, member)
                .leftJoin(order.basketItems, basketItem)
                .leftJoin(basketItem.item, item)
                .where(likeOrderName(memberName), equalOrderStatus(orderStatus))
                .fetch();
//        return null;
    }

    @Override
    public List<OrderDtoV3> searchV3() {
        return query
                .select(new QOrderDtoV3(
                        order.id,
                        member.name,
                        order.orderDate,
                        order.status, order.
                        delivery.address
                ))
                .from(order)
                .leftJoin(order.member, member)
                .leftJoin(order.delivery, delivery)
                .fetch();
//        return null;
    }


    private BooleanExpression likeOrderName(String memberName) {
        if(StringUtils.hasText(memberName)){
            return order.member.name.like("%" + memberName + "%");
        }
        return null;
    }

    private BooleanExpression equalOrderStatus(OrderStatus orderStatus) {
        if(orderStatus != null) {
            return order.status.eq(orderStatus);
        }
        return null;
    }
}
