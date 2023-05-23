package iss.nus.serverwatson.models;

import iss.nus.serverwatson.utils.AuthHelper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String role = "ROLE_USER";
    private Integer enabled = 1;

    public User(String username, String password) {
        this.username = username;
        this.password = AuthHelper.securedPasswordGenerator(password);
    }
    
}
