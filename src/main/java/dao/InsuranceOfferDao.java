package dao;

import model.InsuranceOffer;

import java.util.List;

public interface InsuranceOfferDao {
    List<InsuranceOffer> findAllByVehicleId(Long id);
}
