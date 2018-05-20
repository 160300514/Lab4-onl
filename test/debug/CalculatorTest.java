package debug;

import debug.calculator.CalculatorGUI;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class CalculatorTest
{
    @Test
    public void calTest() throws AWTException {
        Robot rb = new Robot();
        CalculatorGUI cg = new CalculatorGUI();
        cg.clear();
        JButton jb1 = new JButton();
        jb1.setText("1");
        JButton jb2 = new JButton();
        jb2.setText("2");
        JButton jb3 = new JButton();
        jb3.setText("3");
        JButton jb4 = new JButton();
        jb4.setText("4");
        JButton jb5 = new JButton();
        jb5.setText("5");
        JButton jb6 = new JButton();
        jb6.setText("6");
        JButton jb7 = new JButton();
        jb7.setText("7");
        JButton jb8 = new JButton();
        jb8.setText("8");
        JButton jb9 = new JButton();
        jb9.setText("9");
        JButton jb0 = new JButton();
        jb0.setText("0");
        JButton jbdot = new JButton();
        jbdot.setText(".");

        cg.processDigitInput(jb1);
        cg.processDigitInput(jb2);
        cg.processDigitInput(jbdot);
        cg.processDigitInput(jb6);
        cg.setOperation("x");
        cg.performOperation();
        cg.processDigitInput(jb9);
        cg.processDigitInput(jb3);
        cg.setOperation("=");
        cg.performOperation();
        System.out.println(cg.getResult());
    }
}
