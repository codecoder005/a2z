package com.popcorn.a2z.controller.graphql;

import com.popcorn.a2z.domain.response.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles(profiles = {"unit-test"})
@AutoConfigureGraphQlTester
class UserGraphQLControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void testUsersQueryGetAllUsers() {
        //language=GraphQL
        String auth_register = """
        query getAllUsers {
          users {
            getAllUsers {
              userEntityPK {
                userId
              }
              username
              email
              firstName
              gender
              lastName
              password
              createdBy
              createdOn
              updatedBy
              updatedOn
              bankAccounts {
                accountUuid
                accountNo
                ifsc
                nameAsPerBankAccount
                bankName
                bankAccountType
                balance
                createdBy
                createdOn
                updatedBy
                updatedOn
              }
              address {
                userEntityPK {
                  userId
                }
                city
                state
                country
                zipcode
                addressType
                createdBy
                createdOn
                updatedBy
                updatedOn
              }
            }
          }
        }
        """;

        GraphQlTester.EntityList<UserDTO> response = graphQlTester.document(auth_register)
                .execute()
                .path("users.getAllUsers")
                .entityList(UserDTO.class);

        assertNotNull(response.get());
        assertNotNull(response.get().getFirst());
        assertEquals("john.doe", response.get().getFirst().getUsername());
        assertEquals(10001, response.get().getFirst().getUserEntityPK().getUserId());
    }

    @Test
    void testUsersQueryGetAllUsersQueryIsFromClasspath() {
        GraphQlTester.EntityList<UserDTO> response = graphQlTester.documentName("getAllUsers")
                .execute()
                .path("users.getAllUsers")
                .entityList(UserDTO.class);

        assertNotNull(response.get());
        assertNotNull(response.get().getFirst());
        assertEquals("john.doe", response.get().getFirst().getUsername());
        assertEquals(10001, response.get().getFirst().getUserEntityPK().getUserId());
    }
}