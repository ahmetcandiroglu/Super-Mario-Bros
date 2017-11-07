package model.hero;

import model.GameObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Mario extends GameObject{

    private Set<MarioForm> forms;

    public Mario(){
        forms = new HashSet<>();
        setLocation( new Point(48*3,540));
        setDimension( new Dimension(48, 96));
        try{
            setStyle( ImageIO.read(new File("./src/media/mario/super-mario.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<MarioForm> getForms() {
        return forms;
    }

    public void setForms(Set<MarioForm> forms) {
        this.forms = forms;
    }

    public void addForm(MarioForm formToAdd){
        forms.add(formToAdd);
    }

    public void removeForm(MarioForm formToRemove){
        forms.remove(formToRemove);
    }

    @Override
    public void draw() {

    }
}
