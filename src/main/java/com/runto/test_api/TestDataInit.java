package com.runto.test_api;

import com.runto.domain.user.dao.UserRepository;
import com.runto.domain.user.domain.User;
import com.runto.domain.user.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.runto.domain.user.type.Gender.WOMAN;

@RequiredArgsConstructor
@Component
public class TestDataInit {

    private final UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {

        User user = User.builder()
                .email("runto@gmail.com")
                .name("임시유저")
                .nickname("임시유저")
                .phoneNumber("임시 휴대폰 번호")
                .gender(WOMAN)
                //.status()
                .role(UserRole.USER)
                //.profileImageUrl()
                .build();

        userRepository.save(user);
    }
}
