package br.com.jrnb.webflux.repository;

import br.com.jrnb.webflux.entity.User;
import br.com.jrnb.webflux.model.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final ReactiveMongoTemplate template;

    public Mono<User> save(final User  user) {
        return template.save(user);
    }

    public Mono<User> findById(String id) {
        return template.findById(id, User.class);
    }

    public Flux<User> findAll() {
        return template.findAll(User.class);
    }

//    public Mono<User> update(final String id, final UserRequest userRequest) {
//        return template.update(User.class);
//    }
}
