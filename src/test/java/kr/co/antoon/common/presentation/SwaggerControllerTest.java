package kr.co.antoon.common.presentation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(useDefaultFilters = false)
@AutoConfigureMockMvc(addFilters = false)
@Import(SwaggerController.class)
@ExtendWith(MockitoExtension.class)
class SwaggerControllerTest {
    @Autowired
    private MockMvc mockMvc;

/*    @Test
    public void 스웨거_정상작동_테스트() throws Exception {
        mockMvc.perform(get("/swagger"))
                .andExpect(status().isFound())
                .andExpect(content().string(""))
                .andDo(print());
    }*/
}