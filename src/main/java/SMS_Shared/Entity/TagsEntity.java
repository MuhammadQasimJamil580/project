package SMS_Shared.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags", schema = "sms_shared", catalog = "")
public class TagsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "tag_name", nullable = true, length = 255)
    private String tagName;
    @Basic
    @Column(name = "status", nullable = true, length = 255)
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagsEntity that = (TagsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(tagName, that.tagName) && Objects.equals(status, that.status);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, tagName, status);
    }
}
