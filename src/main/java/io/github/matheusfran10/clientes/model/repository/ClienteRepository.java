package io.github.matheusfran10.clientes.model.repository;

import io.github.matheusfran10.clientes.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {



}
