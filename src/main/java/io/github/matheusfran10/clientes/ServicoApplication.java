package io.github.matheusfran10.clientes;

import io.github.matheusfran10.clientes.model.entity.Cliente;
import io.github.matheusfran10.clientes.model.entity.Servico;
import io.github.matheusfran10.clientes.model.repository.ClienteRepository;
import io.github.matheusfran10.clientes.model.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

public class ServicoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicoApplication.class, args);
    }
}
