package project.rest.controller;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno save(@RequestBody @Valid Aluno aluno){
        System.out.println(aluno.getDataNas());
        return alunos.save(aluno);
    }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")Integer id){
        alunos.findById(id)
                .map(aluno -> {alunos.delete(aluno);
                    return Void.TYPE;
                }).orElseThrow(()-> new ResponseStatusException (HttpStatus.NOT_FOUND, "Registro não encontrado"));
    }

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

    @GetMapping(value = "{id}")
    public Aluno getAlunoById(@PathVariable("id") Integer id){
        return alunos.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Aluno Não Encontrado"));
    }
}
