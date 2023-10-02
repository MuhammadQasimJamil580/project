package SMS_Shared.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrganizationSiteDto {
    private Long id;
    private String orgSiteName;
    private String status;
    private Long organizationIndex;
    private String address;
    private String createdBy;
    private String createdDate;
    private String description;
    private String contactNo;
    private Timestamp updatedDate;
    private String updatedBy;
    private GroupDto groupIndex;
}
