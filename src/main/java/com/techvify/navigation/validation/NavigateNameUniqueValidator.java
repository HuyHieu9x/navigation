package com.techvify.navigation.validation;

import com.techvify.navigation.service.impl.NavigateService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NavigateNameUniqueValidator implements ConstraintValidator<NavigateNameUnique,String> {
    @Autowired
    private NavigateService navigateService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return !navigateService.existsByName(name);
    }
}
