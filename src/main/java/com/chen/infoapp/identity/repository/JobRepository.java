package com.chen.infoapp.identity.repository;

import com.chen.infoapp.identity.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface JobRepository extends JpaRepository<Job,String> {

    @Query("select new Map(j.code as code,j.name as name) from Job j")
    public List<Map<String,Object>> findJobs() throws Exception;
}
