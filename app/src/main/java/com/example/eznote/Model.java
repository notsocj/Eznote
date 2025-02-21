package com.example.eznote;

public class Model {
    private String name, number, content;
    private String docId;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Model(String name, String number, String content, String docId) {
        this.name = name;
        this.number = number;
        this.content = content;
        this.docId = docId;
    }
    public String getContent() { return content; }
    public String getDocId() { return docId; }


}
