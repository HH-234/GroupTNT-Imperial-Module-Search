package com.zds.bioengtsnapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zds.bioengtsnapp.domain.Addresses;
import com.zds.bioengtsnapp.service.AddressesService;
import com.zds.bioengtsnapp.mapper.AddressesMapper;
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




