package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.example.demo.dao.AppUserDAO;
import com.example.demo.dao.CountryDAO;
import com.example.demo.entity.AppUser;
import com.example.demo.form.AppUserForm;
import com.example.demo.model.Country;
import com.example.demo.utils.WebUtils;
import com.example.demo.validator.AppUserValidator;

@Controller
public class MainController {

	@Autowired
	private AppUserDAO appUserDAO;

	@Autowired
	private CountryDAO countryDAO;

	@Autowired
	private AppUserValidator appUserValidator;

	// Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		// Form target
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		if (target.getClass() == AppUserForm.class) {
			dataBinder.setValidator(appUserValidator);
		}
		// ...
	}

	@RequestMapping(value = { "/login1" })
	public String staticResource1(Model model) {
		return "login1";
	}
	
	@RequestMapping(value = { "/login2" })
	public String staticResource2(Model model) {
		return "login2";
	}

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "Welcome");
		model.addAttribute("message", "This is welcome page!");
		return "welcomePage";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();

		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);

		return "adminPage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {

		return "loginPage";
	}

	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {

		// After user login successfully.
		String userName = principal.getName();

		System.out.println("User Name: " + userName);

		User loginedUser = (User) ((Authentication) principal).getPrincipal();

		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);

		return "userInfoPage";
	}
	
	@RequestMapping("/members")
	   public String viewMembers(Model model) {
	 
	      List<AppUser> list = appUserDAO.getAppUsers();
	 
	      model.addAttribute("members", list);
	 
	      return "membersPage";
	   }
	 
	   @RequestMapping("/registerSuccessful")
	   public String viewRegisterSuccessful(Model model) {
	 
	      return "registerSuccessfulPage";
	   }
	 
	   // Show Register page.
	   @RequestMapping(value = "/register", method = RequestMethod.GET)
	   public String viewRegister(Model model) {
	 
	      AppUserForm form = new AppUserForm();
	      List<Country> countries = countryDAO.getCountries();
	 
	      model.addAttribute("appUserForm", form);
	      model.addAttribute("countries", countries);
	 
	      return "registerPage";
	   }
	 
	   // This method is called to save the registration information.
	   // @Validated: To ensure that this Form
	   // has been Validated before this method is invoked.
	   @RequestMapping(value = "/register", method = RequestMethod.POST)
	   public String saveRegister(Model model, //
	         @ModelAttribute("appUserForm") @Validated AppUserForm appUserForm, //
	         BindingResult result, //
	         final RedirectAttributes redirectAttributes) {
	 
	      // Validate result
	      if (result.hasErrors()) {
	         List<Country> countries = countryDAO.getCountries();
	         model.addAttribute("countries", countries);
	         return "registerPage";
	      }
	      AppUser newUser= null;
	      try {
	         newUser = appUserDAO.createAppUser(appUserForm);
	      }
	      // Other error!!
	      catch (Exception e) {
	         List<Country> countries = countryDAO.getCountries();
	         model.addAttribute("countries", countries);
	         model.addAttribute("errorMessage", "Error: " + e.getMessage());
	         return "registerPage";
	      }
	 
	      redirectAttributes.addFlashAttribute("flashUser", newUser);
	       
	      return "redirect:/registerSuccessful";
	   }

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();

			String userInfo = WebUtils.toString(loginedUser);

			model.addAttribute("userInfo", userInfo);

			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}

		return "403Page";
	}

}
