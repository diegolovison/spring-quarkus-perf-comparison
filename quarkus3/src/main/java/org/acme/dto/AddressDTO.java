package org.acme.dto;

public record AddressDTO(

    String address,


    String city,


    String country
) {
  public AddressDTO {
    if ((address == null) || address.isBlank()) {
      throw new IllegalArgumentException("Address is mandatory");
    }

    if ((city == null) || city.isBlank()) {
      throw new IllegalArgumentException("City is mandatory");
    }

    if ((country == null) || country.isBlank()) {
      throw new IllegalArgumentException("Country is mandatory");
    }
  }
}
