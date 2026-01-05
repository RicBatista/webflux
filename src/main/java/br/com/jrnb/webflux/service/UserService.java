package br.com.jrnb.webflux.service;

import br.com.jrnb.webflux.entity.User;
import br.com.jrnb.webflux.mapper.UserMapper;
import br.com.jrnb.webflux.model.request.UserRequest;
import br.com.jrnb.webflux.repository.UserRepository;
import br.com.jrnb.webflux.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
        return handlerNotFound(userRepository.findById(id), id);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> update(final String id, final UserRequest userRequest) {
        return findById(id)
                .map(entity -> userMapper.toEntity(userRequest, entity))
                .flatMap(userRepository::save);
    }

    public Mono<User> delete(final String id) {
        return handlerNotFound(userRepository.findAndRemove(id), id);
    }

    private <T> Mono<T> handlerNotFound(Mono<T> mono, String id) {
        return mono.switchIfEmpty(Mono.error(
                new ObjectNotFoundException("User not found with id " + id)
        ));
    }

}
