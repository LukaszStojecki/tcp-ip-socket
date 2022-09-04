package model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class User implements Serializable {

    private Long id;
    private String nick;
    private String login;
    private String password;
    private LocalDateTime insertTime;
    private List<Vehicle> vehicles;
}
