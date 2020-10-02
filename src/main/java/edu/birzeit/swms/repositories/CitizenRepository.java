package edu.birzeit.swms.repositories;

import edu.birzeit.swms.models.Citizen;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends CrudRepository<Citizen, Integer> {
}
