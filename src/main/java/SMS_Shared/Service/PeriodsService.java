package SMS_Shared.Service;

import SMS_Shared.DTO.PeriodsDto;
import SMS_Shared.ResponceDto.ResponseDto;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface PeriodsService {

    ResponseDto<String> save(PeriodsDto periodsDto) throws MessagingException, IOException;
    ResponseDto<String> update (PeriodsDto periodsDto) throws MessagingException, IOException;
    ResponseDto<String> delete(Long id) throws MessagingException, IOException;
    ResponseDto<PeriodsDto> findById(Long id) throws MessagingException, IOException;
    ResponseDto<List<PeriodsDto>> findAll() throws MessagingException, IOException;
    HashMap<Long,String> getperiod() throws MessagingException, IOException;

}
