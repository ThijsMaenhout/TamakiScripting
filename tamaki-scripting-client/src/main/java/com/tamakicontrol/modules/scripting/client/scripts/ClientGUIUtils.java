package com.tamakicontrol.modules.scripting.client.scripts;

import com.tamakicontrol.modules.scripting.AbstractGUIUtils;
import com.tamakicontrol.modules.scripting.client.gui.tools.ImageChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ClientGUIUtils extends AbstractGUIUtils{

    @Override
    protected String openImageChooserImpl(ActionEvent event) {

        Component component = (Component) event.getSource();
        JFrame frame = (JFrame) SwingUtilities.getRoot(component);

        ImageChooser imageChooser = new ImageChooser(frame);
        return (imageChooser.choosePath(""));

    }
}
