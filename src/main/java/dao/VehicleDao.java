package dao;

import model.Vehicle;

import java.util.List;

public interface VehicleDao {
    List<Vehicle> findAllByUserLogin(String login);
}