package com.popcorn.a2z.repository;

import com.popcorn.a2z.entity.UserEntity;
import com.popcorn.a2z.entity.UserEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UserEntityPK> {

}
