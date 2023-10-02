package SMS_Shared.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "add_group", schema = "sms_shared", catalog = "")
public class GroupEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "group_name", nullable = true, length = 255)
    private String groupName;
    @Basic
    @Column(name = "status", nullable = true)
    private String status;
    @OneToMany(mappedBy = "groupIndex")
    private Set<OrganizationSiteEntity> organizationSitesById;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupEntity that = (GroupEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(groupName, that.groupName) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupName, status);
    }



}
