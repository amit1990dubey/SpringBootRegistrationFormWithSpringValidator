package com.spring.ibm.validator;

import com.spring.ibm.DAO.ApplicationUserDAoImpl;
import com.spring.ibm.bean.ApplicationUserForm;
import com.spring.ibm.model.ApplicationUser;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class ApplicationUserValidator implements Validator {

    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    ApplicationUserDAoImpl applicationUserDAo;


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == ApplicationUserForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ApplicationUserForm applicationUserForm = (ApplicationUserForm)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName","NotEmpty.ApplicationUserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"firstName","NotEmpty.ApplicationUserForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"lastName","NotEmpty.ApplicationUserForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"enabled","NotEmpty.ApplicationUserForm.enabled");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"gender","NotEmpty.ApplicationUserForm.gender");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email","NotEmpty.ApplicationUserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","NotEmpty.ApplicationUserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"confirmPassword","NotEmpty.ApplicationUserForm.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"countryCode","NotEmpty.ApplicationUserForm.countryCode");


        if(!this.emailValidator.isValid(applicationUserForm.getEmail()))
        {
            errors.rejectValue("email","Pattern.applicationUserForm.email");
        }
        else if (applicationUserForm.getUserId()== null)
        {
            ApplicationUser dbUser =applicationUserDAo.findApplicatonUserByEmail(applicationUserForm.getEmail());

         if(dbUser!= null)
         {
             errors.rejectValue("email","Duplicate.ApplicationUserForm.email");
         }

        }

        if(!errors.hasFieldErrors("userName"))
        {
            ApplicationUser dbUser = applicationUserDAo.findApplicationUserByUserName(applicationUserForm.getUserName());
                    if(dbUser!= null)
                    {
                        errors.rejectValue("userName","Duplicate.ApplicationUserForm.userName");

                    }
        }

        if(!errors.hasErrors())
        {
            if(!applicationUserForm.getConfirmPassword().equals(applicationUserForm.getPassword()))
            {
                errors.rejectValue("confirmPassword","Match.ApplicationUserForm.confirmPassword");
            }


        }
    }
}
