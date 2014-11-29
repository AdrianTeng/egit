package org.eclipse.egit.ui.internal.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;

/**
 * Factory that create <code>GitConsole</code> for Console view's
 * "Open Console". See org.eclipse.ui.console.consoleFactory for the extension
 * point this factory register to.
 *
 * @author Adrian Teng
 */
public class GitConsoleFactory implements IConsoleFactory {

	private static GitConsole console;

	public void openConsole() {
		GitConsoleFactory.showConsole();
	}

	/**
	 * Create a singleton <code>GitConsole</code> if it is not already
	 * instantiate, or get the existing one if it does
	 *
	 * @return GitConsole
	 */
	public synchronized static GitConsole getConsole() {
		if (GitConsoleFactory.console == null) {
			GitConsoleFactory.console = new GitConsole();
		}
		return GitConsoleFactory.console;
	}

	/**
	 *
	 */
	public synchronized static void destroyConsole() {
    	if (GitConsoleFactory.console != null) {
			ConsolePlugin
					.getDefault()
					.getConsoleManager()
					.removeConsoles(
							new IConsole[] { GitConsoleFactory.console });
    		GitConsoleFactory.console = null;
    	}
    }

	/**
	 *
	 */
	public static void showConsole() {
		GitConsole console = GitConsoleFactory.getConsole();
		IConsoleManager manager = ConsolePlugin.getDefault()
				.getConsoleManager();
		IConsole[] existing = manager.getConsoles();
		boolean exists = false;
		for (int i = 0; i < existing.length; i++) {
			if (console == existing[i]) {
				exists = true;
			}
		}
		if (!exists) {
			manager.addConsoles(new IConsole[] {console});
		}
		manager.showConsoleView(console);
	}

}
