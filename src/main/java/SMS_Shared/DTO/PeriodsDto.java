package SMS_Shared.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeriodsDto {


    private Long id;

    private String periodName;

    private Long organizationIndex;

    private String organizationName;

    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    private String status;

    private String periodStart;

    private String periodEnd;

    private String description;




}
