package com.techvify.navigation.service;

import com.techvify.navigation.entity.Navigate;
import com.techvify.navigation.payLoad.request.NavigateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface INavigateService {
    ResponseEntity<?> create(NavigateRequest navigateRequest,boolean isDeleted);
    ResponseEntity<List<Navigate>> read(boolean isDeleted);
    ResponseEntity<Navigate> update(int id,NavigateRequest navigateRequest);
    ResponseEntity<?> delete(int id);
    boolean existsById(int id);
    boolean existsByName(String name);
    boolean check(boolean isDeleted);
    List<Navigate> getListNotDeleted(boolean isDeleted);
}
