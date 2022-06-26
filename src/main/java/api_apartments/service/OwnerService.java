package api_apartments.service;

import api_apartments.dto.OwnerRequestDTO;
import api_apartments.dto.OwnerResponseDTO;

public interface OwnerService {

    void createOwner(OwnerRequestDTO request);

    void moveOwnerToApartment(Long buildingID, Long apartmentId, Long ownerId);

    void evictOwnerFromApartment(Long buildingId, Long apartmentId, Long ownerId);

    OwnerResponseDTO getOwnerById(Long id);
}
