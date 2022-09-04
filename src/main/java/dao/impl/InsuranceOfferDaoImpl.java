package dao.impl;

import dao.InsuranceOfferDao;
import lombok.AllArgsConstructor;
import model.InsuranceOffer;
import util.JDBCTransactionManager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class InsuranceOfferDaoImpl implements InsuranceOfferDao {

    private static final String ID_FIELD = "id";
    private static final String VEHICLE_ID_FIELD = "vehicle_id";
    private static final String INSURER_FIELD = "insurer";
    private static final String PRICE_FIELD = "price";
    private static final String INSERT_TIME_FIELD = "insert_time";
    private static final int PARAMETER_INDEX = 1;

    private JDBCTransactionManager jdbcTransactionManager;

    @Override
    public List<InsuranceOffer> findAllByVehicleId(Long vehicleId) {
        return jdbcTransactionManager.getInTransaction(connection -> {
            List<InsuranceOffer> insuranceOffers = new ArrayList<>();
            try {
                String query = "SELECT * FROM insurance_offers "
                        + "WHERE vehicle_id = ? ";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(PARAMETER_INDEX, vehicleId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    getAllInsuranceOffer(insuranceOffers, resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return insuranceOffers;
        });
    }

    private void getAllInsuranceOffer(List<InsuranceOffer> insuranceOffers, ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID_FIELD);
        Long vehId = resultSet.getLong(VEHICLE_ID_FIELD);
        String insurer = resultSet.getString(INSURER_FIELD);
        BigDecimal price = resultSet.getBigDecimal(PRICE_FIELD);
        LocalDateTime localDateTime = resultSet.getTimestamp(INSERT_TIME_FIELD).toLocalDateTime();
        insuranceOffers.add(InsuranceOffer.builder()
                .id(id)
                .vehicleId(vehId)
                .insurer(insurer)
                .price(price)
                .insertTime(localDateTime)
                .build());
    }
}
