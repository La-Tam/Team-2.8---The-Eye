/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vanlanguni.ponggame;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author OS
 */
public class Sound{

    private Clip clip;

    public Sound(String fileName) {
        try {
        	   clip = AudioSystem.getClip();
               AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileName));
               clip.open(inputStream);
        } catch (Exception e) {
        }
    }

    public void playSound() {

        clip.setFramePosition(0);
        clip.start();
    }

    /*public void playLoop() {

        clip.setFramePosition(0);
        clip.loop(1000000000);
    }*/

    public void stopSound() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }

}
