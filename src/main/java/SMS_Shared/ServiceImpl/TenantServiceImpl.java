package SMS_Shared.ServiceImpl;


import SMS_Shared.Entity.Tenant;
import SMS_Shared.MultiTenancy.DataSourceConfig;
import SMS_Shared.Repository.TenantRepo;
import SMS_Shared.Service.TenantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private ModelMapper modelMapper;





    /**
     * @param
     * @return
     */
    @Override
    public List<DataSourceConfig> getAllDbNames() {
        List <Tenant> tenantList = tenantRepo.findAll();
        List <DataSourceConfig> tenantDTO_respList = new ArrayList<>();
        for(Tenant tenant : tenantList){
            DataSourceConfig tenantDTO_resp = modelMapper.map(tenant,DataSourceConfig.class);
            tenantDTO_respList.add(tenantDTO_resp);
        }
        return tenantDTO_respList;
    }


}
