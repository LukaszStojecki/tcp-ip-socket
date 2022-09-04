package service;

import model.Vehicle;

import java.util.List;


public interface VehicleService {
    List<Vehicle> getAllByUserLogin(String login);
}
