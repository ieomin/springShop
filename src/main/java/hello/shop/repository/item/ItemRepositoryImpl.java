package hello.shop.repository.item;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.shop.entity.Item;
import org.hibernate.mapping.SingleTableSubclass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static hello.shop.entity.QItem.item;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory query;

    // 팁: jpql을 생성하는 곳은 em이 무조건 필요
    public ItemRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Item> search(ItemSearchCond cond, Pageable pageable) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();
        QueryResults<Item> results = query
                .select(item)
                .from(item)
                // 팁: 존재여부를 조건문 전에 물어보는 것이 동적 쿼리라고 할 수 있음
                .where(likeItemName(itemName), maxPrice(maxPrice))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(item.lastModifiedDate.desc())
                .fetchResults();
        List<Item> content = results.getResults();
        long total = results.getTotal();
        // 보류: <Item> 원래 없는 거였는데 이 코드도 나중에 한 번 뜯어보기
        PageImpl<Item> page = new PageImpl<>(content, pageable, total);
        return page;
//        return null;
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
