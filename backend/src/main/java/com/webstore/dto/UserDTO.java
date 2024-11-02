package com.webstore.dto;

import com.webstore.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String firstName;
    private String lastName;

    public UserDTO(User user) {
        this.firstName = user.getFirstName();
        this.firstName = user.getLastName();
    }
}
