package SMS_Shared.Controller;

//import SMS_Shared.DTO.OrganizationSiteDto;

import SMS_Shared.DTO.OrgDtoForSite;
import SMS_Shared.DTO.RequestDtoSite;
import SMS_Shared.DTO.ResponseOrganizationSiteDto;
import SMS_Shared.ResponceDto.ResponseDto;
import SMS_Shared.Service.OrganizationSiteService;
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
@RequestMapping(value = "/site")
public class OrganizationSiteController {
    @Autowired
    private OrganizationSiteService organizationSiteService;

    @GetMapping("/findByOrg/{id}")
    public ResponseEntity<ResponseDto<List<OrgDtoForSite>>> findAll(@PathVariable Long id) throws MessagingException, IOException{
        ResponseDto<List<OrgDtoForSite>> responseDtoList = organizationSiteService.findAll(id);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ResponseOrganizationSiteDto>> findById(@PathVariable Long id)throws MessagingException, IOException {
        ResponseDto<ResponseOrganizationSiteDto> responseDto = organizationSiteService.findById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<String>> save(@RequestBody RequestDtoSite organizationSiteDto) throws MessagingException, IOException {
        ResponseDto<String> responseDto = organizationSiteService.save(organizationSiteDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto<String>> update(@RequestBody RequestDtoSite organizationSiteDto) throws MessagingException, IOException{
        ResponseDto<String> responseDto = organizationSiteService.update(organizationSiteDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Long id) throws MessagingException, IOException{
        ResponseDto<String> responseDto = organizationSiteService.delete(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getAllOrgSite")
    public HashMap<Long, String> getAllSites() throws MessagingException, IOException{
        HashMap<Long, String> orgsite = organizationSiteService.findAllSites();
        return orgsite;
    }
}
