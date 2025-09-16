package com.popcorn.a2z.repository;

import com.popcorn.a2z.entity.AddressEntity;
import com.popcorn.a2z.entity.UserEntity;
import com.popcorn.a2z.entity.UserEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * {@code UserRepository} is the primary Data Access Layer (DAL)
 * interface for performing CRUD (Create, Read, Update, Delete)
 * and query operations on {@link UserEntity}.
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Extends {@link JpaRepository}, inheriting a wide range of
 *       ready-to-use database operations (such as {@code save}, {@code findById},
 *       {@code findAll}, {@code deleteById}, etc.).</li>
 *   <li>Supports custom query methods following Spring Data JPA’s
 *       method naming conventions (e.g., {@code findByEmail(String email)}).</li>
 *   <li>Works with composite primary keys via {@link UserEntityPK}.</li>
 *   <li>Integrates seamlessly with Spring’s dependency injection,
 *       so it can be injected into services using {@code @Autowired}
 *       or constructor injection.</li>
 * </ul>
 *
 * <p><b>Entity:</b> {@link UserEntity}</p>
 * <p><b>Primary Key:</b> {@link UserEntityPK}</p>
 *
 * <p><b>Typical Use Cases:</b></p>
 * <ul>
 *   <li>Fetching a user by their composite key (e.g., tenant + userId).</li>
 *   <li>Retrieving all users in the system for reporting or administration.</li>
 *   <li>Persisting new users or updating existing users.</li>
 *   <li>Deleting users that are no longer active.</li>
 * </ul>
 *
 * <p><b>Example Usage:</b></p>
 * <pre>{@code
 * @Service
 * public class UserService {
 *
 *     private final UserRepository userRepository;
 *
 *     public UserService(UserRepository userRepository) {
 *         this.userRepository = userRepository;
 *     }
 *
 *     public UserDTO getUser(Long userId, String tenantId) {
 *         UserEntityPK pk = new UserEntityPK(userId, tenantId);
 *         return userRepository.findById(pk)
 *             .map(UserMapper::toDTO)
 *             .orElseThrow(() -> new UserNotFoundException(
 *                 "User not found with ID: " + userId + " in tenant: " + tenantId));
 *     }
 * }
 * }</pre>
 */
public interface UserRepository extends JpaRepository<UserEntity, UserEntityPK> {
    @Query(value = """
    select user from UserEntity user
    where user.address.addressType = :addressType
    """)
    Optional<List<UserEntity>> findByAddressType(@Param(value = "addressType") AddressEntity.AddressType addressType);
}