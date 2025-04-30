package com.lingjing.doujuan.domain;

import com.lingjing.doujuan.infrastructure.storage.UserRepository;
import com.lingjing.doujuan.infrastructure.storage.po.User;
import org.springframework.stereotype.Service;

import java.util.concurrent.Flow;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public interface Publisher<T> {



         void subscribe(Flow.Subscriber<? super T> s);
    }






}
