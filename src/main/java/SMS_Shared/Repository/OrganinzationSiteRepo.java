package SMS_Shared.Repository;

import SMS_Shared.Entity.OrganizationSiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganinzationSiteRepo extends JpaRepository<OrganizationSiteEntity,Long> {

    List<OrganizationSiteEntity> findByStatusNot(String status);
    List<OrganizationSiteEntity> findByStatusNotAndOrganizationIndex(String status,Long id);
    OrganizationSiteEntity findByIdAndStatusNot(Long id,String status);
}
