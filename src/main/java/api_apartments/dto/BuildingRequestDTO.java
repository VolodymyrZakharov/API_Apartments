package api_apartments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class BuildingRequestDTO {
    private String street;
    private String house;
    private List<ApartmentRequestDTO> apartmentsDTO;
}
