package api_apartments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ApartmentResponseForOwnerDTO {
    private Long id;
    private Integer apartmentNumber;
    private Boolean hasBalcony;
    private Long buildingId;
}
