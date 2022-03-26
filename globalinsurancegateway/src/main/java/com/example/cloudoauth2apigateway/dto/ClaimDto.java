package com.example.cloudoauth2apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClaimDto {
    private String VehicleId;
    private String CustomerName;
    private String DateOfIncident;
    private String IncidentStreet;
    private String IncidentTown;
    private String IncidentState;
}
