package assignment.interview_management.security;

import assignment.interview_management.entity.Account;
import assignment.interview_management.exceptions.AuthException;
import assignment.interview_management.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        return accountRepository.findByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new AuthException("Tên đăng nhập hoặc mật khẩu không hợp lệ"));
    }

    private User createUserDetails(Account account) {
        Optional<String> roleOptional = Optional.of(account.getRole());
        List<? extends GrantedAuthority> authorities = roleOptional.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role)))
                .toList();
        return new User(account.getUsername(), account.getPassword(), authorities);
    }

}
