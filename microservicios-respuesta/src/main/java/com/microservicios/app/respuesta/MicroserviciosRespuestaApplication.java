package com.microservicios.app.respuesta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.microservicios.app.respuesta.models.entity",
			"com.microservicios.commons.alumnos.models.entity",
			"com.microservicios.common.examenes.models.entity"
			})
public class MicroserviciosRespuestaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosRespuestaApplication.class, args);
	}

}
