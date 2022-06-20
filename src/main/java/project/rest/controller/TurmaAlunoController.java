package project.rest.controller;

import org.springframework.web.bind.annotation.*;
import project.domain.entity.Aluno;
import project.rest.dto.TurmaAlunoDTO;
import project.service.turmaAlunoService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/turmaaluno")
public class TurmaAlunoController {

    private turmaAlunoService service;

    public TurmaAlunoController(turmaAlunoService service){this.service = service;}

    @PostMapping
    public Aluno saveTurmasByAlunos(@RequestBody @Valid TurmaAlunoDTO turmaAluno){
        return service.salvarTurmaAluno(turmaAluno);
    }

    @DeleteMapping
    public Aluno deleteTurmasByAlunos(@RequestBody TurmaAlunoDTO turmaAluno){
        return service.deletarTurmaAluno(turmaAluno);
    }

    @GetMapping(value = "{id}")
    public List<Aluno> listAlunosByTurmas(@PathVariable("id") Integer id){
        return service.listarAlunosPorTurma(id);
    }
}
