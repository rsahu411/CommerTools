package com.CommerceTool.Customer;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable {

        private String id;
        private String customerId;
        //public String title;
        private String firstName;
        private String middleName;
        private String lastName;
        private String email;
        private String companyName;
        private String externalId;
        private String customerNumber;
        private String password;
        private String streetNumber;
        private String streetName;
        private String city;
        private String country;
        private String state;
        private String region;
        private String pOBox;
        private String postalCode;
        private String apartment;
        private String Building;
        private String newPassword;
        private  String tokenValue;
        private String currentPassword;

        private  Long version;
        private String EmailPref;
        private String grant_type;
        private Boolean isEmailPr;
        private String customerGroupName;
        private String customerGroupKey;
        private String anonymousId;

        private Long ttlMinutes;

        List<ActionDTO> actions;






    public String isEmailPref() {
        return EmailPref;
    }

    public void setEmailPref(String emailPref) {
        EmailPref = emailPref;
    }


}

