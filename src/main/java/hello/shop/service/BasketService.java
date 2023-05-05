package hello.shop.service;

import hello.shop.entity.*;
import hello.shop.repository.basket.BasketRepository;
import hello.shop.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final ItemRepository itemRepository;

    public Basket save(Basket basket){
        return basketRepository.save(basket);
    }
    public Basket findById(Long id){
        return basketRepository.findById(id).get();
    }
    public Basket findByMemberId(Long memberId) {
        return basketRepository.findByMemberId(memberId).get(0);
    }

    @Transactional
    public void addBasketItem(Long basketId, Long itemId, Integer count){

        Basket basket = findById(basketId);
        Basket findBasket = basketRepository.findById(basketId).get();
        Item item = itemRepository.findById(itemId).get();
        BasketItem basketItem = BasketItem.createBasketItem(item, count);
        findBasket.addBasketItem(basketItem);
        Integer totalPrice = basket.getTotalPrice() + item.getPrice()*count;
        basket.setTotalPrice(totalPrice);
    }
    @Transactional
    public void cancelBasketItem(Long basketId, Long basketItemId){
        Basket basket = findById(basketId);
        List<BasketItem> basketItems = basket.getBasketItems();
        for(int i=0; i<basketItems.size(); i++){
            if(basketItems.get(i).getId().equals(basketItemId)){
                basketItems.get(i).setStatus(BasketItemStatus.CANCEL);
                basket.setTotalPrice(basket.getTotalPrice() - basketItems.get(i).getTotalPrice());
                basketItems.remove(i);
            }
        }
    }
    @Transactional
    public void clearBasket(Basket basket) {
        List<BasketItem> basketItems = basket.getBasketItems();
        for (BasketItem bi : basketItems) {
            bi.setBasket(null);
        }
        basket.getBasketItems().clear();
        basket.setTotalPrice(0);
    }


}
