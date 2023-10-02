package SMS_Shared.MultiTenancy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSourceConfig {
    private Long id;
    private String eaDbName;
    private String riDbName;
    private String adminDbName;
    private String userName;
    private String password;
    private String driver_class_name;

}