package com.digitlibraryproject.security;

import com.digitlibraryproject.domain.User;
import com.digitlibraryproject.domain.request.RegistrationUser;
import com.digitlibraryproject.repository.RoleRepository;
import com.digitlibraryproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public SecurityService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean registration(RegistrationUser registrationUser) {
        try {
            User user = new User();
            user.setName(registrationUser.getName());
            user.setEmail(registrationUser.getEmail());
            user.setPhoneNumber(registrationUser.getPhoneNumber());
            user.setLogin(registrationUser.getLogin());
            user.setPassword(passwordEncoder.encode(registrationUser.getPassword()));

            User savedUser = userRepository.save(user);
            roleRepository.setUserRole(savedUser.getId());

            if (savedUser != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Transactional
    public boolean updateUserToAdmin(int id) {
        if (roleRepository.updateUserToAdmin(id) > 0) {
            return true;
        }
        throw new UsernameNotFoundException("USER_NOT_FOUND");
    }

    @Transactional
    public boolean updateAdminToUser(int id) {
        if (roleRepository.updateAdminToUser(id) > 0) {
            return true;
        }
        throw new UsernameNotFoundException("ADMIN_NOT_FOUND");
    }

    @Transactional
    public boolean updateUserToSubscriber(int id) {
        if (roleRepository.updateUserToSubscriber(id) > 0) {
            return true;
        }
        throw new UsernameNotFoundException("USER_NOT_FOUND");
    }
}
