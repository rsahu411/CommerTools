package com.CommerceTool.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionDTO {
    private String action;

    // Base Address
    private String addressId;
    private String key;
    private String title;
    private String salutation;
    private String firstName;
    private String middleName;
    private String lastName;
    private String streetName;
    private String streetNumber;
    private String additionalStreetInfo;
    private String postalCode;
    private String city;
    private String region;
    private String state;
    private String country;
    private String company;
    private String department;
    private String building;
    private String apartment;
    private String pOBox;
    private String phone;
    private String mobile;
    private String email;
    private String fax;
    private String additionalAddressInfo;
    private String externalId;
    private LocalDate dateOfBirth;
    private String customerNumber;

}
