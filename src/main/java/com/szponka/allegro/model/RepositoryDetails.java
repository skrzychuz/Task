package com.szponka.allegro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Date;

@JsonDeserialize(builder = RepositoryDetails.Builder.class)
public class RepositoryDetails {

    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    private Date createdAt;

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public int getStars() {
        return stars;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder {
        private String fullName;
        private String description;
        private String cloneUrl;
        private int stars;
        private Date createdAt;

        @JsonProperty("full_name")
        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        @JsonProperty("description")
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        @JsonProperty("clone_url")
        public Builder cloneUrl(String cloneUrl) {
            this.cloneUrl = cloneUrl;
            return this;
        }

        @JsonProperty("stargazers_count")
        public Builder stars(int stars) {
            this.stars = stars;
            return this;
        }

        @JsonProperty("created_at")
        public Builder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public RepositoryDetails build() {
            RepositoryDetails repositoryDetails = new RepositoryDetails();
            repositoryDetails.fullName = this.fullName;
            repositoryDetails.description = this.description;
            repositoryDetails.cloneUrl = this.cloneUrl;
            repositoryDetails.stars = this.stars;
            repositoryDetails.createdAt = this.createdAt;
            return repositoryDetails;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepositoryDetails that = (RepositoryDetails) o;

        if (stars != that.stars) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (cloneUrl != null ? !cloneUrl.equals(that.cloneUrl) : that.cloneUrl != null) return false;
        return createdAt != null ? createdAt.equals(that.createdAt) : that.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (cloneUrl != null ? cloneUrl.hashCode() : 0);
        result = 31 * result + stars;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RepositoryDetails{");
        sb.append("fullName='").append(fullName).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", cloneUrl='").append(cloneUrl).append('\'');
        sb.append(", stars=").append(stars);
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append('}');
        return sb.toString();
    }
}



