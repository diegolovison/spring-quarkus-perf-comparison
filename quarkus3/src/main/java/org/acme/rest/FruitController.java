package org.acme.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.dto.FruitDTO;

import java.util.List;

@Path("/fruits")
public class FruitController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<FruitDTO> getAll() {
		return FruitData.FRUITS;
	}
}
