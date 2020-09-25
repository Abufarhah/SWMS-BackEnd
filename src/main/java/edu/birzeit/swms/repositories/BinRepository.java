package edu.birzeit.swms.repositories;

import edu.birzeit.swms.models.Bin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BinRepository extends CrudRepository<Bin, Integer> {
}
