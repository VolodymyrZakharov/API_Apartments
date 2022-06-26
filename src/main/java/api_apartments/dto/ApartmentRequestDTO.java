package api_apartments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApartmentRequestDTO {
    private Boolean hasBalcony;
}
