package com.microservicios.app.respuesta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//autoconfiguration para jpa de alumnos y examenes, estas entitys usan jpa
//Por eso tenemos que excluirlo
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication

//Quitamos el entity, ya no son entity, no se registran 
//@EntityScan({"com.microservicios.app.respuesta.models.entity",
//			"com.microservicios.common.examenes.models.entity"
//})
//Comentamos porque ya no nos hace falta, es transient (un atributo nomas)
//			"com.microservicios.commons.alumnos.models.entity",

public class MicroserviciosRespuestaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosRespuestaApplication.class, args);
	}

}
