package project.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
        method = "POST",
        description = "Salva alunos nas turmas",
        summary = "Salva alunos nas turmas",
        tags = "/api/turmaaluno")
    @PostMapping
    public Aluno saveTurmasByAlunos(@RequestBody @Valid TurmaAlunoDTO turmaAluno){
        return service.salvarTurmaAluno(turmaAluno);
    }

    @Operation(
        method = "DELETE",
        description = "Deleta alunos nas turmas",
        summary = "Deleta alunos nas turmas",
        tags = "/api/turmaaluno")
    @DeleteMapping
    public Aluno deleteTurmasByAlunos(@RequestBody TurmaAlunoDTO turmaAluno){
        return service.deletarTurmaAluno(turmaAluno);
    }

    @Operation(
        method = "GET",
        description = "Lista alunos de uma turma",
        summary = "Lista alunos de turma",
        tags = "/api/turmaaluno")
    @GetMapping(value = "{id}")
    public List<Aluno> listAlunosByTurmas(@PathVariable("id") Integer id){
        return service.listarAlunosPorTurma(id);
    }
}
