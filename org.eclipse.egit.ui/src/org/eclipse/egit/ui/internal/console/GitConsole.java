package org.eclipse.egit.ui.internal.console;

import org.eclipse.ui.console.IOConsole;

/**
 *
 * @author Adrian Teng
 *
 */
public class GitConsole extends IOConsole {
	/**
	 * Type identifier
	 */
	public static final String GIT_CONSOLE_TYPE = "org.eclipse.egit.ui.internal.console.GitConsole"; //$NON-NLS-1$

	/**
	 * Construct Git Console
	 */
	public GitConsole() {
		// TODO: Refactor the strings out
		// TODO:
		super("Git", null, null); //$NON-NLS-1$
		this.setType(GIT_CONSOLE_TYPE);
	}


}
