package com.cognizant.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

//@Component
public class TestWebRoute extends RouteBuilder {

//	@Autowired
//	private TaskService taskService;

	@Override
	public void configure() throws Exception {
		restConfiguration().component("servlet").bindingMode(RestBindingMode.auto).host("localhost").port(8080);

		rest("/alltasks").get().route().log("${body}")
//		.to("The body is ${body}!");
				.to("bean:taskService?method=getAllTasks");

	}

}
