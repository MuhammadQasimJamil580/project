package SMS_Shared.Controller;

import SMS_Shared.DTO.GroupDto;
import SMS_Shared.ResponceDto.ResponseDto;
import SMS_Shared.Service.GroupService;
import jakarta.mail.MessagingException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<GroupDto>>> findAll() throws MessagingException, IOException {
        ResponseDto<List<GroupDto>> ResponseDtoList = groupService.findAll();
        return new ResponseEntity<>(ResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<GroupDto>> findById(@PathVariable Long id) throws MessagingException, IOException {
        ResponseDto<GroupDto> responseDto = groupService.findById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<String>> save(@RequestBody GroupDto groupDto) throws MessagingException, IOException {
        ResponseDto<String> responseDto = groupService.save(groupDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto<String>> update(@RequestBody GroupDto groupDto) throws MessagingException, IOException {
        ResponseDto<String> responseDto = groupService.update(groupDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Long id) throws MessagingException, IOException {
        ResponseDto<String> responseDto = groupService.delete(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
