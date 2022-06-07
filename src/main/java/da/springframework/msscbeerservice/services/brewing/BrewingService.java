package da.springframework.msscbeerservice.services.brewing;

import da.springframework.msscbeerservice.config.JmsConfig;
import da.springframework.msscbeerservice.domain.Beer;
import da.brewery.model.events.BrewBeerEvent;
import da.springframework.msscbeerservice.repositories.BeerRepository;
import da.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import da.springframework.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) //every 5 seconds
    public void checkForLowInventory(){
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer inventoryQuantityOnHand = beerInventoryService.getOnHandInventory(beer.getId());

            log.debug("Min On Hand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + inventoryQuantityOnHand);

            if(beer.getMinOnHand() >= inventoryQuantityOnHand){
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
