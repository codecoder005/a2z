package com.popcorn.a2z;

import com.popcorn.a2z.entity.AddressEntity;
import com.popcorn.a2z.entity.BankAccountEntity;
import com.popcorn.a2z.entity.UserEntity;
import com.popcorn.a2z.entity.UserEntityPK;
import com.popcorn.a2z.repository.UserRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableFeignClients
@EnableScheduling
public class A2ZApplication {

	public static void main(String[] args) {
		SpringApplication.run(A2ZApplication.class, args);
	}

    @Bean
    @Profile(value = {"local", "dev", "unit-test"})
    @ConditionalOnProperty(prefix = "spring.jpa.hibernate", name = "ddl-auto", havingValue = "create")
    CommandLineRunner commandLineRunner(UserRepository userRepository, @Value("${application.jpa.fake-data.max-limit:10}") final Integer maxLimit) {
        return args -> {
            Faker faker = new Faker();
            for(int i=10001; i< (10001 + maxLimit); i++) {
                String firstName = faker.name().firstName().replaceAll("[^a-zA-Z0-9]", " ");
                String lastName = faker.name().lastName().replaceAll("[^a-zA-Z0-9]", " ");
                String fullName = firstName + " " + lastName;
                String email = (fullName.replaceAll("[^a-zA-Z0-9]", ".")+ "-" + i + "@email.com").toLowerCase();
                var address = AddressEntity.builder().city("New York").addressType(AddressEntity.AddressType.PERMANENT).build();
                var johnDoeChaseAccount = BankAccountEntity.builder()
                        .accountNo("5678123456" + i)
                        .ifsc("CHSE0001005")
                        .nameAsPerBankAccount(fullName)
                        .bankName("Chase Bank")
                        .balance(BigDecimal.valueOf(12345678.90))
                        .bankAccountType(BankAccountEntity.BankAccountType.SALARY)
                        .build();
                var bankAccounts = List.of(johnDoeChaseAccount);
                var userJohn = UserEntity.builder()
                        .userEntityPK(new UserEntityPK())
                        .username(email.substring(0, email.indexOf("@email.com")))
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .password("password")
                        .gender(UserEntity.Gender.MALE)
                        .address(address)
                        .bankAccounts(bankAccounts)
                        .build();
                address.setUser(userJohn);
                for(var bankAccount: bankAccounts) bankAccount.setUser(userJohn);
                userRepository.saveAndFlush(userJohn);
            }

            Optional<List<UserEntity>> permanentAddressUsers = userRepository.findByAddressType(AddressEntity.AddressType.PERMANENT);
            if(permanentAddressUsers.isPresent() && permanentAddressUsers.get().getFirst() != null) {
                System.out.println(permanentAddressUsers.get().getFirst().getFirstName());
            }
        };
    }
}
