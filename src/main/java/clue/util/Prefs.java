package clue.util;

import java.util.prefs.Preferences;

public class Prefs {

  public static Preferences userNode(Class<?> clazz) {
    String pathName = String.format("com.gws.%s", clazz == null ? "NULL" : clazz.getCanonicalName());
    pathName = pathName.replace('.', '/');
    return Preferences.userRoot().node(pathName);
  }
}
