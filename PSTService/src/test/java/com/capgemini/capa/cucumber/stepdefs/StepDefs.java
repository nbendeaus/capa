package com.capgemini.capa.cucumber.stepdefs;

import com.capgemini.capa.PstServiceApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = PstServiceApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
