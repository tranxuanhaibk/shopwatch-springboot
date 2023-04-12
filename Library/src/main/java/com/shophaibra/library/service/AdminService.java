package com.shophaibra.library.service;

import com.shophaibra.library.dto.AdminDto;
import com.shophaibra.library.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);
}
