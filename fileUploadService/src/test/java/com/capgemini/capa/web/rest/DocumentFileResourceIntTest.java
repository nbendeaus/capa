package com.capgemini.capa.web.rest;

import com.capgemini.capa.FileUploadServiceApp;

import com.capgemini.capa.domain.DocumentFile;
import com.capgemini.capa.repository.DocumentFileRepository;
import com.capgemini.capa.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DocumentFileResource REST controller.
 *
 * @see DocumentFileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileUploadServiceApp.class)
public class DocumentFileResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DocumentFileRepository documentFileRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDocumentFileMockMvc;

    private DocumentFile documentFile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DocumentFileResource documentFileResource = new DocumentFileResource(documentFileRepository);
        this.restDocumentFileMockMvc = MockMvcBuilders.standaloneSetup(documentFileResource)
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
    public static DocumentFile createEntity() {
        DocumentFile documentFile = new DocumentFile()
            .title(DEFAULT_TITLE)
            .author(DEFAULT_AUTHOR)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return documentFile;
    }

    @Before
    public void initTest() {
        documentFileRepository.deleteAll();
        documentFile = createEntity();
    }

    @Test
    public void createDocumentFile() throws Exception {
        int databaseSizeBeforeCreate = documentFileRepository.findAll().size();

        // Create the DocumentFile
        restDocumentFileMockMvc.perform(post("/api/document-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentFile)))
            .andExpect(status().isCreated());

        // Validate the DocumentFile in the database
        List<DocumentFile> documentFileList = documentFileRepository.findAll();
        assertThat(documentFileList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentFile testDocumentFile = documentFileList.get(documentFileList.size() - 1);
        assertThat(testDocumentFile.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDocumentFile.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testDocumentFile.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testDocumentFile.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testDocumentFile.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createDocumentFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentFileRepository.findAll().size();

        // Create the DocumentFile with an existing ID
        documentFile.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentFileMockMvc.perform(post("/api/document-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentFile)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DocumentFile> documentFileList = documentFileRepository.findAll();
        assertThat(documentFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentFileRepository.findAll().size();
        // set the field null
        documentFile.setTitle(null);

        // Create the DocumentFile, which fails.

        restDocumentFileMockMvc.perform(post("/api/document-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentFile)))
            .andExpect(status().isBadRequest());

        List<DocumentFile> documentFileList = documentFileRepository.findAll();
        assertThat(documentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAuthorIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentFileRepository.findAll().size();
        // set the field null
        documentFile.setAuthor(null);

        // Create the DocumentFile, which fails.

        restDocumentFileMockMvc.perform(post("/api/document-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentFile)))
            .andExpect(status().isBadRequest());

        List<DocumentFile> documentFileList = documentFileRepository.findAll();
        assertThat(documentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentFileRepository.findAll().size();
        // set the field null
        documentFile.setFile(null);

        // Create the DocumentFile, which fails.

        restDocumentFileMockMvc.perform(post("/api/document-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentFile)))
            .andExpect(status().isBadRequest());

        List<DocumentFile> documentFileList = documentFileRepository.findAll();
        assertThat(documentFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDocumentFiles() throws Exception {
        // Initialize the database
        documentFileRepository.save(documentFile);

        // Get all the documentFileList
        restDocumentFileMockMvc.perform(get("/api/document-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentFile.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    public void getDocumentFile() throws Exception {
        // Initialize the database
        documentFileRepository.save(documentFile);

        // Get the documentFile
        restDocumentFileMockMvc.perform(get("/api/document-files/{id}", documentFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentFile.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingDocumentFile() throws Exception {
        // Get the documentFile
        restDocumentFileMockMvc.perform(get("/api/document-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDocumentFile() throws Exception {
        // Initialize the database
        documentFileRepository.save(documentFile);
        int databaseSizeBeforeUpdate = documentFileRepository.findAll().size();

        // Update the documentFile
        DocumentFile updatedDocumentFile = documentFileRepository.findOne(documentFile.getId());
        updatedDocumentFile
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);

        restDocumentFileMockMvc.perform(put("/api/document-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentFile)))
            .andExpect(status().isOk());

        // Validate the DocumentFile in the database
        List<DocumentFile> documentFileList = documentFileRepository.findAll();
        assertThat(documentFileList).hasSize(databaseSizeBeforeUpdate);
        DocumentFile testDocumentFile = documentFileList.get(documentFileList.size() - 1);
        assertThat(testDocumentFile.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDocumentFile.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testDocumentFile.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testDocumentFile.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testDocumentFile.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingDocumentFile() throws Exception {
        int databaseSizeBeforeUpdate = documentFileRepository.findAll().size();

        // Create the DocumentFile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDocumentFileMockMvc.perform(put("/api/document-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentFile)))
            .andExpect(status().isCreated());

        // Validate the DocumentFile in the database
        List<DocumentFile> documentFileList = documentFileRepository.findAll();
        assertThat(documentFileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDocumentFile() throws Exception {
        // Initialize the database
        documentFileRepository.save(documentFile);
        int databaseSizeBeforeDelete = documentFileRepository.findAll().size();

        // Get the documentFile
        restDocumentFileMockMvc.perform(delete("/api/document-files/{id}", documentFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DocumentFile> documentFileList = documentFileRepository.findAll();
        assertThat(documentFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentFile.class);
        DocumentFile documentFile1 = new DocumentFile();
        documentFile1.setId("id1");
        DocumentFile documentFile2 = new DocumentFile();
        documentFile2.setId(documentFile1.getId());
        assertThat(documentFile1).isEqualTo(documentFile2);
        documentFile2.setId("id2");
        assertThat(documentFile1).isNotEqualTo(documentFile2);
        documentFile1.setId(null);
        assertThat(documentFile1).isNotEqualTo(documentFile2);
    }
}
