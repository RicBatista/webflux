package br.com.jrnb.webflux.service;

import br.com.jrnb.webflux.entity.User;
import br.com.jrnb.webflux.mapper.UserMapper;
import br.com.jrnb.webflux.model.request.UserRequest;
import br.com.jrnb.webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Mono<User> save(final UserRequest request) {
        return userRepository.save(userMapper.toEntity(request));
    }

    public Mono<User> update(final UserRequest request) {

        return null;
    }

    public Mono<User> findById(final String id) {
        return userRepository.findById(id);
    }

    public Mono<User> findByEmail(final String email) {
        return null;
    }

    public Mono<User> findByUsername(final String username) {
        return null;
    }

    public Mono<User> findByEmailAndPassword(final String email, final String password) {
        return null;
    }

    public Mono<User> findByUsernameAndPassword(final String username, final String password) {
        return null;
    }

    public Mono<User> findByEmailOrUsername(final String email, final String username) {
        return null;
    }

    public Mono<User> findByUsernameOrEmail(final String username, final String email) {
        return null;
    }

    public Mono<User> findByUsernameOrEmailAndPassword(final String username, final String password) {
        return null;
    }

    public Mono<User> findByUsernameAndEmail(final String username, final String email) {
        return null;
    }
}
