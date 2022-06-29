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

    @Override
    public ResponseEntity<?> create(NavigateRequest navigateRequest,boolean isDeleted) {
        try {
            Navigate navigate = modelMapper.map(navigateRequest, Navigate.class);
            List<Navigate> navigateList = getListNotDeleted(isDeleted);

            if (navigateList.size() < 5){
                return new ResponseEntity<>(navigateRepository.save(navigate), HttpStatus.OK);
            }else {
                ResponseMessage responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST,"Delete 1 Obj nav if want create");
                return ResponseEntity.badRequest().body(responseMessage);
            }

        } catch (Exception e) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(e.getMessage(), "CREATE FAIL");
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<Navigate>> read(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedNavigateFilter");
        filter.setParameter("isDeleted", isDeleted);

        List<Navigate> navigateList = navigateRepository.findAll();

        session.disableFilter("deletedNavigateFilter");
        return new ResponseEntity<>(navigateList, HttpStatus.OK);
    }

    @Override
    public boolean check(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedNavigateFilter");
        filter.setParameter("isDeleted", isDeleted);

        List<Navigate> navigateList = navigateRepository.findAll();

        session.disableFilter("deletedNavigateFilter");
        if (navigateList.size() < 5){
            return true;
        }

        return false;
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
    public List<Navigate> getListNotDeleted(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedNavigateFilter");
        filter.setParameter("isDeleted", isDeleted);

        List<Navigate> navigateList = navigateRepository.findAll();

        session.disableFilter("deletedNavigateFilter");

        return  navigateList;
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
