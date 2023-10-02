package SMS_Shared.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GroupDto {
    private Long id;
    private String groupName;
    private String status;
//    private Set<OrganizationSiteDto> organizationSitesById;



}
