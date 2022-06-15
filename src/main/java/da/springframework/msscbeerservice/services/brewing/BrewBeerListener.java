package da.springframework.msscbeerservice.services.brewing;

import da.springframework.msscbeerservice.config.JmsConfig;
import da.springframework.msscbeerservice.domain.Beer;
import da.brewery.model.events.BrewBeerEvent;
import da.brewery.model.events.NewInventoryEvent;
import da.springframework.msscbeerservice.repositories.BeerRepository;
import da.brewery.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional //cause there's no hibernate session
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent brewBeerEvent){
        BeerDto beerDto = brewBeerEvent.getBeerDto();

        Beer beer = beerRepository.findById(beerDto.getId()).get();

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH: " + beerDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
