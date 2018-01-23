package com.example.dipansh.the_boring_blog_android;

/**
 * Created by dipansh on 19/1/18.
 */

public class Post {

    private String status;
    private String title;
    private String content;
    private String slug;
    private String category;
    private String seoTitle;
    private String seoDescription;
    private String author;
    private String publishedDateTime;
    private String createdDateTime;
    private String musicLink;

    public Post() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public Post(String status, String title, String content, String slug, String category, String seoTitle, String seoDescription, String author, String publishedDateTime, String createdDateTime, String musicLink) {
        this.status = status;
        this.title = title;
        this.content = content;
        this.slug = slug;
        this.category = category;
        this.seoTitle = seoTitle;
        this.seoDescription = seoDescription;
        this.author = author;
        this.publishedDateTime = publishedDateTime;
        this.createdDateTime = createdDateTime;
        this.musicLink = musicLink;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedDateTime() {
        return publishedDateTime;
    }

    public void setPublishedDateTime(String publishedDateTime) {
        this.publishedDateTime = publishedDateTime;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getMusicLink() {
        return musicLink;
    }

    public void setMusicLink(String musicLink) {
        this.musicLink = musicLink;
    }
}
