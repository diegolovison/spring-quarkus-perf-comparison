package org.acme.rest;

import org.acme.dto.*;
import java.math.BigDecimal;
import java.util.List;

public class FruitData {

    // Shared Address instances
    private static final AddressDTO NY_ADDRESS = new AddressDTO("123 Apple St", "New York", "USA");
    private static final AddressDTO LON_ADDRESS = new AddressDTO("456 Banana Rd", "London", "UK");
    private static final AddressDTO TOK_ADDRESS = new AddressDTO("789 Cherry Blvd", "Tokyo", "Japan");

    // Shared Store instances
    private static final StoreDTO GLOBAL_MART = new StoreDTO(1L, "Global Mart", "USD", NY_ADDRESS);
    private static final StoreDTO EURO_FOODS = new StoreDTO(2L, "Euro Foods", "EUR", LON_ADDRESS);
    private static final StoreDTO TOKYO_FRESH = new StoreDTO(3L, "Tokyo Fresh", "JPY", TOK_ADDRESS);

    // Static immutable list containing 10 FruitDTO instances
    public static final List<FruitDTO> FRUITS = List.of(
        // 1. Honeycrisp Apple
        new FruitDTO(
            1L, 
            "Honeycrisp Apple", 
            "Crisp, sweet, and slightly tart.", 
            List.of(
                new StoreFruitPriceDTO(GLOBAL_MART, new BigDecimal("1.99")),
                new StoreFruitPriceDTO(EURO_FOODS, new BigDecimal("1.85"))
            )
        ),

        // 2. Cavendish Banana
        new FruitDTO(
            2L, 
            "Cavendish Banana", 
            "The classic, perfectly sweet yellow banana.", 
            List.of(
                new StoreFruitPriceDTO(GLOBAL_MART, new BigDecimal("0.59")),
                new StoreFruitPriceDTO(TOKYO_FRESH, new BigDecimal("80.00"))
            )
        ),

        // 3. Alphonso Mango
        new FruitDTO(
            3L, 
            "Alphonso Mango", 
            "Rich, creamy, tender flesh with deep saffron color.", 
            List.of(new StoreFruitPriceDTO(EURO_FOODS, new BigDecimal("3.50")))
        ),

        // 4. Organic Strawberry
        new FruitDTO(
            4L, 
            "Organic Strawberry", 
            "Juicy red strawberries grown organically.", 
            List.of(
                new StoreFruitPriceDTO(GLOBAL_MART, new BigDecimal("4.99")),
                new StoreFruitPriceDTO(TOKYO_FRESH, new BigDecimal("600.00"))
            )
        ),

        // 5. Eureka Lemon
        new FruitDTO(
            5L, 
            "Eureka Lemon", 
            "Bright yellow, highly acidic citrus fruit.", 
            List.of(new StoreFruitPriceDTO(GLOBAL_MART, new BigDecimal("0.89")))
        ),

        // 6. Hass Avocado
        new FruitDTO(
            6L, 
            "Hass Avocado", 
            "Pear-shaped fruit known for its buttery texture.", 
            List.of(
                new StoreFruitPriceDTO(GLOBAL_MART, new BigDecimal("1.25")),
                new StoreFruitPriceDTO(EURO_FOODS, new BigDecimal("1.10"))
            )
        ),

        // 7. Bing Cherry
        new FruitDTO(
            7L, 
            "Bing Cherry", 
            "Large, dark red, and intensely sweet cherries.", 
            List.of(new StoreFruitPriceDTO(TOKYO_FRESH, new BigDecimal("450.00")))
        ),

        // 8. White Peach
        new FruitDTO(
            8L, 
            "White Peach", 
            "Fragrant peach with a sweet, low-acid flavor profile.", 
            List.of(
                new StoreFruitPriceDTO(GLOBAL_MART, new BigDecimal("2.49")),
                new StoreFruitPriceDTO(TOKYO_FRESH, new BigDecimal("350.00"))
            )
        ),

        // 9. Dragon Fruit
        new FruitDTO(
            9L, 
            "Dragon Fruit", 
            "Vibrant pink skin with speckles of tiny black seeds.", 
            List.of(new StoreFruitPriceDTO(EURO_FOODS, new BigDecimal("5.00")))
        ),

        // 10. Wild Blueberries
        new FruitDTO(
            10L, 
            "Wild Blueberries", 
            "Small, antioxidant-rich wild berries.", 
            null // Passes null to allow the compact constructor fallback to initialize an empty list
        )
    );
}