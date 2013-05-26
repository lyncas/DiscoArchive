package org.discobots.smartdashboard.extension.divider;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class DividerExtension extends StaticWidget
{
  public void init()
  {
    setPreferredSize(new Dimension(64, 200));
  }

  protected void paintComponent(Graphics g)
  {
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getSize().width, getSize().height);
  }

  public void propertyChanged(Property prprt)
  {
  }
}