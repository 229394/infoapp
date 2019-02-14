package com.chen.infoapp.identity.repository;

import com.chen.infoapp.identity.model.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface DeptRepository extends JpaRepository<Dept,Long> {

    @Query("select new Map(p.id as code,p.name as name) from Dept p")
    public List<Map<String,Object>> findDepts();
}
