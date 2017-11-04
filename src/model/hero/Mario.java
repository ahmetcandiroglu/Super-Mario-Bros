package model.hero;

import model.GameObject;

import java.util.HashSet;
import java.util.Set;

public class Mario extends GameObject{

    private Set<MarioForm> forms;

    public Mario(){
        forms = new HashSet<>();
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
