package hello.shop.repository.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.shop.entity.Item;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static hello.shop.entity.QItem.item;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory query;

    // 팁: jpql을 생성하는 곳은 em이 무조건 필요
    public ItemRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Item> search(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();
        return query
                .select(item)
                .from(item)
                // 팁: 존재여부를 조건문 전에 물어보는 것이 동적 쿼리라고 할 수 있음
                .where(likeItemName(itemName), maxPrice(maxPrice))
                .fetch();
    }
    
    private BooleanExpression likeItemName(String itemName){
        if(StringUtils.hasText(itemName)){
            return item.name.like("%" + itemName + "%");
        }
        return null;
    }

    private BooleanExpression maxPrice(Integer maxPrice){
        if(maxPrice != null){
            return item.price.loe(maxPrice);
        }
        return null;
    }
}
