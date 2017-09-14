package fr.hugolaloge.epitechheaders;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by laloge_h on 10/01/16.
 *
 * Epitech Header creater and updater
 *
 * TODO Update on delete
 * TODO Better language recognisation
 * TODO Name configuration
 * TODO STD Comments configuration, add language, etc...
 */
public class MakeHeaders extends AnAction {

  private Settings settings = ServiceManager.getService(Settings.class);

  private static final Logger myLog = Logger.getInstance(MakeHeaders.class);
  protected static final SimpleDateFormat myFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", new Locale("en"));

  protected static final Map<String, String[] > myStdComments;
  static {
    myStdComments = new HashMap<String, String[] >();
    myStdComments.put("c", new String[] {"/*", "** ", "*/"});
    myStdComments.put("h", new String[] {"/*", "** ", "*/"});
    myStdComments.put("hh", new String[] {"//", "// ", "//"});
    myStdComments.put("cpp", new String[] {"//", "// ", "//"});
    myStdComments.put("hpp", new String[] {"//", "// ", "//"});
    myStdComments.put("py", new String[] {"##", "## ", "##"});
    myStdComments.put("UNKNOWN", new String[] {"", "", ""});
  }

  @Override
  public void update(final AnActionEvent e) {
    //Get required data keys
    final Project project = e.getData(CommonDataKeys.PROJECT);
    final Editor editor = e.getData(CommonDataKeys.EDITOR);
    //Set visibility only in case of existing project and editor
    e.getPresentation().setVisible((project != null && editor != null));
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    final Project project = e.getProject();
    if (project == null) {
      return;
    }
    Editor editor = e.getData(CommonDataKeys.EDITOR);
    if (editor == null) {
      return;
    }
    final VirtualFile vFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
    if (vFile == null)
      return;
    final String extension = vFile.getExtension();
    final Document document = editor.getDocument();

    CharSequence c = document.getCharsSequence();
    Pattern p = Pattern.compile(".. Last update .*");
    Matcher m = p.matcher(c);

    Runnable runnable;
    if (m.find()) {
      final int start = m.start(),
              end = m.end();

      runnable = new Runnable() {
        @Override
        public void run() {
          document.replaceString(start, end, getUpdateLineHeader(extension, settings));
        }
      };

    } else {
      final String header = generateHeader(extension, project, vFile, settings);
      runnable = new Runnable() {
        @Override
        public void run() {
          document.replaceString(0, 0, header);
        }
      };
    }
    //Making the replacement
    WriteCommandAction.runWriteCommandAction(project, runnable);
  }

  static public String generateHeader(String extension, Project project, VirtualFile file, Settings settings) {
    final Date date = new Date();
    final String formattedDate = myFormatter.format(date),
            userLogin = settings.login,
            userEmail = settings.email,
            userFullName = settings.fullName,
            fileName = file.getName(),
            projectName = project.getName(),
            filePath = project.getBasePath();
    final String[] stdComments = getStdComment(extension);

    return String.format(
            "%s\n" +
                    "%s%s for %s in %s\n" +
                    "%s\n" +
                    "%sMade by %s\n" +
                    "%sLogin   <%s>\n" +
                    "%s\n" +
                    "%sStarted on  %s %s\n" +
                    "%sLast update %s %s\n" +
                    "%s\n\n",
            stdComments[0],
            stdComments[1], fileName, projectName, filePath,
            stdComments[1],
            stdComments[1], userFullName,
            stdComments[1], userEmail,
            stdComments[1],
            stdComments[1], formattedDate, userFullName,
            stdComments[1], formattedDate, userFullName,
            stdComments[2]
    );
  }

  static public String getUpdateLineHeader(String extension, Settings settings) {
    Date date = new Date();
    final String[] stdComment = getStdComment(extension);
    final String userFullName = settings.fullName;

    return String.format("%sLast update %s %s",
            stdComment[1], myFormatter.format(date), userFullName);
  }

  static public String[] getStdComment(String extension) {
    if (myStdComments.containsKey(extension))
      return myStdComments.get(extension);
    else
      return myStdComments.get("UNKNOWN");
  }

}
