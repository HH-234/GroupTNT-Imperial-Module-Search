package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Courses;
import generator.service.CoursesService;
import generator.mapper.CoursesMapper;
import org.springframework.stereotype.Service;

/**
* @author 33882
* @description 针对表【courses】的数据库操作Service实现
* @createDate 2026-01-02 23:18:31
*/
@Service
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses>
    implements CoursesService{

}




