package edu.birzeit.swms.repositories;

import edu.birzeit.swms.enums.Status;
import edu.birzeit.swms.models.Area;
import edu.birzeit.swms.models.Bin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BinRepository extends CrudRepository<Bin, Integer> {

    List<Bin> findByStatus(Status status);

    List<Bin> findByArea(Area area);

}
