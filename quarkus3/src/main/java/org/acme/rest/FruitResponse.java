package org.acme.rest;

import org.acme.dto.FruitDTO;

import java.util.List;

public class FruitResponse {
    public List<FruitDTO> fruits;
    int total;

    public FruitResponse(List<FruitDTO> fruits) {
        this.fruits = fruits;
        this.total = fruits.size();
    }
}
