package model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class Vehicle implements Serializable {

    private Long id;
    private String userLogin;
    private String brand;
    private String model;
    private LocalDateTime insertTime;
    private List<InsuranceOffer> insuranceOffers;
}
