package project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project.domain.entity.Aluno;
import project.domain.entity.Turma;
import project.domain.repository.Alunos;
import project.domain.repository.Turmas;
import project.rest.dto.TurmaAlunoDTO;
import project.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class turmaServiceImpl implements turmaAlunoService {

    private final Alunos alunosRepository;
    private final Turmas turmasRepository;

    @Override
    @Transactional
    public Aluno salvarTurmaAluno(TurmaAlunoDTO turmaAluno) {

        Integer idaluno = turmaAluno.getAluno();
        Aluno aluno = alunosRepository.findById(idaluno)
                .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Codigo de aluno Invalido.")
                );
        List<Integer> itens = turmaAluno.getTurmas();
        if (itens.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Lista de turmas Vazia");
        }
        List<Turma> turmas = aluno.getTurmas();

            itens.stream()
                .map(item -> {
                    turmas.add(turmasRepository.findById(item)
                            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Turma Invalida"))
                    );
                    aluno.setTurmas(turmas);
                    alunosRepository.save(aluno);
                    return aluno;
                }).collect(Collectors.toList());
        return aluno;
    }

    @Override
    @Transactional
    public Aluno deletarTurmaAluno(TurmaAlunoDTO turmaAluno) {
        Integer idaluno = turmaAluno.getAluno();
        Aluno aluno = alunosRepository.findById(idaluno)
                .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Codigo de aluno Invalido.")
                );

        List<Integer> itens = turmaAluno.getTurmas();

        List<Turma> turmasFicar = new ArrayList<Turma>();

        if (itens.isEmpty()) {
            aluno.setTurmas(turmasFicar);
            alunosRepository.save(aluno);
            return aluno;
        }

        aluno.getTurmas().forEach
                (item -> {
                    Integer controle = 0;
                    for (int c=0; c<itens.size();c++){
                        if(item.getId() == itens.get(c)){
                            controle = 1;
                        }
                    }

                    if(controle == 0){
                        turmasFicar.add(item);
                    }
                });
        aluno.setTurmas(turmasFicar);
        alunosRepository.save(aluno);
        return aluno;


    }

    @Override
    public List<Aluno> listarAlunosPorTurma(Integer id) {
       return alunosRepository.findAlunoByTurma(id);
    }
}