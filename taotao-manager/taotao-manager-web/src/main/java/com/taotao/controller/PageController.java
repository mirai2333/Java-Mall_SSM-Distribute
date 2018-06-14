package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	@RequestMapping("/")
	private String getIndexPage() {
		return "index";
	}
	
	@RequestMapping("/{page}")
	private String getPages(@PathVariable String page) {
		return page;
	}
}
