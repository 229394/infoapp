package com.chen.infoapp.identity.repository;

import com.chen.infoapp.identity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role,Long>,JpaSpecificationExecutor<Role> {

}
