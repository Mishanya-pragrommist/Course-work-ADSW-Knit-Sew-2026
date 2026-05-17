package misha.bondarenko.entities.user;

import misha.bondarenko.entities.products.Item;

public class CartItem {
    private Item item;

    public CartItem(Item item) {
        this.item = item;
    }
}
