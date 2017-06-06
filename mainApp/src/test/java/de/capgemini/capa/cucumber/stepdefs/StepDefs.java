package de.capgemini.capa.cucumber.stepdefs;

import de.capgemini.capa.CapaApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CapaApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
