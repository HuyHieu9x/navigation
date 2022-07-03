package com.techvify.navigation.service;

import com.techvify.navigation.entity.Navigate;
import com.techvify.navigation.payLoad.request.navigate.NavigateCreateRequest;
import com.techvify.navigation.payLoad.request.navigate.NavigateUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface INavigateService {
    ResponseEntity<Navigate> create(NavigateCreateRequest navigateRequest, boolean isDeleted);
    ResponseEntity<?> update(int id, NavigateUpdateRequest navigateUpdateRequest);
    ResponseEntity<?> delete(int id);
    boolean existsById(int id);
    boolean existsByName(String name);
    List<Navigate> getAll(boolean isDeleted);
}
