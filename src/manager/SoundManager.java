package manager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {

    public SoundManager() {}

    private AudioInputStream loadAudio(String url) {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    getClass().getResourceAsStream("/media/audio/" + url + ".wav"));
            return inputStream;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    private Clip getClip(AudioInputStream stream) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void playBackground() {
        Clip clip = getClip(loadAudio("background"));
        clip.start();

    }

    public void playJump() {
        Clip clip = getClip(loadAudio("jump"));
        clip.start();

    }

    public void playCoin() {
        Clip clip = getClip(loadAudio("coin"));
        clip.start();

    }

    public void playFireball() {
        Clip clip = getClip(loadAudio("fireball"));
        clip.start();

    }

    public void playGameOver() {
        Clip clip = getClip(loadAudio("gameOver"));
        clip.start();

    }

    public void playStomp() {
        Clip clip = getClip(loadAudio("stomp"));
        clip.start();

    }

    public void playOneUp() {
        Clip clip = getClip(loadAudio("oneUp"));
        clip.start();

    }

    public void playSuperMushroom() {

        Clip clip = getClip(loadAudio("superMushroom"));
        clip.start();

    }

    public void playMarioDies() {

        Clip clip = getClip(loadAudio("marioDies"));
        clip.start();

    }
}
