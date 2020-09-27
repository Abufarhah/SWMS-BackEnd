package edu.birzeit.swms.repositories;

import edu.birzeit.swms.models.Area;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends CrudRepository<Area, Integer> {
}
