package api_apartments.repository;

import api_apartments.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    Integer countAllByBuildingId(Long id);

    List<Apartment> findAllByBuilding_Id(Long id);

    List<Apartment> findAllByIdIn(List<Long> id);

    Boolean existsApartmentByIdAndBuilding_Id(Long apartmentId, Long buildingId);

}
