package de.judgeman.SpringBootWithSpringSecurityExample.Services;

import de.judgeman.SpringBootWithSpringSecurityExample.Enums.Permission;
import de.judgeman.SpringBootWithSpringSecurityExample.Exceptions.UsernameAlreadyExistsException;
import de.judgeman.SpringBootWithSpringSecurityExample.Model.AuthenticatedUser;
import de.judgeman.SpringBootWithSpringSecurityExample.Model.User;
import de.judgeman.SpringBootWithSpringSecurityExample.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Value("${defaultAdminUser.username}")
    private String defaultAdminUsername;

    @Value("${defaultAdminUser.password}")
    private String defaultAdminPassword;

    @Value("${defaultUser.username}")
    private String defaultUsername;

    @Value("${defaultUser.password}")
    private String defaultPassword;

    public void init() throws UsernameAlreadyExistsException {
        if (!existingAnyUser()) {
            createDefaultAdminUser();
            createNormalUser();
        }
    }

    private void createNormalUser() throws UsernameAlreadyExistsException {
        createUser(defaultUsername, defaultPassword, Permission.USER_AREA);
    }

    private void createDefaultAdminUser() throws UsernameAlreadyExistsException {
        createUser(defaultAdminUsername, defaultAdminPassword, Permission.ADMIN_AREA);
    }

    private void createUser(String username, String password, Permission permission) throws UsernameAlreadyExistsException {
        ArrayList<Permission> permissions = new ArrayList<>();
        permissions.add(permission);

        createUser(username, password, permissions);
    }

    private void createUser(String username, String password, List<Permission> permissions) throws UsernameAlreadyExistsException {
        if (userRepository.findByUsername(username) != null) {
            throw new UsernameAlreadyExistsException(username + " already exists!");
        }

        User adminUser = new User();
        adminUser.setUsername(username);
        adminUser.setPassword(getPasswordEncoder().encode(password));
        adminUser.setPermissions(permissions);

        userRepository.save(adminUser);
    }

    private boolean existingAnyUser() {
        return userRepository.findFirstByOrderByUsernameAsc() != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user with name " + username + " found");
        }

        return new AuthenticatedUser(user);
    }

    public AuthenticatedUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof AuthenticatedUser) {
            return (AuthenticatedUser) authentication.getPrincipal();
        }

        return null;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String getDefaultAdminUsername() {
        return defaultAdminUsername;
    }

    public String getDefaultAdminPassword() {
        return defaultAdminPassword;
    }

    public String getDefaultUserUsername() {
        return defaultUsername;
    }

    public String getDefaultUserPassword() {
        return defaultPassword;
    }
}
