package fr.hugolaloge.epitechheaders;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.util.xmlb.XmlSerializerUtil;

/**
 * Created by laloge_h on 14/01/16.
 */
@State(name = "EpitechHeadersSettings",
        storages = {@Storage(file = StoragePathMacros.APP_CONFIG + "/epitechheaders.xml")}
)
public class Settings implements PersistentStateComponent<Settings> {
  public String login = "login";
  public String email = "prenom.nom@epitech.eu";
  public String fullName = "Prenom Nom";
  public boolean updateOnSave = true;

  public Settings getState() {
    return this;
  }

  public void loadState(Settings state) {
    XmlSerializerUtil.copyBean(state, this);
  }
}
