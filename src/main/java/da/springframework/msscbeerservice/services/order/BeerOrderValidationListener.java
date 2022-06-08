package da.springframework.msscbeerservice.services.order;

import da.brewery.model.events.ValidateOrderRequest;
import da.brewery.model.events.ValidateOrderResult;
import da.springframework.msscbeerservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Slf4j
public class BeerOrderValidationListener {

    private final BeerOrderValidator beerOrderValidator;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen (ValidateOrderRequest validateOrderRequest) {

        Boolean isValid = beerOrderValidator.validateOrder(validateOrderRequest.getBeerOrderDto());

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                ValidateOrderResult.builder()
                        .orderId(validateOrderRequest.getBeerOrderDto().getId())
                        .isValid(isValid)
                        .build());
    }
}
