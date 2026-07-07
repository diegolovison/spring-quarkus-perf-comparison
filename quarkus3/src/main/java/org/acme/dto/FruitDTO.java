package org.acme.dto;

import java.util.ArrayList;
import java.util.List;

public record FruitDTO(Long id, String name, String description, List<StoreFruitPriceDTO> storePrices) {
  public FruitDTO {
    if (name == null) {
      throw new IllegalArgumentException("Name is mandatory");
    }

    if (storePrices == null) {
      storePrices = new ArrayList<>();
    }
  }
}
