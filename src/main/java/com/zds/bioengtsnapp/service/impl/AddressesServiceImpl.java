package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Addresses;
import generator.service.AddressesService;
import generator.mapper.AddressesMapper;
import org.springframework.stereotype.Service;

/**
* @author 33882
* @description 针对表【addresses】的数据库操作Service实现
* @createDate 2026-01-02 23:16:57
*/
@Service
public class AddressesServiceImpl extends ServiceImpl<AddressesMapper, Addresses>
    implements AddressesService{

}




