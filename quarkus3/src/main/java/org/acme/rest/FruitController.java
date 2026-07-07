package org.acme.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.dto.FruitDTO;
import org.acme.service.FruitService;

import java.util.List;

@Path("/fruits")
public class FruitController {

	private final FruitService fruitService;

	public FruitController(FruitService fruitService) {
		this.fruitService = fruitService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public FruitResponse getAll() {
		List<FruitDTO> fruits = this.fruitService.getAllFruits();
		return new FruitResponse(fruits);
	}
}
