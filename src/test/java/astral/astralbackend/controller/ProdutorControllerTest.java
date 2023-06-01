package astral.astralbackend.controller;

import astral.astralbackend.dtos.produtor.AtualizaProdutorDTO;
import astral.astralbackend.dtos.produtor.CadastroProdutorDTO;
import astral.astralbackend.dtos.produtor.DetalhamentoProdutorDTO;
import astral.astralbackend.entity.Produtor;
import astral.astralbackend.repository.ProdutorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContexts;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProdutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CadastroProdutorDTO> dadosCadastroProdutorJSON;

    @Autowired
    private JacksonTester<DetalhamentoProdutorDTO> dadosDetalhamentoProdutorJSON;

    @MockBean
    private ProdutorRepository produtorRepository;

    @Test
    @DisplayName("Devolver código 201 quando as informações forem válidas")
    @WithMockUser(roles = "ADMIN")
    void cadastrar() throws Exception{
        var response = mvc.perform(post("/produtor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroProdutorJSON.write(new CadastroProdutorDTO("Juninho Capixa",
                                "27996136533", "junin@gmai.com", "12345")).getJson())
                )
                .andReturn()
                .getResponse();

        var jsonEsperado = dadosDetalhamentoProdutorJSON.write(
                new DetalhamentoProdutorDTO(null, "Juninho Capixa",
                        "27996136533", "junin@gmai.com", true)
        ).getJson();

        var responseContent = response.getContentAsString();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(responseContent).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Devolver código 200 quando os parâmetros forem válidos")
    @WithMockUser
    void listarProdutorAtivoEDisponivel() throws Exception{
        var response =  mvc.perform(get("/produtor")
                        .param("disponivel", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

}