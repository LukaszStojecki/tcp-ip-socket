package service.impl;

import dao.InsuranceOfferDao;
import lombok.AllArgsConstructor;
import model.InsuranceOffer;
import service.InsuranceOfferService;

import java.util.List;


@AllArgsConstructor
public class InsuranceOfferServiceImpl implements InsuranceOfferService {
    private InsuranceOfferDao insuranceOfferDao;

    @Override
    public List<InsuranceOffer> getAllByVehicleId(Long id) {
        return insuranceOfferDao.findAllByVehicleId(id);
    }
}
