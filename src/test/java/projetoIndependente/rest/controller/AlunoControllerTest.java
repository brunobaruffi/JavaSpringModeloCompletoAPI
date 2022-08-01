package projetoIndependente.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import project.domain.entity.Aluno;
import project.domain.repository.Alunos;
import project.rest.controller.AlunoController;


@ExtendWith(MockitoExtension.class)
@WebMvcTest({AlunoController.class})
@ContextConfiguration(classes = {AlunoController.class})
@AutoConfigureMockMvc(addFilters = false)
class AlunoControllerTest {

  @InjectMocks
  private AlunoController alunoController;

  @MockBean
  private Alunos alunos;

  @Autowired
  private MockMvc mockMvc;

  private MvcResult response;

  private Aluno aluno;

  private String id;

  @Test
  void shouldGetAlunoByIdTestSuccess() throws Exception {
    givenGetAlunoByIdTestSuccess();
    givenIdAlunoValid();
    whenCallGetAlunoByIdTest();
    thenExpectOkStatus();
  }

  @Test
  void shouldGetAlunoByIdTestNotFound() throws Exception {
    givenGetAlunoByIdTestNotFound();
    givenIdAlunoValid();
    whenCallGetAlunoByIdTest();
    thenExpectNotFoundStatus();
  }

  // methods
  private void givenIdAlunoValid(){
    //criar objeto que vai funcionar no filtro.
    id = "2";
  }
  private void givenGetAlunoByIdTestSuccess(){
    aluno = Aluno.builder().Id(2).build();
    when(alunos.findById(anyInt())).thenReturn(Optional.ofNullable(aluno));
  }
  private void givenGetAlunoByIdTestNotFound(){
    aluno = null;
    when(alunos.findById(anyInt())).thenReturn(Optional.ofNullable(aluno));
  }

  private void whenCallGetAlunoByIdTest() throws Exception {
    //criar a requisição get na api. Atenção ao import da requisição get ou outras
    response = mockMvc.perform(get("/api/aluno".concat("/").concat(id))
            .accept(MediaType.APPLICATION_JSON))
        .andReturn();
  }

  private void thenExpectOkStatus(){
    assertEquals(HttpStatus.OK.value(), response.getResponse().getStatus());
  }

  private void thenExpectNotFoundStatus(){
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getResponse().getStatus());
  }
}
