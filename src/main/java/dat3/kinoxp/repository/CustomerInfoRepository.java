package dat3.kinoxp.repository;

import dat3.kinoxp.entity.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public class CustomerInfoRepository extends JpaRepository<CustomerInfo, String> {
}
