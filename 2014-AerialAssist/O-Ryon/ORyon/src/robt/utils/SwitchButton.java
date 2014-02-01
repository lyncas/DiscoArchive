/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package robt.utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 *
 * @author Developer
 */
public class SwitchButton extends Button{
    DigitalInput input;

    public SwitchButton(DigitalInput in){
        this.input=in;
    }
    public boolean get() {
        return input.get();
    }
    
}
