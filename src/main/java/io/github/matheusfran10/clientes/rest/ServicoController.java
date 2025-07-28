package io.github.matheusfran10.clientes.rest;

import io.github.matheusfran10.clientes.model.entity.Cliente;
import io.github.matheusfran10.clientes.model.entity.Servico;
import io.github.matheusfran10.clientes.model.repository.ClienteRepository;
import io.github.matheusfran10.clientes.model.repository.ServicoRepository;
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
@RequestMapping("api/servicos")
@Api(tags = "Serviços")
public class ServicoController {

    private final ServicoRepository repository;
    private final ClienteRepository repositoryCliente;


    public ServicoController(ServicoRepository repository, ClienteRepository repositoryCliente) {
        this.repository = repository;
        this.repositoryCliente = repositoryCliente;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Criar serviço")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Serviço cadastrado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao cadastrar serviço")
    })
    public ResponseEntity<Servico> salvar(@RequestBody @Valid Servico servico) {
        // ✅ Aqui você pode validar se o cliente existe antes de salvar
        if (servico.getCliente() == null || servico.getCliente().getId() == null) {
            return ResponseEntity.badRequest().build(); // Cliente não informado
        }

        // Poderia validar se o cliente existe no banco:
        if (!repositoryCliente.existsById(servico.getCliente().getId())) {
            return ResponseEntity.badRequest().build(); // Cliente não existe
        }

        Servico salvo = repository.save(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }


    @GetMapping
    @ApiOperation(value = "Listar todos os serviços")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Serviços encontrado"),
            @ApiResponse(code = 404, message = "Serviços não encontrados")
    })
    public List<Servico> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar serviços por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Serviço encontrado"),
            @ApiResponse(code = 404, message = "Serviço não encontrado")
    })
    public ResponseEntity<Servico> buscarPorId(@PathVariable Integer id) {
        Optional<Servico> servico = repository.findById(id);
        return servico.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar serviço")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Serviço atualizado"),
            @ApiResponse(code = 404, message = "Serviço não encontrado")
    })
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Servico servicoAtualizado) {
        return repository.findById(id).map(servico -> {
            servico.setDescricao(servicoAtualizado.getDescricao());
            servico.setValor(servicoAtualizado.getValor());
            servico.setCliente(servicoAtualizado.getCliente());
            repository.save(servico);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Excluir serviço")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Serviço excluído"),
            @ApiResponse(code = 404, message = "Serviço não encontrado")
    })
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        return repository.findById(id).map(servico -> {
            repository.delete(servico);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
