package com.techvify.navigation.service.impl;

import com.techvify.navigation.comon.ResponseMessage;
import com.techvify.navigation.entity.Navigate;
import com.techvify.navigation.payLoad.request.navigate.NavigateCreateRequest;
import com.techvify.navigation.payLoad.request.navigate.NavigateUpdateRequest;
import com.techvify.navigation.repository.NavigateRepository;
import com.techvify.navigation.service.INavigateService;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class NavigateService implements INavigateService {
    @Autowired
    private NavigateRepository navigateRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EntityManager entityManager;
    private static final int NUM = 5;
    private static final int SIZE = 10;

    @Override
    public ResponseEntity<Navigate> create(NavigateCreateRequest navigateRequest, boolean isDeleted) {
        try {
            List<Navigate> navigateList = getAll(isDeleted);
            Navigate navigate = modelMapper.map(navigateRequest, Navigate.class);
            if (navigateList.size() < SIZE) {
                return new ResponseEntity<>(navigateRepository.save(navigate), HttpStatus.OK);
            } else {
                navigateList.get(NUM).setDeleted(true);
                return new ResponseEntity<>(navigateRepository.save(navigate), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> update(int id, NavigateUpdateRequest navigateUpdateRequest) {
        try {
            Navigate navigate = navigateRepository.findById(id).get();

            if (navigateUpdateRequest.getName() != null) {
                boolean checkNameExists = navigateRepository.existsByName(navigateUpdateRequest.getName());
                boolean checkName = navigateUpdateRequest.getName().equals(navigate.getName());

                if (checkNameExists && !checkName) {
                    ResponseMessage responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST, "Name is duplicate");
                    return ResponseEntity.badRequest().body(responseMessage);
                }

                navigate.setName(navigateUpdateRequest.getName());
            } else {
                navigate.setName(navigate.getName());
            }

            if (navigateUpdateRequest.getLink() != null) {
                navigate.setLink(navigateUpdateRequest.getLink());
            } else {
                navigate.setLink(navigate.getLink());
            }

            return new ResponseEntity<>(navigateRepository.save(navigate), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        navigateRepository.deleteById(id);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "Delete successfully");
        return ResponseEntity.ok().body(responseMessage);
    }

    @Override
    public List<Navigate> getAll(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedNavigateFilter");
        filter.setParameter("isDeleted", isDeleted);
        List<Navigate> navigateList = navigateRepository.findAll();
        session.disableFilter("deletedNavigateFilter");
        return navigateList;
    }

    @Override
    public boolean existsById(int id) {
        return navigateRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return navigateRepository.existsByName(name);
    }
}
