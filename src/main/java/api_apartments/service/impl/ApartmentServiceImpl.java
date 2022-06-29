package api_apartments.service.impl;

import api_apartments.dto.ApartmentResponseDTO;
import api_apartments.dto.OwnerResponseDTO;
import api_apartments.entity.Apartment;
import api_apartments.entity.Building;
import api_apartments.entity.Owner;
import api_apartments.repository.ApartmentRepository;
import api_apartments.repository.BuildingRepository;
import api_apartments.repository.OwnerRepository;
import api_apartments.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public List<ApartmentResponseDTO> getAllApartmentsByBuildingId(Long id, boolean hasOwners) {
        Building building = buildingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Apartment> apartmentsByBuildingId = apartmentRepository.findAllByBuilding_Id(id);

        if (hasOwners) {
            List<Long> occupiedApartmentsId = ownerRepository.findAllByApartmentIn(apartmentsByBuildingId).stream()
                    .map(a -> a.getApartment().getId())
                    .collect(Collectors.toList());
            return apartmentsToListResponseDTO(apartmentRepository.findAllByIdIn(occupiedApartmentsId));
        } else
            return apartmentsToListResponseDTO(apartmentsByBuildingId);
    }

    private List<ApartmentResponseDTO> apartmentsToListResponseDTO(List<Apartment> apartments) {
        return apartments.stream()
                .map(a -> apartmentToDTO(a, ownerRepository.findAllByApartment(a).stream()
                        .map(this::ownerToDTO).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    private ApartmentResponseDTO apartmentToDTO(Apartment a, List<OwnerResponseDTO> ownersDTO) {
        return ApartmentResponseDTO.builder()
                .id(a.getId())
                .apartmentNumber(a.getApartmentNumber())
                .hasBalcony(a.getHasBalcony())
                .buildingId(a.getBuilding().getId())
                .owners(ownersDTO)
                .build();
    }

    private OwnerResponseDTO ownerToDTO(Owner o) {
        return OwnerResponseDTO.builder()
                .id(o.getId())
                .name(o.getName())
                .build();
    }
}
