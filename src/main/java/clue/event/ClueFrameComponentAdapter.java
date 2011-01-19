package clue.event;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import clue.gui.ClueFrame;
import clue.util.Prefs;

public class ClueFrameComponentAdapter implements ComponentListener {

  public ClueFrameComponentAdapter(ClueFrame clueFrame) {
    clueFrame.addComponentListener(this);
    clueFrame.setSize(Prefs.userNode(ClueFrame.class).getInt("width", 1024), Prefs.userNode(ClueFrame.class).getInt("height", 600));
    clueFrame.setLocation(Prefs.userNode(ClueFrame.class).getInt("x", 10), Prefs.userNode(ClueFrame.class).getInt("y", 10));
  }

  @Override
  public void componentResized(ComponentEvent e) {
    Prefs.userNode(ClueFrame.class).putInt("width", e.getComponent().getWidth());
    Prefs.userNode(ClueFrame.class).putInt("height", e.getComponent().getHeight());
  }

  @Override
  public void componentMoved(ComponentEvent e) {
    Prefs.userNode(ClueFrame.class).putInt("x", e.getComponent().getX());
    Prefs.userNode(ClueFrame.class).putInt("y", e.getComponent().getY());
  }

  @Override
  public void componentShown(ComponentEvent e) {
  }

  @Override
  public void componentHidden(ComponentEvent e) {
  }
}
