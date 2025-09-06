package com.popcorn.a2z;

import com.popcorn.a2z.entity.AddressEntity;
import com.popcorn.a2z.entity.UserEntity;
import com.popcorn.a2z.entity.UserEntityPK;
import com.popcorn.a2z.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class A2ZApplication {

	public static void main(String[] args) {
		SpringApplication.run(A2ZApplication.class, args);
	}

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            AddressEntity address = AddressEntity.builder().city("New York").addressType(AddressEntity.AddressType.PERMANENT).build();
            UserEntity userJohn = UserEntity.builder()
                    .userEntityPK(new UserEntityPK())
                    .username("john.doe")
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@email.com")
                    .password("password")
                    .gender(UserEntity.Gender.MALE)
                    .address(address)
                    .build();
            address.setUser(userJohn);
            userRepository.saveAndFlush(userJohn);
        };
    }

}
