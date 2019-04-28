package com.room.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@WebAppConfiguration
public class WXControllerTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private WXController wxc;
    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(this.wac).build();
    }
    @Test
    public void decodeUserInfo() throws Exception {
//        mvc.perform(get("/wx/login").param("code", "003cSk2V07luHU1kcBZU0L1q2V0cSk2l")).andExpect(status().is4xxClientError()).andDo(print());
        wxc.decodeUserInfo("003cSk2V07luHU1kcBZU0L1q2V0cSk2l");
    }

    @Test
    public void checkSession() {
    }
}