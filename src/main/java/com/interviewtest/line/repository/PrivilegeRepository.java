package com.interviewtest.line.repository;

import com.interviewtest.line.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);
    
    Collection<Privilege> findByDeleted(int isDeleted);

}
