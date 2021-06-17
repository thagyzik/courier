package br.com.courier.controller;

import br.com.courier.dto.NewOrderDto;
import br.com.courier.model.Order;
import br.com.courier.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/")
public class CourierController {

	@Autowired
	private OrderService orderService;

	@GetMapping()
	public ModelAndView index() {

		ModelAndView mv = new ModelAndView("index");
		mv.addObject("orderIndex", new NewOrderDto());

		return mv;

	}

	@GetMapping("/login")
	public ModelAndView login() {

		ModelAndView mv = new ModelAndView("login");

		return mv;

	}

	@GetMapping(value = "/orders")
	public ModelAndView getAll(Principal principal) {
		ModelAndView mv = new ModelAndView("allOrders");

		List<Order> orderList = orderService.findAll();
		Collections.reverse(orderList);
		mv.addObject("orders", orderList);
		mv.addObject("loggedUser", principal);
		return mv;
	}

	@PostMapping
	@Transactional
	public ModelAndView saveNewOrder(@Valid @ModelAttribute("order") NewOrderDto newOrderDto, Errors errors, Principal principal, RedirectAttributes attr) {

		if (errors.hasErrors()) {
			ModelAndView mv = new ModelAndView("form");
			mv.addObject("newOrder", newOrderDto);
			mv.addObject("user", principal.getName());

			StringBuilder errorsMessage = new StringBuilder();

			for (ObjectError error : errors.getAllErrors()){

				String newDefaultMessage = error.getDefaultMessage();

				String[] code = error.getCodes();

				if (code != null && newDefaultMessage != null && !newDefaultMessage.contains("mobile")) {

					if (code[0].contains("senderDetails")) {

						newDefaultMessage = newDefaultMessage.replace("Invalid", "Invalid Sender ");

					} else if (code[0].contains("receiverDetails")) {

						newDefaultMessage = newDefaultMessage.replace("Invalid", "Invalid Receiver ");

					}

				}

				errorsMessage.append("  -  ").append(newDefaultMessage);

			}

			attr.addFlashAttribute("message", errorsMessage.toString());

			return new ModelAndView("redirect:/addorder");

		}

		Order order = newOrderDto.toOrder();
		order.setDelivered(LocalDate.now());

		orderService.saveNewOrder(order);

		return new ModelAndView("redirect:/orders");
	}

	@GetMapping("/orderdetails")
	public ModelAndView showOrderDetails(@Valid @ModelAttribute("order") NewOrderDto newOrderDto, RedirectAttributes attr) {

		String redirect = "redirect:/";

		if (newOrderDto.getIdOrder().equals("") || !newOrderDto.getIdOrder().matches("[0-9]+")){

			attr.addFlashAttribute("message", "Invalid Order Number");

			return new ModelAndView(redirect);

		} else {

			List<Order> order = orderService.findOrder(Long.valueOf(newOrderDto.getIdOrder()));

			if (order != null && !order.isEmpty()) {

				for (Order newOrder : order) {

					if (newOrder != null) {

						ModelAndView mv = new ModelAndView("show");
						mv.addObject("orders", orderService.findOrder(Long.valueOf(newOrderDto.getIdOrder())));
						return mv;

					} else {

						attr.addFlashAttribute("message", "Invalid Order Number");

						return new ModelAndView(redirect);

					}

				}

			}

			return new ModelAndView("redirect:/");

		}

	}

	@GetMapping("/addorder")
	public ModelAndView addOrder(Principal principal) {
		ModelAndView mv = new ModelAndView("form");
		mv.addObject("user", principal.getName());
		mv.addObject("newOrder", new NewOrderDto());
		return mv;
	}

	@GetMapping("/updateorderstatus/{id}")
	public ModelAndView updateOrder(@PathVariable Long id, Principal principal) {

		List<Order> orderList = orderService.findOrder(id);
		Order order = null;
		NewOrderDto newOrderDto = new NewOrderDto();

		if(orderList != null && !orderList.isEmpty()) {

			for (Order myOrder : orderList) {

				order = myOrder;

			}

			newOrderDto = new NewOrderDto(order);

		}

		ModelAndView mv = new ModelAndView("update");
		mv.addObject("user", principal.getName());
		mv.addObject("orderUpdate", newOrderDto);

		return mv;

	}

	@PostMapping("/update")
	@Transactional
	public ModelAndView updateConfig(@Valid @ModelAttribute("order") NewOrderDto newOrderDto, Errors errors, Principal principal) {
		if (errors.hasErrors()) {
			ModelAndView mv = new ModelAndView("update");
			mv.addObject("order", newOrderDto);
			mv.addObject("orderUpdate", principal.getName());
			return mv;
		}

		Order order = newOrderDto.toOrder();

		orderService.updateOrder(order);

		return new ModelAndView("redirect:/orders");
	}

}
