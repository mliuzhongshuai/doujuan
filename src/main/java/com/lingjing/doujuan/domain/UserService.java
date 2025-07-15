package com.lingjing.doujuan.domain;

import com.lingjing.doujuan.infrastructure.storage.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
