package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.PhoneNumbers;
import generator.service.PhoneNumbersService;
import generator.mapper.PhoneNumbersMapper;
import org.springframework.stereotype.Service;

/**
* @author 33882
* @description 针对表【phone_numbers】的数据库操作Service实现
* @createDate 2026-01-02 23:18:33
*/
@Service
public class PhoneNumbersServiceImpl extends ServiceImpl<PhoneNumbersMapper, PhoneNumbers>
    implements PhoneNumbersService{

}




