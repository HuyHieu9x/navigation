package com.techvify.navigation.controller;

import com.techvify.navigation.entity.Navigate;
import com.techvify.navigation.payLoad.request.NavigateRequest;
import com.techvify.navigation.service.impl.NavigateService;
import com.techvify.navigation.validation.NavigateIdExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/navigate")
@Validated
public class NavigateController {
    @Autowired
    private NavigateService navigateService;

    @GetMapping
    public ResponseEntity<List<Navigate>> getAll(@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
        return new ResponseEntity<>(navigateService.read(isDeleted), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid NavigateRequest navigateRequest, BindingResult bindingResult, @RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return navigateService.create(navigateRequest, isDeleted);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Navigate> update(@NavigateIdExists @PathVariable("id") int id, @RequestBody @Valid NavigateRequest navigateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return navigateService.update(id, navigateRequest);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@NavigateIdExists @PathVariable("id") int id) {
        return navigateService.delete(id);
    }

}
