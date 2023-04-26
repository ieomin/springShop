package hello.shop.repository.item;

import hello.shop.entity.Item;

import java.util.List;

public interface ItemRepositoryCustom {

    public List<Item> search(ItemSearchCond cond);

}
