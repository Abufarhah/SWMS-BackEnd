package edu.birzeit.swms.repositories;

import edu.birzeit.swms.models.ConfirmationToken;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Integer> {

    Optional<ConfirmationToken> findAllByConfirmationToken(String confirmationToken);

}
