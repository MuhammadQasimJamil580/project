package SMS_Shared.Controller;

import SMS_Shared.DTO.PeriodsDto;
import SMS_Shared.ResponceDto.ResponseDto;
import SMS_Shared.Service.PeriodsService;
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
@RequestMapping(value = "/period")
public class PeriodsController {
    @Autowired
    private PeriodsService periodsService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<PeriodsDto>>> findAll() throws MessagingException, IOException {
        ResponseDto<List<PeriodsDto>> responseDtoList = periodsService.findAll();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<PeriodsDto>> findById(@PathVariable Long id) throws MessagingException, IOException{
        ResponseDto<PeriodsDto> responseDto = periodsService.findById(id);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto<String>> update(@RequestBody PeriodsDto periodsDto) throws MessagingException, IOException{
        ResponseDto<String> responseDto = periodsService.update(periodsDto);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<String>> save(@RequestBody PeriodsDto periodsDto) throws MessagingException, IOException{
        ResponseDto<String> responseDto = periodsService.save(periodsDto);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Long id)throws MessagingException, IOException{
        ResponseDto<String> responseDto=periodsService.delete(id);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @GetMapping("/getperiod")
    public HashMap<Long,String> findAllPeriod()throws MessagingException, IOException{
        HashMap<Long,String> period=periodsService.getperiod();
        return  period;
    }
}
