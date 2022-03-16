package com.spring.boot.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.boot.reactor.models.User;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String[] name = { "Sc Castellanos", "Jesús Castellanos", "Almircar Castellanos", "1 Castellanos",
				"qwerty Castellanos", "jESUS Castellanos", "Sc Qwerty", "Jesus Castellanos" };

		Flux<User> names = Flux.just(name)
				.map(nombre -> new User(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
				.filter(usuario -> usuario.getName().toLowerCase().equals("sc")).doOnNext(user -> {
					if (user == null) {
						throw new RuntimeException("Name is emty");
					}
					System.out.println(user.getName().concat(" ").concat(user.getLastName()));
				}).map(usuario -> {
					String nombre = usuario.getName();
					usuario.setName(nombre.toLowerCase());
					return usuario;
				});

		names.subscribe(element -> log.info(element.toString()), error -> log.error(error.getMessage()),
				new Runnable() {
					public void run() {
						log.info("Ha finalizado la ejecución del observable con éxito");
					}
				});
	}

}
