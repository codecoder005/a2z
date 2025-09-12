package com.popcorn.a2z;

import com.popcorn.a2z.entity.AddressEntity;
import com.popcorn.a2z.entity.BankAccountEntity;
import com.popcorn.a2z.entity.UserEntity;
import com.popcorn.a2z.entity.UserEntityPK;
import com.popcorn.a2z.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class A2ZApplication {

	public static void main(String[] args) {
		SpringApplication.run(A2ZApplication.class, args);
	}

    @Bean
    @Profile(value = "!prod")
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            var address = AddressEntity.builder().city("New York").addressType(AddressEntity.AddressType.PERMANENT).build();
            var johnDoeChaseAccount = BankAccountEntity.builder()
                    .accountNo("5678123456")
                    .ifsc("CHSE0001005")
                    .nameAsPerBankAccount("John Doe")
                    .bankName("Chase Bank")
                    .balance(BigDecimal.valueOf(12345678.90))
                    .bankAccountType(BankAccountEntity.BankAccountType.SALARY)
                    .build();
            var bankAccounts = List.of(johnDoeChaseAccount);
            var userJohn = UserEntity.builder()
                    .userEntityPK(new UserEntityPK())
                    .username("john.doe")
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@email.com")
                    .password("password")
                    .gender(UserEntity.Gender.MALE)
                    .address(address)
                    .bankAccounts(bankAccounts)
                    .build();
            address.setUser(userJohn);
            for(var bankAccount: bankAccounts) bankAccount.setUser(userJohn);
            userRepository.saveAndFlush(userJohn);
        };
    }

}
