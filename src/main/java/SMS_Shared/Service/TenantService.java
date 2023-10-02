package SMS_Shared.Service;

import SMS_Shared.MultiTenancy.DataSourceConfig;


import java.util.List;

public interface TenantService {

    List<DataSourceConfig> getAllDbNames();



}
