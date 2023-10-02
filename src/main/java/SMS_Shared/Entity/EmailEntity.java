package SMS_Shared.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "email_credentials", schema = "sms_global", catalog = "")
public class EmailEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "email_user_id", nullable = true, length = 255)
    private String emailUserId;
    @Basic
    @Column(name = "email_password", nullable = true)
    private String emailPassword;
    @Basic
    @Column(name = "smtp", nullable = true)
    private String smtp;
    @Basic
    @Column(name = "port", nullable = true)
    private String port;
    @Basic
    @Column(name = "authentication", nullable = true)
    private String authentication;
    @Basic
    @Column(name = "hostname", nullable = true)
    private String hostname;
    @Basic
    @Column(name = "email_to", nullable = true)
    private String emailTo;
}
