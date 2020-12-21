package da.springframework.msscbeerservice.services;

import da.springframework.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {

    BeerDto getBeerById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeerById(UUID beerId, BeerDto beerDto);
}
