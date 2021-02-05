package edu.birzeit.swms.repositories;

import edu.birzeit.swms.models.Round;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends CrudRepository<Round, Integer> {
}
