/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FilesUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.util.EscapeUtils;

/**
 *
 * @author rodo
 */
@ManagedBean(name = "fileUploadView")
@RequestScoped

public class FileUploadView {

    private UploadedFile file;
    private UploadedFiles files;
    private String dropZoneText = "Drop zone p:inputTextarea demo.";
    private byte[] imageArray;
    private boolean eventHappened = false;

    public FileUploadView() {
    }
    
    public FileUploadView(byte[] imageArray) {
        this.imageArray = imageArray;
    }

    public void upload(FileUploadEvent event) {

        this.setImageArray(event.getFile().getContent());
        this.setFile(event.getFile());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(this.imageArray);
        String fileName = event.getFile().getFileName();
        String yourProjectPath = "/Users/rodo/Documents/Developer/Java/Projects/BIS10_FinalProject"; // This is the path to update!
        String path = yourProjectPath + "/web/resources/recipes/" + fileName;
        
        System.out.println(event.getFile().getFileName());
        System.out.println(path);
        
        try{
            BufferedImage savedImage = ImageIO.read(inputStream);
            ImageIO.write(savedImage, "png", new File(path));
        }catch(IOException e){
            e.printStackTrace();
        }

        if (file != null) {
            FacesMessage message = new FacesMessage("Imagen ", file.getFileName() + " cargada con exito!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        
        
        this.eventHappened = true;

    }

    public void uploadMultiple() {
        if (files != null) {
            for (UploadedFile f : files.getFiles()) {
                FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void handleFileUploadTextarea(FileUploadEvent event) {
        String jsVal = "PF('textarea').jq.val";
        String fileName = EscapeUtils.forJavaScript(event.getFile().getFileName());
        PrimeFaces.current().executeScript(jsVal + "(" + jsVal + "() + '\\n\\n" + fileName + " uploaded.')");
    }

    public void handleFilesUpload(FilesUploadEvent event) {
        for (UploadedFile f : event.getFiles().getFiles()) {
            FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFiles getFiles() {
        return files;
    }

    public void setFiles(UploadedFiles files) {
        this.files = files;
    }

    public String getDropZoneText() {
        return dropZoneText;
    }

    public void setDropZoneText(String dropZoneText) {
        this.dropZoneText = dropZoneText;
    }

    public byte[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(byte[] imageArray) {
        this.imageArray = imageArray;
    }

    public boolean isEventHappened() {
        return eventHappened;
    }

    public void setEventHappened(boolean eventHappened) {
        this.eventHappened = eventHappened;
    }
    
    

}
