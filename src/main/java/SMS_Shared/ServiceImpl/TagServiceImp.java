package SMS_Shared.ServiceImpl;

import SMS_Shared.DTO.TagDto;
import SMS_Shared.Entity.TagsEntity;
import SMS_Shared.Repository.TagRepo;
import SMS_Shared.ResponceDto.ResponseDto;
import SMS_Shared.Service.EmailService;
import SMS_Shared.Service.TagService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is the implementation of the TagService interface.
 */
@Slf4j
@Service
public class TagServiceImp implements TagService {

    @Autowired
    EmailService emailService;

    Logger logger = LoggerFactory.getLogger(TagServiceImp.class);
    @Autowired
    private TagRepo tagRepo;

    /**
     * This method is used to save or create a new tag.
     *
     * @param tagDTO
     * @return {@link ResponseDto} => String
     */
    @Override
    public ResponseDto<String> save(TagDto tagDTO) throws MessagingException, IOException {
        ResponseDto<String> ResponseDto = new ResponseDto<>();
        try {
            logger.info("save tag method");
            TagsEntity tagEntity = new TagsEntity();
            BeanUtils.copyProperties(tagDTO, tagEntity);
            tagEntity.setStatus("1");
            tagRepo.save(tagEntity);
            logger.info("Tag entity saved");
            ResponseDto.setMessage(tagEntity.getTagName() + " Saved Successfully");
            ResponseDto.setStatusCode(HttpStatus.OK.toString());
        } catch (Exception e) {

            emailService.sendEmailWithAttachment("Exception", e);

            logger.error("Error in saving tag " + e.getMessage());
            ResponseDto.setData(e.getMessage());
            ResponseDto.setMessage("Something went wrong!");
            ResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }

        return ResponseDto;
    }

    /**
     * This method is used to update a tag.
     *
     * @param tagDTO
     * @return {@link ResponseDto} => String
     */
    @Override
    public ResponseDto<String> update(TagDto tagDTO) throws MessagingException, IOException {
        ResponseDto<String> ResponseDto = new ResponseDto<>();


        try {
            logger.info("update tag method");
            TagsEntity tagEntity = tagRepo.findByIdAndStatusNot(tagDTO.getId(), "-1");
            if (tagEntity == null) {

                logger.error(" This Tag Index not found");
                ResponseDto.setData(null);
                ResponseDto.setMessage("couldn't found");
                ResponseDto.setStatusCode(HttpStatus.BAD_GATEWAY.toString());
                return ResponseDto;
            }
            if (tagDTO.getTagName() == null) {
                tagDTO.setTagName(tagEntity.getTagName());
            }
            if (tagDTO.getStatus() == null) {
                tagDTO.setStatus(tagEntity.getStatus());
            }
            BeanUtils.copyProperties(tagDTO, tagEntity);
            tagRepo.save(tagEntity);

            logger.info("update tag success");
            ResponseDto.setMessage(tagEntity.getTagName() + " Saved Successfully");
            ResponseDto.setStatusCode(HttpStatus.OK.toString());

        } catch (Exception e) {

            emailService.sendEmailWithAttachment("Exception ", e);
            logger.error("Error while updating tag " + e.getMessage());
            ResponseDto.setData(e.getMessage());
            ResponseDto.setMessage("Something went wrong!");
            ResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        }

        return ResponseDto;
    }

    /**
     * This method is used to delete a tag.
     *
     * @param id
     * @return {@link ResponseDto} => String
     */
    @Override
    public ResponseDto<String> delete(Long id) throws MessagingException, IOException {
        ResponseDto<String> responseDto = new ResponseDto<>();
        try {
            logger.info("delete tag Method");
            TagsEntity tagEntity = tagRepo.findByIdAndStatusNot(id, "-1");
            if (tagEntity == null) {
                logger.error("tag Index " +
                        id + "Not Found");
                responseDto.setData(null);
                responseDto.setMessage("couldn't found");
                responseDto.setStatusCode(HttpStatus.BAD_GATEWAY.toString());
                return responseDto;
            }
            tagEntity.setStatus("-1");
            logger.info("delete tag successfully");
            tagRepo.save(tagEntity);

            responseDto.setMessage(tagEntity.getTagName() + " Deleted Successfully");
            responseDto.setStatusCode(HttpStatus.OK.toString());

        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            logger.error("delete tag failed", e);
            responseDto.setData(e.getMessage());
            responseDto.setMessage("Something went wrong!");
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());

        }

        return responseDto;
    }

    /**
     * This method is used to get specific tag on the basis of id.
     *
     * @param id
     * @return {@link ResponseDto} => TagDto
     */
    @Override
    public ResponseDto<TagDto> findOne(Long id) throws MessagingException, IOException {
        ResponseDto<TagDto> ResponseDto = new ResponseDto<>();
        try {
            logger.info("Find tag by id");
            TagDto tagDTO = new TagDto();
            TagsEntity tagEntity = tagRepo.findByIdAndStatusNot(id, "-1");
            BeanUtils.copyProperties(tagEntity, tagDTO);

            logger.info("Succressfully find tag");
            ResponseDto.setData(tagDTO);
            ResponseDto.setMessage(tagDTO.getTagName() + " Information");
            ResponseDto.setStatusCode(HttpStatus.OK.toString());
        } catch (Exception e) {

            emailService.sendEmailWithAttachment("Exception", e);
            logger.error("Find tag by id failed", e);
            ResponseDto.setData(null);
            ResponseDto.setMessage("Something went wrong!");
            ResponseDto.setStatusCode(HttpStatus.BAD_GATEWAY.toString());
        }
        return ResponseDto;
    }

    /**
     * This method is used to get all tags.
     *
     * @return {@link ResponseDto}
     */
    @Override
    public ResponseDto<List<TagDto>> findAll() throws MessagingException, IOException {
        logger.info("Find all tags");
        ResponseDto<List<TagDto>> ResponseDto = new ResponseDto<>();
        try {
            List<TagsEntity> entityList = tagRepo.findByStatusNot("-1");
            List<TagDto> tagDTOList = new ArrayList<>(entityList.size());
            entityList.forEach(tagEntity -> {
                TagDto tagDTO = new TagDto();
                BeanUtils.copyProperties(tagEntity, tagDTO);
                tagDTOList.add(tagDTO);
            });
            logger.info("Found " + tagDTOList.size() + " tags");
            ResponseDto.setData(tagDTOList);
            ResponseDto.setMessage("All data");
            ResponseDto.setStatusCode(HttpStatus.OK.toString());
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            logger.error("Exception while find all tags");
            ResponseDto.setData(null);
            ResponseDto.setMessage("Something went wrong!");
            ResponseDto.setStatusCode(HttpStatus.BAD_GATEWAY.toString());
        }
        return ResponseDto;
    }

    /**
     * This method is used to get the hashmap of all tags.
     *
     * @return {@link ResponseDto} => HashMap<Long,String>
     */
    @Override
    public HashMap<Long, String> findAllTags() throws MessagingException, IOException {
        logger.info("All tags in HashMap");
        ResponseDto responseDto = new ResponseDto<>();
        HashMap<Long, String> tag = new HashMap<>();
        List<TagsEntity> tagsEntityList = tagRepo.findByStatusNot("-1");
        if (tagsEntityList.isEmpty()) {
            logger.error("No tags found");
            responseDto.setMessage("Not Exist");
        }
        try {

            tag = (HashMap<Long, String>) tagsEntityList.stream().
                    collect(Collectors.toMap(TagsEntity::getId, TagsEntity::getTagName));
            logger.info("Successfully Done");
            return tag;
        } catch (Exception e) {
            emailService.sendEmailWithAttachment("Exception", e);
            logger.error("Exception while this method");
            responseDto.setMessage("Not Found Tags");
        }
        return tag;
    }
}
