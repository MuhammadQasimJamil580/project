package SMS_Shared.Controller;

import SMS_Shared.DTO.TagDto;
import SMS_Shared.ResponceDto.ResponseDto;
import SMS_Shared.Service.TagService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDto<TagDto>> findOne(@PathVariable Long id) throws MessagingException, IOException{
        ResponseDto<TagDto> ResponseDto = tagService.findOne(id);
        return new ResponseEntity<>(ResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<TagDto>>> findAll() throws MessagingException, IOException{
        ResponseDto<List<TagDto>> ResponseDto = tagService.findAll();
        return new ResponseEntity<>(ResponseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<String>> save(@RequestBody TagDto tagDTO) throws MessagingException, IOException {
        ResponseDto<String> ResponseDto = tagService.save(tagDTO);
        return new ResponseEntity<>(ResponseDto, HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Long id) throws MessagingException, IOException{
        ResponseDto<String> ResponseDto = tagService.delete(id);
        return new ResponseEntity<>(ResponseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto<String>> update(@RequestBody TagDto tagDTO) throws MessagingException, IOException{
        ResponseDto<String> ResponseDto = tagService.update(tagDTO);
        return new ResponseEntity<>(ResponseDto, HttpStatus.OK);
    }

    @GetMapping("/alltags")
    public HashMap<Long, String> getAllTags() throws MessagingException, IOException{
        HashMap<Long, String> tag = tagService.findAllTags();
        return tag;
    }

}
