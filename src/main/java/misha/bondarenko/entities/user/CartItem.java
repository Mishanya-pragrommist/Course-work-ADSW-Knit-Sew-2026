package misha.bondarenko.entities.user;

import lombok.Getter;
import lombok.Setter;
import misha.bondarenko.entities.products.Item;

@Getter
@Setter
public class CartItem {
    private Item item;

    public CartItem(Item item) {
        this.item = item;
    }
}
