package br.com.jrnb.webflux.service;

import br.com.jrnb.webflux.entity.User;
import br.com.jrnb.webflux.mapper.UserMapper;
import br.com.jrnb.webflux.model.request.UserRequest;
import br.com.jrnb.webflux.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Test
    void save() {
        UserRequest request = new UserRequest("", "", "");
        User entity = User.builder().build();

        when(userMapper.toEntity(any(UserRequest.class))).thenReturn(entity);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(User.builder().build()));

        Mono<User> result = userService.save(request);

        StepVerifier.create(result)
                .expectNextMatches(Objects::nonNull)
                .expectComplete()
                .verify();

        assertNotNull(request.name());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findById() {
        // Arrange
        User entity = User.builder().id("123").name("Maria").build();
        when(userRepository.findById(anyString())).thenReturn(Mono.just(entity));

        // Act
        Mono<User> result = userService.findById("123");

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(user -> user.getId().equals("123") && user.getName().equals("Maria"))
                .expectComplete()
                .verify();

        verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    void findAll() {
        // Arrange
        User entity = User.builder().name("Maria").build();
        when(userRepository.findAll()).thenReturn(Flux.just(entity));

        // Act
        Flux<User> result = userService.findAll();

        // Assert
        StepVerifier.create(result)
                .expectNext(entity) // Verifica se o objeto emitido é o esperado
                .expectComplete()
                .verify();

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void update() {
        // Arrange
        String id = "123";
        UserRequest request = new UserRequest("Maria Alterada", "email@test.com", "123");
        User entityAntiga = User.builder().id(id).name("Maria Antiga").build();
        User entityNova = User.builder().id(id).name("Maria Alterada").build();

        when(userRepository.findById(anyString())).thenReturn(Mono.just(entityAntiga));

        when(userMapper.toEntity(any(UserRequest.class), any(User.class))).thenReturn(entityNova);

        when(userRepository.save(any(User.class))).thenReturn(Mono.just(entityNova));

        Mono<User> result = userService.update(id, request);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getName().equals("Maria Alterada"))
                .expectComplete()
                .verify();

        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toEntity(any(UserRequest.class), any(User.class));
    }

    @Test
    void delete() {
        User entity = User.builder().build();
        when(userRepository.findAndRemove(anyString())).thenReturn(Mono.just(entity));
        Mono<User> result = userService.delete("123");

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass() == User.class)
                .expectComplete()
                .verify();

        verify(userRepository, times(1)).findAndRemove(anyString());
        verify(userRepository, never()).findById(anyString());
        verify(userRepository, never()).findAndRemove(String.valueOf(ArgumentMatchers.any(User.class)));
    }
}