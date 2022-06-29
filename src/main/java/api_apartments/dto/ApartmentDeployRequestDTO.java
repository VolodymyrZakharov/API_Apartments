package api_apartments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ApartmentDeployRequestDTO {
    private Boolean hasBalcony;
    private List<OwnerRequestDTO> owners;
}
