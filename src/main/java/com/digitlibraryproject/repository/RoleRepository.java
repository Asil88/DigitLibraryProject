package com.digitlibraryproject.repository;

import com.digitlibraryproject.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO roles (id,user_id,role) VALUES (DEFAULT,:userId,'USER')")
    void setUserRole(Integer userId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE roles SET role = 'ADMIN' WHERE user_id = :userId")
    int updateUserToAdmin(Integer userId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE roles SET role = 'USER' WHERE user_id = :userId")
    int updateAdminToUser(Integer userId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE roles SET role = 'SUBSCRIBER' WHERE user_id = :userId")
    int updateUserToSubscriber(Integer userId);
}
