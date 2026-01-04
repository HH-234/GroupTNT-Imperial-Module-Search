package com.zds.bioengtsnapp.controller;

import com.zds.bioengtsnapp.domain.Users;
import com.zds.bioengtsnapp.dto.UserDetailDTO;
import com.zds.bioengtsnapp.service.UsersService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("users")
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
    @GetMapping("/search")
    public List<Users> searchByFullName(@RequestParam String fullName) {
        return usersService.getUsersByFullName(fullName);
    }

    /**
     * 根据全名搜索用户详细信息（包含手机号码和地址）
     * @param fullName 用户全名
     * @return 用户详细信息列表（包含手机、地址、discovery_url_id等）
     */
    @GetMapping("/search/details")
    public List<UserDetailDTO> searchDetailsByFullName(@RequestParam String fullName) {
        return usersService.getUserDetailsByFullName(fullName);
    }
}
