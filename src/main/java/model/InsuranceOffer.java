package model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
public class InsuranceOffer implements Serializable {

    private Long id;
    private Long vehicleId;
    private String insurer;
    private BigDecimal price;
    private LocalDateTime insertTime;
}
