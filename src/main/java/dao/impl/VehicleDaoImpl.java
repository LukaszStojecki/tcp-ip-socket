package dao.impl;

import dao.VehicleDao;
import lombok.AllArgsConstructor;
import model.Vehicle;
import service.InsuranceOfferService;
import util.JDBCTransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class VehicleDaoImpl implements VehicleDao {

    private static final String ID_FIELD = "id";
    private static final String USER_LOGIN_FIELD = "user_login";
    private static final String BRAND_FIELD = "brand";
    private static final String MODEL_FIELD = "model";
    private static final String INSERT_TIME_FIELD = "insert_time";
    private static final int PARAMETER_INDEX = 1;

    private JDBCTransactionManager jdbcTransactionManager;
    private InsuranceOfferService insuranceOfferService;

    @Override
    public List<Vehicle> findAllByUserLogin(String login) {

        return jdbcTransactionManager.getInTransaction(connection -> {
            List<Vehicle> vehicles = new ArrayList<>();
            try {
                String query = "SELECT * "
                        + "FROM vehicles "
                        + "WHERE user_login = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(PARAMETER_INDEX, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    getAllVehicle(vehicles, resultSet);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return vehicles;
        });

    }

    private void getAllVehicle(List<Vehicle> vehicles, ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID_FIELD);
        String userLogin = resultSet.getString(USER_LOGIN_FIELD);
        String brand = resultSet.getString(BRAND_FIELD);
        String model = resultSet.getString(MODEL_FIELD);
        LocalDateTime localDateTime = resultSet.getTimestamp(INSERT_TIME_FIELD).toLocalDateTime();
        vehicles.add(Vehicle.builder()
                .id(id)
                .userLogin(userLogin)
                .brand(brand)
                .model(model)
                .insertTime(localDateTime)
                .insuranceOffers(insuranceOfferService.getAllByVehicleId(id))
                .build());
    }
}
