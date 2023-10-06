package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor

@Entity
public class CustomerInfo {

    @Id
    @OneToMany
    private PhoneNumber phoneNumber;
    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;
    @Column(name = "email", length = 50, nullable = false)
    private String email;

public CustomerInfo(PhoneNumber phoneNumber, String firstName, String lastName, String email) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}