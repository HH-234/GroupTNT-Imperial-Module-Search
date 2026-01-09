package com.zds.bioengtsnapp.controller;

import com.zds.bioengtsnapp.domain.Users;
import com.zds.bioengtsnapp.dto.UserDetailDTO;
import com.zds.bioengtsnapp.service.UsersService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
     * Search user basic information by full name
     * URL: /users/search?fullName=xxx
     */
    @GetMapping("/users/search")
    public List<Users> searchByFullName(@RequestParam String fullName) {
        return usersService.getUsersByFullName(fullName);
    }

    /**
     * Search user detailed information by full name (including phoneNumbers / addresses / discoveryUrlId etc.)
     * URL: /users/search/details?fullName=xxx&page=1&size=10
     */
    @GetMapping("/users/search/details")
    public IPage<UserDetailDTO> searchDetailsByFullName(
            @RequestParam(required = false, defaultValue = "") String fullName,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return usersService.getUserDetailsByFullNamePage(page, size, fullName);
    }
}
