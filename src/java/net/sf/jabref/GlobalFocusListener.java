package net.sf.jabref;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.*;
import javax.swing.*;

public class GlobalFocusListener implements FocusListener {
  Component focused = null;

  public GlobalFocusListener() {
  }
  public void focusGained(FocusEvent e) {
    if (!e.isTemporary())
      focused = (Component)e.getSource();
  }
  public void focusLost(FocusEvent e) {
  }

  public JComponent getFocused() {
    return (JComponent)focused;
  }
}
