package de.capgemini.capa.cucumber.stepdefs;

import de.capgemini.capa.OutlookServiceApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = OutlookServiceApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
