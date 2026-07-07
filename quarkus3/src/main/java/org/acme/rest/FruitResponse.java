package org.acme.rest;

import org.acme.dto.FruitDTO;

import java.util.List;

public class FruitResponse {
    public List<FruitDTO> fruits;

    public FruitResponse(List<FruitDTO> fruits) {
        this.fruits = fruits;
    }
}
