package misha.bondarenko.controllers;

import misha.bondarenko.entities.products.Item;
import misha.bondarenko.services.interfaces.IItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final IItemService itemService;

    public ProductRestController(IItemService productService) {
        this.itemService = productService;
    }

    @GetMapping("/{productId}/check-stock")
    public ResponseEntity<Map<String, Object>> checkStock(@PathVariable String productId,
                                                          @RequestParam int quantity) {
        System.out.println("Checking stock for " + productId);
        Map<String, Object> response = new HashMap<>();

        // Припускаємо, що у вашій сутності Product є поле кількості на складі (наприклад, quantityInStock)
        Item item = itemService.findItemById(Long.parseLong(productId));

        if (item == null) {
            response.put("available", false);
            response.put("message", "Товар не знайдено");
            return ResponseEntity.ok(response);
        }

        response.put("available", item.isAvailable());
        response.put("currentStock", item.getStockQuantity());

        return ResponseEntity.ok(response);
    }
}
