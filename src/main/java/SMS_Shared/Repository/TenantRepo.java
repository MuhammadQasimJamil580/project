package SMS_Shared.Repository;


import SMS_Shared.Entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepo extends JpaRepository<Tenant, Long> {


}
