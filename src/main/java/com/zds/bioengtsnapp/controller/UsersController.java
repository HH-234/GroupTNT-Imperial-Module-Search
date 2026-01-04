package com.zds.bioengtsnapp.controller;

import com.zds.bioengtsnapp.domain.Users;
import com.zds.bioengtsnapp.dto.UserDetailDTO;
import com.zds.bioengtsnapp.service.UsersService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * 根据全名搜索用户基本信息
     * @param fullName 用户全名
     * @return 用户基本信息列表
     */
    @GetMapping("/users/search")
    public List<Users> searchByFullName(@RequestParam String fullName) {
        return usersService.getUsersByFullName(fullName);
    }
}
