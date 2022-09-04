package service;

import model.InsuranceOffer;

import java.util.List;

public interface InsuranceOfferService {
    List<InsuranceOffer> getAllByVehicleId(Long id);
}
