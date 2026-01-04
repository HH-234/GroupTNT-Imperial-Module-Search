package com.zds.bioengtsnapp.controller;

import com.zds.bioengtsnapp.domain.Users;
import com.zds.bioengtsnapp.dto.UserDetailDTO;
import com.zds.bioengtsnapp.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * 根据全名搜索用户基本信息
     * URL: /users/search?fullName=xxx
     */
    @GetMapping("/users/search")
    public List<Users> searchByFullName(@RequestParam String fullName) {
        return usersService.getUsersByFullName(fullName);
    }

    /**
     * 根据全名搜索用户详细信息（包含 phoneNumbers / addresses / discoveryUrlId 等）
     * URL: /users/search/details?fullName=xxx
     */
    @GetMapping("/users/search/details")
    public List<UserDetailDTO> searchDetailsByFullName(@RequestParam String fullName) {
        return usersService.getUserDetailsByFullName(fullName);
    }
}
