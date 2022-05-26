package da.springframework.msscbeerservice.events;

import da.springframework.msscbeerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -7229137464711858491L;

    private BeerDto beerDto;
}
