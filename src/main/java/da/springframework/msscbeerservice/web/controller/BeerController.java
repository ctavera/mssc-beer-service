package da.springframework.msscbeerservice.web.controller;

import da.springframework.msscbeerservice.services.BeerService;
import da.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId) {

        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@Validated @RequestBody BeerDto beerDto) {

        return new ResponseEntity<>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDto beerDto) {

        return new ResponseEntity<>(beerService.updateBeerById(beerId, beerDto), HttpStatus.NO_CONTENT);
    }
}
