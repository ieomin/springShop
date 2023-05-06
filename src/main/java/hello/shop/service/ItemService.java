package hello.shop.service;

import hello.shop.entity.Item;
import hello.shop.entity.Member;
import hello.shop.repository.item.ItemRepository;
import hello.shop.repository.item.ItemSearchCond;

import hello.shop.web.SessionConst;
import hello.shop.web.form.item.ItemCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void save(Item item){
        itemRepository.save(item);
    }
    public List<Item> findAll(){
        return itemRepository.findAll();
    }
    public Item findById(Long id){
        return itemRepository.findById(id).get();
    }

    public Page<Item> search(ItemSearchCond cond, Pageable pageable){
        return itemRepository.search(cond, pageable);
    }

    public Item createItem(String name, Integer price, Integer quantity, Member member) {
        Item findItem = itemRepository.findByName(name);
        if(findItem != null){
            return null;
        }
        Item item = Item.createItem(name, price, quantity, member);
        save(item);
        return item;
    }

    @Transactional
    public Item updateItem(Long id, String updateName, Integer price, Integer quantity) {

        Item item = findById(id);
        String oldName = item.getName();

        if(!oldName.equals(updateName)){
            Item findItem = itemRepository.findByName(updateName);
            if(findItem != null){
                return null;
            }
            item.setName(updateName);
            item.setPrice(price);
            item.setQuantity(quantity);
            return item;
        }

        else{
            item.setPrice(price);
            item.setQuantity(quantity);
            return item;
        }
    }


}