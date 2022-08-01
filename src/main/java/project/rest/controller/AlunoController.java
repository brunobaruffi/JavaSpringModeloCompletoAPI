package project.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import project.domain.entity.Aluno;
import project.domain.repository.Alunos;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/aluno")
public class AlunoController {

    private Alunos alunos;

    public AlunoController(Alunos alunos){this.alunos = alunos;}

  @Operation(
      method = "POST",
      description = "Cadastramento de alunos",
      summary = "Cadastramento de alunos",
      tags = "/api/aluno")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno save(@RequestBody @Valid Aluno aluno){
        System.out.println(aluno.getDataNas());
        return alunos.save(aluno);
    }

  @Operation(
      method = "DELETE",
      description = "Remoção de alunos",
      summary = "Remoção de alunos",
      tags = "/api/aluno")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")Integer id){
        alunos.findById(id)
                .map(aluno -> {alunos.delete(aluno);
                    return Void.TYPE;
                }).orElseThrow(()-> new ResponseStatusException (HttpStatus.NOT_FOUND, "Registro não encontrado"));
    }

  @Operation(
      method = "PUT",
      description = "Atualização de dados dos alunos",
      summary = "Atualização de dados dos alunos",
      tags = "/api/aluno")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Aluno aluno){
        alunos.findById(id)
                .map(alunoExistente -> { // outra forma de fazer o if. igual ao do DELETE.
                    aluno.setId(alunoExistente.getId());
                    alunos.save(aluno);
                    return aluno;
                }).orElseThrow(()->new ResponseStatusException (HttpStatus.NOT_FOUND,"Registro não Encontrado"));
    }

  @Operation(
      method = "GET",
      description = "Lista geral de alunos",
      summary = "Lista geral de alunos",
      tags = "/api/aluno")
    @GetMapping
    public List<Aluno> find(Aluno filtro){
        ExampleMatcher macher = ExampleMatcher.
                matching().
                withIgnoreCase().
                withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );
        Example exemple = Example.of(filtro,macher);
        return alunos.findAll(exemple);
    }

  @Operation(
      method = "GET",
      description = "Lista de aluno especifico",
      summary = "Lista de aluno especifico",
      tags = "/api/aluno")
    @GetMapping(value = "{id}")
    public Aluno getAlunoById(@PathVariable("id") Integer id){

        return alunos.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Aluno Não Encontrado"));
//    return ResponseEntity.status(HttpStatus.CREATED)
//        .body(alunos.findById(id)
//            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Aluno Não Encontrado")));
    }
}
