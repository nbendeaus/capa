package com.capgemini.capa.service;

import com.capgemini.capa.PstServiceApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PstServiceApp.class)
@Transactional
public class PSTServiceTest {

    @Autowired
    private PSTService pstServices;

    @Test
    public void testFindCalendar(){
        pstServices.getAppointment();
    }





}
