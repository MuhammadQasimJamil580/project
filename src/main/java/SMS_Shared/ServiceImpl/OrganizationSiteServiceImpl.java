package SMS_Shared.ServiceImpl;

import SMS_Shared.DTO.*;
import SMS_Shared.Entity.GroupEntity;
import SMS_Shared.Entity.OrganizationSiteEntity;
import SMS_Shared.MultiTenancy.RequestInterceptor;
import SMS_Shared.Repository.GroupRepo;
import SMS_Shared.Repository.OrganinzationSiteRepo;
import SMS_Shared.ResponceDto.ResponseDto;
import SMS_Shared.Service.EmailService;
import SMS_Shared.Service.OrganizationSiteService;
import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class OrganizationSiteServiceImpl implements OrganizationSiteService {

    public final EmailService emailService;

    @Autowired
    private OrganinzationSiteRepo organinzationSiteRepo;
    @Autowired
    private GroupRepo groupRepo;
    @Value("${usermanagment-service}")
    private String usermanagmentServiceUrl;

    private static final WebClient webClient = WebClient.create();

    public OrganizationSiteServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }


    @Override
    public ResponseDto<String> save(RequestDtoSite requestDtoSite) throws MessagingException, IOException {
        ResponseDto<String> responseDto = new ResponseDto<>();
        try {
            OrganizationSiteEntity organizationSiteEntity = new OrganizationSiteEntity();
            GroupEntity groupEntity = groupRepo.findByIdAndStatusNot(Long.valueOf(requestDtoSite.getGroupIndex()), "-1");
            BeanUtils.copyProperties(requestDtoSite, organizationSiteEntity);
            if (groupEntity == null) {
                responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
                responseDto.setMessage("Group not Exist");
                return responseDto;
            } else {
                organizationSiteEntity.setGroupIndex(groupEntity);
            }
            organizationSiteEntity.setGroupIndex(groupEntity);
            organizationSiteEntity.setStatus("1");
            organizationSiteEntity.setCreatedDate(String.valueOf(new Date()));
            organinzationSiteRepo.save(organizationSiteEntity);
            responseDto.setMessage("Saved Successfully");
            responseDto.setStatusCode(HttpStatus.OK.toString());
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setMessage("not update");
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }

        return responseDto;
    }

    @Override
    public ResponseDto<String> update(RequestDtoSite requestDtoSite) throws MessagingException, IOException {
        ResponseDto<String> responseDto = new ResponseDto<>();
        try {
            OrganizationSiteEntity organizationSiteEntity = organinzationSiteRepo.findById(requestDtoSite.getId()).orElseThrow();
            GroupEntity groupEntity = groupRepo.findByIdAndStatusNot(Long.valueOf(requestDtoSite.getGroupIndex()), "-1");
            BeanUtils.copyProperties(requestDtoSite, organizationSiteEntity);
            if (groupEntity == null) {
                responseDto.setMessage("Group not exist");
                responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
                return responseDto;
            }
            organizationSiteEntity.setGroupIndex(groupEntity);
            organinzationSiteRepo.save(organizationSiteEntity);
            responseDto.setMessage("Updated Successfully");
            responseDto.setStatusCode(HttpStatus.OK.toString());
            responseDto.setMessage("Updated Successfully");
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
            responseDto.setMessage("Not updated");
        }
        return responseDto;
    }

    @Override
    public ResponseDto<String> delete(Long id) throws MessagingException, IOException {
        ResponseDto<String> responseDto = new ResponseDto<>();
        try {
            OrganizationSiteEntity organizationSiteEntity = organinzationSiteRepo.findByIdAndStatusNot(id, "-1");
            organizationSiteEntity.setStatus("-1");
            organinzationSiteRepo.save(organizationSiteEntity);
            responseDto.setStatusCode(HttpStatus.OK.toString());
            responseDto.setMessage("Deleted Successfully");
        } catch (Exception e) {

            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
            responseDto.setMessage("not deleted");
        }
        return responseDto;
    }

    @Override
    public ResponseDto<List<OrgDtoForSite>> findAll(Long id) throws MessagingException, IOException {
        ResponseDto<List<OrgDtoForSite>> responseDto = new ResponseDto<>();
        HashMap<Long, String> organization = getorganization();
        organization.keySet();
        try {

            List<OrganizationSiteEntity> organizationSiteEntityList = organinzationSiteRepo.findByStatusNotAndOrganizationIndex("-1", id);
            List<OrgDtoForSite> orgDtoForSiteList = new ArrayList<>(organizationSiteEntityList.size());

            organizationSiteEntityList.forEach(organizationSiteEntity -> {

                OrgDtoForSite orgDtoForSite = new OrgDtoForSite();
                GroupDto groupDto = new GroupDto();

                BeanUtils.copyProperties(organizationSiteEntity, orgDtoForSite);
                if (organizationSiteEntity.getGroupIndex() != null) {
                    BeanUtils.copyProperties(organizationSiteEntity.getGroupIndex(), groupDto);
                }
                if (organizationSiteEntity.getOrganizationIndex() == null) {
                    responseDto.setMessage("Organization not Exist");
                    responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());

                }
                orgDtoForSite.setGroupIndex(groupDto);
                orgDtoForSite.setOrganizationName(organization.get((organizationSiteEntity.getOrganizationIndex())));
                orgDtoForSiteList.add(orgDtoForSite);
            });

            responseDto.setData(orgDtoForSiteList);
            responseDto.setStatusCode(HttpStatus.OK.toString());
        } catch (Exception e) {


            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }
        return responseDto;
    }

    @Override
    public ResponseDto<ResponseOrganizationSiteDto> findById(Long id) throws MessagingException, IOException {
        ResponseDto<ResponseOrganizationSiteDto> responseDto = new ResponseDto<>();
        try {
            OrganizationSiteEntity organizationSiteEntity = organinzationSiteRepo.findByIdAndStatusNot(id, "-1");
            GroupDto groupDto = new GroupDto();
            ResponseOrganizationSiteDto responseOrganizationSiteDto = new ResponseOrganizationSiteDto();
            if(organizationSiteEntity.getId()==null){
                responseDto.setMessage("Id Not Exist");
                responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
            }
            if (organizationSiteEntity.getGroupIndex() != null) {
                BeanUtils.copyProperties(organizationSiteEntity.getGroupIndex(), groupDto);
            }
            BeanUtils.copyProperties(organizationSiteEntity, responseOrganizationSiteDto);
            responseOrganizationSiteDto.setGroupIndex(groupDto);
            responseDto.setStatusCode(HttpStatus.OK.toString());
            responseDto.setData(responseOrganizationSiteDto);
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setMessage("Something went wrong");

            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }
        return responseDto;
    }

    @Override
    public HashMap<Long, String> findAllSites() throws MessagingException, IOException {
        ResponseDto responseDto = new ResponseDto<>();
        HashMap<Long, String> orgsite = new HashMap<>();

        try {
            List<OrganizationSiteEntity> organizationSiteEntityList = organinzationSiteRepo.findByStatusNot("-1");
            if (organizationSiteEntityList.isEmpty()) {
                responseDto.setMessage("Not Exist");
            }
            try {
                orgsite = (HashMap<Long, String>) organizationSiteEntityList.stream()
                        .collect(Collectors.toMap(org -> org.getId(), org -> org.getOrgSiteName()));
                return orgsite;
            } catch (Exception e) {
                emailService.sendEmailWithAttachment("Exception", e);
                responseDto.setMessage("NOt Found Org Sites");
            }
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setMessage("Something went wrong");

        }

        return orgsite;
    }

    private HashMap<Long, String> getorganization() {
        HashMap<Long, String> organizationmap = new HashMap<>();

        organizationmap = webClient.get()
                .uri(usermanagmentServiceUrl )
                .header(HttpHeaders.AUTHORIZATION, RequestInterceptor.Token,HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<HashMap<Long, String>>() {
                }).block();
//                .bodyToMono(new ParameterizedTypeReference HashMap.class).block();
        return organizationmap;
    }

}