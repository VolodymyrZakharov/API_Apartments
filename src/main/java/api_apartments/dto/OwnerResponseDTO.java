package api_apartments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class OwnerResponseDTO {
    private Long id;
    private String name;
    private ApartmentResponseForOwnerDTO apartment;
}
