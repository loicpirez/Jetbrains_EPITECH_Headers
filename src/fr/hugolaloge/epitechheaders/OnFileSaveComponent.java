package fr.hugolaloge.epitechheaders;

import com.intellij.AppTopics;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManagerAdapter;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

/**
 * Created by laloge_h on 18/01/16.
 */
public class OnFileSaveComponent implements ApplicationComponent {

  private Settings settings = ServiceManager.getService(Settings.class);

  public OnFileSaveComponent() {
  }

  @Override
  public void initComponent() {
    MessageBus bus = ApplicationManager.getApplication().getMessageBus();

    MessageBusConnection connection = bus.connect();

    connection.subscribe(AppTopics.FILE_DOCUMENT_SYNC,
            new FileDocumentManagerAdapter() {
              @Override
              public void beforeDocumentSaving(@NotNull Document document) {

              }
            });
  }

  @Override
  public void disposeComponent() {
    // TODO: insert component disposal logic here
  }

  @Override
  @NotNull
  public String getComponentName() {
    return "OnFileSaveComponent";
  }
}
