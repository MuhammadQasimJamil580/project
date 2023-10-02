package SMS_Shared.Repository;

import SMS_Shared.Entity.TagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TagRepo extends JpaRepository<TagsEntity,Long> {
    public TagsEntity findByIdAndStatusNot(Long id,String status);
    public List<TagsEntity> findByStatusNot(String status);

    @Query(value = "Select email_user_id,email_password,smtp,port,authentication,hostname,email_to from sms_shared.email_credentials where id=1", nativeQuery = true)
    Map<String, Object> getEmailCredentials();
}
