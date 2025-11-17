package com.flightapp.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerRequest {

    @NotBlank
    private String name;

    private String gender;

    private Integer age;

    private String mealPref;  

    private String seatNumber; 
}
