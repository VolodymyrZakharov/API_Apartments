package api_apartments.service.impl;


import api_apartments.dto.ApartmentResponseForOwnerDTO;
import api_apartments.dto.OwnerRequestDTO;
import api_apartments.dto.OwnerResponseDTO;
import api_apartments.entity.Apartment;
import api_apartments.entity.Building;
import api_apartments.entity.Owner;
import api_apartments.repository.ApartmentRepository;
import api_apartments.repository.BuildingRepository;
import api_apartments.repository.OwnerRepository;
import api_apartments.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public void createOwner(OwnerRequestDTO request) {
        Owner owner = Owner.builder()
                .name(request.getName())
                .apartment(null)
                .build();
        ownerRepository.save(owner);
    }

    @Override
    public void moveOwnerToApartment(Long buildingId, Long apartmentId, Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Owner with id [%s] does not exist", ownerId)));

        Building building = buildingRepository.findById(buildingId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Building with id [%s] does not exist", buildingId)));

        Apartment apartment = apartmentRepository.findById(apartmentId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Apartment with id [%s] does not exist", apartmentId)));

        if (!apartmentRepository.existsApartmentByIdAndBuilding_Id(apartmentId, buildingId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Building with buildingId [%s] do not have apartment with id [%s]", buildingId, apartmentId));

        if (owner.getApartment() != null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Owner is already registered in apartment [%s] in building [%s]", apartmentId, buildingId));

        owner.setApartment(apartmentRepository.findById(apartmentId).orElseThrow());
        ownerRepository.save(owner);
    }

    @Override
    public void evictOwnerFromApartment(Long buildingId, Long apartmentId, Long ownerId) {

        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Owner with id [%s] does not exist", ownerId)));

        if (!apartmentRepository.existsApartmentByIdAndBuilding_Id(apartmentId, buildingId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Building with buildingId [%s] do not have apartment with id [%s]", buildingId, apartmentId));

        if (owner.getApartment() == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Owner with id [%s] is not registered in any apartment", ownerId));

        if (owner.getApartment().getId() != apartmentId)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Owner with id [%s] is registered in another apartment with id [%s]", ownerId, ownerRepository.findById(ownerId)));

        owner.setApartment(null);
        ownerRepository.save(owner);
    }

    @Override
    public OwnerResponseDTO getOwnerById(Long id) {
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Apartment apartment = owner.getApartment();

        ApartmentResponseForOwnerDTO apartmentDTO;
        if (apartment == null) {
            apartmentDTO = null;
        } else apartmentDTO = ApartmentResponseForOwnerDTO.builder()
                .id(apartment.getId())
                .apartmentNumber(apartment.getApartmentNumber())
                .hasBalcony(apartment.getHasBalcony())
                .buildingId(apartment.getBuilding().getId())
                .build();

        return OwnerResponseDTO.builder()
                .id(owner.getId())
                .name(owner.getName())
                .apartment(apartmentDTO)
                .build();
    }
}
