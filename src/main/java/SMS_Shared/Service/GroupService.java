package SMS_Shared.Service;

import SMS_Shared.DTO.GroupDto;
import SMS_Shared.ResponceDto.ResponseDto;
import jakarta.mail.MessagingException;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.List;

public interface GroupService {

    ResponseDto<String> save(GroupDto groupDto) throws MessagingException, IOException;
    ResponseDto<String> update(GroupDto groupDto) throws MessagingException, IOException;
    ResponseDto<String> delete(Long id) throws MessagingException, IOException;
    ResponseDto<GroupDto> findById(Long id) throws MessagingException, IOException;
    ResponseDto<List<GroupDto>> findAll() throws MessagingException, IOException;




}
