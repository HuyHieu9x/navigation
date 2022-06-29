package com.techvify.navigation.validation;

import com.techvify.navigation.service.impl.NavigateService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NavigateIdExistsValidator implements ConstraintValidator<NavigateIdExists,Integer> {
    @Autowired
    private NavigateService navigateService;

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        return navigateService.existsById(id);
    }
}
