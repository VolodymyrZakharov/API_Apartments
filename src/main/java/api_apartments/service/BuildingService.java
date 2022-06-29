package api_apartments.service;

import api_apartments.dto.BuildingDeployRequestDTO;
import api_apartments.dto.BuildingRequestDTO;
import api_apartments.dto.BuildingResponseDTO;
import api_apartments.dto.OwnerRequestDTO;
import api_apartments.entity.Apartment;

import java.util.List;

public interface BuildingService {

    void createBuilding(BuildingRequestDTO request, int apartmentsCount);

    List<BuildingResponseDTO> getAllBuildingsByStreet(String street);

    void destroyBuildingById(Long id);

    void deployTheCity(List<BuildingDeployRequestDTO> buildings);

}
