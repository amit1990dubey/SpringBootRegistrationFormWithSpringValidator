package com.spring.ibm.cotroller;


import com.spring.ibm.DAO.ApplicationUserDAoImpl;
import com.spring.ibm.DAO.CountryDAOImpl;
import com.spring.ibm.bean.ApplicationUserForm;
import com.spring.ibm.model.ApplicationUser;
import com.spring.ibm.model.Country;
import com.spring.ibm.validator.ApplicationUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ApplicationUserDAoImpl applicationUserDAo;

    @Autowired
    private CountryDAOImpl countryDAO;

    @Autowired
    ApplicationUserValidator applicationUserValidator;

    // Set a Form validator
    protected void initBinder(WebDataBinder dataBinder)
    {
        Object target = dataBinder.getTarget();
        if(target == null)
        {
            return;

        }
        System.out.println("Target=" +target);

        if(target.getClass()== ApplicationUserForm.class)
        {
            dataBinder.setValidator(applicationUserValidator);
        }
    }

    @RequestMapping("/")
    public String viewHome(Model model)
    {
        List<ApplicationUser> list = applicationUserDAo.getApplicationUsers();
        model.addAttribute("members",list);
        return "membersPage";
    }

    @RequestMapping("/registerSuccessful")

    public String viewRegisterSuccessful(Model model)
    {
        return "registerSuccessfulPage";
    }

    // Show Register page.
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String viewRegister(Model model)
    {
        ApplicationUserForm form = new ApplicationUserForm();
        List<Country> countries = countryDAO.getCountries();

        model.addAttribute("applicationUserForm",form);
        model.addAttribute("countries",countries);

        return "registerPage";
    }


    // This method is called to save the registration information.
    // @Validated: To ensure that this Form
    // has been Validated before this method is invoked.
    @RequestMapping(value = "/register", method = RequestMethod.POST)


    public String saveRegister(
            Model model,
            @ModelAttribute("applicationUserForm")
             @Validated ApplicationUserForm applicationUserForm,
            BindingResult result, final RedirectAttributes redirectAttributes) {
        if(result.hasErrors())
        {
            List<Country> countries = countryDAO.getCountries();
            model.addAttribute("countries",countries);
            return "registerPage";
        }

        ApplicationUser neUser = null;
            try {
                neUser =applicationUserDAo.createApplicationUser(applicationUserForm);
            }

            catch (Exception e)
            {
                List<Country> countries = countryDAO.getCountries();
                model.addAttribute("countries",countries);
                model.addAttribute("errorMessage","Error:" +e.getMessage());
                return "registerPage";

            }

        redirectAttributes.addFlashAttribute("flashUser", neUser);

        return "redirect:/registerSuccessful";
    }



}
