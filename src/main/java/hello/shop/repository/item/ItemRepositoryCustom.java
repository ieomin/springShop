package hello.shop.repository.item;

import hello.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {

    public Page<Item> search(ItemSearchCond cond, Pageable pageable);

}
