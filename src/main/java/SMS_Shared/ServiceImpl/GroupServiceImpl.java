package SMS_Shared.ServiceImpl;

import SMS_Shared.DTO.GroupDto;
import SMS_Shared.Entity.GroupEntity;
import SMS_Shared.Repository.GroupRepo;
import SMS_Shared.ResponceDto.ResponseDto;
import SMS_Shared.Service.EmailService;
import SMS_Shared.Service.GroupService;
import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    public final EmailService emailService;

    @Autowired
    private GroupRepo groupRepo;

    public GroupServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public ResponseDto<String> save(GroupDto groupDto) throws MessagingException, IOException {
        ResponseDto<String> responseDto = new ResponseDto<>();
        try {
            GroupEntity groupEntity = new GroupEntity();
            BeanUtils.copyProperties(groupDto, groupEntity);
            groupEntity.setStatus("1");
            groupRepo.save(groupEntity);
            responseDto.setMessage("Created Successfully");
            responseDto.setStatusCode(HttpStatus.OK.toString());

        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception occurs", e);
            responseDto.setMessage("Not created ");
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }

        return responseDto;
    }

    @Override
    public ResponseDto<String> update(GroupDto groupDto) throws MessagingException, IOException {
        ResponseDto<String> responseDto = new ResponseDto<>();
        try {
            GroupEntity groupEntity = groupRepo.findById(groupDto.getId()).orElseThrow();
            if (groupEntity == null) {
                responseDto.setMessage("Not Exist");
                responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
                return responseDto;
            } else {
                BeanUtils.copyProperties(groupDto, groupEntity);
                groupRepo.save(groupEntity);
                responseDto.setMessage("Updated Successfully");
                responseDto.setStatusCode(HttpStatus.OK.toString());
            }
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setMessage("Not updated");
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }
        return responseDto;
    }

    @Override
    public ResponseDto<String> delete(Long id) throws MessagingException, IOException {
        ResponseDto<String> responseDto = new ResponseDto<>();
        try {
            GroupEntity groupEntity = groupRepo.findByIdAndStatusNot(id, "-1");
            if (groupEntity == null) {
                responseDto.setMessage("Not Exist");
                responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
                return responseDto;
            } else {
                groupEntity.setStatus("-1");
                groupRepo.delete(groupEntity);
                responseDto.setMessage("Deleted Successfully");
                responseDto.setStatusCode(HttpStatus.OK.toString());
            }
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setMessage("Not Deleted");
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }
        return responseDto;
    }

    @Override
    public ResponseDto<GroupDto> findById(Long id) throws MessagingException, IOException {
        ResponseDto<GroupDto> responseDto = new ResponseDto<>();
        try {
            GroupEntity groupEntity = groupRepo.findByIdAndStatusNot(id, "-1");
            if (groupEntity == null) {
                responseDto.setMessage("Not Exist");
                responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
                return responseDto;
            } else {
                GroupDto groupDto = new GroupDto();
                BeanUtils.copyProperties(groupEntity, groupDto);
                responseDto.setData(groupDto);
                responseDto.setStatusCode(HttpStatus.OK.toString());
            }
        } catch (Exception e) {

            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }
        return responseDto;
    }

    @Override
    public ResponseDto<List<GroupDto>> findAll() throws MessagingException, IOException {
        ResponseDto responseDto = new ResponseDto<>();
        try {
            List<GroupEntity> groupEntityList = groupRepo.findByStatusNot("-1");
            if (groupEntityList == null) {
                responseDto.setMessage("Not Exist");
                responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
                return responseDto;
            } else {
                List<GroupDto> groupDtoList = new ArrayList<>(groupEntityList.size());
                groupEntityList.forEach(groupEntity -> {
                    GroupDto groupDto = new GroupDto();
                    BeanUtils.copyProperties(groupEntity, groupDto);
                    groupDtoList.add(groupDto);
                });
                responseDto.setData(groupDtoList);
                responseDto.setStatusCode(HttpStatus.OK.toString());
            }
        } catch (Exception e) {

            emailService.sendEmailWithAttachment("Exception", e);
            responseDto.setMessage("Not find such group");
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }
        return responseDto;
    }
}
