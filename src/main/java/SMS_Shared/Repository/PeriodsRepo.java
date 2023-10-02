package SMS_Shared.Repository;

import SMS_Shared.Entity.PeriodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodsRepo extends JpaRepository<PeriodsEntity,Long> {

    List<PeriodsEntity> findByStatusNot(String status);

    PeriodsEntity findByIdAndStatusNot(Long id, String status);

}
