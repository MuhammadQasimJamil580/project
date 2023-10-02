package SMS_Shared.Service;

import SMS_Shared.DTO.TagDto;
import SMS_Shared.ResponceDto.ResponseDto;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public interface TagService {
    ResponseDto<String> save(TagDto tagDTO) throws MessagingException, IOException;

    ResponseDto<String> update(TagDto tagDTO) throws MessagingException, IOException;

    ResponseDto<String> delete(Long id) throws MessagingException, IOException;

    ResponseDto<TagDto> findOne(Long id) throws MessagingException, IOException;

    ResponseDto<List<TagDto>> findAll() throws MessagingException, IOException;

    HashMap<Long, String> findAllTags() throws MessagingException, IOException;
}
