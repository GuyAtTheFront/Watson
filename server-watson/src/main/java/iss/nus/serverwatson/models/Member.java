package iss.nus.serverwatson.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Member {
    private Long id;
    private String username;
    private String imageUrl;
}
