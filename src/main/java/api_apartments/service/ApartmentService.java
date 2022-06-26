package api_apartments.service;

import api_apartments.dto.ApartmentResponseDTO;

import java.util.List;

public interface ApartmentService {

    List<ApartmentResponseDTO> getAllApartmentsByBuildingId(Long id, boolean hasOwners);


}
