package de.capgemini.capa.web.rest;

import de.capgemini.capa.CapaApp;

import de.capgemini.capa.domain.Employee;
import de.capgemini.capa.repository.EmployeeRepository;
import de.capgemini.capa.repository.search.EmployeeSearchRepository;
import de.capgemini.capa.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.capgemini.capa.domain.enumeration.Team;
/**
 * Test class for the EmployeeResource REST controller.
 *
 * @see EmployeeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CapaApp.class)
public class EmployeeResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_DAY_RATE = 1L;
    private static final Long UPDATED_DAY_RATE = 2L;

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORE = "AAAAAAAAAA";
    private static final String UPDATED_SHORE = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final Double DEFAULT_PROJECT_AVAILABILITY = 1D;
    private static final Double UPDATED_PROJECT_AVAILABILITY = 2D;

    private static final Double DEFAULT_INTERN_PROJECT_AVAILABILITY = 1D;
    private static final Double UPDATED_INTERN_PROJECT_AVAILABILITY = 2D;

    private static final LocalDate DEFAULT_FORM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FORM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_CAPACITY_PA = 1D;
    private static final Double UPDATED_CAPACITY_PA = 2D;

    private static final Double DEFAULT_CAPACITY_IPA = 1D;
    private static final Double UPDATED_CAPACITY_IPA = 2D;

    private static final Team DEFAULT_TEAM = Team.OSC;
    private static final Team UPDATED_TEAM = Team.NSC;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeSearchRepository employeeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeResource employeeResource = new EmployeeResource(employeeRepository, employeeSearchRepository);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(employeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .dayRate(DEFAULT_DAY_RATE)
            .shortName(DEFAULT_SHORT_NAME)
            .shore(DEFAULT_SHORE)
            .grade(DEFAULT_GRADE)
            .projectAvailability(DEFAULT_PROJECT_AVAILABILITY)
            .internProjectAvailability(DEFAULT_INTERN_PROJECT_AVAILABILITY)
            .formDate(DEFAULT_FORM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .capacityPA(DEFAULT_CAPACITY_PA)
            .capacityIPA(DEFAULT_CAPACITY_IPA)
            .team(DEFAULT_TEAM);
        return employee;
    }

    @Before
    public void initTest() {
        employeeSearchRepository.deleteAll();
        employee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getDayRate()).isEqualTo(DEFAULT_DAY_RATE);
        assertThat(testEmployee.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testEmployee.getShore()).isEqualTo(DEFAULT_SHORE);
        assertThat(testEmployee.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEmployee.getProjectAvailability()).isEqualTo(DEFAULT_PROJECT_AVAILABILITY);
        assertThat(testEmployee.getInternProjectAvailability()).isEqualTo(DEFAULT_INTERN_PROJECT_AVAILABILITY);
        assertThat(testEmployee.getFormDate()).isEqualTo(DEFAULT_FORM_DATE);
        assertThat(testEmployee.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testEmployee.getCapacityPA()).isEqualTo(DEFAULT_CAPACITY_PA);
        assertThat(testEmployee.getCapacityIPA()).isEqualTo(DEFAULT_CAPACITY_IPA);
        assertThat(testEmployee.getTeam()).isEqualTo(DEFAULT_TEAM);

        // Validate the Employee in Elasticsearch
        Employee employeeEs = employeeSearchRepository.findOne(testEmployee.getId());
        assertThat(employeeEs).isEqualToComparingFieldByField(testEmployee);
    }

    @Test
    @Transactional
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        employee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFirstName(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setLastName(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmail(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDayRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setDayRate(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setShore(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setGrade(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].dayRate").value(hasItem(DEFAULT_DAY_RATE.intValue())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shore").value(hasItem(DEFAULT_SHORE.toString())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
            .andExpect(jsonPath("$.[*].projectAvailability").value(hasItem(DEFAULT_PROJECT_AVAILABILITY.doubleValue())))
            .andExpect(jsonPath("$.[*].internProjectAvailability").value(hasItem(DEFAULT_INTERN_PROJECT_AVAILABILITY.doubleValue())))
            .andExpect(jsonPath("$.[*].formDate").value(hasItem(DEFAULT_FORM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].capacityPA").value(hasItem(DEFAULT_CAPACITY_PA.doubleValue())))
            .andExpect(jsonPath("$.[*].capacityIPA").value(hasItem(DEFAULT_CAPACITY_IPA.doubleValue())))
            .andExpect(jsonPath("$.[*].team").value(hasItem(DEFAULT_TEAM.toString())));
    }

    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.dayRate").value(DEFAULT_DAY_RATE.intValue()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.shore").value(DEFAULT_SHORE.toString()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.toString()))
            .andExpect(jsonPath("$.projectAvailability").value(DEFAULT_PROJECT_AVAILABILITY.doubleValue()))
            .andExpect(jsonPath("$.internProjectAvailability").value(DEFAULT_INTERN_PROJECT_AVAILABILITY.doubleValue()))
            .andExpect(jsonPath("$.formDate").value(DEFAULT_FORM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.capacityPA").value(DEFAULT_CAPACITY_PA.doubleValue()))
            .andExpect(jsonPath("$.capacityIPA").value(DEFAULT_CAPACITY_IPA.doubleValue()))
            .andExpect(jsonPath("$.team").value(DEFAULT_TEAM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        employeeSearchRepository.save(employee);
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findOne(employee.getId());
        updatedEmployee
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .dayRate(UPDATED_DAY_RATE)
            .shortName(UPDATED_SHORT_NAME)
            .shore(UPDATED_SHORE)
            .grade(UPDATED_GRADE)
            .projectAvailability(UPDATED_PROJECT_AVAILABILITY)
            .internProjectAvailability(UPDATED_INTERN_PROJECT_AVAILABILITY)
            .formDate(UPDATED_FORM_DATE)
            .toDate(UPDATED_TO_DATE)
            .capacityPA(UPDATED_CAPACITY_PA)
            .capacityIPA(UPDATED_CAPACITY_IPA)
            .team(UPDATED_TEAM);

        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployee)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getDayRate()).isEqualTo(UPDATED_DAY_RATE);
        assertThat(testEmployee.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testEmployee.getShore()).isEqualTo(UPDATED_SHORE);
        assertThat(testEmployee.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmployee.getProjectAvailability()).isEqualTo(UPDATED_PROJECT_AVAILABILITY);
        assertThat(testEmployee.getInternProjectAvailability()).isEqualTo(UPDATED_INTERN_PROJECT_AVAILABILITY);
        assertThat(testEmployee.getFormDate()).isEqualTo(UPDATED_FORM_DATE);
        assertThat(testEmployee.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testEmployee.getCapacityPA()).isEqualTo(UPDATED_CAPACITY_PA);
        assertThat(testEmployee.getCapacityIPA()).isEqualTo(UPDATED_CAPACITY_IPA);
        assertThat(testEmployee.getTeam()).isEqualTo(UPDATED_TEAM);

        // Validate the Employee in Elasticsearch
        Employee employeeEs = employeeSearchRepository.findOne(testEmployee.getId());
        assertThat(employeeEs).isEqualToComparingFieldByField(testEmployee);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Create the Employee

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        employeeSearchRepository.save(employee);
        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Get the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean employeeExistsInEs = employeeSearchRepository.exists(employee.getId());
        assertThat(employeeExistsInEs).isFalse();

        // Validate the database is empty
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        employeeSearchRepository.save(employee);

        // Search the employee
        restEmployeeMockMvc.perform(get("/api/_search/employees?query=id:" + employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].dayRate").value(hasItem(DEFAULT_DAY_RATE.intValue())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shore").value(hasItem(DEFAULT_SHORE.toString())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
            .andExpect(jsonPath("$.[*].projectAvailability").value(hasItem(DEFAULT_PROJECT_AVAILABILITY.doubleValue())))
            .andExpect(jsonPath("$.[*].internProjectAvailability").value(hasItem(DEFAULT_INTERN_PROJECT_AVAILABILITY.doubleValue())))
            .andExpect(jsonPath("$.[*].formDate").value(hasItem(DEFAULT_FORM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].capacityPA").value(hasItem(DEFAULT_CAPACITY_PA.doubleValue())))
            .andExpect(jsonPath("$.[*].capacityIPA").value(hasItem(DEFAULT_CAPACITY_IPA.doubleValue())))
            .andExpect(jsonPath("$.[*].team").value(hasItem(DEFAULT_TEAM.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = new Employee();
        employee1.setId(1L);
        Employee employee2 = new Employee();
        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);
        employee2.setId(2L);
        assertThat(employee1).isNotEqualTo(employee2);
        employee1.setId(null);
        assertThat(employee1).isNotEqualTo(employee2);
    }
}
