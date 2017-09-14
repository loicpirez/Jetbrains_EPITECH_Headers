package fr.hugolaloge.epitechheaders;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBTextField;
import com.sun.istack.internal.Nullable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by laloge_h on 14/01/16.
 *
 */
public class Configuration implements Configurable {

  private static final String TEXT_DISPLAY_NAME = "Epitech Headers";

  private static final String TEXT_HELP_TOPIC = "Configuration pour les headers epitech";

  private static final String TEXT_TITLE_USERNAME = "User name and login";

  private static final String TEXT_LOGIN = "Login";

  private static final String TEXT_EMAIL = "Email";

  private static final String TEXT_FULLNAME = "Full name";

  private static final String TEXT_UPDATE_ON_SAVE = "Update on save";

  private Settings settings = ServiceManager.getService(Settings.class);

  //private JTextField login;

  private JTextField email;

  private JTextField fullName;

  private JCheckBox updateOnSave;

  @Nullable
  @Override
  public JComponent createComponent()
  {
    JPanel panel = initComponent();
    return panel;
  }

  @Override
  public boolean isModified() {
    return !settings.email.equals(email.getText())
            || !settings.fullName.equals(fullName.getText())
            || settings.updateOnSave != updateOnSave.isSelected();
  }

  @Override
  public void apply() throws ConfigurationException {
    //settings.login = login.getText();
    settings.email = email.getText();
    settings.fullName = fullName.getText();
    settings.updateOnSave = updateOnSave.isSelected();
  }

  @Override
  public void reset() {
    //login.setText(settings.login);
    email.setText(settings.email);
    fullName.setText(settings.fullName);
    updateOnSave.setSelected(settings.updateOnSave);
  }

  @Override
  public void disposeUIResources() {
    //login = null;
    email = null;
    fullName = null;
  }

  @Override
  public String getDisplayName() {
    return TEXT_DISPLAY_NAME;
  }

  public String getHelpTopic() {
    return TEXT_HELP_TOPIC;
  }

  private JPanel initComponent() {
    JPanel panel = new JPanel();
    panel.setBorder(IdeBorderFactory.createTitledBorder(TEXT_TITLE_USERNAME));
    panel.setLayout(new GridLayout());
    //panel.add(login = new JTextField(TEXT_LOGIN));
    panel.add(email = new JTextField(TEXT_EMAIL));
    panel.add(fullName = new JTextField(TEXT_FULLNAME));
    panel.add(updateOnSave = new JCheckBox(TEXT_UPDATE_ON_SAVE));
    //panel.add(Box.createHorizontalGlue());
    //panel.setMinimumSize(new Dimension(Short.MAX_VALUE, 0));
    return panel;
  }

}
