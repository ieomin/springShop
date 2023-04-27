package hello.shop.service;

import hello.shop.entity.Item;
import hello.shop.repository.item.ItemRepository;
import hello.shop.repository.item.ItemSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void save(Item item){
        itemRepository.save(item);
    }

    public List<Item> findAll(){
        List<Item> items = itemRepository.findAll();
        return items;
    }

    public Item findById(Long id){
        Item item = itemRepository.findById(id).get();
        return item;
    }

    public Page<Item> search(ItemSearchCond cond, Pageable pageable){
        Page<Item> search = itemRepository.search(cond, pageable);
        return search;
    }

    public void createItem(String name, Integer price, Integer quantity) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setQuantity(quantity);
        save(item);
    }

    @Transactional
    public void updateItem(Long id, String name, Integer price, Integer quantity) {
        Item item = findById(id);
        item.setName(name);
        item.setPrice(price);
        item.setQuantity(quantity);
    }


}
