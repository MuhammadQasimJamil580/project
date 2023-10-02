package SMS_Shared.Entity;

import SMS_Shared.MultiTenancy.RequestInterceptor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "periods", schema = "sms_shared", catalog = "")
public class PeriodsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "period_name", nullable = true, length = 255)
    private String periodName;
    @Basic
    @Column(name = "organization_index", nullable = true)
    private Long organizationIndex;
    @Basic
    @Column(name = "status", nullable = true, length = 10)
    private String status;
    @Basic
    @Column(name = "period_start", nullable = true, length = 255)
    private String periodStart;
    @Basic
    @Column(name = "period_end", nullable = true, length = 255)
    private String periodEnd;
    @Basic
    @Column(name = "created_by", nullable = true, length = 255)
    private String createdBy;
    @Basic
    @Column(name = "created_date", nullable = true)
    private ZonedDateTime createdDate;
    @Basic
    @Column(name = "description", nullable = true, length = 255)
    private String description;
    @Basic
    @Column(name = "updated_date", nullable = true)
    private ZonedDateTime updatedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodsEntity that = (PeriodsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(periodName, that.periodName) && Objects.equals(organizationIndex, that.organizationIndex) && Objects.equals(status, that.status) && Objects.equals(periodStart, that.periodStart) && Objects.equals(periodEnd, that.periodEnd) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdDate, that.createdDate) && Objects.equals(description, that.description) && Objects.equals(updatedDate, that.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, periodName, organizationIndex, status, periodStart, periodEnd, createdBy, createdDate, description, updatedDate);
    }

    @PrePersist
    public void created() {
        createdDate = ZonedDateTime.now();
        createdBy = String.valueOf(RequestInterceptor.UserId);
    }

//    @PreUpdate
//    public void updated() {
//        updatedDate = ZonedDateTime.now();
//        updatedBy  = String.valueOf(RequestInterceptor.UserId);
//    }


}
