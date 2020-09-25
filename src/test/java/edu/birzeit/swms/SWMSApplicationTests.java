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

    @Test
    void createBin(){
        Bin bin=new Bin();
        bin.setLatitude(10);
        bin.setLongitude(100);
        bin.setStatus(Status.UNDER_THRESHOLD);
        binRepository.save(bin);
    }

}
