package io.github.matheusfran10.clientes.rest;

import io.github.matheusfran10.clientes.model.entity.Cliente;
import io.github.matheusfran10.clientes.model.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@Api(tags = "Clientes")
public class ClienteController {

    private final ClienteRepository repository;

    @Autowired
    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Criar cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente cadastrado"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar cliente")
    })
    public Cliente salvar(@RequestBody @Valid Cliente cliente ){
        return  repository.save(cliente);
    }

    @GetMapping
    @ApiOperation(value = "Listar todos os clientes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Clientes encontrado"),
            @ApiResponse(code = 404, message = "Clientes não encontrados")
    })
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar cliente por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        Optional<Cliente> cliente = repository.findById(id);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente atualizado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Cliente clienteAtualizado) {
        return repository.findById(id).map(cliente -> {
            cliente.setNome(clienteAtualizado.getNome());
            cliente.setCpf(clienteAtualizado.getCpf());
            // atualize outros campos se tiver
            repository.save(cliente);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Excluir cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente excluído"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        return repository.findById(id).map(cliente -> {
            repository.delete(cliente);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
