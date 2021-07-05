package com.example.pdfapplication;

public class Model {
    int dislike;
    String filename, fileurl;
    int like,view;

    public Model(int dislike, String filename, String fileurl, int like, int view) {
        this.dislike = dislike;
        this.filename = filename;
        this.fileurl = fileurl;
        this.like = like;
        this.view = view;
    }

    public Model() {
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
