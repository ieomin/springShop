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

    public Basket findById(Long id){
        Basket basket = basketRepository.findById(id).get();
        return basket;
    }

    @Transactional
    public void addBasketItem(Long basketId, Long itemId, Integer count){
        Basket basket = basketRepository.findById(basketId).get();
        Item item = itemRepository.findById(itemId).get();
        basket.addBasketItem(new BasketItem(item, count));
    }

    public Basket findByMemberId(Long memberId) {
        List<Basket> baskets = basketRepository.findByMemberId(memberId);
        Basket basket = baskets.get(0);
        return basket;
    }

    // 결과: 넘겨받은 basket은 빈관리대상이 아니어서 함수로 사용하기 애매해네 그래서 아이디를 받고 함수에서 찾으면서 시작하는 듯
    @Transactional
    public void clearBasket(Basket basket) {
        List<BasketItem> basketItems = basket.getBasketItems();
        for (BasketItem bi : basketItems) {
            bi.setBasket(null);
        }
        basket.getBasketItems().clear();
    }

    @Transactional
    public void cancelBasketItem(Long basketId, Long basketItemId){
        Basket basket = findById(basketId);
        List<BasketItem> basketItems = basket.getBasketItems();
        for(int i=0; i<basketItems.size(); i++){
            if(basketItems.get(i).getId() == basketItemId){
                basketItems.get(i).setStatus(BasketItemStatus.CANCEL);
                log.info("itemName = {}", basketItems.get(i).getItem().getName());
                basketItems.remove(i);
            }
        }
    }
}
