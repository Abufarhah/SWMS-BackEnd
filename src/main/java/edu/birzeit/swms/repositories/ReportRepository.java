package edu.birzeit.swms.repositories;

import edu.birzeit.swms.models.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<Report,Integer> {
}
