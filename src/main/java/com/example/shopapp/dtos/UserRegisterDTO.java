package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    private String fullName;
    private String phoneNumber;
    private String password;
    private String retypePassword;
    private int facebookAccountID;
    private int googleAccountID;
    private int roleID;

}
