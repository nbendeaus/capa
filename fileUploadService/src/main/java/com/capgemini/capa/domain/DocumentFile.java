package com.capgemini.capa.domain;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Document file entity.
 */
@ApiModel(description = "The Document file entity.")
@Document(collection = "document_file")
public class DocumentFile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Size(min = 2)
    @Field("title")
    private String title;

    @NotNull
    @Field("author")
    private String author;

    @NotNull
    @Field("file")
    private byte[] file;

    @Field("file_content_type")
    private String fileContentType;

    @Size(max = 150)
    @Field("description")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public DocumentFile title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public DocumentFile author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public byte[] getFile() {
        return file;
    }

    public DocumentFile file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public DocumentFile fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getDescription() {
        return description;
    }

    public DocumentFile description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DocumentFile documentFile = (DocumentFile) o;
        if (documentFile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentFile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentFile{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", author='" + getAuthor() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + fileContentType + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
