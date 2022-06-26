package api_apartments.service;

import api_apartments.dto.BuildingRequestDTO;
import api_apartments.dto.BuildingResponseDTO;

import java.util.List;

public interface BuildingService {

    void createBuilding(BuildingRequestDTO request, int apartmentsCount);

    List<BuildingResponseDTO> getAllBuildingsByStreet(String street);

    void destroyBuildingById(Long id);
}
