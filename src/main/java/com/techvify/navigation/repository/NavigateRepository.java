package com.techvify.navigation.repository;

import com.techvify.navigation.entity.Navigate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NavigateRepository extends JpaRepository<Navigate,Integer> {
    boolean existsById(int id);
    boolean existsByName(String name);

}
