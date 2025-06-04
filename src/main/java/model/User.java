package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private Long id;

    @NotEmpty(message = "Username must not be empty")
    private String userName;

    @NotEmpty(message = "Password must not be empty")
    private String password;

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

}
