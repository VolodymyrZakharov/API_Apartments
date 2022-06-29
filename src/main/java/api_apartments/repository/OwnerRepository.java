package api_apartments.repository;

import api_apartments.entity.Apartment;
import api_apartments.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    List<Owner> findAllByApartment(Apartment apartment);

    List<Owner> findAllByApartmentIn(List<Apartment> apartments);
}
