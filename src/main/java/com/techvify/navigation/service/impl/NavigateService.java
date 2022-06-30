package com.techvify.navigation.service.impl;

import com.techvify.navigation.comon.ResponseMessage;
import com.techvify.navigation.entity.Navigate;
import com.techvify.navigation.payLoad.request.NavigateRequest;
import com.techvify.navigation.repository.NavigateRepository;
import com.techvify.navigation.service.INavigateService;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<Navigate> create(NavigateRequest navigateRequest, boolean isDeleted) {
        try {
            List<Navigate> navigateList = read(isDeleted);
            Navigate navigate = modelMapper.map(navigateRequest, Navigate.class);
            if (navigateList.size() < SIZE) {
                return new ResponseEntity<>(navigateRepository.save(navigate), HttpStatus.OK);
            } else {
                boolean check = false;
                for (int i = 0; i < navigateList.size(); i++) {
                    if (i == NUM) {
                        navigateList.get(NUM).setDeleted(true);
                        check = true;
                        break;
                    }
                }
                if (check == true) {
                    navigateRepository.save(navigate);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Navigate> update(int id, NavigateRequest navigateRequest) {
        try {
            Navigate navigate = navigateRepository.findById(id).get();
            navigate.setName(navigateRequest.getName());
            navigate.setLink(navigateRequest.getLink());

            return new ResponseEntity<>(navigateRepository.save(navigate), HttpStatus.OK);
        } catch (Exception e) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(e.getMessage(), "UPDATE FAIL");
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        navigateRepository.deleteById(id);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, "Delete successfully");
        return ResponseEntity.ok().body(responseMessage);
    }

    @Override
    public List<Navigate> read(boolean isDeleted) {
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
