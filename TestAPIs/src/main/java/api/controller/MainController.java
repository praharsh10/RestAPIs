package api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.service.MainService;


@RestController
public class MainController {
	
	@Autowired
	MainService service;
	
	@RequestMapping(value = "/getCall", method = RequestMethod.GET)
	@ResponseBody
	public String getCall()
	{
		return this.service.getCall();
	}
	
	@RequestMapping(value = "/anotherCall", method = RequestMethod.GET)
	@ResponseBody
	public String anotherCall()
	{
		return this.service.anotherCall();
	}
	
	
	
	@RequestMapping(value = "/resetHystrix", method = RequestMethod.GET)
	@ResponseBody
	public String resetHystrix()
	{
		return this.service.resetHystrix();
	}
	

}
