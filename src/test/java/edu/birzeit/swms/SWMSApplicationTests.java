package edu.birzeit.swms;

import edu.birzeit.swms.enums.Status;
import edu.birzeit.swms.models.Bin;
import edu.birzeit.swms.repositories.BinRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SWMSApplicationTests {

    @Autowired
    BinRepository binRepository;

    @Test
    void contextLoads() {
    }

}
