package com.capgemini.capa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.capgemini.capa.domain.DocumentFile;

import com.capgemini.capa.repository.DocumentFileRepository;
import com.capgemini.capa.web.rest.util.HeaderUtil;
import com.capgemini.capa.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DocumentFile.
 */
@RestController
@RequestMapping("/api")
public class DocumentFileResource {

    private final Logger log = LoggerFactory.getLogger(DocumentFileResource.class);

    private static final String ENTITY_NAME = "documentFile";

    private final DocumentFileRepository documentFileRepository;

    public DocumentFileResource(DocumentFileRepository documentFileRepository) {
        this.documentFileRepository = documentFileRepository;
    }

    /**
     * POST  /document-files : Create a new documentFile.
     *
     * @param documentFile the documentFile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentFile, or with status 400 (Bad Request) if the documentFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/document-files")
    @Timed
    public ResponseEntity<DocumentFile> createDocumentFile(@Valid @RequestBody DocumentFile documentFile) throws URISyntaxException {
        log.debug("REST request to save DocumentFile : {}", documentFile);
        if (documentFile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new documentFile cannot already have an ID")).body(null);
        }
        DocumentFile result = documentFileRepository.save(documentFile);
        return ResponseEntity.created(new URI("/api/document-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /document-files : Updates an existing documentFile.
     *
     * @param documentFile the documentFile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentFile,
     * or with status 400 (Bad Request) if the documentFile is not valid,
     * or with status 500 (Internal Server Error) if the documentFile couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/document-files")
    @Timed
    public ResponseEntity<DocumentFile> updateDocumentFile(@Valid @RequestBody DocumentFile documentFile) throws URISyntaxException {
        log.debug("REST request to update DocumentFile : {}", documentFile);
        if (documentFile.getId() == null) {
            return createDocumentFile(documentFile);
        }
        DocumentFile result = documentFileRepository.save(documentFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentFile.getId()))
            .body(result);
    }

    /**
     * GET  /document-files : get all the documentFiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of documentFiles in body
     */
    @GetMapping("/document-files")
    @Timed
    public ResponseEntity<List<DocumentFile>> getAllDocumentFiles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DocumentFiles");
        Page<DocumentFile> page = documentFileRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/document-files");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /document-files/:id : get the "id" documentFile.
     *
     * @param id the id of the documentFile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentFile, or with status 404 (Not Found)
     */
    @GetMapping("/document-files/{id}")
    @Timed
    public ResponseEntity<DocumentFile> getDocumentFile(@PathVariable String id) {
        log.debug("REST request to get DocumentFile : {}", id);
        DocumentFile documentFile = documentFileRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(documentFile));
    }

    /**
     * DELETE  /document-files/:id : delete the "id" documentFile.
     *
     * @param id the id of the documentFile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/document-files/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocumentFile(@PathVariable String id) {
        log.debug("REST request to delete DocumentFile : {}", id);
        documentFileRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
