package SMS_Shared.Repository;

import SMS_Shared.Entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepo extends JpaRepository<GroupEntity,Long> {

    List<GroupEntity> findByStatusNot(String status);

    GroupEntity findByIdAndStatusNot(Long id,String status);
}
