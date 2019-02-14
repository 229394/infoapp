package com.chen.infoapp.identity.repository;

import com.chen.infoapp.identity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {

    @Query("select u.userId from User u where u.userId not in (select u.userId from User u inner join u.roles r where r.id=?1)")
    List<String> getRolesUsers(Long id);

    @Query("select u.userId from User u inner join u.roles r where r.id=?1")
    List<String> findRoleUsers(Long id);

}
