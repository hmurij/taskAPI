package com.cognizant.routes;

import java.time.LocalTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cognizant.service.TaskService;

//@Component
public class MyFirstTimerRouter extends RouteBuilder {

	@Autowired
	private GetCurrentTimeBean currentTimeBean;

	@Autowired
	private SimpleProcessingComponenet processingComponenet;

	@SuppressWarnings("unused")
	@Autowired
	private TaskService taskService;

	@Override
	public void configure() throws Exception {
		from("timer:first-timer").log("${body}").transform().constant("My constant message").log("${body}")
//				.transform().constant("Locle date/time: " + LocalTime.now())
//				.bean("currentTimeBean")
				.bean(currentTimeBean, "getCurrentTime").log("${body}")
//				.bean(taskService, "getAllTasks")
				.bean(processingComponenet, "process").process(new SimpleLoggingProcessor()).to("log:first-timer");
	}

}

@Component
class GetCurrentTimeBean {
	public String getCurrentTime() {
		return "Local time: " + LocalTime.now();
	}

}

@Component
class SimpleProcessingComponenet {

	private Logger logger = LoggerFactory.getLogger(SimpleProcessingComponenet.class);

	public void process(String message) {
		logger.info("SimpleProccesingComponet {}", message);
	}

}

class SimpleLoggingProcessor implements Processor {

	private Logger logger = LoggerFactory.getLogger(SimpleProcessingComponenet.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());

	}

}
