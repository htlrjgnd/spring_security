package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;
import web.service.UserServiceImp;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}



	@RequestMapping(value = "hello", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC-SECURITY application");
		messages.add("5.2.0 version by sep'19 ");
		model.addAttribute("messages", messages);
		return "hello";
	}


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }


	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String userPage(Model model) {
		model.addAttribute("user", getUser());
		return "user";
	}
	@RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
	public String changeUser(@ModelAttribute("user") User user) {
		user.setRoles(getUser().getRoles());
		userService.save(user);
		return "user";
	}

	@RequestMapping(value = "admin", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String adminPage(Model model) {
		model.addAttribute("user", getUser().getUsername());
		model.addAttribute("users", userService.getAll());
		return "admin";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("list", new ArrayList<Long>());
		model.addAttribute("allRoles", roleService.getAll());
		return "add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String add(@ModelAttribute("user")  User user, @RequestParam("list") List<Long> list) {
		user.setRoles(addRolesUser(list));
		userService.addUser(user);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String delete(@PathVariable Long id) {
		if (id != 1) {
			userService.deleteById(id);
		}
		return "redirect:/admin";
	}
	@RequestMapping(value = "/{id}/change", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String change(Model model, @PathVariable("id") Long id) {
		model.addAttribute("user", userService.findById(id));
		model.addAttribute("allRoles", roleService.getAll());
		model.addAttribute("list", new ArrayList<Long>());
		return "change";
	}

	@RequestMapping(value = "/change/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String update(@ModelAttribute("user") User user, @RequestParam("list") List<Long> list) {
		user.setRoles(addRolesUser(list));
		userService.addUser(user);
		return "redirect:/admin";
	}

	private Set<Role> addRolesUser(List<Long> list){
		Set<Role> set = new HashSet<>();
		for (Long i : list) {
			set.add(roleService.getById(i));
		}
		return set;
	}

	private User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		return user;
	}

}