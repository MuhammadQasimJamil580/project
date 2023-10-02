package SMS_Shared.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organization_site", schema = "sms_shared", catalog = "")
public class OrganizationSiteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "org_site_name", nullable = true, length = 255)
    private String orgSiteName;
    @Basic
    @Column(name = "status", nullable = true, length = 10)
    private String status;
    @Basic
    @Column(name = "organization_index", nullable = true)
    private Long organizationIndex;
    @Basic
    @Column(name = "address", nullable = true, length = 255)
    private String address;
    @Basic
    @Column(name = "created_by", nullable = true, length = 255)
    private String createdBy;
    @Basic
    @Column(name = "created_date", nullable = true, length = 255)
    private String createdDate;
    @Basic
    @Column(name = "description", nullable = true, length = 255)
    private String description;
    @Basic
    @Column(name = "contact_no", nullable = true, length = 255)
    private String contactNo;
    @Basic
    @Column(name = "updated_date", nullable = true)
    private Timestamp updatedDate;
    @Basic
    @Column(name = "updated_by", nullable = true, length = 200)
    private String updatedBy;
    @ManyToOne
    @JoinColumn(name = "group_index", referencedColumnName = "id")
    private GroupEntity groupIndex;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSiteEntity that = (OrganizationSiteEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(orgSiteName, that.orgSiteName) && Objects.equals(status, that.status) && Objects.equals(organizationIndex, that.organizationIndex) && Objects.equals(address, that.address) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdDate, that.createdDate) && Objects.equals(description, that.description) && Objects.equals(contactNo, that.contactNo) && Objects.equals(updatedDate, that.updatedDate) && Objects.equals(updatedBy, that.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orgSiteName, status, organizationIndex, address, createdBy, createdDate, description, contactNo, updatedDate, updatedBy);
    }



}
