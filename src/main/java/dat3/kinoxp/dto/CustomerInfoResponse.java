package dat3.kinoxp.dto;

import dat3.kinoxp.entity.CustomerInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerInfoResponse {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String email;

    public CustomerInfoResponse(CustomerInfo customerInfo) {
        this.phoneNumber = customerInfo.getPhoneNumber();
        this.firstName = customerInfo.getFirstName();
        this.lastName = customerInfo.getLastName();
        this.email = customerInfo.getEmail();
    }
}
