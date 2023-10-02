package SMS_Shared.ServiceImpl;

import SMS_Shared.DTO.PeriodsDto;
import SMS_Shared.Entity.PeriodsEntity;
import SMS_Shared.MultiTenancy.RequestInterceptor;
import SMS_Shared.Repository.PeriodsRepo;
import SMS_Shared.ResponceDto.ResponseDto;
import SMS_Shared.Service.EmailService;
import SMS_Shared.Service.PeriodsService;
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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeriodsServiceImpl implements PeriodsService {

    @Autowired
    EmailService emailService;
    @Autowired
    private PeriodsRepo periodsRepo;

    @Value("${usermanagment-service}")
    private String usermanagmentServiceUrl;

    private static final WebClient webClient = WebClient.create();
    @Override
    public ResponseDto<String> save(PeriodsDto periodsDto) throws MessagingException, IOException {

        ResponseDto<String> responseDto = new ResponseDto<>();
        try {
            PeriodsEntity periodsEntity = new PeriodsEntity();
            BeanUtils.copyProperties(periodsDto, periodsEntity);
            periodsEntity.setStatus(String.valueOf(1));
            periodsDto.setCreatedDate(ZonedDateTime.now());
            periodsRepo.save(periodsEntity);
            responseDto.setMessage("Created Successfully");
            responseDto.setStatusCode(HttpStatus.OK.toString());
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception ", e);
            responseDto.setMessage("Not created ");
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }
        return responseDto;
    }

    @Override
    public ResponseDto<String> update(PeriodsDto periodsDto) throws MessagingException, IOException {
        ResponseDto<String> responseDto = new ResponseDto<>();
        try {
            PeriodsEntity periodsEntity = periodsRepo.findById(periodsDto.getId()).orElseThrow();
            if (periodsEntity == null) {
                responseDto.setMessage("Not Exist");
                responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
                return responseDto;
            } else {
                BeanUtils.copyProperties(periodsDto, periodsEntity);
                periodsDto.setUpdatedDate(ZonedDateTime.now());
                periodsRepo.save(periodsEntity);
                responseDto.setStatusCode(HttpStatus.OK.toString());
                responseDto.setMessage("Updated Successfully");
            }
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setStatusCode(HttpStatus.OK.toString());
            responseDto.setMessage("Updated Successfully");
        }

        return responseDto;
    }

    @Override
    public ResponseDto<String> delete(Long id) throws MessagingException, IOException {
        ResponseDto<String> responseDto = new ResponseDto<>();
        try {
            PeriodsEntity periodsEntity = periodsRepo.findByIdAndStatusNot(id, "-1");
            if (periodsEntity == null) {
                responseDto.setStatusCode(HttpStatus.OK.toString());
                responseDto.setMessage("Not Exist");
            } else {
                periodsEntity.setStatus("-1");
                periodsRepo.save(periodsEntity);
                responseDto.setStatusCode(HttpStatus.OK.toString());
                responseDto.setMessage("Deleted Successfully");
            }
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
            responseDto.setMessage("Not Deleted");
        }
        return responseDto;
    }

    @Override
    public ResponseDto<PeriodsDto> findById(Long id) throws MessagingException, IOException {
        ResponseDto<PeriodsDto> responseDto = new ResponseDto<>();
        try {
            PeriodsEntity periodsEntity = periodsRepo.findByIdAndStatusNot(id, "-1");
            if (periodsEntity == null) {
                responseDto.setMessage("Id Not Exist");
                responseDto.setStatusCode(HttpStatus.OK.toString());
            } else {
                PeriodsDto periodsDto = new PeriodsDto();
                BeanUtils.copyProperties(periodsEntity, periodsDto);
                responseDto.setData(periodsDto);
                responseDto.setStatusCode(HttpStatus.OK.toString());
            }
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }
        return responseDto;
    }

    @Override
    public ResponseDto<List<PeriodsDto>> findAll() throws MessagingException, IOException {
        ResponseDto<List<PeriodsDto>> responseDto = new ResponseDto<>();
        HashMap<Long, String> organization = getorganization();
        organization.keySet();
        try {
            List<PeriodsEntity> periodsEntityList = periodsRepo.findByStatusNot("-1");
            if (periodsEntityList == null) {
                responseDto.setMessage("Not Exist");
                responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
                return responseDto;
            } else {
                List<PeriodsDto> periodsDtoList = new ArrayList<>(periodsEntityList.size());
                periodsEntityList.forEach(periodsEntity -> {
                    PeriodsDto periodsDto = new PeriodsDto();
                    BeanUtils.copyProperties(periodsEntity, periodsDto);
                    periodsDto.setOrganizationName(organization.get(periodsEntity.getId()));
                    periodsDtoList.add(periodsDto);
                });
                responseDto.setData(periodsDtoList);
                responseDto.setStatusCode(HttpStatus.OK.toString());
            }
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }
        return responseDto;
    }

    @Override
    public HashMap<Long, String> getperiod() throws MessagingException, IOException {
        ResponseDto responseDto = new ResponseDto<>();
        HashMap<Long, String> period = new HashMap<>();
        try {
            List<PeriodsEntity> periodsEntityList = periodsRepo.findByStatusNot("-1");

            if (periodsEntityList.isEmpty()) {
                responseDto.setMessage("Not found");
            }

            try {
                period = (HashMap<Long, String>) periodsEntityList.stream()
                        .collect(Collectors.toMap(PeriodsEntity::getId, PeriodsEntity::getPeriodName));
                return period;
            } catch (Exception e) {
                emailService.sendEmailWithAttachment("Exception ", e);
                responseDto.setMessage("Error in Period");
            }

        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception ", e);
            responseDto.setMessage("Error in Period");

        }
        return period;
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
