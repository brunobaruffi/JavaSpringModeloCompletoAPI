package projetoIndependente.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import project.domain.enums.StatusAlunos;
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

  private String json;

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

  @Test
  void shouldPostSaveAlunoTestSuccess() throws Exception {
    givenPostAlunoSaveTestSuccess();
    whenCallPostAlunoSaveTest();
    thenExpectCreatedStatus();
  }

  @Test
  void shouldPostSaveAlunoTestBadRequest() throws Exception {
    givenPostAlunoSaveTestBadRequest();
    whenCallPostAlunoSaveTest();
    thenExpectBadRequestStatus();
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

  private void givenPostAlunoSaveTestSuccess() throws Exception {
    //resposta padrão de salvamento com sucesso
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = formatter.parse("2021-01-01");

    aluno = Aluno.builder()
        .nome("Teste")
        .cpf("12312312312")
        .status(StatusAlunos.valueOf("ATIVO"))
        .dataNas(date)
        .turmas(null)
        .build();

    when(alunos.save(any())).thenReturn(aluno);

    //criação de uma string JSON com a entrada necessaria no post
    //json = "{\"nome\":\"Teste\",\"cpf\":\"12312312312\",\"dataNas\":\"2022-02-02\",\"status\":\"ATIVO\"}";
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    json = ow.writeValueAsString(aluno);

  }

  private void givenPostAlunoSaveTestBadRequest() throws Exception {
    //resposta padrão de salvamento com sucesso
    aluno = Aluno.builder()
        .cpf("12312312312")
        .status(StatusAlunos.valueOf("ATIVO"))
        .build();
    when(alunos.save(any())).thenReturn(aluno);

    //criação de uma string JSON com a entrada necessaria no post
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    json = ow.writeValueAsString(aluno);
  }


  private void whenCallGetAlunoByIdTest() throws Exception {
    //criar a requisição get na api. Atenção ao import da requisição get ou outras
    response = mockMvc.perform(get("/api/aluno".concat("/").concat(id))
            .accept(MediaType.APPLICATION_JSON))
        .andReturn();
  }

  private void whenCallPostAlunoSaveTest() throws Exception {
    //criar a requisição get na api. Atenção ao import da requisição get ou outras
    response = mockMvc.perform(post("/api/aluno")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andReturn();

  }


  private void thenExpectOkStatus(){
    assertEquals(HttpStatus.OK.value(), response.getResponse().getStatus());
  }

  private void thenExpectCreatedStatus(){
    assertEquals(HttpStatus.CREATED.value(), response.getResponse().getStatus());
  }

  private void thenExpectNotFoundStatus(){
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getResponse().getStatus());
  }

  private void thenExpectBadRequestStatus(){
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getResponse().getStatus());
  }
}
