package dat3.kinoxp.repository;

import dat3.kinoxp.entity.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, String> {
}