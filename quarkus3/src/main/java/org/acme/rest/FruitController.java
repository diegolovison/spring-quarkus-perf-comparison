package org.acme.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/fruits")
public class FruitController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public FruitResponse getAll() {
		return new FruitResponse(FruitData.FRUITS);
	}
}
