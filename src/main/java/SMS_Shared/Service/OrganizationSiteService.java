package SMS_Shared.Service;

//import SMS_Shared.DTO.OrganizationSiteDto;

import SMS_Shared.DTO.OrgDtoForSite;
import SMS_Shared.DTO.RequestDtoSite;
import SMS_Shared.DTO.ResponseOrganizationSiteDto;
import SMS_Shared.ResponceDto.ResponseDto;
import io.netty.handler.codec.MessageAggregationException;
import jakarta.mail.MessagingException;

import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface OrganizationSiteService {

    ResponseDto<String> save(RequestDtoSite requestDtoSite) throws MessagingException, IOException;


    ResponseDto<String> update(RequestDtoSite requestDtoSite) throws MessagingException, IOException;


    ResponseDto<String> delete(Long id) throws MessagingException, IOException;


    ResponseDto<List<OrgDtoForSite>> findAll(Long id) throws MessagingException, IOException;


    ResponseDto<ResponseOrganizationSiteDto> findById(Long id) throws MessagingException, IOException;

    
    HashMap<Long,  String> findAllSites() throws MessagingException, IOException;

}
