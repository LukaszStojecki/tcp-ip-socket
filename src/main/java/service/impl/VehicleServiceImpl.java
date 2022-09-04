package service.impl;

import dao.VehicleDao;
import lombok.AllArgsConstructor;
import model.Vehicle;
import service.VehicleService;

import java.util.List;

@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private VehicleDao vehicleDao;

    @Override
    public List<Vehicle> getAllByUserLogin(String login) {
        return vehicleDao.findAllByUserLogin(login);
    }
}
