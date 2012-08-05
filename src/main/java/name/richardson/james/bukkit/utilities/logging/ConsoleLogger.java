package name.richardson.james.bukkit.utilities.logging;

import java.text.MessageFormat;
import java.util.logging.Level;

public final class ConsoleLogger extends AbstractLogger {

  private final java.util.logging.Logger logger;

  public ConsoleLogger(Object owner) {
    this.logger = java.util.logging.Logger.getLogger(owner.getClass().getName());
    this.logger.setLevel(Logger.DEFAULT_LEVEL);
  }
  
  public ConsoleLogger(java.util.logging.Logger logger) {
    this.logger = logger;
    this.logger.setLevel(Logger.DEFAULT_LEVEL);
  }

  public void config(Object object, String message, Object... elements) {
    if (!this.logger.isLoggable(Level.CONFIG)) return;
    final String formattedMessage = MessageFormat.format(message, elements);
    this.logger.config(formattedMessage);
  }

  public void debug(Object object, String message, Object... elements) {
    if (!this.logger.isLoggable(Level.ALL)) return;
    message = "<" + object.getClass().getName() + "> " + message;
    String formattedMessage = MessageFormat.format(message, elements);
    this.logger.config(formattedMessage);
  }

  public void info(Object object, String message, Object... elements) {
    if (!this.logger.isLoggable(Level.INFO)) return;
    String formattedMessage = MessageFormat.format(message, elements);
    this.logger.config(formattedMessage);
  }

  public boolean isDebugging() {
    return this.logger.isLoggable(Logger.DEBUG_LEVEL);
  }

  public void setDebugging(boolean debugging) {
    if (debugging) {
      this.logger.setLevel(Logger.DEBUG_LEVEL);
    } else {
      this.logger.setLevel(Logger.DEFAULT_LEVEL);
    }
  }
  
  public void severe(Object object, String message, Object... elements) {
    if (!this.logger.isLoggable(Level.SEVERE)) return;
    String formattedMessage = MessageFormat.format(message, elements);
    this.logger.config(formattedMessage);
  }

  public void warning(Object object, String message, Object... elements) {
    if (!this.logger.isLoggable(Level.WARNING)) return;
    String formattedMessage = MessageFormat.format(message, elements);
    this.logger.config(formattedMessage);
  }

  public String getName() {
    return this.logger.getName();
  }
  
}
