package com.algaworks.algamoney.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algamoney.api.event.CreateResourceEvent;

@Component
public class CreateResourceListener implements ApplicationListener<CreateResourceEvent> {

	@Override
	public void onApplicationEvent(CreateResourceEvent recursoCriadoEvent) {
		HttpServletResponse response = recursoCriadoEvent.getResponse();
		Long code = recursoCriadoEvent.getCode();
		
		adicionarHeaderLocation(response, code);
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Long code) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
				.buildAndExpand(code).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
