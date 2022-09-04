package socket;

import dao.impl.InsuranceOfferDaoImpl;
import dao.impl.VehicleDaoImpl;
import exception.DataException;
import model.User;
import model.Vehicle;
import service.VehicleService;
import service.impl.InsuranceOfferServiceImpl;
import service.impl.VehicleServiceImpl;
import util.JDBCConfiguration;
import util.JDBCTransactionManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class TCPServer {
    private static final int PORT = 7000;
    private final VehicleService vehicleService = createVehicleService();

    public static void main(String[] args) {

        TCPServer tcpServer = new TCPServer();
        try {
            Socket socket = tcpServer.openConnection();
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(socket.getOutputStream());
            User user = tcpServer.getRequest(socket);
            tcpServer.sendResponse(objectOutputStream, user);
            tcpServer.closeConnection(socket);
        } catch (IOException e) {
            throw new DataException("Something wrong with data processing", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found.", e);
        }
    }

    private VehicleService createVehicleService() {
        JDBCTransactionManager jdbcTransactionManager = new JDBCTransactionManager(new JDBCConfiguration());
        InsuranceOfferDaoImpl insuranceOfferDao = new InsuranceOfferDaoImpl(jdbcTransactionManager);
        InsuranceOfferServiceImpl insuranceOfferService = new InsuranceOfferServiceImpl(insuranceOfferDao);
        VehicleDaoImpl vehicleDao = new VehicleDaoImpl(jdbcTransactionManager, insuranceOfferService);
        return new VehicleServiceImpl(vehicleDao);
    }

    private Socket openConnection() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());
        return clientSocket;
    }

    private void closeConnection(Socket socket) throws IOException {
        socket.close();
    }

    private void sendResponse(ObjectOutputStream objectOutputStream, User user)
            throws IOException {
        List<Vehicle> response = vehicleService.getAllByUserLogin(user.getLogin());
        objectOutputStream.writeObject(response);
    }

    private User getRequest(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        return (User) objectInputStream.readObject();
    }
}
