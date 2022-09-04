package socket;

import dao.impl.UserDaoImpl;
import exception.DataException;
import model.User;
import model.Vehicle;
import service.UserService;
import service.impl.UserServiceImpl;
import util.JDBCConfiguration;
import util.JDBCTransactionManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class TCPClient {
    private static final String LOGIN = "user1";
    private static final String PASSWORD = "user123";
    private static final String HOST = "localhost";
    private static final int PORT = 7000;
    private final UserService userService = createUserService();
    private Socket socket;

    public static void main(String[] args) {

        TCPClient tcpClient = new TCPClient();
        try {
            tcpClient.openConnection();
            tcpClient.sendRequest();
            System.out.println(tcpClient.getResponse());
            tcpClient.closeConnection();
        } catch (IOException e) {
            throw new DataException("Something wrong with data processing", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found.", e);
        }
    }

    private void openConnection() throws IOException {
        socket = new Socket(HOST, PORT);
    }

    private void sendRequest() throws IOException {
        User authenticatedUser = userService.login(LOGIN, PASSWORD);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(authenticatedUser);
        objectOutputStream.flush();
    }

    private List<Vehicle> getResponse() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        List<Vehicle> vehicles = (List<Vehicle>) objectInputStream.readObject();
        return vehicles;
    }

    private UserServiceImpl createUserService() {
        return new UserServiceImpl(new UserDaoImpl(new JDBCTransactionManager(new JDBCConfiguration())));
    }

    private void closeConnection() throws IOException {
        socket.close();
    }
}
