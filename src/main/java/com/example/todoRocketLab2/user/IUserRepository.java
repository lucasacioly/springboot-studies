package com.example.todoRocketLab2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);

    UserModel findByEmail(String email);
}
